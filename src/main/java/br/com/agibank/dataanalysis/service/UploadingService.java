package br.com.agibank.dataanalysis.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.exception.CalcException;
import br.com.agibank.dataanalysis.exception.CreateOutputFileException;
import br.com.agibank.dataanalysis.exception.FileInvalidException;
import br.com.agibank.dataanalysis.exception.GetInputFilesException;
import br.com.agibank.dataanalysis.exception.InvalidFileDirectoryException;
import br.com.agibank.dataanalysis.exception.MemoryExceededException;
import br.com.agibank.dataanalysis.exception.ReadingInputFileException;
import br.com.agibank.dataanalysis.exception.SumaryAnalysisException;
import br.com.agibank.dataanalysis.model.ReportResume;

/**
 * 
 * class responsible for the web upload rule for files
 * 
 * @author sergio.melo
 *
 */
@Service
public class UploadingService {

	private static Logger logger = LogManager.getLogger(DataAnalysisService.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	/**
	 * 
	 * Upload multipart file from web
	 * 
	 * @param uploadingFiles
	 * @throws IllegalStateException
	 * @throws FileInvalidException
	 * @throws IOException
	 * @throws CalcException 
	 * @throws CreateOutputFileException 
	 * @throws ReadingInputFileException 
	 * @throws GetInputFilesException 
	 */
	@Async
	public void uploadingPost(MultipartFile[] uploadingFiles) throws IOException {		
		
		for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(AppConstants.UPLOADING_DIR + uploadedFile.getOriginalFilename());
			try {
				if(isValideFileExtension(file.getAbsolutePath())) {
					uploadedFile.transferTo(file);
				}
			} catch (IllegalStateException | FileInvalidException | IOException e) {
				logger.error("it was not possible to upload the file " + file.getName() + ", the file was moved to "
						+ "the folder of the files with processing failures. Cause: " + e);
				dataAnalysisService.sendFileToProcessingFailureDirectory(file);
			}
		}	
		try {
			dataAnalysisService.execute();
		} catch (GetInputFilesException | FileInvalidException | CreateOutputFileException | CalcException e) {			
			logger.error(e);
		}
		
	}
	
	/**
	 * 
	 * Get report resume data object
	 * 
	 * @return
	 * @throws SumaryAnalysisException
	 * @throws IOException
	 */
	public ReportResume getSumaryData() throws SumaryAnalysisException, IOException {
		return dataAnalysisService.getReportResumeSummaryData();
	}
	
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
	
	/**
	 * 
	 * Validates if the directory exists, if not, create a new directory
	 * 
	 * @author sergio.melo
	 * 
	 * @param dir
	 * @throws InvalidFileDirectoryException
	 */
	public void validateFileDirectory(String dir) throws InvalidFileDirectoryException {
		File file = new File(dir);
		if(!file.exists()) {
			new File(dir).mkdirs();
		}
	}
	
	/**
	 * 
	 * Validates if the directory exists, if not, create a new directory
	 * 
	 * @author sergio.melo
	 * 
	 * @param dirList
	 * @throws InvalidFileDirectoryException
	 */
	public void validateFileDirectory(List<String> dirList) throws InvalidFileDirectoryException {
		for(String dir : dirList) {
			File file = new File(dir);
			if(!file.exists()) {
				new File(dir).mkdirs();
				logger.info(">>> directory created: " + file.getAbsolutePath());
			}
		}
	}
	
	public static void createDirectoriesFiles(List<String> dirList) throws InvalidFileDirectoryException {
		for(String dir : dirList) {
			File file = new File(dir);
			if(!file.exists()) {
				new File(dir).mkdirs();
				logger.info(">>> directory created: " + file.getAbsolutePath());
			}
		}
	}
	
	public int getTotalFiles() throws InvalidFileDirectoryException {
		int total = 0;
		File file = new File(AppConstants.UPLOADING_DIR);
		if(file.exists()) {
			File[] files = file.listFiles();
			total = files.length;
		}
		return total;
	}
	
	public long getTotalFilesByBytes() throws MemoryExceededException {
		long total = 0;
		File file = new File(AppConstants.UPLOADING_DIR);
		if(file.exists()) {
			File[] files = file.listFiles();
			for(File fileByte : files) {
				total += fileByte.length();
			}
		}
		return total;
	}
}
