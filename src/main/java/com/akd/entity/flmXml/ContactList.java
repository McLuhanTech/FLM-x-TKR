package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class ContactList {

    //联系人列表   [必填]
    List<Contact> Contact;

    @XmlElement(name = "Contact")
    public List<Contact> getContact() {
        return Contact;
    }

    public void setContact(List<Contact> contact) {
        Contact = contact;
    }
}
