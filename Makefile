clean:
	rm -rf target/
	rm -f *~

javadoc:
	rm -rf docs/*
	javadoc -d ./docs ./src/main/java/com/spnthreatviz/*

purgedb:
	rm -rf db/stv.db

run:
	mvn package
	java -cp target/Spn-ThreatViz-0.0.jar com.spnthreatviz.DataLoader
	java -cp target/Spn-ThreatViz-0.0.jar com.spnthreatviz.Bootstrap
