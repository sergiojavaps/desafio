# Desafio Agibank

### The system performs the process in two ways:
1. upload batches of files to http://localhost/8080.
2. The system automatically creates directories. Copy batches of files manually to directory %HOMEPATH%data/in, and wait for the system job to finish processing. The job verifies the directory of the files in real time, if the files are added, removed or edited, the job runs the process of generating the resume.done.dat file.  

### Run 
- ./mvnw spring-boot:run
- http://localhost/8080/.

### What will you need:
- java 8.
- Apache Maven 3.6.0.
- Tomcat 8 (optional, only if you want to use catalina with the .war file published on your machine).

