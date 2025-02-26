package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class IPAddress {

    //IP地址   [必填]
    String Address;

    //主机名
    String Host;

    @XmlElement(name = "Host")
    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    @XmlElement(name = "Address")
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
