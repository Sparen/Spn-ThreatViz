# Sparen ThreatViz

Welcome to the master repository for Sparen ThreatViz, a project by Andrew Fan.

[![Build Status](https://travis-ci.com/Sparen/Spn-ThreatViz.svg?branch=master)](https://travis-ci.com/Sparen/Spn-ThreatViz)

Spn-ThreatViz is a data visualizer for the National Vulnerability Database's Data Feed. It features a simple REST API, a SQL database, and a mechanism for transforming the CVE JSON files into a simple Java Object, as well as (in the future) the ability to search through the provided data via special keywords (likely limited to names of companies and their products).

Note that this project is not meant for a production environment and is not meant to be used seriously; the only data actually stored is the 'current' file from May 21, 2018. In total there is over 500MB of JSON data, and this is beyond the amount of data I am willing to store in a git repo (and beyond the amount of money I'm willing to throw at AWS for storage for this particular application).

### Build Commands

This project uses Maven and Java 1.8. If running locally, the application will launch on localhost:8080

Test suites consist of all java files prefixed with 'Test' located in src/test/java/com/spnthreatviz/

Run the tests with:

```
mvn -Dtest=Test* test
```

Alternatively, running `mvn package` will run the tests as well. TravisCI has been setup with our application (see the badge at the top of the README)

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

