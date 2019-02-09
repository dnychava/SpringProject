package org.idchavan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.idchavan.entity.interfaces.IMasterMainGroupBO;

@Entity
@Table(
		name = "MSTR_MAIN_GROUP",
		uniqueConstraints={
				@UniqueConstraint(
						columnNames={"MSTR_MAIN_GROUP_SQNS_NUMBR" },
						name="UK__MSTR_MAIN_GROUP__SQNS_NUMBR"
					)
			}
)
public class MasterMainGroupEntityImpl extends AbstractBaseEntity implements IMasterMainGroupBO, Serializable {
	
	private static final long serialVersionUID = -2964788344301337674L;

	private String inEng;

	private String inMarathi;

	private String inShort;

	private int sequence;
	
	public MasterMainGroupEntityImpl() {
	}
	
	/**
	 * @return the inEng
	 */
	@Column(name="MSTR_MAIN_GROUP_IN_ENG",  nullable=false, length=500)
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
	@Column(name="MSTR_MAIN_GROUP_IN_MARATHI",  nullable=false, length=500)
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
	@Column(name="MSTR_MAIN_GROUP_IN_SHORT",  nullable=false, length=500)
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
	 * @return the sequence
	 */
	@Column(name="MSTR_MAIN_GROUP_SQNS_NUMBR",  nullable=false)
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Id
	@Column(name="MSTR_MAIN_GROUP_RID", nullable=false, length=36)
	@Override
	public String getRid() {
		return super.getRid();
	}

	
	@Override
	public void setRid(String rid) {
		super.setRid(rid);
		
	}

	@Column(name="MSTR_MAIN_GROUP_CREATED_BY", nullable=true, length=15)
	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	public void setCreatedBy(String createdBy) {
		super.setCreatedBy(createdBy);
	}

	@Column(name="MSTR_MAIN_GROUP_CREATED_TIMESTAMP", nullable=true)
	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		super.setCreatedDate(createdDate);
		
	}

	@Column(name="MSTR_MAIN_GROUP_MODIFIED_BY", nullable=true, length=15)
	@Override
	public String getModifiedBy() {
		return super.getModifiedBy();
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		super.setModifiedBy(modifiedBy);
		
	}

	@Column(name="MSTR_MAIN_GROUP_MODIFIED_TIMESTAMP", nullable=true)
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
		return "MasterMainGroupEntityImpl [inEng=" + inEng + ", inMarathi=" + inMarathi + ", inShort=" + inShort
				+ ", sequence=" + sequence + "]";
	}
}
