package br.com.agibank.dataanalysis.constants;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.agibank.dataanalysis.configuration.ConfigProperties;

/**
 * 
 * class of constants
 * 
 * @author sergio.melo
 *
 */
public class AppConstants {
	
	@Autowired
	ConfigProperties configProperties;
	
	public static final String UPLOADING_DIR = System.getProperty("user.home") + "/data/in/";
	public static final String READING_DIR = System.getProperty("user.home") + "/data/out/";
	public static final String PROCESSED_FILES = System.getProperty("user.home") + "/data/processed/";
	public static final String SALESMAN_ID = "001";
	public static final String CLIENT_ID = "002";
	public static final String SALE_ID = "003";
	public static final String SEPARATOR = "ç";
	public static final String OUTPUT_FILE_NAME = "resumefile.done.dat";
	public final static String FILE_EXTENSION = "dat";
	
}
