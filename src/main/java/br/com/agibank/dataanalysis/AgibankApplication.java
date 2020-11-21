package br.com.agibank.dataanalysis;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.agibank.dataanalysis.configuration.ConfigProperties;
import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.exception.InvalidFileDirectoryException;
import br.com.agibank.dataanalysis.service.UploadingService;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@EnableScheduling
@Async
public class AgibankApplication {

	private static Logger logger = LogManager.getLogger(AgibankApplication.class);
	
	public static void main(String[] args) {
		
		
		logger.info("==============================================================================");
		logger.info("::AGIBANK DATA ANALYSIS::");
		logger.info("===============================================================================");
		logger.info("::You can use the system in two ways.");
		logger.info("::1. in the browser via file upload to http://localhost:8080");
		logger.info("::2. adding files to the %HOMEPATH%/data/in entry directory and wait");
		logger.info("       for the system job to run, the standard run period every 10 seconds");
		logger.info("===============================================================================");
		logger.info("::AGIBANK DATA ANALYSIS::");
		logger.info("================================================================================");
		
		List<String> dirList = new ArrayList<String>();
    	dirList.add(AppConstants.READING_DIR);
    	dirList.add(AppConstants.UPLOADING_DIR);
    	dirList.add(AppConstants.PROCESSED_FILES);
    	dirList.add(AppConstants.PROCESSING_FAILURE_DIR);
    	try {
			UploadingService.createDirectoriesFiles(dirList);
			SpringApplication.run(AgibankApplication.class, args);
		} catch (InvalidFileDirectoryException e) {
			// TODO Auto-generated catch block
			logger.info("attention, it was not possible to create the directories of the files, "
					+ "check your settings and try again. Cause: " + e);
			SpringApplication.run(AgibankApplication.class, args);
		}
	}
	
}
