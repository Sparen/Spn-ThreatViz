# Sparen ThreatViz

Welcome to the master repository for Sparen ThreatViz, a project by Andrew Fan.

[![Build Status](https://travis-ci.com/Sparen/Spn-ThreatViz.svg?branch=master)](https://travis-ci.com/Sparen/Spn-ThreatViz)

Spn-ThreatViz is a data visualizer for the National Vulnerability Database's Data Feed.

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

