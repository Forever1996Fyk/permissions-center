package com.ah.cloud.permissions.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: YK-Platform
 * @description: 文件后缀类型枚举
 * @author: YuKai Fan
 * @create: 2022-05-06 21:20
 **/
public enum FileSuffixTypeEnum {
    /**
     * 未知
     */
    UNKNOWN("unknown"),
    /**
     * JEPG.
     */
    JPEG("jpeg"),

    /**
     * PNG.
     */
    PNG("png"),

    /**
     * GIF.
     */
    GIF("gif"),

    /**
     * JPG.
     */
    JPG("jpg"),

    /**
     * TIFF.
     */
    TIFF("tiff"),

    /**
     * Windows Bitmap.
     */
    BMP("bmp"),

    /**
     * CAD.
     */
    DWG("dwg"),

    /**
     * Adobe Photoshop.
     */
    PSD("psd"),


    /**
     * svg
     */
    SVG("svg"),

    /**
     * Rich Text Format.
     */
    RTF("rtf"),

    /**
     * XML.
     */
    XML("xml"),

    /**
     * HTML.
     */
    HTML("html"),
    /**
     * CSS.
     */
    CSS("css"),
    /**
     * JS.
     */
    JS("js"),
    /**
     * Email [thorough only].
     */
    EML("eml"),

    /**
     * Outlook Express.
     */
    DBX("dbx"),

    /**
     * Outlook (pst).
     */
    PST("pst"),

    /**
     * MS Word/Excel.
     */
    XLS_DOC("xls"), XLSX_DOCX("xlsx"),
    DOC("doc"),DOCX("docx"), TXT("txt"),PPT("ppt"),
    /**
     * Visio
     */
    VSD("vsd"),
    /**
     * MS Access.
     */
    MDB("mdb"),
    /**
     * WPS文字wps、表格et、演示dps都是一样的
     */
    WPS("d0cf11e0a1b11ae10000"),
    /**
     * torrent
     */
    TORRENT("torrent"),
    /**
     * WordPerfect.
     */
    WPD("wpd"),

    /**
     * Postscript.
     */
    EPS("eps"),

    /**
     * Adobe Acrobat.
     */
    PDF("pdf"),

    /**
     * Quicken.
     */
    QDF("qdf"),

    /**
     * Windows Password.
     */
    PWL("pwl"),

    /**
     * ZIP Archive.
     */
    ZIP("zip"),

    /**
     * RAR Archive.
     */
    RAR("rar"),
    /**
     * JSP Archive.
     */
    JSP("jsp"),
    /**
     * JAVA Archive.
     */
    JAVA("java"),
    /**
     * CLASS Archive.
     */
    CLASS("class"),
    /**
     * JAR Archive.
     */
    JAR("jar"),
    /**
     * MF Archive.
     */
    MF("mf"),
    /**
     *EXE Archive.
     */
    EXE("exe"),
    /**
     *CHM Archive.
     */
    CHM("chm"),
    /*
     * INI("235468697320636F6E66"), SQL("494E5345525420494E54"), BAT(
     * "406563686F206f66660D"), GZ("1F8B0800000000000000"), PROPERTIES(
     * "6C6F67346A2E726F6F74"), MXP(
     * "04000000010000001300"),
     */
    /**
     * Wave.
     */
    WAV("wav"),

    /**
     * AVI.
     */
    AVI("avi"),

    /**
     * Real Audio.
     */
    RAM("ram"),

    /**
     * Real Media.
     */
    RM("rm"),

    /**
     * MPEG (mpg).
     */
    MPG("mpg"),

    /**
     * Quicktime.
     */
    MOV("mov"),

    /**
     * Windows Media.
     */
    ASF("asf"),

    /**
     * MIDI.
     */
    MID("mid"),
    /**
     * MP4.
     */
    MP4("mp4"),
    /**
     * MP3.
     */
    MP3("mp3"),
    /**
     * FLV.
     */
    FLV("flv");

    private String value = "";

    /**
     * Constructor.
     *
     * @param value
     */
    FileSuffixTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 图片后缀集合
     */
    public static List<FileSuffixTypeEnum> IMAGE_LIST = new ArrayList<FileSuffixTypeEnum>() {
        {
            add(JPEG);
            add(JPG);
            add(GIF);
            add(PNG);
            add(BMP);
            add(TIFF);
            add(DWG);
            add(PSD);
            add(SVG);
        }
    };

    /**
     * 文档后缀集合
     */
    public static List<FileSuffixTypeEnum> DOC_LIST = new ArrayList<FileSuffixTypeEnum>() {
        {
            add(RTF);add(XML);add(HTML);add(CSS);add(JS);add(EML);add(DBX);add(PST);add(VSD);add(MDB);
            add(WPS);add(WPD);add(EPS);add(PDF);add(QDF);add(PWL);add(ZIP);add(RAR);add(JSP);add(JAVA);
            add(CLASS);add(JAR);add(MF);add(EXE);add(CHM);add(DOC);add(DOCX);add(TXT);add(PPT);
        }
    };

    /**
     * 表格后缀集合
     */
    public static List<FileSuffixTypeEnum> EXCEL_LIST = new ArrayList<FileSuffixTypeEnum>() {
        {
            add(XLS_DOC);add(XLSX_DOCX);
        }
    };

    /**
     * 表格后缀集合
     */
    public static List<FileSuffixTypeEnum> VIDEO_LIST = new ArrayList<FileSuffixTypeEnum>() {
        {
            add(AVI);add(RAM);add(RM);add(MPG);add(MOV);add(ASF);add(MP4);add(FLV);add(MID);
        }
    };

    /**
     * 表格后缀集合
     */
    public static List<FileSuffixTypeEnum> TOTTENT_LIST = new ArrayList<FileSuffixTypeEnum>() {
        {
            add(TORRENT);
        }
    };

    /**
     * 音频后缀集合
     */
    public static List<FileSuffixTypeEnum> AUDIO_LIST = new ArrayList<FileSuffixTypeEnum>() {
        {
            add(WAV);add(MP3);
        }
    };

    public FileTypeEnum convertFileType() {
        if (IMAGE_LIST.contains(this)) {
            return FileTypeEnum.IMAGE;
        }
        if (EXCEL_LIST.contains(this)) {
            return FileTypeEnum.EXCEL;
        }
        if (VIDEO_LIST.contains(this)) {
            return FileTypeEnum.VIDEO;
        }
        if (DOC_LIST.contains(this)) {
            return FileTypeEnum.DOC;
        }
        if (AUDIO_LIST.contains(this)) {
            return FileTypeEnum.AUDIO;
        }
        if (TOTTENT_LIST.contains(this)) {
            return FileTypeEnum.TOTTENTS;
        }
        return FileTypeEnum.OTHER;
    }

    public static FileSuffixTypeEnum getByType(String value) {
        FileSuffixTypeEnum[] values = values();
        for (FileSuffixTypeEnum fileSuffixTypeEnum : values) {
            if (Objects.equals(value, fileSuffixTypeEnum.getValue())) {
                return fileSuffixTypeEnum;
            }
        }
        return UNKNOWN;
    }
}
