package org.idchavan.common;

import org.springframework.stereotype.Component;

@Component
public class FileInfo {

	private String orignalFileName;
	
	private String filePath;
	
	private String RenameFileName;
	
	private String fileUploadMsg;
	
	public FileInfo() {
	}

	public String getOrignalFileName() {
		return orignalFileName;
	}

	public void setOrignalFileName(String orignalFileName) {
		this.orignalFileName = orignalFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getRenameFileName() {
		return RenameFileName;
	}

	public void setRenameFileName(String renameFileName) {
		RenameFileName = renameFileName;
	}

	public String getFileUploadMsg() {
		return fileUploadMsg;
	}

	public void setFileUploadMsg(String fileUploadMsg) {
		this.fileUploadMsg = fileUploadMsg;
	}

	@Override
	public String toString() {
		return "FileInfo [orignalFileName=" + orignalFileName + ", filePath=" + filePath + ", RenameFileName="
				+ RenameFileName + ", fileUploadMsg=" + fileUploadMsg + "]";
	}
}
