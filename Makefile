clean:
	rm -rf target/
	rm -f *~

javadoc:
	rm -rf docs/*
	javadoc -d ./docs ./src/main/java/com/spn-threatviz/*
