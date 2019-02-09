package org.idchavan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.idchavan.entity.interfaces.IMasterProgramNameBO;

@Entity
@Table(
		name="MSTR_PROGRAM_NAME",
		uniqueConstraints={
				@UniqueConstraint(
						columnNames={"MSTR_PROGRAM_NAME_ACCOUNT_HEAD", "MSTR_PROGRAM_NAME_CATEGORY", "MSTR_PROGRAM_NAME_SHARE" },
						name="UK__MSTR_PROGRAM_NAME__ACCOUNTHEAD_CATEGORY_SHARETYPE"
					)
			}
		)
public class MasterProgramNameEntityImpl extends AbstractBaseEntity implements  IMasterProgramNameBO, Serializable {

	private static final long serialVersionUID = -65828250059762343L;
	
	private String mstrMainGroupRid = null;
	
	private String inEng;

	private String inMarathi;

	private String inShort;
	
	private String accountHead;
	
	private String category;
	
	private String shareType;
	
	public MasterProgramNameEntityImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the mstrMainGroupRid
	 */
	@Column(name="MSTR_PROGRAM_NAME__MSTR_MAIN_GROUP_RID",  nullable=false, length=36)
	public String getMstrMainGroupRid() {
		return mstrMainGroupRid;
	}

	/**
	 * @param mstrMainGroupRid the mstrMainGroupRid to set
	 */
	public void setMstrMainGroupRid(String mstrMainGroupRid) {
		this.mstrMainGroupRid = mstrMainGroupRid;
	}

	/**
	 * @return the inEng
	 */
	@Column(name="MSTR_PROGRAM_NAME_IN_ENG",  nullable=false, length=500)
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
	@Column(name="MSTR_PROGRAM_NAME_IN_MARATHI",  nullable=false, length=500)
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
	@Column(name="MSTR_PROGRAM_NAME_IN_SHORT",  nullable=false, length=500)
	public String getInShort() {
		return inShort;
	}

	/**
	 * @param inShort the inShort to set
	 */
	public void setInShort(String inShort) {
		this.inShort = inShort;
	}

	/**
	 * @return the accountHead
	 */
	@Column(name="MSTR_PROGRAM_NAME_ACCOUNT_HEAD",  nullable=false, length=50)
	public String getAccountHead() {
		return accountHead;
	}

	/**
	 * @param accountHead the accountHead to set
	 */
	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}

	/**
	 * @return the category
	 */
	@Column(name="MSTR_PROGRAM_NAME_CATEGORY",  nullable=false, length=50)
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the shareType
	 */
	@Column(name="MSTR_PROGRAM_NAME_SHARE",  nullable=false, length=50)
	public String getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the shareType to set
	 */
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	@Id
	@Column(name="MSTR_PROGRAM_NAME_RID", unique = true, nullable=false, length=36)
	@Override
	public String getRid() {
		return super.getRid();
	}

	
	@Override
	public void setRid(String rid) {
		super.setRid(rid);
		
	}

	@Column(name="MSTR_PROGRAM_NAME_CREATED_BY", nullable=true, length=15)
	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	public void setCreatedBy(String createdBy) {
		super.setCreatedBy(createdBy);
	}

	@Column(name="MSTR_PROGRAM_NAME_CREATED_TIMESTAMP", nullable=true)
	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		super.setCreatedDate(createdDate);
		
	}

	@Column(name="MSTR_PROGRAM_NAME_MODIFIED_BY", nullable=true, length=15)
	@Override
	public String getModifiedBy() {
		return super.getModifiedBy();
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		super.setModifiedBy(modifiedBy);
		
	}

	@Column(name="MSTR_PROGRAM_NAME_MODIFIED_TIMESTAMP", nullable=true)
	@Override
	public Date getModifiedDate() {
		return super.getModifiedDate();
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		super.setModifiedDate(modifiedDate);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MasterProgramNameEntityImpl [inEng=" + inEng + ", inMarathi=" + inMarathi + ", inShort=" + inShort
				+ ", accountHead=" + accountHead + ", category=" + category + ", shareType=" + shareType + "]";
	}
	
	
}
