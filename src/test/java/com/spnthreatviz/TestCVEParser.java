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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        cvep = new CVEParser();
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
}