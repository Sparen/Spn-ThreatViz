package com.spnthreatviz;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

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
    public void setup() {}

    //------------------------------------------------------------------------//
    // Tests
    //------------------------------------------------------------------------//

    //Note: DOES NOT CURRENTLY TEST THE VERSION NUMBER ARRAY

    @Test
    public void testDBM() { //tests all standard database manager functionality.
        //First we need to create an instance in order to purge the existing database.
        DatabaseManager dbm = new DatabaseManager();
        dbm.purge();
        //Now we reinitialize the DBM.
        dbm = new DatabaseManager();

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
        dbm.createCVE(u1);
        dbm.createCVE(u2);

        //Check if they are present with validCVE
        assertTrue(dbm.validCVE(u1.toString()));
        assertTrue(dbm.validCVE(u2.toString()));

        //Retrieve via getCVE and check fields
        CVEObject u3 = dbm.getCVE(u1.toString());
        CVEObject u4 = dbm.getCVE(u2.toString());

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

        //Clean up
        dbm.purge();
    }
}