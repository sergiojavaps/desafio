package br.com.agibank.dataanalysis.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.service.UploadingService;

/**
 * 
 * Controller class of upload requests
 * 
 * @author sergio.melo
 *
 */
@Controller
public class UploadingController {
	
	private static Logger logger = LogManager.getLogger(UploadingController.class);
	@Autowired
	private UploadingService uploadingService;
	@Autowired
	private ApplicationContext appContext;
	
	/**
	 * 
	 * Returns template with upload files
	 * 
	 * @author sergio.melo
	 * 
	 * @param model
	 * @return
	 */
	@Async
	@RequestMapping(value = {"/", "/agibank"})
	public String uploading(Model model) {
		try {
			List<String> dirList = new ArrayList<String>();
			File file = new File(AppConstants.UPLOADING_DIR);
			File procFile = new File(AppConstants.PROCESSED_FILES);
			File failFile = new File(AppConstants.PROCESSING_FAILURE_DIR);
			File resultFile = new File(AppConstants.READING_DIR);
			dirList.add(file.getPath());
			dirList.add(resultFile.getPath());
			dirList.add(procFile.getPath());
			dirList.add(failFile.getPath());
			model.addAttribute("files", file.listFiles());
			model.addAttribute("procFiles", procFile.listFiles());
			model.addAttribute("failFiles", failFile.listFiles());
			model.addAttribute("result", resultFile.listFiles());
			model.addAttribute("dirList", dirList);
			model.addAttribute("appContextName", appContext.getApplicationName());
			return "uploading";
		} catch(Exception e) {
			model.addAttribute("errorMessage", e);			
			return "error";
		}
    }
	
	/**
	 * 
	 * Upload files
	 * 
	 * @author sergio.melo
	 * 
	 * @param uploadingFiles
	 * @return
	 */
	@Async
    @RequestMapping(value = {"/up", "/agibank/up"}, method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, Model model) { 			    	
        try {
			uploadingService.uploadingPost(uploadingFiles);			
			return ("redirect:/");
		} catch (IOException e) {
			logger.error("file to upload or IO error. Cause: " + e);
    		model.addAttribute("file to upload or IO error. Cause: " + e);    		
    		return ("redirect:error");
		}	         		 
    }
	
}
