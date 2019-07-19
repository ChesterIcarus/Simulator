To compile: 
    mvn clean compile assembly:single
Should result in JAR file in /target
This JAR must be ran in headless mode, like so:
    java -Djava.awt.headless=true -jar target/Coeus-1.0-SNAPSHOT-jar-with-dependencies.jar

In some scenarios additional memory may be required, like so:
    java -Djava.awt.headless=true -jar -Xms1g -Xmx16g target/Coeus-1.0-SNAPSHOT-jar-with-dependencies.jar
