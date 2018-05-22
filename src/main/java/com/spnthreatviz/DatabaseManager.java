package com.spnthreatviz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Database Manager class, handling all direct communication with database.
 * Based on http://www.vogella.com/tutorials/MySQLJava/article.html Referenced
 * http://www.sqlitetutorial.net/sqlite-java/create-table/
 * Copied from uniText Xchange; heavily modified.
 */
public class DatabaseManager {
    
    /**
     * Empty constructor for the Database Manager. Deprecated.
     */ 
    public DatabaseManager() {
        initialize();
    }

    /* GENERAL NOTES
     * - Since we need a unique ID for each element and the NVD does not provide any, I am using the CVEObject.ToString as a unique ID.
     */

    /**
     * Initializes all databases by creating the files and setting up all the tables necessary.
     */
    public static void initialize() {
        configureDataSource("db/stv.db");
        String query = "CREATE TABLE IF NOT EXISTS CVE (\n" + "`cveid` CHAR(1024) NOT NULL PRIMARY KEY,\n"
                + "`vendor` CHAR(128) NOT NULL,\n"                  + "`productName` CHAR(256) NOT NULL,\n"
                + "`versionValues` VARCHAR(2048) NOT NULL,\n"       + "`description` CHAR(512) NOT NULL,\n"
                + "`attackVector` CHAR(64) NOT NULL,\n"             + "`attackComplexity` CHAR(64) NOT NULL,\n"
                + "`privilegesRequired` CHAR(64) NOT NULL,\n"       + "`userInteraction` CHAR(64) NOT NULL,\n"
                + "`confidentialityImpact` CHAR(64) NOT NULL,\n"    + "`integrityImpact` CHAR(64) NOT NULL,\n"
                + "`availabilityImpact` CHAR(64) NOT NULL,\n"       + "`baseScore` FLOAT(2,2) NOT NULL,\n"
                + "`baseSeverity` CHAR(64) NOT NULL,\n"             + "`exploitabilityScore` FLOAT(2,2) NOT NULL,\n"
                + "`impactScore` FLOAT(2,2) NOT NULL,\n"            + "`publishDate` CHAR(128) NOT NULL,\n" 
                + "`lastModifiedDate` CHAR(128) NOT NULL\n"         + ");";
        createNewTable(query, "db/stv.db");
    }

    //----------------Threat Code----------------//

    /**
     * Adds a new CVE entry to the database. 
     * @param newCVE the new entry being added to the database.
     * @return true if the entry was successfully created, false if the entry already exists or if there was another failure.
     */
    public static boolean createCVE(CVEObject newCVE) {
        PreparedStatement pst = null;
        int rs = 0;
        Connection testConnection = null;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.sqlite.JDBC");

            // Setup the connection with the stvDB
            testConnection = DriverManager.getConnection("jdbc:sqlite:db/stv.db");
            String query = " insert into CVE (cveid, vendor, productName, versionValues, description"
                    + ", attackVector, attackComplexity, privilegesRequired, userInteraction"
                    + ", confidentialityImpact, integrityImpact, availabilityImpact"
                    + ", baseScore, baseSeverity, exploitabilityScore, impactScore, publishDate, lastModifiedDate)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = testConnection.prepareStatement(query);
            pst.setString(1, newCVE.toString());
            pst.setString(2, newCVE.getVendor());
            pst.setString(3, newCVE.getProductName());

            //We create a string delimited by |.
            String[] versionValuesArray = newCVE.getVersionValues();
            String versionValuesString = "";
            for (int i = 0; i < versionValuesArray.length; i += 1) {
                versionValuesString = versionValuesString.concat(versionValuesArray[i]);
                if (i != versionValuesArray.length - 1) {
                    versionValuesString = versionValuesString.concat("|");
                }
            }

            pst.setString(4, versionValuesString);
            pst.setString(5, newCVE.getDescription());
            pst.setString(6, newCVE.getAttackVector());
            pst.setString(7, newCVE.getAttackComplexity());
            pst.setString(8, newCVE.getPrivilegesRequired());
            pst.setString(9, newCVE.getUserInteraction());
            pst.setString(10, newCVE.getConfidentialityImpact());
            pst.setString(11, newCVE.getIntegrityImpact());
            pst.setString(12, newCVE.getAvailabilityImpact());
            pst.setFloat(13, newCVE.getBaseScore());
            pst.setString(14, newCVE.getBaseSeverity());
            pst.setFloat(15, newCVE.getExploitabilityScore());
            pst.setFloat(16, newCVE.getImpactScore());
            pst.setString(17, newCVE.getPublishDate());
            pst.setString(18, newCVE.getLastModifiedDate());
            rs = pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (testConnection != null) {
                try {
                    testConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    
    /**
     * Retrieves the CVE Object associated with the provided CVE ID.
     * @param cveID The ID of the CVE to obtain from the database.
     * @return A CVEObject.
     */
    public static CVEObject getCVE(String cveID) {
        CVEObject toReturn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean status;
        Connection testConnection = null;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.sqlite.JDBC");
            // Setup the connection with the stvDB
            testConnection = DriverManager.getConnection("jdbc:sqlite:db/stv.db");
            pst = testConnection.prepareStatement("select * from CVE where cveid=?");
            pst.setString(1, cveID);
            rs = pst.executeQuery();
            status = rs.next();
            if (status) {
                String vendor = rs.getString("vendor");
                String productName = rs.getString("productName");
                String versionValues = rs.getString("versionValues");
                String description = rs.getString("description");
                String attackVector = rs.getString("attackVector");
                String attackComplexity = rs.getString("attackComplexity");
                String privilegesRequired = rs.getString("privilegesRequired");
                String userInteraction = rs.getString("userInteraction");
                String confidentialityImpact = rs.getString("confidentialityImpact");
                String integrityImpact = rs.getString("integrityImpact");
                String availabilityImpact = rs.getString("availabilityImpact");
                float baseScore = rs.getFloat("baseScore");
                String baseSeverity = rs.getString("baseSeverity");
                float exploitabilityScore = rs.getFloat("exploitabilityScore");
                float impactScore = rs.getFloat("impactScore");
                String publishDate = rs.getString("publishDate");
                String lastModifiedDate = rs.getString("lastModifiedDate");

                //Convert versionValues back to an Array
                String[] versionValuesString = versionValues.split("[|]");

                toReturn = new CVEObject(vendor, productName, versionValuesString, description, attackVector, attackComplexity, privilegesRequired, userInteraction, confidentialityImpact, integrityImpact, availabilityImpact, baseScore, baseSeverity, exploitabilityScore, impactScore, publishDate, lastModifiedDate);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (testConnection != null) {
                try {
                    testConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return toReturn;
    }

    /**
     * Determines whether a given CVE ID exists in the database.
     * @param cveID the CVE ID to verify.
     * @return true if the id is in the database, false otherwise.
     */
    public static boolean validCVE(String cveID) {
        boolean status = false;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection testConnection = null;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.sqlite.JDBC");

            // Setup the connection with the stvDB
            testConnection = DriverManager.getConnection("jdbc:sqlite:db/stv.db");
            pst = testConnection.prepareStatement("select * from CVE where cveid=?");
            pst.setString(1, cveID);
            rs = pst.executeQuery();
            status = rs.next();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (testConnection != null) {
                try {
                    testConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;

    }

    //Note: Multiple space-delimited words should be handled in STVService with Set operations (union/intersection) on the results of this query.
    //TODO: Enable searching description

    /**
     * Retrieves the CVE Object associated with the provided search terms.
     * @param search The search term to use in the query.
     * @return An ArrayList of CVE Objects.
     */
    public static ArrayList<CVEObject> getCVESearch(String search) {
        ArrayList<CVEObject> toReturn = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection testConnection = null;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.sqlite.JDBC");
            // Setup the connection with the stvDB
            testConnection = DriverManager.getConnection("jdbc:sqlite:db/stv.db");
            pst = testConnection.prepareStatement("select * from CVE where vendor=? or productName=?");
            pst.setString(1, search);
            pst.setString(2, search);
            rs = pst.executeQuery();
            while (rs.next()) { //for every row in the returned ResultSet
                String vendor = rs.getString("vendor");
                String productName = rs.getString("productName");
                String versionValues = rs.getString("versionValues");
                String description = rs.getString("description");
                String attackVector = rs.getString("attackVector");
                String attackComplexity = rs.getString("attackComplexity");
                String privilegesRequired = rs.getString("privilegesRequired");
                String userInteraction = rs.getString("userInteraction");
                String confidentialityImpact = rs.getString("confidentialityImpact");
                String integrityImpact = rs.getString("integrityImpact");
                String availabilityImpact = rs.getString("availabilityImpact");
                float baseScore = rs.getFloat("baseScore");
                String baseSeverity = rs.getString("baseSeverity");
                float exploitabilityScore = rs.getFloat("exploitabilityScore");
                float impactScore = rs.getFloat("impactScore");
                String publishDate = rs.getString("publishDate");
                String lastModifiedDate = rs.getString("lastModifiedDate");

                //Convert versionValues back to an Array
                String[] versionValuesString = versionValues.split("[|]");

                CVEObject temp = new CVEObject(vendor, productName, versionValuesString, description, attackVector, attackComplexity, privilegesRequired, userInteraction, confidentialityImpact, integrityImpact, availabilityImpact, baseScore, baseSeverity, exploitabilityScore, impactScore, publishDate, lastModifiedDate);
                toReturn.add(temp); //store into ArrayList
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (testConnection != null) {
                try {
                    testConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return toReturn;
    }
    
    //----------------Database and General Code----------------//    

    /**
     * Creates a new Database associated with the current filename.
     * @param fileName the name of the new database. Make sure to include a .db extension.
     */
    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Given a database and a create new table query, add the table to the database.
     * @param tableQuery The query to create a new table to be added to the database.
     * @param databasename The database that the table is being added to.
     */
    public static void createNewTable(String tableQuery, String databasename) {
        String url = "jdbc:sqlite:" + databasename;

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(tableQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /**
     * Check if the database file exists in the current directory. If it does,
     * create a DataSource instance for the file and return it.
     * @param filename the name of the source file.
     */
    private static void configureDataSource(String filename) {
        Path todoPath = Paths.get(".", filename);
        if (!(Files.exists(todoPath))) {
            createNewDatabase(filename);
        }
    }
    
    /**
     * Purges database files.
     */
    public static void purge() {
        try {
            String stvPath = "db/stv.db";
            
            Path pass = Paths.get(".", stvPath);
            if (Files.exists(pass)) {
                Files.delete(pass);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
