package br.com.agibank.dataanalysis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("config")
public class ConfigProperties {

	private String timeJob;
	private String path;
	
	public String getTimeJob() {
		return timeJob;
	}
	public void setTimeJob(String timeJob) {
		this.timeJob = timeJob;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
