package org.idchavan.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MSTR_SHARE_PER")
public class MasterSharePercentageEntity {

	@Id
    @Column(name = "MSTR_SHARE_PER_RID", nullable = false, length = 36)
    private String rid;

    @Column(name = "MSTR_SHARE_PER_SHARE_TYPE", nullable = false, length = 10)
    private String shareType;
    
    @Column(name = "MSTR_SHARE_PER_FROM_DATE", nullable = false)
    private Date fromDate;
    
    @Column(name = "MSTR_SHARE_PER_TO_DATE", nullable = false)
    private Date toDate;
    
    @Column(name = "MSTR_SHARE_PER_PERCENTAGE", nullable = false)
    private int percentage;
    
    @Column(name = "MSTR_SHARE_PER_CREATED_BY", nullable = true, length = 50)
    private String createdBy;

    @Column(name = "MSTR_SHARE_PER_CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "MSTR_SHARE_PER_MODIFIED_BY", nullable = true, length = 50)
    private String modifiedBy;

    @Column(name = "MSTR_SHARE_PER_MODIFIED_DATE", nullable = true)
    private String modifiedDate;
    
    public MasterSharePercentageEntity() {
	}

	public String getRid() {
		if (this.rid == null || "null".equalsIgnoreCase(rid)) {
			this.rid = UUID.randomUUID().toString();
		}
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "MasterSharePercentageEntity [rid=" + rid + ", shareType=" + shareType + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", percentage=" + percentage + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}
}
