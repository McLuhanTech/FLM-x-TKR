package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class AuditoriumList {

    //厅列表   [必填]
    List<Auditorium> Auditorium;

    @XmlElement(name = "Auditorium")
    public List<Auditorium> getAuditorium() {
        return Auditorium;
    }

    public void setAuditorium(List<Auditorium> auditorium) {
        Auditorium = auditorium;
    }
}
