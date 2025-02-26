package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class AddressList {

    //地址列表   [必填]
    List<Address> Address;
    @XmlElement(name = "Address")
    public List<Address> getAddress() {
        return Address;
    }

    public void setAddress(List<Address> address) {
                Address = address;
    }


}
