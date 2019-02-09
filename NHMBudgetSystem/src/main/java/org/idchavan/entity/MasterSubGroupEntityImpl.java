package org.idchavan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.idchavan.entity.interfaces.IMasterMainGroupBO;
import org.idchavan.entity.interfaces.IMasterSubGroupBO;

@Entity
@Table(name = "MSTR_SUB_GROUP")
public class MasterSubGroupEntityImpl extends AbstractBaseEntity implements IMasterSubGroupBO, Serializable {
	
	private static final long serialVersionUID = -7937991757137292039L;

	private String inEng;

	private String inMarathi;

	private String inShort;
	
	private MasterMainGroupEntityImpl mstrMainGrpEnitityImpl;
	
	public MasterSubGroupEntityImpl() {
	}
	
	
	
	/**
	 * @return the inEng
	 */
	@Column(name="MSTR_SUB_GROUP_IN_ENG",  nullable=false, length=500)
	public String getInEng() {
		return inEng;
	}

	/**
	 * @param inEng the inEng to set
	 */
	public void setInEng(String inEng) {
		this.inEng = inEng;
	}

	/**
	 * @return the inMarathi
	 */
	@Column(name="MSTR_SUB_GROUP_IN_MARATHI",  nullable=false, length=500)
	public String getInMarathi() {
		return inMarathi;
	}

	/**
	 * @param inMarathi the inMarathi to set
	 */
	public void setInMarathi(String inMarathi) {
		this.inMarathi = inMarathi;
	}

	/**
	 * @return the inShort
	 */
	@Column(name="MSTR_SUB_GROUP_IN_SHORT",  nullable=false, length=500)
	public String getInShort() {
		return inShort;
	}

	/**
	 * @param inShort the inShort to set
	 */
	public void setInShort(String inShort) {
		this.inShort = inShort;
	}

	@Id
	@Column(name="MSTR_SUB_GROUP_RID", nullable=false, length=36)
	@Override
	public String getRid() {
		return super.getRid();
	}

	
	@Override
	public void setRid(String rid) {
		super.setRid(rid);
		
	}

	@Column(name="MSTR_SUB_GROUP_CREATED_BY", nullable=true, length=15)
	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	public void setCreatedBy(String createdBy) {
		super.setCreatedBy(createdBy);
	}

	@Column(name="MSTR_SUB_GROUP_CREATED_TIMESTAMP", nullable=true)
	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		super.setCreatedDate(createdDate);
		
	}

	@Column(name="MSTR_SUB_GROUP_MODIFIED_BY", nullable=true, length=15)
	@Override
	public String getModifiedBy() {
		return super.getModifiedBy();
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		super.setModifiedBy(modifiedBy);
		
	}

	@Column(name="MSTR_SUB_GROUP_MODIFIED_TIMESTAMP", nullable=true)
	@Override
	public Date getModifiedDate() {
		return super.getModifiedDate();
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		super.setModifiedDate(modifiedDate);
		
	}

	
}
