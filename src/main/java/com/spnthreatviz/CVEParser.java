package com.spnthreatviz;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.mysql.cj.xdevapi.JsonNumber;
import com.mysql.cj.xdevapi.JsonString;

/*
 * This class is based on https://stackoverflow.com/questions/11428329/
 * Refer to https://stackoverflow.com/questions/23070298 for custom deserialization (originally planned, but CVE data is too nested for this to be a functional option)
 */

/** 
 * The CVEParser class parses the NVD CVE files into POJOs and should be utilized when setting up the database.
 */
public class CVEParser {

    /**
     * GSON object for parsing primitives.
     */
    private Gson gson;

    /**
     * Constructor for a CVEParser. Does nothing.
     */
    public CVEParser() {
         gson = new Gson();
    }

    /**
     * Isolates the CVE_Items array from input JsonElement for later parsing.
     * @param input the JSON string to use as input.
     * @exception STVException.CVEParsingException on missing CVE_Items field in provided JSON input.
     */
    public JsonArray getCVEItemsAsArray(String input) throws STVException.CVEParsingException {
        JsonParser parser = new JsonParser();
        JsonObject cveRootObj = parser.parse(input).getAsJsonObject(); //Turn input into a JsonObject
        JsonElement parsedCVEItems = cveRootObj.get("CVE_Items");
        if (parsedCVEItems == null) {
            throw new STVException.CVEParsingException("getCVEItems: input missing CVE_Items field.", new Throwable());
        }
        return parsedCVEItems.getAsJsonArray();
    }

    /**
     * Iterates over the elements of the CVE_Items JsonArray and creates CVEObjects to be added to the database.
     * @param cveItems the JsonArray of cveItems to be parsed and added to the database.
     * @exception STVException.CVEParsingException on missing fields in provided JSON input.
     */
    public void loadCVEItemsToObjects(JsonArray cveItems) throws STVException.CVEParsingException  {
        for (JsonElement cveElement : cveItems) {
            JsonObject cve = cveElement.getAsJsonObject(); //convert array element to JsonObject
            try {
                CVEObject cveObj = parseCVE(cve); //parse to a POJO
                //System.out.println(cveObj); //Debug Print Statement
                //Handle Database entry
            } catch (STVException.CVEParsingException e) {
                //Note that there was an error when parsing, but continue onwards with the rest of the entries.
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Handles parsing the important bits of a JsonObject into a meaningful CVEObject.
     * @param cveItem the JsonObject to parse and add to a CVEObject instance.
     * @return New CVEObject containing data from the input.
     * @exception STVException.CVEParsingException on missing fields in provided JSON input.
     */
    public CVEObject parseCVE(JsonObject cveItem) throws STVException.CVEParsingException  {
        CVEObject toReturn = new CVEObject();

        //One by one, obtain the important fields.
        //To avoid exceptions, unwrap one layer at a time.

        //Unwrap main CVE component
        JsonElement cve_main_e = cveItem.get("cve");
        if (cve_main_e == null) { throw new STVException.CVEParsingException("parseCVE: input missing cve field.", new Throwable()); }
        JsonObject cve_main = cve_main_e.getAsJsonObject();

        //Vendor, product name, and version data
        JsonElement layer_affects_e = cve_main.get("affects");
        if (layer_affects_e == null) { throw new STVException.CVEParsingException("parseCVE: input missing affects field.", new Throwable()); }
        JsonObject layer_affects = layer_affects_e.getAsJsonObject();

        JsonElement layer_vendor_e = layer_affects.get("vendor");
        if (layer_vendor_e == null) { throw new STVException.CVEParsingException("parseCVE: input missing vendor field.", new Throwable()); }
        JsonObject layer_vendor = layer_vendor_e.getAsJsonObject();

        JsonArray layer_vendordata = layer_vendor.get("vendor_data").getAsJsonArray();

        //If vendor_data is empty for whatever reason, leave the fields as empty strings.
        //Do not throw an exception if it is empty, as there may be a use for it being empty.
        if (layer_vendordata.size() > 0) {
            //We will only ever choose the first vendor in the array for simplicity.
            JsonObject layer_vendorobj = layer_vendordata.get(0).getAsJsonObject();
            String vendor_name = layer_vendorobj.get("vendor_name").getAsString();
            toReturn.setVendor(vendor_name);

            JsonElement layer_product_e = layer_vendorobj.get("product");
            //Only set product name and version values if the product field exists.
            //However, do not throw an exception if it is null, as that's still technically 'valid'
            if (layer_product_e != null) {
                JsonObject layer_product = layer_product_e.getAsJsonObject();

                JsonArray layer_productdata = layer_product.get("product_data").getAsJsonArray();

                //Like with vendor_data, we will only choose the first product in the array for simplicity, and will leave the fields as empty strings if it is empty.
                if (layer_productdata.size() > 0) {
                    JsonObject layer_productobj = layer_productdata.get(0).getAsJsonObject();
                    String product_name = layer_productobj.get("product_name").getAsString();
                    toReturn.setProductName(product_name);

                    //Handle version object.
                    JsonElement layer_version_e = layer_productobj.get("version");
                    if (layer_version_e != null) {
                        JsonObject layer_version = layer_version_e.getAsJsonObject();

                        JsonArray version_data = layer_version.get("version_data").getAsJsonArray();
                        String[] version_values = new String[version_data.size()];
                        //Iterate over version_data and insert values into array.
                        for (int i = 0; i < version_data.size(); i += 1) {
                            JsonObject version_value = version_data.get(i).getAsJsonObject();
                            version_values[i] = version_value.get("version_value").getAsString();
                        }
                        toReturn.setVersionValues(version_values);
                    }
                }
            }
        }

        //Next we handle the description.
        JsonElement layer_description_e = cve_main.get("description");
        if (layer_description_e != null) {
            JsonObject layer_description = layer_description_e.getAsJsonObject();
            //If it does not exist, just leave description blank.
            JsonArray layer_descriptiondata = layer_description.get("description_data").getAsJsonArray();
            //We will only use the first element.
            if (layer_descriptiondata.size() > 0) {
                JsonObject descriptionobj = layer_descriptiondata.get(0).getAsJsonObject();
                String description = descriptionobj.get("value").getAsString();
                toReturn.setDescription(description);
            }
        }

        //Next we handle everything related to the impact of the vulnerability
        JsonElement cve_impact_e = cveItem.get("impact");
        if (cve_impact_e == null) { throw new STVException.CVEParsingException("parseCVE: input missing impact field.", new Throwable()); }
        JsonObject cve_impact = cve_impact_e.getAsJsonObject();
        //If no baseMetric V3 exists, then leave default values
        //TODO: Potentially utilize V2, though not all fields match up perfectly
        JsonElement layer_baseMetricV3_e = cve_impact.get("baseMetricV3");
        if (layer_baseMetricV3_e != null) {
            JsonObject layer_baseMetricV3 = layer_baseMetricV3_e.getAsJsonObject();

            JsonObject layer_cvssv3 = layer_baseMetricV3.get("cvssV3").getAsJsonObject();
            //Extract fields
            String attackVector = layer_cvssv3.get("attackVector").getAsString();
            String attackComplexity = layer_cvssv3.get("attackComplexity").getAsString();
            String privilegesRequired = layer_cvssv3.get("privilegesRequired").getAsString();
            String userInteraction = layer_cvssv3.get("userInteraction").getAsString();
            String confidentialityImpact = layer_cvssv3.get("confidentialityImpact").getAsString();
            String integrityImpact = layer_cvssv3.get("integrityImpact").getAsString();
            String availabilityImpact = layer_cvssv3.get("availabilityImpact").getAsString();
            Number baseScore = layer_cvssv3.get("baseScore").getAsNumber();
            String baseSeverity = layer_cvssv3.get("baseSeverity").getAsString();

            toReturn.setAttackVector(attackVector);
            toReturn.setAttackComplexity(attackComplexity);
            toReturn.setPrivilegesRequired(privilegesRequired);
            toReturn.setUserInteraction(userInteraction);
            toReturn.setConfidentialityImpact(confidentialityImpact);
            toReturn.setIntegrityImpact(integrityImpact);
            toReturn.setAvailabilityImpact(availabilityImpact);
            toReturn.setBaseScore(baseScore.floatValue());
            toReturn.setBaseSeverity(baseSeverity);

            Number exploitabilityScore = layer_baseMetricV3.get("exploitabilityScore").getAsNumber();
            Number impactScore = layer_baseMetricV3.get("impactScore").getAsNumber();

            toReturn.setExploitabilityScore(exploitabilityScore.floatValue());
            toReturn.setImpactScore(impactScore.floatValue());
        } else {
            //V2 options
        }

        //Finally we handle the dates
        String pubdate_raw = cveItem.get("publishedDate").getAsString();
        String moddate_raw = cveItem.get("lastModifiedDate").getAsString();
        //Substrings utilized for storage (YYYY-MM-DD)
        if (pubdate_raw != null) {
            toReturn.setPublishDate(pubdate_raw.substring(0, 10));
        }
        if (moddate_raw != null) {
            toReturn.setLastModifiedDate(moddate_raw.substring(0, 10));
        }

        return toReturn;
    }

}
