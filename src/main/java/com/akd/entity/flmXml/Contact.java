package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class Contact {

    //联系人姓名   [必填]
    String Name;

    //联系人电话1
    String Phone1;

    //联系人电话2
    String Phone2;

    //联系人邮件
    String Email;

    //联系人职务
    String Type;

    @XmlElement(name = "Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @XmlElement(name = "Phone1")
    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    @XmlElement(name = "Phone2")
    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    @XmlElement(name = "Email")
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @XmlElement(name = "Type")
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
