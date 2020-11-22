package br.com.agibank.dataanalysis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("config")
public class ConfigProperties {

	private String timeJob;
	private String pathIn;
	private String pathOut;
	private String pathProcessed;
	private String turnOffTheJob;
	
	public String getTimeJob() {
		return timeJob;
	}
	public void setTimeJob(String timeJob) {
		this.timeJob = timeJob;
	}
	public String getPathIn() {
		return pathIn;
	}
	public void setPathIn(String pathIn) {
		this.pathIn = pathIn;
	}
	public String getPathOut() {
		return pathOut;
	}
	public void setPathOut(String pathOut) {
		this.pathOut = pathOut;
	}
	public String getPathProcessed() {
		return pathProcessed;
	}
	public void setPathProcessed(String pathProcessed) {
		this.pathProcessed = pathProcessed;
	}
	public String getTurnOffTheJob() {
		return turnOffTheJob;
	}
	public void setTurnOffTheJob(String turnOffTheJob) {
		this.turnOffTheJob = turnOffTheJob;
	}
	
}
