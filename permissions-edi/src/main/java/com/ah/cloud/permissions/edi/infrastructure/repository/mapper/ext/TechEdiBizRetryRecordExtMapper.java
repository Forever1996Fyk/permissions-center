package com.ah.cloud.permissions.edi.infrastructure.repository.mapper.ext;

import com.ah.cloud.permissions.edi.application.manager.RetryResourceManager;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordScanQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 14:39
 **/
@Mapper
public interface TechEdiBizRetryRecordExtMapper {

    /**
     * 获取tech重试记录最大id
     *
     * @param env
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    @Select("<script>"
            + " SELECT MAX(id) FROM " + RetryResourceManager.DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME + " " +
            "WHERE "
            + " record_status in (3,0) "
            + " AND deleted = 0 "
            + " AND env = #{env} "
            + "<if test='createTimeStart!=null' and test='createTimeEnd!=null'>"
            + " AND create_time>=#{createTimeStart} AND create_time &lt;= #{createTimeEnd}"
            + "</if>"
            + "</script>"
    )
    Long getRetryMaxId(String env, Date createTimeStart, Date createTimeEnd);

    /**
     * 获取tech重试记录最小id
     *
     * @param env
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    @Select("<script>"
            + " SELECT MIN(id) FROM " + RetryResourceManager.DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME + " " +
            "WHERE "
            + " record_status in (3,0) "
            + " AND deleted = 0 "
            + " AND env = #{env} "
            + "<if test='createTimeStart!=null' and test='createTimeEnd!=null'>"
            + " AND create_time>=#{createTimeStart} AND  create_time &lt;= #{createTimeEnd}"
            + "</if>"
            + "</script>"
    )
    Long getRetryMinId(String env, Date createTimeStart, Date createTimeEnd);


    /**
     * 获取重试记录集合
     *
     * @param query
     * @return
     */
    @Select("<script>"
            + "SELECT id, biz_no, biz_id, biz_type, biz_source, error_message, last_op_time, record_status," +
            "biz_params, retry_times, " +
            "  version, ext, remark, creator, modifier, create_time, modify_time, deleted, sharding_key, " +
            "env FROM " + RetryResourceManager.DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME + " WHERE "
            + " record_status in (3,0) "
            + " AND deleted = 0 "
            + " AND env = #{env} "
            + " and id > #{minId,jdbcType=BIGINT} "
            + " and id &lt;= #{maxId,jdbcType=BIGINT} "
            + "<if test='createTimeStart!=null' and test='createTimeEnd!=null'>"
            + " and create_time>=#{createTimeStart} and  create_time &lt;= #{createTimeEnd}"
            + "</if>"
            + " ORDER BY id "
            + " LIMIT ${maxQuerySize} "
            + "</script>"
    )
    List<RetryBizRecord> listRetryBizRecord(RetryBizRecordScanQuery query);

    /**
     * 获取状态为重试中的最小记录id
     * @param env
     * @return
     */
    @Select("<script>"
            + " SELECT MIN(id) FROM " + RetryResourceManager.DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME + " " +
            "WHERE "
            + " record_status = 1 "
            + " AND deleted = 0 "
            + " AND env = #{env} "
            + "</script>"
    )
    Long getResetMinId(String env);

    /**
     * 获取状态为重试中的最大记录id
     *
     * @param env
     * @return
     */
    @Select("<script>"
            + " SELECT MAX(id) FROM " + RetryResourceManager.DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME + " " +
            "WHERE "
            + " record_status = 1 "
            + " AND deleted = 0 "
            + " AND env = #{env} "
            + "</script>"
    )
    Long getResetMaxId(String env);

    /**
     * 获取状态为重试中的重试记录集合
     *
     * @param minId
     * @param maxId
     * @param limit
     * @param env
     * @return
     */
    @Select("<script>"
            + "SELECT id, biz_no, biz_id, biz_type, biz_source, error_message, last_op_time, record_status," +
            "biz_params, retry_times, " +
            "  version, ext, remark, creator, modifier, create_time, modify_time, deleted, sharding_key, " +
            "env FROM " + RetryResourceManager.DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME + " WHERE "
            + " record_status = 1 "
            + " AND deleted = 0 "
            + " AND env = #{env} "
            + " and id > #{minId,jdbcType=BIGINT} "
            + " and id &lt;= #{maxId,jdbcType=BIGINT} "
            + " ORDER BY id "
            + " LIMIT ${limit} "
            + "</script>"
    )
    List<RetryBizRecord> listResetRecord(Long minId, Long maxId, Integer limit, String env);
}
