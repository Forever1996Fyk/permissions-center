package com.ah.cloud.permissions.edi.infrastructure.constant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 14:32
 **/
public class EdiConstants {

    /**
     * 默认edi扫描最大数量
     */
    public final static Integer DEFAULT_SCAN_RECORD_PAGE_SIZE = 1000;
    /**
     * edi标记
     */
    public final static String EDI = "EDI";

    /**
     * tech-edi标记
     */
    public final static String TECH_EDI = "TECH_EDI";

    /**
     * edi记录最大错误信息长度
     */
    public final static Integer RETRY_RECORD_MAX_ERROR_MESSAGE_LENGTH = 500;

    /**
     * int 1
     */
    public final static Integer ONE_INT = 1;


    /**
     * Long 0
     */
    public final static Long ZERO = 0L;

    /**
     * 限制一条记录sql
     */
    public final static String LIMIT_SQL = "limit 1";

    /**
     * 下划线
     */
    public static final String SEPARATOR_UNDERLINE = "_";
}
