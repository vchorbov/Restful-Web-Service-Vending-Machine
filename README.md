# Restful-Web-Service-Vending-Machine 

In order to build and start the Spring Boot Application you can either:

- If importerd in an IDE of choice, go to the Application.java class and "Run" the application;
- If you have maven installed on your machine, via the terminal you can use the followin commands:
    -  `mvn install` - will download all the dependencies, build and generate a maven artefact
        of the Spring Boot project. The artefact is nothing less than a .jar file.
    - inside the target folder `cd target` you will find the generated .jar file
    - to run the file type `java -jar {name of the file}` in the terminal
- If you do not have maven installed on your machine, you can also use the maven wrapper command files, which come with the project
   - run `./mvnw install` to build
   - and then start the project the same way as described above.
