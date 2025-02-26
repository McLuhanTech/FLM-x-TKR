package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class FacilityInfo {


    //设备唯一ID   [必填]
    String FacilityID;

    //设备名称   [必填]
    String FacilityName;

    //TZ格式的设施的时区名称
    String FacilityTimeZone;

    // 院线
    String Circuit;

    //是否是局部   [必填]
    String FLMPartial;

    //地址   [必填]
    AddressList AddressList;

    //联系人   [必填]
    ContactList ContactList;

    @XmlElement(name = "FacilityID")
    public String getFacilityID() {
        return FacilityID;
    }

    public void setFacilityID(String facilityID) {
        FacilityID = facilityID;
    }
    @XmlElement(name = "FacilityName")
    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String facilityName) {
        FacilityName = facilityName;
    }

    @XmlElement(name = "FacilityTimeZone")
    public String getFacilityTimeZone() {
        return FacilityTimeZone;
    }

    public void setFacilityTimeZone(String facilityTimeZone) {
        FacilityTimeZone = facilityTimeZone;
    }

    @XmlElement(name = "Circuit")
    public String getCircuit() {
        return Circuit;
    }

    public void setCircuit(String circuit) {
        Circuit = circuit;
    }

    @XmlElement(name = "FLMPartial")
    public String getFLMPartial() {
        return FLMPartial;
    }

    public void setFLMPartial(String FLMPartial) {
        this.FLMPartial = FLMPartial;
    }

    @XmlElement(name = "AddressList")
    public AddressList getAddressList() {
        return AddressList;
    }

    public void setAddressList(AddressList addressList) {
        AddressList = addressList;
    }

    @XmlElement(name = "ContactList")
    public ContactList getContactList() {
        return ContactList;
    }

    public void setContactList(ContactList contactList) {
        ContactList = contactList;
    }
}
