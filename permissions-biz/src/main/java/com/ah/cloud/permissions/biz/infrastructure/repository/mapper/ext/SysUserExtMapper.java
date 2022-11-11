package com.ah.cloud.permissions.biz.infrastructure.repository.mapper.ext;

import com.ah.cloud.permissions.biz.domain.user.query.SysUserExportQuery;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.ResultHandler;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 14:26
 **/
@Mapper
public interface SysUserExtMapper extends BaseMapper<SysUser> {

    /**
     * 流式查询导出
     *
     * @param query
     * @param resultHandler
     */
    @Select("<script>" +
            "select account, nick_name, phone, email, sex, dept_code, post_code, status " +
            "from sys_user " +
            "<where> " +
            " 1 = 1 and deleted = 0 " +
            "<if test='account != null'> and account = #{account} </if>" +
            "<if test='nickName != null'> and nick_name like concat('%', #{nickName}, '%') </if>" +
            "<if test='phone != null'> and phone = #{phone} </if>" +
            "<if test='email != null'> and email = #{email} </if>" +
            "<if test='sex != null'> and sex = #{sex} </if>" +
            "<if test='deptCode != null'> and dept_code = #{deptCode} </if>" +
            "<if test='postCode != null'> and post_code = #{postCode} </if>" +
            "<if test='status != null '> and status = #{status} </if>" +
            "</where>" +
            "</script>")
    @ResultType(SysUser.class)
    void streamQueryForExport(SysUserExportQuery query, ResultHandler<SysUser> resultHandler);

}
