package br.com.agibank.dataanalysis.service;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.exception.FileInvalidException;

/**
 * 
 * Uploading service class
 * 
 * @author sergio.melo
 *
 */
@Service
public class UploadingService {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(UploadingService.class);
	
	/**
	 * 
	 * Validate file extension
	 * 
	 * @param file
	 * @return
	 * @throws FileInvalidException
	 */
	public boolean isValideFileExtension(String file) throws FileInvalidException {
		if(Objects.nonNull(file)) {
			String fileExtension= StringUtils.getFilenameExtension(file);
			fileExtension = Objects.isNull(fileExtension) ? "none" : fileExtension;
			if(fileExtension.equalsIgnoreCase(AppConstants.FILE_EXTENSION)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
