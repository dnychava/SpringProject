package org.idchavan.filterDTO;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OrderDetailFilterDTO {

	private String rid;

	private String programName;

	private String category;
	
	private String shareType;
	
	private char completed;
	
	private List<String> ridListForDTLTbl;

	public OrderDetailFilterDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	
	public char getCompleted() {
		return completed;
	}

	public void setCompleted(char completed) {
		this.completed = completed;
	}
	
	/**
	 * @return the ridListForDTLTbl
	 */
	public List<String> getRidListForDTLTbl() {
		return ridListForDTLTbl;
	}

	/**
	 * @param ridListForDTLTbl the ridListForDTLTbl to set
	 */
	public void setRidListForDTLTbl(List<String> ridListForDTLTbl) {
		this.ridListForDTLTbl = ridListForDTLTbl;
	}

	@Override
	public String toString() {
		return "OrderDetailFilterDTO [rid=" + rid + ", programName=" + programName + ", category=" + category
				+ ", shareType=" + shareType + ", completed=" + completed + "]";
	}	
}
