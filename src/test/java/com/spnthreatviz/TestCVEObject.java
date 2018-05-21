package com.spnthreatviz;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 * Test suite for CVEObject class.
 */
public class TestCVEObject {

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

    @Test
    public void testStandard() { //tests standard/default constructor
        CVEObject u1 = new CVEObject("afcdtech", "danmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");

        assertTrue(u1.getVendor().equals("afcdtech"));
        assertTrue(u1.getProductName().equals("danmakanvas"));
        assertTrue(u1.getDescription().equals("Vulnerability in the Danmakanvas Test Suite."));
        assertTrue(u1.getAttackVector().equals("NETWORK"));
        assertTrue(u1.getAttackComplexity().equals("LOW"));
        assertTrue(u1.getPrivilegesRequired().equals("LOW"));
        assertTrue(u1.getUserInteraction().equals("REQUIRED"));
        assertTrue(u1.getConfidentialityImpact().equals("LOW"));
        assertTrue(u1.getIntegrityImpact().equals("LOW"));
        assertTrue(u1.getAvailabilityImpact().equals("LOW"));
        assertTrue(u1.getBaseScore() == 5.0f);
        assertTrue(u1.getBaseSeverity().equals("LOW"));
        assertTrue(u1.getExploitabilityScore() == 1.0f);
        assertTrue(u1.getImpactScore() == 1.0f);
        assertTrue(u1.getPublishDate().equals("2018-05-20"));
        assertTrue(u1.getLastModifiedDate().equals("2018-05-20"));

        u1.setVendor("locaa");
        u1.setProductName("neodanmakanvas");
        u1.setDescription("Vulnerability in the NeoDanmakanvas Script Engine.");
        u1.setAttackVector("LOCAL");
        u1.setAttackComplexity("MEDIUM");
        u1.setPrivilegesRequired("NONE");
        u1.setUserInteraction("HIGH");
        u1.setConfidentialityImpact("NONE");
        u1.setIntegrityImpact("NONE");
        u1.setAvailabilityImpact("NONE");
        u1.setBaseScore(2.0f);
        u1.setBaseSeverity("MEDIUM");
        u1.setExploitabilityScore(2.0f);
        u1.setImpactScore(2.0f);
        u1.setPublishDate("2018-05-21");
        u1.setLastModifiedDate("2018-05-21");

        assertTrue(u1.getVendor().equals("locaa"));
        assertTrue(u1.getProductName().equals("neodanmakanvas"));
        assertTrue(u1.getDescription().equals("Vulnerability in the NeoDanmakanvas Script Engine."));
        assertTrue(u1.getAttackVector().equals("LOCAL"));
        assertTrue(u1.getAttackComplexity().equals("MEDIUM"));
        assertTrue(u1.getPrivilegesRequired().equals("NONE"));
        assertTrue(u1.getUserInteraction().equals("HIGH"));
        assertTrue(u1.getConfidentialityImpact().equals("NONE"));
        assertTrue(u1.getIntegrityImpact().equals("NONE"));
        assertTrue(u1.getAvailabilityImpact().equals("NONE"));
        assertTrue(u1.getBaseScore() == 2.0f);
        assertTrue(u1.getBaseSeverity().equals("MEDIUM"));
        assertTrue(u1.getExploitabilityScore() == 2.0f);
        assertTrue(u1.getImpactScore() == 2.0f);
        assertTrue(u1.getPublishDate().equals("2018-05-21"));
        assertTrue(u1.getLastModifiedDate().equals("2018-05-21"));
    }

    @Test
    public void testEmpty() { //tests empty constructor
        CVEObject u1 = new CVEObject();
        assertTrue(u1.getVendor().equals(""));
        assertTrue(u1.getProductName().equals(""));
        assertTrue(u1.getDescription().equals(""));
        assertTrue(u1.getAttackVector().equals(""));
        assertTrue(u1.getAttackComplexity().equals(""));
        assertTrue(u1.getPrivilegesRequired().equals(""));
        assertTrue(u1.getUserInteraction().equals(""));
        assertTrue(u1.getConfidentialityImpact().equals(""));
        assertTrue(u1.getIntegrityImpact().equals(""));
        assertTrue(u1.getAvailabilityImpact().equals(""));
        assertTrue(u1.getBaseScore() == -1.0f);
        assertTrue(u1.getBaseSeverity().equals(""));
        assertTrue(u1.getExploitabilityScore() == -1.0f);
        assertTrue(u1.getImpactScore() == -1.0f);
        assertTrue(u1.getPublishDate().equals("0000-00-00"));
        assertTrue(u1.getLastModifiedDate().equals("0000-00-00"));

        u1.setVendor("locaa");
        u1.setProductName("neodanmakanvas");
        u1.setDescription("Vulnerability in the NeoDanmakanvas Script Engine.");
        u1.setAttackVector("LOCAL");
        u1.setAttackComplexity("MEDIUM");
        u1.setPrivilegesRequired("NONE");
        u1.setUserInteraction("HIGH");
        u1.setConfidentialityImpact("NONE");
        u1.setIntegrityImpact("NONE");
        u1.setAvailabilityImpact("NONE");
        u1.setBaseScore(2.0f);
        u1.setBaseSeverity("MEDIUM");
        u1.setExploitabilityScore(2.0f);
        u1.setImpactScore(2.0f);
        u1.setPublishDate("2018-05-21");
        u1.setLastModifiedDate("2018-05-21");

        assertTrue(u1.getVendor().equals("locaa"));
        assertTrue(u1.getProductName().equals("neodanmakanvas"));
        assertTrue(u1.getDescription().equals("Vulnerability in the NeoDanmakanvas Script Engine."));
        assertTrue(u1.getAttackVector().equals("LOCAL"));
        assertTrue(u1.getAttackComplexity().equals("MEDIUM"));
        assertTrue(u1.getPrivilegesRequired().equals("NONE"));
        assertTrue(u1.getUserInteraction().equals("HIGH"));
        assertTrue(u1.getConfidentialityImpact().equals("NONE"));
        assertTrue(u1.getIntegrityImpact().equals("NONE"));
        assertTrue(u1.getAvailabilityImpact().equals("NONE"));
        assertTrue(u1.getBaseScore() == 2.0f);
        assertTrue(u1.getBaseSeverity().equals("MEDIUM"));
        assertTrue(u1.getExploitabilityScore() == 2.0f);
        assertTrue(u1.getImpactScore() == 2.0f);
        assertTrue(u1.getPublishDate().equals("2018-05-21"));
        assertTrue(u1.getLastModifiedDate().equals("2018-05-21"));
    }

    @Test
    public void testToString() {
        CVEObject u1 = new CVEObject("afcdtech", "danmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        assertEquals(u1.toString(), "afcdtech: danmakanvas - Vulnerability in the Danmakanvas Test Suite.");
    }

    @Test
    public void testEquals() {
        CVEObject u1 = new CVEObject("afcdtech", "danmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        CVEObject u2 = new CVEObject("afcdtech", "danmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        assertEquals(u1, u1);
        assertEquals(u1, u2);
        assertNotEquals(u1, null);
        CVEObject u3 = new CVEObject("locaa", "danmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        CVEObject u4 = new CVEObject("afcdtech", "neodanmakanvas", new String[0],
                "Vulnerability in the Danmakanvas Test Suite.", "NETWORK", "LOW",
                "LOW", "REQUIRED", "LOW", "LOW",
                "LOW", 5.0f, "LOW", 1.0f, 1.0f,
                "2018-05-20", "2018-05-20");
        assertNotEquals(u1, u3);
        assertNotEquals(u1, u4);
        assertNotEquals(u2, u3);
        assertNotEquals(u2, u4);
        assertNotEquals(u3, u4);
    }
}