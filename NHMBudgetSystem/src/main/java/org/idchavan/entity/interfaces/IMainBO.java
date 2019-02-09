package org.idchavan.entity.interfaces;

import java.util.Date;

public interface IMainBO {

	/**
	 * @return the rid
	 */
	public String getRid();

	/**
	 * @param rid
	 *            the rid to set
	 */
	public void setRid(String rid);

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy();

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy);

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate();

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate);

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy();

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy);

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate();

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate);

}
