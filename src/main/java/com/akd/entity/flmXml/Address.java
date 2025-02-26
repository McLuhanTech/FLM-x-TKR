package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class Address {

    //详情地址   [必填]
    String Addressee;

    //街道   [必填]
    String StreetAddress;

    //街道2
    String StreetAddress2;

    //街道   [必填]
    String City;

    //省市   [必填]
    String Province;

    //邮编
    String PostalCode;

    //ISO3166国家代码(cn,us)   [必填]
    String Country;

    @XmlElement(name = "Addressee")
    public String getAddressee() {
        return Addressee;
    }

    public void setAddressee(String addressee) {
        Addressee = addressee;
    }

    @XmlElement(name = "StreetAddress")
    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    @XmlElement(name = "StreetAddress2")
    public String getStreetAddress2() {
        return StreetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        StreetAddress2 = streetAddress2;
    }

    @XmlElement(name = "City")
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    @XmlElement(name = "Province")
    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    @XmlElement(name = "PostalCode")
    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    @XmlElement(name = "Country")
    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
