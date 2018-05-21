package com.spnthreatviz;

import java.util.ArrayList;

/**
 * A class representing a single CVE Object, holding all information important for ThreatViz.
 */
public class CVEObject {

    /**
     * Vendor for the product with the vulnerability.
     */
    private String vendor;

    /**
     * Product with the vulnerability.
     */
    private String productName;

    /**
     * Versions of product that contain vulnerability.
     */
    private String[] versionValues;

    /**
     * Description of vulnerability.
     */
    private String description;

    /**
     * Attack vector for the vulnerability (baseMetricV3).
     */
    private String attackVector;

    /**
     * Attack complexity for the vulnerability (baseMetricV3).
     */
    private String attackComplexity;

    /**
     * Required privileges required to abuse vulnerability (baseMetricV3).
     */
    private String privilegesRequired;

    /**
     * What type of user interaction is needed to abuse vulnerability (baseMetricV3).
     */
    private String userInteraction;

    /**
     * Confidentiality impact of the vulnerability (baseMetricV3).
     */
    private String confidentialityImpact;

    /**
     * Integrity impact of the vulnerability (baseMetricV3).
     */
    private String integrityImpact;

    /**
     * Availability impact of the vulnerability (baseMetricV3).
     */
    private String availabilityImpact;

    /**
     * Base score of the vulnerability (baseMetricV3).
     */
    private float baseScore;

    /**
     * Base severity of the vulnerability (baseMetricV3).
     */
    private String baseSeverity;

    /**
     * Exploitability score of the vulnerability (baseMetricV3).
     */
    private float exploitabilityScore;

    /**
     * Impact score of the vulnerability (baseMetricV3).
     */
    private float impactScore;

    /**
     * Publishing date of the vulnerability (YYYY-MM-DD).
     */
    private String publishDate;

    /**
     * Last modification date of the vulnerability (YYYY-MM-DD).
     */
    private String lastModifiedDate;


    /**
     * Default constructor for the CVEObject class, used when creating an empty instance to be populated with data.
     */
    public CVEObject() {
        this.vendor = "";
        this.productName = "";
        this.versionValues = new String[0];
        this.description = "";
        this.attackVector = "";
        this.attackComplexity = "";
        this.privilegesRequired = "";
        this.userInteraction = "";
        this.confidentialityImpact = "";
        this.integrityImpact = "";
        this.availabilityImpact = "";
        this.baseScore = -1f;
        this.baseSeverity = "";
        this.exploitabilityScore = -1f;
        this.impactScore = -1f;
        this.publishDate = "0000-00-00";
        this.lastModifiedDate = "0000-00-00";
    }

    /**
     * Default constructor for the CVEObject class, used with data provided from JSON.
     * @param vendor vendor for the product with the vulnerability.
     * @param productName product with the vulnerability.
     * @param versionValues versions of product that contain vulnerability.
     * @param description description of vulnerability.
     * @param attackVector attack vector for the vulnerability (baseMetricV3).
     * @param attackComplexity attack complexity for the vulnerability (baseMetricV3).
     * @param privilegesRequired Required privileges required to abuse vulnerability (baseMetricV3).
     * @param userInteraction What type of user interaction is needed to abuse vulnerability (baseMetricV3).
     * @param confidentialityImpact confidentiality impact of the vulnerability (baseMetricV3).
     * @param integrityImpact integrity impact of the vulnerability (baseMetricV3).
     * @param availabilityImpact availability impact of the vulnerability (baseMetricV3).
     * @param baseScore base score of the vulnerability (baseMetricV3).
     * @param baseSeverity base severity of the vulnerability (baseMetricV3).
     * @param exploitabilityScore exploitability score of the vulnerability (baseMetricV3).
     * @param impactScore impact score of the vulnerability (baseMetricV3).
     * @param publishDate publishing date of the vulnerability (YYYY-MM-DD).
     * @param lastModifiedDate last modification date of the vulnerability (YYYY-MM-DD).
     */
    public CVEObject(String vendor, String productName, String[] versionValues, String description,
                     String attackVector, String attackComplexity, String privilegesRequired, String userInteraction,
                     String confidentialityImpact, String integrityImpact, String availabilityImpact,
                     float baseScore, String baseSeverity, float exploitabilityScore, float impactScore,
                     String publishDate, String lastModifiedDate) {
        this.vendor = vendor;
        this.productName = productName;
        this.versionValues = versionValues;
        this.description = description;
        this.attackVector = attackVector;
        this.attackComplexity = attackComplexity;
        this.privilegesRequired = privilegesRequired;
        this.userInteraction = userInteraction;
        this.confidentialityImpact = confidentialityImpact;
        this.integrityImpact = integrityImpact;
        this.availabilityImpact = availabilityImpact;
        this.baseScore = baseScore;
        this.baseSeverity = baseSeverity;
        this.exploitabilityScore = exploitabilityScore;
        this.impactScore = impactScore;
        this.publishDate = publishDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Gets the vendor for this vulnerability.
     * @return vendor for this vulnerability.
     */
    public String getVendor() {
        return this.vendor;
    }

    /**
     * Gets the product name for this vulnerability.
     * @return product name for this vulnerability.
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * Gets the version values for this vulnerability.
     * @return version values for this vulnerability.
     */
    public String [] getVersionValues() {
        return this.versionValues;
    }

    /**
     * Gets the description for this vulnerability.
     * @return description for this vulnerability.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the attack vector for this vulnerability.
     * @return attack vector for this vulnerability.
     */
    public String getAttackVector() {
        return this.attackVector;
    }

    /**
     * Gets the attack complexity for this vulnerability.
     * @return attack complexity for this vulnerability.
     */
    public String getAttackComplexity() {
        return this.attackComplexity;
    }

    /**
     * Gets the required privileges for this vulnerability.
     * @return required privileges for this vulnerability.
     */
    public String getPrivilegesRequired() {
        return this.privilegesRequired;
    }

    /**
     * Gets the required user interaction for this vulnerability.
     * @return required user interaction for this vulnerability.
     */
    public String getUserInteraction() {
        return this.userInteraction;
    }

    /**
     * Gets the confidentiality impact for this vulnerability.
     * @return confidentiality impact for this vulnerability.
     */
    public String getConfidentialityImpact() {
        return this.confidentialityImpact;
    }

    /**
     * Gets the integrity impact for this vulnerability.
     * @return integrity impact for this vulnerability.
     */
    public String getIntegrityImpact() {
        return this.integrityImpact;
    }

    /**
     * Gets the availability impact for this vulnerability.
     * @return availability impact for this vulnerability.
     */
    public String getAvailabilityImpact() {
        return this.availabilityImpact;
    }

    /**
     * Gets the base score for this vulnerability.
     * @return base score for this vulnerability.
     */
    public float getBaseScore() {
        return this.baseScore;
    }

    /**
     * Gets the base severity for this vulnerability.
     * @return base severity for this vulnerability.
     */
    public String getBaseSeverity() {
        return this.baseSeverity;
    }

    /**
     * Gets the exploitability score for this vulnerability.
     * @return exploitability score for this vulnerability.
     */
    public float getExploitabilityScore() {
        return this.exploitabilityScore;
    }

    /**
     * Gets the impact score for this vulnerability.
     * @return impact score for this vulnerability.
     */
    public float getImpactScore() {
        return this.impactScore;
    }

    /**
     * Gets the publish date for this vulnerability.
     * @return publish date for this vulnerability.
     */
    public String getPublishDate() {
        return this.publishDate;
    }

    /**
     * Gets the last modification date for this vulnerability.
     * @return last modification date for this vulnerability.
     */
    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    /**
     * Sets the vendor for this vulnerability.
     * @param vendor the vendor for this vulnerability.
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * Sets the product name for this vulnerability.
     * @param productName the product name for this vulnerability.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Sets the version values for this vulnerability.
     * @param versionValues the version values for this vulnerability.
     */
    public void setVersionValues(String[] versionValues) {
        this.versionValues = versionValues;
    }

    /**
     * Sets the description for this vulnerability.
     * @param description the description for this vulnerability.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the attack vector for this vulnerability.
     * @param attackVector the attack vector for this vulnerability.
     */
    public void setAttackVector(String attackVector) {
        this.attackVector = attackVector;
    }

    /**
     * Sets the attack complexity for this vulnerability.
     * @param attackComplexity the attack complexity for this vulnerability.
     */
    public void setAttackComplexity(String attackComplexity) {
        this.attackComplexity = attackComplexity;
    }

    /**
     * Sets the required privileges for this vulnerability.
     * @param privilegesRequired the required privileges for this vulnerability.
     */
    public void setPrivilegesRequired(String privilegesRequired) {
        this.privilegesRequired = privilegesRequired;
    }

    /**
     * Sets the required user interaction for this vulnerability.
     * @param userInteraction the required user interaction for this vulnerability.
     */
    public void setUserInteraction(String userInteraction) {
        this.userInteraction = userInteraction;
    }

    /**
     * Sets the confidentiality impact for this vulnerability.
     * @param confidentialityImpact the confidentiality impact for this vulnerability.
     */
    public void setConfidentialityImpact(String confidentialityImpact) {
        this.confidentialityImpact = confidentialityImpact;
    }

    /**
     * Sets the integrity impact for this vulnerability.
     * @param integrityImpact the integrity impact for this vulnerability.
     */
    public void setIntegrityImpact(String integrityImpact) {
        this.integrityImpact = integrityImpact;
    }

    /**
     * Sets the availability impact for this vulnerability.
     * @param availabilityImpact the availability impact for this vulnerability.
     */
    public void setAvailabilityImpact(String availabilityImpact) {
        this.availabilityImpact = availabilityImpact;
    }

    /**
     * Sets the base score for this vulnerability.
     * @param baseScore the base score for this vulnerability.
     */
    public void setBaseScore(float baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * Sets the base severity for this vulnerability.
     * @param baseSeverity the base severity for this vulnerability.
     */
    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

    /**
     * Sets the exploitability score for this vulnerability.
     * @param exploitabilityScore the exploitability score for this vulnerability.
     */
    public void setExploitabilityScore(float exploitabilityScore) {
        this.exploitabilityScore = exploitabilityScore;
    }

    /**
     * Sets the impact score for this vulnerability.
     * @param impactScore the impact score for this vulnerability.
     */
    public void setImpactScore(float impactScore) {
        this.impactScore = impactScore;
    }

    /**
     * Sets the publish date for this vulnerability.
     * @param publishDate the publish date for this vulnerability.
     */
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Sets the last modification date for this vulnerability.
     * @param lastModifiedDate the last modification date for this vulnerability.
     */
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return this.vendor.concat(": ").concat(this.productName).concat(" - ").concat(this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }
        //Equality testing on fields
        CVEObject obj = (CVEObject) o;
        return this.vendor.equals(obj.vendor) && this.productName.equals(obj.productName); //.equals on important fields
    }

    @Override
    public int hashCode() {
        return 11 * this.vendor.hashCode() * this.productName.hashCode();
    }
}
