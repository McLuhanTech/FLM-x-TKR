package com.akd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class KeyDeliveryMessage implements Serializable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = -8906164090689535935L;

    private String filename;

    private String uuid;

    private String annotation;

    private String thumbprint;

    private Integer number;

    private String validBefore;  //密钥过期时间

    private String validAfter;   //密钥开始生效时间

    private String compositionPlaylistId;

    private String cplName;

    private String CN;


    private String operation;

    private String path;

    private int status;

    public static final int NOT_VALID = 0;// 未生效的

    public static final int VALID = 1;// 有效的

    public static final int ABOUT_TO_EXPIRE = 2;// 还有24小时就过期的

    public static final int OUT_OF_DATE = 3;// 已经过期

    public static final int NOT_EXIST_AUDIT = 4;// 在影厅不存在

    public static final int ERROR = -1;// 错误

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCN() {
        return CN;
    }

    public void setCN(String cN) {
        CN = cN;
    }

    public String getCplName() {
        return cplName;
    }

    public void setCplName(String cplName) {
        this.cplName = cplName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Map<Integer, Integer> servers;

    public KeyDeliveryMessage() {
        servers = new HashMap<>();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getValidBefore() {
        return validBefore;
    }

    /**
     * 1 转换时区到中国大陆时区
     * 3 返回时间字符串
     */
    public void setValidBefore(String validBefore) {
        validBefore = changeTimetoTimeZone(validBefore, Locale.SIMPLIFIED_CHINESE);
        this.validBefore = validBefore;
    }

    public String getValidAfter() {
        return validAfter;
    }

    public void setValidAfter(String validAfter) {
        validAfter = changeTimetoTimeZone(validAfter, Locale.SIMPLIFIED_CHINESE);
        this.validAfter = validAfter;
    }

    public static KeyDeliveryMessage create(String filename, String xml)
        throws ParserConfigurationException, SAXException, IOException {
        final KeyDeliveryMessage kdm = new KeyDeliveryMessage();
        kdm.setFilename(filename);
        kdm.setUUID("urn:uuid:00000000-0000-0000-0000-000000000000");
        DefaultHandler handler = new DefaultHandler() {
            private String tag = null;

            @Override
            public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
                if (qName.equals("MessageId") || qName.equals("AnnotationText") || qName
                    .equals("X509SubjectName") || qName.equals("ContentKeysNotValidBefore") || qName
                    .equals("ContentKeysNotValidAfter") || qName.equals("CompositionPlaylistId")) {
                    tag = qName;
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if (qName.equals("MessageId") || qName.equals("AnnotationText") || qName
                    .equals("X509SubjectName") || qName.equals("ContentKeysNotValidBefore") || qName
                    .equals("ContentKeysNotValidAfter") || qName.equals("CompositionPlaylistId")
                    || qName.equals("ContentTitleText")) {
                    tag = null;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (tag != null) {
                    String string = new String(ch, start, length);
                    if (tag.equals("MessageId")) {
                        kdm.setUUID(string);
                    } else if (tag.equals("AnnotationText")) {
                        kdm.setAnnotation(string);
                    } else if (tag.equals("X509SubjectName")) {
                        String[] pairs = string.split(",");
                        for (String pair : pairs) {
                            int pos = pair.indexOf("dnQualifier=");
                            if (pos != -1) {
                                kdm.setThumbprint(
                                    pair.replace("dnQualifier=", "").replace("\134", ""));
                                break;
                            }
                        }
                        for (String pair : pairs) {
                            int cnPos = pair.indexOf("CN=");
                            if (cnPos != -1) {

                                String cnString = pair.replace("CN=", "").replace("\134", "");
                                kdm.setCN(cnString.substring(cnString.indexOf(".") + 1));
                                break;
                            }
                        }
                    } else if (tag.equals("ContentKeysNotValidBefore")) {
                        kdm.setValidAfter(string);
                    } else if (tag.equals("ContentKeysNotValidAfter")) {
                        kdm.setValidBefore(string);
                    } else if (tag.equals("CompositionPlaylistId")) {
                        kdm.setCompositionPlaylistId(string);
                    } else if (tag.equals("ContentTitleText")) {
                        kdm.setCplName(string);
                    }
                }
            }
        };
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(new InputSource(new StringReader(xml)), handler);
        return kdm;
    }

    public String getThumbprint() {
        return thumbprint;
    }

    public void setThumbprint(String thumbprint) {
        this.thumbprint = thumbprint;
    }

    public String getCompositionPlaylistId() {
        return compositionPlaylistId;
    }

    public void setCompositionPlaylistId(String compositionPlaylistId) {
        this.compositionPlaylistId = compositionPlaylistId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 1 接收时间字符串 格式为："2017-02-14T15:51:00+08:00"
     * 2 接收timeZone
     * 3 转换到给定的timeZone 并 返回时间字符串
     */
    public String changeTimetoTimeZone(String timeString, Locale locale) {
        // 对于时间字符串中没有指定时区的 默认为北京时间 GST+8
        if (!timeString.contains("T"))
            return timeString;
        // 转换时时区
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        try {
            Date date = simpleDateFormat.parse(timeString);
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            timeString = simpleDateFormat.format(date);
        } catch (ParseException e) {
            logger.error(
                "changeTimetoTimeZone occur ParseException, timeString: {}, ParseException: {}",
                timeString, e);
        }
        return timeString;
    }
}
