package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class KeyInfoList {

    List<KeyInfo> KeyInfo;

    @XmlElement(name = "KeyInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
    public List<KeyInfo> getKeyInfo() {
        return KeyInfo;
    }

    public void setKeyInfo(List<KeyInfo> keyInfo) {
        KeyInfo = keyInfo;
    }
}
