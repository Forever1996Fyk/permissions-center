package com.ah.cloud.permissions.biz.infrastructure.util;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.FileSuffixTypeEnum;
import com.ah.cloud.permissions.enums.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
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
     * 上传本地文件
     * @param inputStream
     * @param path
     * @param fileName
     * @throws IOException
     */
    public static void uploadLocal(InputStream inputStream, String path, String fileName) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream outputStream = new FileOutputStream(file + "/" + fileName);
        IOUtils.copy(inputStream, outputStream);
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
        fullFileNameArray[1] = StringUtils.substring(fullFileName, lastIndexOf, lastIndexOf + 1);
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
     * @param fis
     * @return
     */
    public static String getFileSHA1(InputStream fis) {
        byte[] buffer = new byte[4096];
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            int len;
            while ((len = fis.read(buffer)) != -1) {
                sha1.update(buffer, 0, len);
            }
            return new BigInteger(1, sha1.digest()).toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据输入流 获取文件MD5值
     *
     * @param fis
     * @return
     */
    public static String getFileMD5(InputStream fis) {
        byte[] buffer = new byte[4096];
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int len;
            while ((len = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            return new BigInteger(1, md5.digest()).toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
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
}
