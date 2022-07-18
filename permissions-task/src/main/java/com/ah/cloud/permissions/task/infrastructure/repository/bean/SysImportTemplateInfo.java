package com.ah.cloud.permissions.task.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 导入模板信息
 * </p>
 *
 * @author auto_generation
 * @since 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_import_template_info")
public class SysImportTemplateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板路径
     */
    private String templateUrl;

    /**
     * 详细业务类型
     */
    private String bizType;

    /**
     * 模板状态 1 - 启用 2 - 禁用
     */
    private Integer status;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 是否删除
     */
    private Long deleted;


}
