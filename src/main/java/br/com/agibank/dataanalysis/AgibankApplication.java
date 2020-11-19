package br.com.agibank.dataanalysis;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.agibank.dataanalysis.configuration.ConfigProperties;
import br.com.agibank.dataanalysis.constants.AppConstants;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@EnableScheduling
@Async
public class AgibankApplication {

	private static Logger logger = LogManager.getLogger(AgibankApplication.class);
	
	@Autowired
	ConfigProperties configProperties;
	
	public static void main(String[] args) {
		new File(AppConstants.UPLOADING_DIR).mkdirs();
		new File(AppConstants.READING_DIR).mkdirs();
		new File(AppConstants.PROCESSED_FILES).mkdirs();
		
		logger.info("==============================================================================");
		logger.info("::AGIBANK DATA ANALYSIS::");
		logger.info("===============================================================================");
		logger.info(">>> You can use the system in two ways.");
		logger.info(">>> 1. in the browser via file upload to http://localhost:8080");
		logger.info(">>> 2. adding files to the %HOMEPATH%/data/in entry directory and wait");
		logger.info("       for the system job to run, the standard run period every 10 seconds");
		logger.info("===============================================================================");
		logger.info("::AGIBANK DATA ANALYSIS::");
		logger.info("================================================================================");
		
		SpringApplication.run(AgibankApplication.class, args);
	}
	
	
	
}
