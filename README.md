# Sparen ThreatViz

Welcome to the master repository for Sparen ThreatViz, a project by Andrew Fan.

[![Build Status](https://travis-ci.com/Sparen/Spn-ThreatViz.svg?branch=master)](https://travis-ci.com/Sparen/Spn-ThreatViz)

Spn-ThreatViz is a data visualizer for the National Vulnerability Database's Data Feed. It features a simple REST API, a SQL database, and a mechanism for transforming the CVE JSON files into a simple Java Object, as well as the ability to search through the provided data via vendor and product names and sort by various fields. This project was built using Maven, Spark Java, and ReactJS (without Node, JSX, or Babel). Requires ES6 compatible browser.

Note that this project is not meant for a production environment and is not meant to be used seriously; the only data actually stored is the 'current' file from May 21, 2018. In total there is over 500MB of JSON data, and this is beyond the amount of data I am willing to store in a git repo (and beyond the amount of money I'm willing to throw at AWS for storage for this particular application).

### Usage

To utilize this project, first run `DataLoader.java`, which will load the JSON files in resources/nvdcve into the SQL Database (note: filepaths for the JSON files are currently hardcoded within `DataLoader.java` due to the intended scope of this project).

Next, run `Bootstrap.java` to boot up the server. The contents of resources/public will be served on localhost:8080.

In the Data Table view, the search terms input are space delimited and utilize OR (so 'apache oracle' will return all queries with vendors apache or oracle). ⇕ prefixed header categories are sortable. Hitting once will sort using string/number comparisons; hitting twice will reverse the order.

### Build Commands

This project uses Maven and Java 1.8. If running locally, the application will launch on localhost:8080

Test suites consist of all java files prefixed with 'Test' located in src/test/java/com/spnthreatviz/

Run the tests with:

```
mvn -Dtest=Test* test
```

Alternatively, running `mvn package` will run the tests as well. TravisCI has been setup with our application (see the badge at the top of the README).

### Demo

To demo the package, it is necessary to first run DataLoader.java, and then Bootstrap.java. The former will set up the database, while the latter will setup localhost:8080 and ignite Spark Java.

The `make run` Makefile command packages the project and then runs the two executables from the jar. It is recommended to use `make run` for quick and painless setup.

### Known Issues and Potential Features
• Project does not support baseMetricV2 and if baseMetricV3 is not provided, data fields are saved as N/A. [Possible Extension]  
• JSON files to load currently hardcoded instead of loading all in directory [Possible Extension]  
• No way to update database with new information [Possible Extension]  

### Makefile Commands

Current Makefile commands (for reference):

```
make clean
```
Removes all build files and emacs temporary files

```
make javadoc
```
Creates documentation based off of Javadoc

```
make purgedb
```
Purge contents of local databases

```
make run
```
Package and run the application


