# Agibank Challenge
### Data analysis
The system should read data from the default directory, located at %HOMEPATH%/data/in.    
The system should read only .dat files. After processing all files within the standard input directory, the system should create a file within the   standard output directory, located at %HOMEPATH%/data/out.    
The file name must follow the pattern, {flat_file_name} .done.dat.  
The contents of the output file must summarize the following data:
- Number of customers in the input file
- Salesperson quantity in input file
- Most expensive sale ID
- The worst seller

The system must be running at all times.  
All new files are available, everything must be executed.

### The system performs the process in two ways:
1. upload batches of files to http://localhost/8080. You will see that after uploading the files, the system reads only the .dat files, and visually makes available the files that were uploaded, processed or that failed in the reading process, as well as the result of the data read and calculated, as requested in the challenge.
2. The system automatically creates directories. Copy batches of files manually to directory %HOMEPATH%/data/in, and wait for the system job to finish processing. The job verifies the directory of the files in real time, if the files are added, removed or edited, the job runs the process of generating the resume.done.dat file.  

### Run 
- ./mvnw spring-boot:run
- http://localhost/8080/

### What will you need:
- springboot
- java 8.
- Apache Maven 3.6.0.
- Tomcat 8 (optional, only if you want to use catalina with the .war file published on your machine).
- Docker (optional)
1. https://hub.docker.com/r/sergiomelo/agibank
2. docker pull sergiomelo/agibank
3. docker container run -p 8080:8080 -it IMAGE_ID
4. http://localhost:8080/agibank/ 
