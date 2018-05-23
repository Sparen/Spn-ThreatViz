package com.spnthreatviz;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

/**
 * Integration Test suite for STVService.
 */
public class TestSTVService {

    //------------------------------------------------------------------------//
    // Setup
    //------------------------------------------------------------------------//

    //Below is directly taken from the 2018 dataset (some parts abbreviated for clarity)
    private String JSONExample1_Good = "{\"cve\" : {\"data_type\" : \"CVE\", \"data_format\" : \"MITRE\", \"data_version\" : \"4.0\", \"CVE_data_meta\" : {\"ID\" : \"CVE-2018-2753\", \"ASSIGNER\" : \"cve@mitre.org\"}, \"affects\" : {\"vendor\" : {\"vendor_data\" : [{\"vendor_name\" : \"oracle\", \"product\" : {\"product_data\" : [{\"product_name\" : \"solaris\", \"version\" : {\"version_data\" : [{\"version_value\" : \"11.3\"}]}}]}}]}}, \"problemtype\" : {\"problemtype_data\" : [{\"description\" : [{\"lang\" : \"en\", \"value\" : \"CWE-284\"}]}]}, \"references\" : {\"reference_data\" : [{\"url\" : \"http://www.oracle.com/technetwork/security-advisory/cpuapr2018-3678067.html\", \"name\" : \"http://www.oracle.com/technetwork/security-advisory/cpuapr2018-3678067.html\", \"refsource\" : \"CONFIRM\"}, {\"url\" : \"http://www.securityfocus.com/bid/103879\", \"name\" : \"103879\", \"refsource\" : \"BID\"}, {\"url\" : \"http://www.securitytracker.com/id/1040702\", \"name\" : \"1040702\", \"refsource\" : \"SECTRACK\"}]}, \"description\" : {\"description_data\" : [{\"lang\" : \"en\", \"value\" : \"Abbreviated Description\"}]}}, \"impact\" : {\"baseMetricV3\" : {\"cvssV3\" : {\"version\" : \"3.0\", \"vectorString\" : \"CVSS:3.0/AV:L/AC:H/PR:L/UI:R/S:U/C:H/I:H/A:N\", \"attackVector\" : \"LOCAL\", \"attackComplexity\" : \"HIGH\", \"privilegesRequired\" : \"LOW\", \"userInteraction\" : \"REQUIRED\", \"scope\" : \"UNCHANGED\", \"confidentialityImpact\" : \"HIGH\", \"integrityImpact\" : \"HIGH\", \"availabilityImpact\" : \"NONE\", \"baseScore\" : 6.0, \"baseSeverity\" : \"MEDIUM\"}, \"exploitabilityScore\" : 0.8, \"impactScore\" : 5.2}, \"baseMetricV2\" : {\"cvssV2\" : {\"version\" : \"2.0\", \"vectorString\" : \"(AV:L/AC:H/Au:N/C:P/I:P/A:N)\", \"accessVector\" : \"LOCAL\", \"accessComplexity\" : \"HIGH\", \"authentication\" : \"NONE\", \"confidentialityImpact\" : \"PARTIAL\", \"integrityImpact\" : \"PARTIAL\", \"availabilityImpact\" : \"NONE\", \"baseScore\" : 2.6}, \"severity\" : \"LOW\", \"exploitabilityScore\" : 1.9, \"impactScore\" : 4.9, \"obtainAllPrivilege\" : false, \"obtainUserPrivilege\" : false, \"obtainOtherPrivilege\" : false, \"userInteractionRequired\" : true}}, \"publishedDate\" : \"2018-04-19T02:29Z\", \"lastModifiedDate\" : \"2018-04-25T16:25Z\"}";

    public static STVService stv;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setup() {
        stv = new STVService();
    }

    @Before
    public void reset() {
        DatabaseManager.purge(); //clear out databases before each test
        DatabaseManager.initialize(); //reset databases before each test
    }

    @AfterClass
    public static void purge() {
        DatabaseManager.purge(); //clear out databases after tests
    }

    //------------------------------------------------------------------------//
    // Tests
    //------------------------------------------------------------------------//

    @Test
    public void testGTM_OK() throws STVException.STVServiceException { //tests getTestMessage
        String s = stv.getTestMessage("Testing Message");
        assertEquals(s, "Testing Message");
    }

    @Test 
    public void testGSR_OK() throws STVException.STVServiceException { //test getSearchResult
        //First, load some stuff into the database
        //Replace below version with the STVService function if such a function is made.
        //START CODE FROM testDBMSearch
        CVEObject u1 = new CVEObject("afcdtech", "danmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        CVEObject u2 = new CVEObject("locaa", "neodanmakanvas", new String[0],
                "Vulnerability in the NeoDanmakanvas Script Engine.", "LOCAL", "MEDIUM",
                "NONE", "HIGH", "NONE", "NONE",
                "NONE", 2.0f, "MEDIUM", 2.0f, 2.0f,
                "2018-05-21", "2018-05-21");
        CVEObject u3 = new CVEObject("afcdtech", "neodanmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        CVEObject u4 = new CVEObject("locaa", "artifactcontest", new String[0],
                "Vulnerability in the Artifact Contest Judging System.", "LOCAL", "MEDIUM",
                "NONE", "HIGH", "NONE", "NONE",
                "NONE", 2.0f, "MEDIUM", 2.0f, 2.0f,
                "2018-05-21", "2018-05-21");
        DatabaseManager.createCVE(u1);
        DatabaseManager.createCVE(u2);
        DatabaseManager.createCVE(u3);
        DatabaseManager.createCVE(u4);
        //END CODE FROM testDBMSearch

        ArrayList<CVEObject> q1 = stv.getSearchResult("LoCaA NeoDanmakanvas");
        assertEquals(3, q1.size());
        ArrayList<CVEObject> q2 = stv.getSearchResult("foo bar");
        assertEquals(0, q2.size());
        ArrayList<CVEObject> q3 = stv.getSearchResult("XKCD327'); DROP TABLE CVE;--");
        assertEquals(0, q3.size());
        ArrayList<CVEObject> q4 = stv.getSearchResult("foo artifactcontest");
        assertEquals(1, q4.size());
        ArrayList<CVEObject> q5 = stv.getSearchResult("");
        assertEquals(4, q5.size());
    }

}