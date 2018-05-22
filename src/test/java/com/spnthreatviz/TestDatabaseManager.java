package com.spnthreatviz;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Test suite for DatabaseManager class.
 */
public class TestDatabaseManager {

    //------------------------------------------------------------------------//
    // Setup
    //------------------------------------------------------------------------//

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        DatabaseManager.purge();
        DatabaseManager.initialize();
    }

    @After
    public void tearDown() {
        DatabaseManager.purge();
    }

    //------------------------------------------------------------------------//
    // Tests
    //------------------------------------------------------------------------//

    @Test
    public void testDBMBasic() { //tests all basic (add/verify/retrieve) database manager functionality.
        //We will create a number of CVE Objects and ensure that we can put them in and take them out
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

        //Load into database with createCVE
        DatabaseManager.createCVE(u1);
        DatabaseManager.createCVE(u2);

        //Check if they are present with validCVE
        assertTrue(DatabaseManager.validCVE(u1.toString()));
        assertTrue(DatabaseManager.validCVE(u2.toString()));

        //Retrieve via getCVE and check fields
        CVEObject u3 = DatabaseManager.getCVE(u1.toString());
        CVEObject u4 = DatabaseManager.getCVE(u2.toString());

        assertTrue(u3.getVendor().equals("afcdtech"));
        assertTrue(u3.getProductName().equals("danmakanvas"));
        assertTrue(u3.getDescription().equals("Vulnerability in the Danmakanvas Test Suite."));
        assertTrue(u3.getAttackVector().equals("NETWORK"));
        assertTrue(u3.getAttackComplexity().equals("LOW"));
        assertTrue(u3.getPrivilegesRequired().equals("LOW"));
        assertTrue(u3.getUserInteraction().equals("REQUIRED"));
        assertTrue(u3.getConfidentialityImpact().equals("LOW"));
        assertTrue(u3.getIntegrityImpact().equals("LOW"));
        assertTrue(u3.getAvailabilityImpact().equals("LOW"));
        assertTrue(u3.getBaseScore() == 5.0f);
        assertTrue(u3.getBaseSeverity().equals("LOW"));
        assertTrue(u3.getExploitabilityScore() == 1.0f);
        assertTrue(u3.getImpactScore() == 1.0f);
        assertTrue(u3.getPublishDate().equals("2018-05-20"));
        assertTrue(u3.getLastModifiedDate().equals("2018-05-20"));

        assertTrue(u4.getVendor().equals("locaa"));
        assertTrue(u4.getProductName().equals("neodanmakanvas"));
        assertTrue(u4.getDescription().equals("Vulnerability in the NeoDanmakanvas Script Engine."));
        assertTrue(u4.getAttackVector().equals("LOCAL"));
        assertTrue(u4.getAttackComplexity().equals("MEDIUM"));
        assertTrue(u4.getPrivilegesRequired().equals("NONE"));
        assertTrue(u4.getUserInteraction().equals("HIGH"));
        assertTrue(u4.getConfidentialityImpact().equals("NONE"));
        assertTrue(u4.getIntegrityImpact().equals("NONE"));
        assertTrue(u4.getAvailabilityImpact().equals("NONE"));
        assertTrue(u4.getBaseScore() == 2.0f);
        assertTrue(u4.getBaseSeverity().equals("MEDIUM"));
        assertTrue(u4.getExploitabilityScore() == 2.0f);
        assertTrue(u4.getImpactScore() == 2.0f);
        assertTrue(u4.getPublishDate().equals("2018-05-21"));
        assertTrue(u4.getLastModifiedDate().equals("2018-05-21"));
    }

    @Test
    public void testDBMVersionValues() { //Tests the version number array
        String[] u1arr = {"12.0", "12.1", "12.2", "12.4"};
        String[] u1arrb = {"12.0", "12.1", "12.2", "12.4", "12.5"};
        CVEObject u1 = new CVEObject("afcdtech", "danmakanvas", u1arr,
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        DatabaseManager.createCVE(u1);
        CVEObject u3 = DatabaseManager.getCVE(u1.toString());
        assertArrayEquals(u1arr, u3.getVersionValues());
        assertFalse(Arrays.equals(u1arrb, u3.getVersionValues()));
    }

    @Test
    public void testDBMSearch() { //tests database manager search functionality.
        //We will create a number of CVE Objects and ensure that we can put them in and take them out
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

        //Load into database with createCVE
        DatabaseManager.createCVE(u1);
        DatabaseManager.createCVE(u2);
        DatabaseManager.createCVE(u3);
        DatabaseManager.createCVE(u4);

        //Check if they are present with validCVE
        assertTrue(DatabaseManager.validCVE(u1.toString()));
        assertTrue(DatabaseManager.validCVE(u2.toString()));
        assertTrue(DatabaseManager.validCVE(u3.toString()));
        assertTrue(DatabaseManager.validCVE(u4.toString()));

        //Retrieve via getCVESearch and check fields
        ArrayList<CVEObject> query1 = DatabaseManager.getCVESearch("afcdtech");
        assertEquals(query1.size(), 2);
        ArrayList<CVEObject> query2 = DatabaseManager.getCVESearch("locaa");
        assertEquals(query2.size(), 2);
        ArrayList<CVEObject> query3 = DatabaseManager.getCVESearch("neodanmakanvas");
        assertEquals(query3.size(), 2);
        ArrayList<CVEObject> query4 = DatabaseManager.getCVESearch("danmakanvas");
        assertEquals(query4.size(), 1);
        ArrayList<CVEObject> query5 = DatabaseManager.getCVESearch("foobar");
        assertEquals(query5.size(), 0);

        CVEObject q4o = query4.get(0); //should have only one row

        assertTrue(q4o.getVendor().equals("afcdtech"));
        assertTrue(q4o.getProductName().equals("danmakanvas"));
        assertTrue(q4o.getDescription().equals("Vulnerability in the Danmakanvas Test Suite."));
        assertTrue(q4o.getAttackVector().equals("NETWORK"));
        assertTrue(q4o.getAttackComplexity().equals("LOW"));
        assertTrue(q4o.getPrivilegesRequired().equals("LOW"));
        assertTrue(q4o.getUserInteraction().equals("REQUIRED"));
        assertTrue(q4o.getConfidentialityImpact().equals("LOW"));
        assertTrue(q4o.getIntegrityImpact().equals("LOW"));
        assertTrue(q4o.getAvailabilityImpact().equals("LOW"));
        assertTrue(q4o.getBaseScore() == 5.0f);
        assertTrue(q4o.getBaseSeverity().equals("LOW"));
        assertTrue(q4o.getExploitabilityScore() == 1.0f);
        assertTrue(q4o.getImpactScore() == 1.0f);
        assertTrue(q4o.getPublishDate().equals("2018-05-20"));
        assertTrue(q4o.getLastModifiedDate().equals("2018-05-20"));
    }
}