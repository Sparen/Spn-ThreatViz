package com.spnthreatviz;

import java.util.ArrayList;

/**
 * A class representing a single CVE Object, holding all information important for ThreatViz.
 */
public class CVEObject {

    /**
     * Default constructor for the CVEObject class, used with data provided from JSON.
     */
    public CVEObject() {
    }

    @Override
    public String toString() {
        return ""; //TODO
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
        return true; //.equals on important fields
    }

    @Override
    public int hashCode() {
        return (0);
    }
}
