package br.com.agibank.dataanalysis;

import java.io.File;

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

	public static void main(String[] args) {
		
		new File(AppConstants.UPLOADING_DIR).mkdirs();
		new File(AppConstants.READING_DIR).mkdirs();
		new File(AppConstants.PROCESSED_FILES).mkdirs();
		SpringApplication.run(AgibankApplication.class, args);
	}

}
