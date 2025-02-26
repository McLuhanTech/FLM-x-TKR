package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class DeviceGroup {

    //设备   [必填]
    List<Device> Device;

    @XmlElement(name = "Device")
    public List<Device> getDevice() {
        return Device;
    }

    public void setDevice(List<Device> device) {
        Device = device;
    }
}
