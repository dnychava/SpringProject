package org.idchavan.entity.interfaces;

import java.util.Date;

public interface IMasterMainGroupBO extends IMainBO {

	/**
	 * @return the inEng
	 */
	public String getInEng();

	/**
	 * @param inEng the inEng to set
	 */
	public void setInEng(String inEng);

	/**
	 * @return the inMarathi
	 */
	public String getInMarathi();
	/**
	 * @param inMarathi the inMarathi to set
	 */
	public void setInMarathi(String inMarathi);
	
	/**
	 * @return the inShort
	 */
	public String getInShort();
	
	/**
	 * @param inShort the inShort to set
	 */
	public void setInShort(String inShort);

	/**
	 * @return the sequence
	 */
	public int getSequence();

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence);

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate);
}
