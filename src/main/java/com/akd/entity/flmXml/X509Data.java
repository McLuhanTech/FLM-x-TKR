package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class X509Data {

    //主体名称   [必填]
    String X509SubjectName;

    //证书   [必填]
    String X509Certificate;

    @XmlElement(name = "X509SubjectName", namespace = "http://www.w3.org/2000/09/xmldsig#")
    public String getX509SubjectName() {
        return X509SubjectName;
    }

    public void setX509SubjectName(String x509SubjectName) {
        X509SubjectName = x509SubjectName;
    }

    @XmlElement(name = "X509Certificate", namespace = "http://www.w3.org/2000/09/xmldsig#")
    public String getX509Certificate() {
        return X509Certificate;
    }

    public void setX509Certificate(String x509Certificate) {
        X509Certificate = x509Certificate;
    }
}
