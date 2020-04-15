package ru.kck.usb.zags.fataloiv.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

@XmlRootElement(name = "DeathList")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeathList {

    @XmlElement(name = "Death")
    private List<Death> deathList = null;

    public List<Death> getDeathList() {
        return deathList;
    }

    public void setDeathList(List<Death> deathList) {
        this.deathList = deathList;
    }
}