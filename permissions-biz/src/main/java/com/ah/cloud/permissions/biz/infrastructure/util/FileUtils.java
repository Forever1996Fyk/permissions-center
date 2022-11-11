package com.ah.cloud.permissions.biz.infrastructure.util;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.FileSuffixTypeEnum;
import com.ah.cloud.permissions.enums.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import sun.security.krb5.internal.crypto.Des;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 17:22
 **/
@Slf4j
public class FileUtils {

    /**
     * 文件转化
     *
     * @param filePath
     */
    public static File transferTo(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 文件转化
     *
     * @param fis      文件输入流
     * @param filePath 文件路径
     * @return void
     * @author YuKai Fan
     * @date 2020/6/11 22:14
     */
    public static void transferTo(InputStream fis, String filePath) throws IOException {
        byte[] buffer = new byte[4096];
        OutputStream fos = Files.newOutputStream(transferTo(filePath).toPath());
        int len;
        while ((len = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.flush();
    }

    /**
     * 判断当前文件名是否带有后缀, 以及后缀是否正确
     *
     * @param fileName
     * @return
     */
    public static boolean fileNameHasSuffix(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        String[] fullFileName = splitFullFileName(fileName);
        return fullFileName.length == 2;
    }


    /**
     * 分离带有后缀的文件名称
     * <p>
     * 0: 仅文件名fileName
     * 1: 文件后缀 suffix
     *
     * @param fullFileName
     * @return
     */
    public static String[] splitFullFileName(String fullFileName) {
        String[] fullFileNameArray = new String[2];
        int lastIndexOf = StringUtils.lastIndexOf(fullFileName, PermissionsConstants.DOT_SEPARATOR);
        fullFileNameArray[0] = StringUtils.substring(fullFileName, 0, lastIndexOf);
        fullFileNameArray[1] = StringUtils.substring(fullFileName, lastIndexOf + 1, fullFileName.length());
        return fullFileNameArray;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        return splitFullFileName(fileName)[1];
    }

    /**
     * 根据输入流 获取文件SHA1值
     *
     * @param inputStream
     * @return
     */
    public static String getFileSHA1(InputStream inputStream) {
        try {
            return DigestUtils.sha1Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据输入流 获取文件MD5值
     *
     * @param inputStream
     * @return
     */
    public static String getFileMD5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据文件类型获取相应的标识
     *
     * @param value 文件类型
     * @return
     */
    public static FileTypeEnum getFileType(FileSuffixTypeEnum value) {
        return value.convertFileType();
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 根据文件地址获取流
     * @param fileName
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream getInputStream(String fileName, String filePath) throws FileNotFoundException {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        //1.得到数据文件
        File file = new File(filePath);
        FileInputStream fileInputStream;
        try {
            //2.创建流对象
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error("File not found path[{}] name[{}]", filePath, fileName, e);
            throw e;
        }
        return fileInputStream;
    }
}
