package org.idchavan.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "DOC_TBL")
public class DocumentEntity {

    @Id
    @Column(name = "DOC_TBL_RID", nullable = false, length = 36)
    private String rid;

    @Column(name = "DOC_TBL_FILE_NAME", nullable = false, length = 200)
    private String fileName;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "DOC_TBL_CONTENT", nullable = false)
    private byte[] content = "".getBytes();

    @Column(name = "DOC_TBL_DESCRIPTION", nullable = true, length = 225)
    private String description;

    @Column(name = "DOC_TBL_CREATED_BY", nullable = true, length = 15)
    private String createdBy;

    @Column(name = "DOC_TBL_CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "DOC_TBL_MODIFIED_BY", nullable = true, length = 15)
    private String modifiedBy;

    @Column(name = "DOC_TBL_MODIFIED_DATE", nullable = true)
    private String modifiedDate;

    public DocumentEntity() {
        
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "DocumentEntity [rid=" + rid + ", fileName=" + fileName + ", content=" + Arrays.toString(content)
				+ ", description=" + description + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}
	
}
