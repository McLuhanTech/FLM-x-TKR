package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class KeyInfo {
    X509Data X509Data;

    @XmlElement(name = "X509Data", namespace = "http://www.w3.org/2000/09/xmldsig#")
    public X509Data getX509Data() {
        return X509Data;
    }

    public void setX509Data(X509Data x509Data) {
        X509Data = x509Data;
    }
}
