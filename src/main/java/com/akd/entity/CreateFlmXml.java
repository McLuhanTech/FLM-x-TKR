package com.akd.entity;

import com.akd.entity.flmXml.*;
import com.akd.utils.DateUtilCommon;
import com.akd.utils.FileUtil;
import com.akd.utils.HttpUtils;
import com.akd.utils.XMLUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class CreateFlmXml {

    public static final String TOKEN = "100010_5+CGg7POye4UAvUNmUqY9g==";

    public static final String HOST = "http://127.0.0.1:8111";

    public static final String ADD_URL = "/api/v1/flm/add";

    public static final String UPDATE_URL =  "/api/v1/flm/update";


    public static void main(String[] args) throws InterruptedException {

        //-------------- 影院信息  start --------------//
        AddressList addressList = new AddressList();
        Address address = new Address();
        address.setAddressee("晶鑫商业广场");
        address.setStreetAddress("大寨路与民洁路十字");
        address.setCity("西安");
        address.setProvince("陕西");
        address.setPostalCode("710000");
        address.setCountry("CN");
        List<Address> addressLists = new ArrayList();
        addressLists.add(address);
        addressList.setAddress(addressLists);

        List<Contact> contactsList = new ArrayList<>();
        Contact contact = new Contact();
        contact.setName("任XX");
        contact.setPhone1("18688888888");
        contactsList.add(contact);
        ContactList contactList = new ContactList();
        contactList.setContact(contactsList);

        FacilityInfo facilityInfo = new FacilityInfo();
        facilityInfo.setContactList(contactList);
        facilityInfo.setAddressList(addressList);
        facilityInfo.setFacilityID("urn:uuid:a8cbc990-fc19-48fb-8b41-8b31ce799cfd");
        facilityInfo.setFacilityName("Daxing Cinemas");
        facilityInfo.setFacilityTimeZone("aisa/beijing");
        facilityInfo.setFLMPartial("False");
        //-------------- 影院信息  end --------------//


        //-------------- 影厅设备信息  start --------------//
        AuditoriumList auditoriumList = new AuditoriumList();
        List<Auditorium> auditoriumLists = new ArrayList<>();
        Auditorium auditorium = new Auditorium();
        auditorium.setAuditoriumName("1号厅");
        auditorium.setAuditoriumNumber("1");
        auditorium.setAdjustableScreenMask("top");
        auditorium.setAudioFormat("5.1");
        auditorium.setAuditoriumInstallDate("2018-12-25T16:59:46");
        auditorium.setLargeFormatType("ATMOS");
        auditorium.setScreenType("white");
        Digital3DSystem digital3DSystem = new Digital3DSystem();
        digital3DSystem.setIsActive("true");
        digital3DSystem.setDigital3DConfiguration("Dolby 3D");
        digital3DSystem.setScreenLuminance(20);
        auditorium.setDigital3DSystem(digital3DSystem);
        auditorium.setScreenAspectRatio("1.85");
        DeviceGroupList deviceGroupList = new DeviceGroupList();
        List<DeviceGroup> deviceGroupLists = new ArrayList<>();
        DeviceGroup deviceGroup = new DeviceGroup();
        List<Device> devices = new ArrayList<>();
        Device device = new Device();
        device.setDeviceIdentifier("qU/QbqVZ7HegT4pJlo8li0SYPEs=");
        device.setDeviceSerial("A43598");
        device.setDeviceTypeID("PLY");
        device.setIsActive("true");
        device.setResolution("2k");
        device.setManufacturerName("gdc");
        device.setModelNumber("sa-2100");
        device.setInstallDate("2018-12-25T16:59:46");
        IPAddressList ipAddressList = new IPAddressList();
        List<IPAddress> ipAddressLists = new ArrayList<>();
        IPAddress ipAddress = new IPAddress();
        ipAddress.setAddress("192.168.8.22");
        ipAddress.setHost("");
        ipAddressLists.add(ipAddress);
        ipAddressList.setIPAddress(ipAddressLists);
        device.setIPAddressList(ipAddressList);
        KeyInfoList keyInfoList = new KeyInfoList();
        List<KeyInfo> keyInfoLists = new ArrayList<>();
        KeyInfo keyInfo = new KeyInfo();
        X509Data x509Data = new X509Data();
        x509Data.setX509Certificate("MIIEcjCCA1qgAwIBAgIDAn0MMA0GCSqGSIb3DQEBCwUAMIGOMSUwIwYDVQQuExxSZEdQdlBOS2g2OFk2bEh0Mm5TcU9YQmV3cUU9MRgwFgYDVQQLEw9DQS5HREMtVEVDSC5DT00xGDAWBgNVBAoTD0NBLkdEQy1URUNILkNPTTExMC8GA1UEAxMoLlNBMjEwMC5TRVJWRVJTLlBST0RVQ1RTLkNBLkdEQy1URUNILkNPTTAeFw0xNTAyMDYxNTE2MTZaFw0yNTAyMDMxNTE2MTZaMIGhMSUwIwYDVQQuExxxVS9RYnFWWjdIZWdUNHBKbG84bGkwU1lQRXM9MRgwFgYDVQQLEw9DQS5HREMtVEVDSC5DT00xGDAWBgNVBAoTD0NBLkdEQy1URUNILkNPTTFEMEIGA1UEAxM7TUQgRk0gU00gU1BCLkE0MzU5OC5TQTIxMDAuU0VSVkVSUy5QUk9EVUNUUy5DQS5HREMtVEVDSC5DT00wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC494acv8AZKrSTActvB6KWAz2cjmq8xs/sYta8zQTxrjiKe6DRJWWr4BnyvFiZdDRunpwzgbxRi5Y6XtffUWroKnyKi1XUSrAA5IlDIm/3BnWZzR2UNENPv9+QEafVo48OysWs8lqVN84kBUyyFMf5naQ+rkUTiy/qatnV89ypZ42G50mgH4ONWrzQrBAJPORauVnZF71znUynr3YuT14Vjaspb5EcHhkC1kjlQFZoW7twj1Y+KI8kG/p8iOc+JEXgjCXHFSiRrgZ/GrDyqiBbOEInOK7ldbcAbxBP3k1BuuO5SeUCXuJLMMRC+EnMDexR/SUQw8xNPTXSB2uwcVWVAgMBAAGjgcMwgcAwDgYDVR0PAQH/BAQDAgWgMAwGA1UdEwEB/wQCMAAwgZ8GA1UdIwSBlzCBlKGBjaSBijCBhzElMCMGA1UELhMcUWRIRlZzbk1ud2ZJQ0c1OWg3MDlxRy9BdDk4PTEYMBYGA1UECxMPQ0EuR0RDLVRFQ0guQ09NMRgwFgYDVQQKEw9DQS5HREMtVEVDSC5DT00xKjAoBgNVBAMTIS5TRVJWRVJTLlBST0RVQ1RTLkNBLkdEQy1URUNILkNPTYICBCUwDQYJKoZIhvcNAQELBQADggEBAE2PqYm0k2cFSBc+OIIe35uDp1hfzMWCPq0+bDwpuG382MnRuPcD54rh+y5KV0f6FZ9xSP4qVln7IR6XhiBco3zOJsWIRPjg6y1AGMYnJoS5FiqkSvR3Ci6q2RPJTy+hOGnzgzSxab6fEhtNx3py8k+XfvwKUMC9+svg5iRFyyXgAL+ydufQEnL/6AZKikNOWfxS+u00ZeaUyMwaZGXR/scS3xO4Wy5eYDHLR7rr4XwQ5ZQeU8BBZ9ljt7khHnk5bneHWYkbQylgdtLf30cdRiEPfLNby2ZXr8q/1wB9anItUmnMo7gkShn7Z8rW/4PWyOCJYNDZT9/dzZpU/ZLxpFA=");
        x509Data.setX509SubjectName("dnQualifier=qU/QbqVZ7HegT4pJlo8li0SYPEs=,OU=CA.GDC-TECH.COM,O=CA.GDC-TECH.COM,CN=MD FM SM SPB.A43598.SA2100.SERVERS.PRODUCTS.CA.GDC-TECH.COM");
        keyInfo.setX509Data(x509Data);
        keyInfoLists.add(keyInfo);
        keyInfoList.setKeyInfo(keyInfoLists);
        device.setKeyInfoList(keyInfoList);
        List<String> kdmDeliveryMethodList = new ArrayList<>();
        kdmDeliveryMethodList.add("ftp");
        device.setKDMDeliveryMethodList(kdmDeliveryMethodList);
        List<String> dcpDeliveryMethodList = new ArrayList<>();
        dcpDeliveryMethodList.add("ftp");
        device.setDCPDeliveryMethodList(dcpDeliveryMethodList);
        devices.add(device);
        deviceGroup.setDevice(devices);
        deviceGroupLists.add(deviceGroup);
        deviceGroupList.setDeviceGroup(deviceGroupLists);
        auditorium.setDeviceGroupList(deviceGroupList);
        auditoriumLists.add(auditorium);
        auditoriumList.setAuditorium(auditoriumLists);
        //-------------- 影厅设备信息  end --------------//

        FlmXmlRoot flmXmlRoot = new FlmXmlRoot();
        flmXmlRoot.setAnnotationText("1");
        flmXmlRoot.setAuditoriumList(auditoriumList);
        flmXmlRoot.setFacilityInfo(facilityInfo);
        flmXmlRoot.setIssueDate("2015-12-25T16:59:46");
        flmXmlRoot.setMessageId("urn:uuid:f8cbc990-fc19-48fb-8b41-8b31ce688cfd");
        String xml = XMLUtil.convertToXml(flmXmlRoot);
        xml = FileUtil.readToString("/Users/miaoyaogong/Downloads/add.txt");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("token",TOKEN);
        paramMap.put("flm",xml);
        List<String> resList = new ArrayList<>();
        Runnable runnable = () ->{
            String httpRes = HttpUtils.doPost(HOST + ADD_URL, null, paramMap);
            System.out.println(httpRes);
        };
        String resListStr = "请求--开始时间：" + DateUtilCommon.getDefaultDateStr(new Date());
        for(int i =0; i<100; i++){
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        }
        resListStr = resListStr + ", 结束时间：" + DateUtilCommon.getDefaultDateStr(new Date());
        System.out.println(JSONObject.toJSONString(resListStr));

    }

}
