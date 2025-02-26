package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Device {

    //PLY-放映服务器，SMS-银幕管理系统，LE-加密链路 ，LD-解密链路，PR-放映机，SM-安全管理模块，TMS-影院管理系统
    //   [必填]
    String DeviceTypeID;

    //设备唯一标识   [必填]
    String DeviceIdentifier;

    //设备序列号
    String DeviceSerial;

    //制造名称   [必填]
    String ManufacturerName;

    //设备型号   [必填]
    String ModelNumber;

    //安装时间
    String InstallDate;

    //设备当前是否处于活动状态   [必填]
    String IsActive;

    //分辨率（2k，4k）
    String Resolution;

    //kdm传输方式
    List<String> KDMDeliveryMethodList;

    //dcp传输方式
    List<String> DCPDeliveryMethodList;

    //ip地址列表
    IPAddressList IPAddressList;

    //设备安全证书链
    KeyInfoList KeyInfoList;

    @XmlElement(name = "DeviceTypeID")
    public String getDeviceTypeID() {
        return DeviceTypeID;
    }

    public void setDeviceTypeID(String deviceTypeID) {
        DeviceTypeID = deviceTypeID;
    }

    @XmlElement(name = "DeviceIdentifier")
    public String getDeviceIdentifier() {
        return DeviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        DeviceIdentifier = deviceIdentifier;
    }

    @XmlElement(name = "DeviceSerial")
    public String getDeviceSerial() {
        return DeviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        DeviceSerial = deviceSerial;
    }

    @XmlElement(name = "ManufacturerName")
    public String getManufacturerName() {
        return ManufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        ManufacturerName = manufacturerName;
    }

    @XmlElement(name = "ModelNumber")
    public String getModelNumber() {
        return ModelNumber;
    }

    public void setModelNumber(String modelNumber) {
        ModelNumber = modelNumber;
    }

    @XmlElement(name = "InstallDate")
    public String getInstallDate() {
        return InstallDate;
    }

    public void setInstallDate(String installDate) {
        InstallDate = installDate;
    }

    @XmlElement(name = "IsActive")
    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    @XmlElement(name = "Resolution")
    public String getResolution() {
        return Resolution;
    }

    public void setResolution(String resolution) {
        Resolution = resolution;
    }

    @XmlElement(name = "IPAddressList")
    public IPAddressList getIPAddressList() {
        return IPAddressList;
    }

    public void setIPAddressList(IPAddressList IPAddressList) {
        this.IPAddressList = IPAddressList;
    }

    @XmlElement(name = "KeyInfoList")
    public KeyInfoList getKeyInfoList() {
        return KeyInfoList;
    }

    public void setKeyInfoList(KeyInfoList keyInfoList) {
        KeyInfoList = keyInfoList;
    }

    @XmlElement(name = "KDMDeliveryMethodList")
    public List<String> getKDMDeliveryMethodList() {
        return KDMDeliveryMethodList;
    }

    public void setKDMDeliveryMethodList(List<String> KDMDeliveryMethodList) {
        this.KDMDeliveryMethodList = KDMDeliveryMethodList;
    }

    @XmlElement(name = "DCPDeliveryMethodList")
    public List<String> getDCPDeliveryMethodList() {
        return DCPDeliveryMethodList;
    }

    public void setDCPDeliveryMethodList(List<String> DCPDeliveryMethodList) {
        this.DCPDeliveryMethodList = DCPDeliveryMethodList;
    }
}
