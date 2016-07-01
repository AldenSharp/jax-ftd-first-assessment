package com.cooksys.ftd.assessment.filesharing.model.db;

import com.cooksys.ftd.assessment.filesharing.model.db.File;

public class File {

	private Integer fileId;
	private String filepath;
	private byte[] filedata;

	public File() {
		super();
	}

	public File(Integer fileId, String filepath, byte[] filedata) {
		super();
		this.fileId = fileId;
		this.filepath = filepath;
		this.filedata = filedata;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) { 
		this.filedata = filedata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		result = prime * result + ((filepath == null) ? 0 : filepath.hashCode());
		result = prime * result + ((filedata == null) ? 0 : filedata.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		File other = (File) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		if (filepath == null) {
			if (other.filepath != null)
				return false;
		} else if (!filepath.equals(other.filepath))
			return false;
		if (filedata == null) {
			if (other.filedata != null)
				return false;
		} else if (!filedata.equals(other.filedata))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + fileId + ", firstName=" + filepath + ", lastName=" + filedata + "]";
	}

}
