package com.spnthreatviz;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 * Test suite for CVEParser class.
 */
public class TestCVEParser {

    //------------------------------------------------------------------------//
    // Setup
    //------------------------------------------------------------------------//

    private CVEParser cvep;
    private String JSONExample1_Good;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        cvep = new CVEParser();
        //Below is directly taken from the 2018 dataset (some parts abbreviated for clarity)
        JSONExample1_Good = "{\"cve\" : {\"data_type\" : \"CVE\", \"data_format\" : \"MITRE\", \"data_version\" : \"4.0\", \"CVE_data_meta\" : {\"ID\" : \"CVE-2018-2753\", \"ASSIGNER\" : \"cve@mitre.org\"}, \"affects\" : {\"vendor\" : {\"vendor_data\" : [{\"vendor_name\" : \"oracle\", \"product\" : {\"product_data\" : [{\"product_name\" : \"solaris\", \"version\" : {\"version_data\" : [{\"version_value\" : \"11.3\"}]}}]}}]}}, \"problemtype\" : {\"problemtype_data\" : [{\"description\" : [{\"lang\" : \"en\", \"value\" : \"CWE-284\"}]}]}, \"references\" : {\"reference_data\" : [{\"url\" : \"http://www.oracle.com/technetwork/security-advisory/cpuapr2018-3678067.html\", \"name\" : \"http://www.oracle.com/technetwork/security-advisory/cpuapr2018-3678067.html\", \"refsource\" : \"CONFIRM\"}, {\"url\" : \"http://www.securityfocus.com/bid/103879\", \"name\" : \"103879\", \"refsource\" : \"BID\"}, {\"url\" : \"http://www.securitytracker.com/id/1040702\", \"name\" : \"1040702\", \"refsource\" : \"SECTRACK\"}]}, \"description\" : {\"description_data\" : [{\"lang\" : \"en\", \"value\" : \"Abbreviated Description\"}]}}, \"impact\" : {\"baseMetricV3\" : {\"cvssV3\" : {\"version\" : \"3.0\", \"vectorString\" : \"CVSS:3.0/AV:L/AC:H/PR:L/UI:R/S:U/C:H/I:H/A:N\", \"attackVector\" : \"LOCAL\", \"attackComplexity\" : \"HIGH\", \"privilegesRequired\" : \"LOW\", \"userInteraction\" : \"REQUIRED\", \"scope\" : \"UNCHANGED\", \"confidentialityImpact\" : \"HIGH\", \"integrityImpact\" : \"HIGH\", \"availabilityImpact\" : \"NONE\", \"baseScore\" : 6.0, \"baseSeverity\" : \"MEDIUM\"}, \"exploitabilityScore\" : 0.8, \"impactScore\" : 5.2}, \"baseMetricV2\" : {\"cvssV2\" : {\"version\" : \"2.0\", \"vectorString\" : \"(AV:L/AC:H/Au:N/C:P/I:P/A:N)\", \"accessVector\" : \"LOCAL\", \"accessComplexity\" : \"HIGH\", \"authentication\" : \"NONE\", \"confidentialityImpact\" : \"PARTIAL\", \"integrityImpact\" : \"PARTIAL\", \"availabilityImpact\" : \"NONE\", \"baseScore\" : 2.6}, \"severity\" : \"LOW\", \"exploitabilityScore\" : 1.9, \"impactScore\" : 4.9, \"obtainAllPrivilege\" : false, \"obtainUserPrivilege\" : false, \"obtainOtherPrivilege\" : false, \"userInteractionRequired\" : true}}, \"publishedDate\" : \"2018-04-19T02:29Z\", \"lastModifiedDate\" : \"2018-04-25T16:25Z\"}";
    }

    //------------------------------------------------------------------------//
    // Tests
    //------------------------------------------------------------------------//

    @Test
    public void testGetCVEItems_PEX1() throws STVException.CVEParsingException { //tests getCVEItems
        thrown.expect(STVException.CVEParsingException.class);
        String sample1 = "{}";
        cvep.getCVEItems(sample1);
    }

    @Test
    public void testParseCVEGood1() throws STVException.CVEParsingException { //tests parseCVE with proper data
        JsonParser parser = new JsonParser();
        JsonObject cvejson1 = parser.parse(JSONExample1_Good).getAsJsonObject(); //Turn input into a JsonObject
        CVEObject u1 = cvep.parseCVE(cvejson1);

        //Ensure all the fields in o1 are as they should be
        assertTrue(u1.getVendor().equals("oracle"));
        assertTrue(u1.getProductName().equals("solaris"));
        assertTrue(u1.getDescription().equals("Abbreviated Description"));
        assertTrue(u1.getAttackVector().equals("LOCAL"));
        assertTrue(u1.getAttackComplexity().equals("HIGH"));
        assertTrue(u1.getPrivilegesRequired().equals("LOW"));
        assertTrue(u1.getUserInteraction().equals("REQUIRED"));
        assertTrue(u1.getConfidentialityImpact().equals("HIGH"));
        assertTrue(u1.getIntegrityImpact().equals("HIGH"));
        assertTrue(u1.getAvailabilityImpact().equals("NONE"));
        assertTrue(u1.getBaseScore() == 6.0f);
        assertTrue(u1.getBaseSeverity().equals("MEDIUM"));
        assertTrue(u1.getExploitabilityScore() == 0.8f);
        assertTrue(u1.getImpactScore() == 5.2f);
        assertTrue(u1.getPublishDate().equals("2018-04-19"));
        assertTrue(u1.getLastModifiedDate().equals("2018-04-25"));
        //Version data testing
        String[] versiondatau1 = u1.getVersionValues();
        assertTrue(versiondatau1.length == 1);
        assertTrue(versiondatau1[0].equals("11.3"));
    }
}