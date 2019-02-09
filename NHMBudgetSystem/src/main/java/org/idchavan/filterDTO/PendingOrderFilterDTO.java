package org.idchavan.filterDTO;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class PendingOrderFilterDTO {
	
	private String year;
	
	private Date fromDate;
	
	private Date toDate; 
	
	private String shareType;
	
	private String programs;
	
	private String categories;
	
	private String docFormatType;
	
	public PendingOrderFilterDTO() {
		
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the shareType
	 */
	public String getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the shareType to set
	 */
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	/**
	 * @return the programs
	 */
	public String getPrograms() {
		return programs;
	}

	/**
	 * @param programs the programs to set
	 */
	public void setPrograms(String programs) {
		this.programs = programs;
	}

	/**
	 * @return the categories
	 */
	public String getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(String categories) {
		this.categories = categories;
	}
	
	
	public String getDocFormatType() {
		return docFormatType;
	}

	public void setDocFormatType(String docFormatType) {
		this.docFormatType = docFormatType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PendingOrderFilterDTO [year=" + year + ", fromDate=" + fromDate + ", toDate=" + toDate + ", shareType="
				+ shareType + ", programs=" + programs + ", categories=" + categories + "]";
	}

	
}
