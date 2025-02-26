package com.akd.entity.flmXml;


import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class DeviceGroupList {

    //设备分组列表   [必填]
    List<DeviceGroup> DeviceGroup;

    @XmlElement(name = "DeviceGroup")
    public List<DeviceGroup> getDeviceGroup() {
        return DeviceGroup;
    }

    public void setDeviceGroup(List<DeviceGroup> deviceGroup) {
        DeviceGroup = deviceGroup;
    }
}
