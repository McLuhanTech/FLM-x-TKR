package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class IPAddressList {

    List<IPAddress> IPAddress;

    @XmlElement(name = "IPAddress")
    public List<IPAddress> getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(List<IPAddress> IPAddress) {
        this.IPAddress = IPAddress;
    }
}
