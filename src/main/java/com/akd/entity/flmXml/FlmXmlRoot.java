package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FacilityListMessage")
public class FlmXmlRoot {
    //消息的唯一标识   [必填]
    String MessageId;

    //创建时间   [必填]
    String IssueDate;

    //可读名称   [必填]
    String AnnotationText;

    //影院信息   [必填]
    FacilityInfo FacilityInfo;

    //影厅列表   [必填]
    AuditoriumList AuditoriumList;

    @XmlElement(name = "MessageId")
    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    @XmlElement(name = "IssueDate")
    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    @XmlElement(name = "AnnotationText")
    public String getAnnotationText() {
        return AnnotationText;
    }

    public void setAnnotationText(String annotationText) {
        AnnotationText = annotationText;
    }

    @XmlElement(name = "FacilityInfo")
    public FacilityInfo getFacilityInfo() {
        return FacilityInfo;
    }

    public void setFacilityInfo(FacilityInfo facilityInfo) {
        FacilityInfo = facilityInfo;
    }

    @XmlElement(name = "AuditoriumList")
    public AuditoriumList getAuditoriumList() {
        return AuditoriumList;
    }

    public void setAuditoriumList(AuditoriumList auditoriumList) {
        AuditoriumList = auditoriumList;
    }
}
