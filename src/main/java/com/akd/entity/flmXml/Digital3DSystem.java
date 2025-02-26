package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class Digital3DSystem {

    //是否可用   [必填]
    String IsActive;

    //3D配置（RealD，Dolby 3D）
    String Digital3DConfiguration;

    //安装时间
    String InstallDate;

    //银幕亮度（1-29）
    Integer ScreenLuminance;

    //是否支持ghostbusting技术
    String Ghostbusting;

    //ghostbusting配置的详细信息
    String GhostbustingConfiguration;

    @XmlElement(name = "IsActive")
    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    @XmlElement(name = "Digital3DConfiguration")
    public String getDigital3DConfiguration() {
        return Digital3DConfiguration;
    }

    public void setDigital3DConfiguration(String digital3DConfiguration) {
        Digital3DConfiguration = digital3DConfiguration;
    }

    @XmlElement(name = "InstallDate")
    public String getInstallDate() {
        return InstallDate;
    }

    public void setInstallDate(String installDate) {
        InstallDate = installDate;
    }

    @XmlElement(name = "ScreenLuminance")
    public Integer getScreenLuminance() {
        return ScreenLuminance;
    }

    public void setScreenLuminance(Integer screenLuminance) {
        ScreenLuminance = screenLuminance;
    }

    @XmlElement(name = "Ghostbusting")
    public String getGhostbusting() {
        return Ghostbusting;
    }

    public void setGhostbusting(String ghostbusting) {
        Ghostbusting = ghostbusting;
    }

    @XmlElement(name = "GhostbustingConfiguration")
    public String getGhostbustingConfiguration() {
        return GhostbustingConfiguration;
    }

    public void setGhostbustingConfiguration(String ghostbustingConfiguration) {
        GhostbustingConfiguration = ghostbustingConfiguration;
    }
}
