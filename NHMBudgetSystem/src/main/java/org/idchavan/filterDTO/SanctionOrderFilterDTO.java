package org.idchavan.filterDTO;

import org.springframework.stereotype.Component;

@Component
public class SanctionOrderFilterDTO {
	
	private String year;
	
	private String programs;
	
	private String categories;
	
	private String docFormatType;
	
	public SanctionOrderFilterDTO() {
		
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPrograms() {
		return programs;
	}

	public void setPrograms(String programs) {
		this.programs = programs;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}
	
	public String getDocFormatType() {
		return docFormatType;
	}

	public void setDocFormatType(String docFormatType) {
		this.docFormatType = docFormatType;
	}

	@Override
	public String toString() {
		return "SanctionOrderFilterDTO [year=" + year + ", programs=" + programs + ", categories=" + categories + "]";
	}
}
