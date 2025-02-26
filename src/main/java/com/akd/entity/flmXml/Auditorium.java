package com.akd.entity.flmXml;

import javax.xml.bind.annotation.XmlElement;

public class Auditorium {

    //厅号   [必填]
    String AuditoriumNumber;

    //厅名   [必填]
    String AuditoriumName;

    //银幕画幅比（1.85、2.39、1.66、1.37等）
    String ScreenAspectRatio;

    //银幕画幅（top：SCOPE，side：flat，both是2048*1080）
    String AdjustableScreenMask;

    //音频类型(eg:5.1)
    String AudioFormat;

    //安装日期
    String AuditoriumInstallDate;

    //大荧幕格式(中国巨幕（CGS），杜比全景声（ATMOS），IMAX)
    String LargeFormatType;

    //银幕类型（silver，white，other）
    String ScreenType;

    //品牌类型
    String PLF;

    //光源类型
    String LSF;

    //数字3d系统
    Digital3DSystem Digital3DSystem;

    //影厅设备列表    [必填]
    DeviceGroupList DeviceGroupList;

    @XmlElement(name = "AuditoriumName")
    public String getAuditoriumName() {
        return AuditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        AuditoriumName = auditoriumName;
    }

    @XmlElement(name = "ScreenAspectRatio")
    public String getScreenAspectRatio() {
        return ScreenAspectRatio;
    }

    public void setScreenAspectRatio(String screenAspectRatio) {
        ScreenAspectRatio = screenAspectRatio;
    }

    @XmlElement(name = "AdjustableScreenMask")
    public String getAdjustableScreenMask() {
        return AdjustableScreenMask;
    }

    public void setAdjustableScreenMask(String adjustableScreenMask) {
        AdjustableScreenMask = adjustableScreenMask;
    }

    @XmlElement(name = "AudioFormat")
    public String getAudioFormat() {
        return AudioFormat;
    }

    public void setAudioFormat(String audioFormat) {
        AudioFormat = audioFormat;
    }

    @XmlElement(name = "AuditoriumInstallDate")
    public String getAuditoriumInstallDate() {
        return AuditoriumInstallDate;
    }

    public void setAuditoriumInstallDate(String auditoriumInstallDate) {
        AuditoriumInstallDate = auditoriumInstallDate;
    }

    @XmlElement(name = "LargeFormatType")
    public String getLargeFormatType() {
        return LargeFormatType;
    }

    public void setLargeFormatType(String largeFormatType) {
        LargeFormatType = largeFormatType;
    }

    @XmlElement(name = "ScreenType")
    public String getScreenType() {
        return ScreenType;
    }

    public void setScreenType(String screenType) {
        ScreenType = screenType;
    }

    @XmlElement(name = "PLF")
    public String getPLF() {
        return PLF;
    }

    public void setPLF(String PLF) {
        this.PLF = PLF;
    }

    @XmlElement(name = "LSF")
    public String getLSF() {
        return LSF;
    }

    public void setLSF(String LSF) {
        this.LSF = LSF;
    }

    @XmlElement(name = "Digital3DSystem")
    public Digital3DSystem getDigital3DSystem() {
        return Digital3DSystem;
    }

    public void setDigital3DSystem(Digital3DSystem digital3DSystem) {
        this.Digital3DSystem = digital3DSystem;
    }

    @XmlElement(name = "DeviceGroupList")
    public DeviceGroupList getDeviceGroupList() {
        return DeviceGroupList;
    }

    public void setDeviceGroupList(DeviceGroupList deviceGroupList) {
        DeviceGroupList = deviceGroupList;
    }

    @XmlElement(name = "AuditoriumNumber")
    public String getAuditoriumNumber() {
        return AuditoriumNumber;
    }

    public void setAuditoriumNumber(String auditoriumNumber) {
        AuditoriumNumber = auditoriumNumber;
    }
}
