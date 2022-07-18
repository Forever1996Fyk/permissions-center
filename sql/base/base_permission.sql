create table sys_user
(
    id            bigint                                  not null comment '主键id'
        primary key,
    user_id       bigint                                  not null comment '用户id',
    name          varchar(32)                             not null comment '用户姓名',
    account       varchar(20)   default ''                not null comment '登录账号',
    nick_name     varchar(16)   default ''                not null comment '昵称',
    tenant_id     varchar(64)   default ''                not null comment '租户id',
    post_code     varchar(16)   default ''                not null comment '岗位编码',
    dept_code     varchar(16)   default ''                not null comment '部门编码',
    password      varchar(100)  default ''                not null comment '登录密码',
    phone         varchar(11)   default ''                not null comment '手机号',
    email         varchar(50)   default ''                not null comment '邮箱',
    sex           tinyint(3) unsigned default 1 not null comment '性别(1: 男, 2: 女)',
    avatar        varchar(255)  default ''                not null comment '用于头像路径',
    status        tinyint(3) unsigned default 1 not null comment '账号状态(1:正常,2:停用,3:注销)',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    constraint unique_user_id
        unique (user_id, deleted) comment '账号唯一索引',
    constraint unique_account
        unique (account, deleted) comment '账号唯一索引',
    constraint unique_email
        unique (email, deleted) comment '邮箱唯一索引',
    constraint unique_phone
        unique (phone, deleted) comment '手机号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统用户表';

create table sys_role
(
    id            bigint auto_increment comment '主键id'
        primary key,
    role_code     varchar(16)   default ''                not null comment '角色编码',
    role_name     varchar(32)   default ''                not null comment '角色名称',
    remark        varchar(200)  default ''                not null comment '备注',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除'
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统角色表';

create table sys_menu
(
    id            bigint auto_increment comment '主键id'
        primary key,
    parent_id     bigint        default 0                 not null comment '父菜单id',
    menu_code     varchar(16)   default ''                not null comment '菜单编码',
    menu_name     varchar(32)   default ''                not null comment '菜单名称',
    menu_type     tinyint       default 1                 not null comment '菜单类型',
    menu_order    tinyint(3) default 0 not null comment '菜单序号',
    component     varchar(100)  default ''                not null comment '组件路径',
    menu_path     varchar(100)  default ''                not null comment '菜单url',
    open_type     tinyint(3) unsigned default 1 not null comment '打开方式(1:页面内,2:外链)',
    hidden        tinyint(3) unsigned default 0 not null comment '是否隐藏',
    menu_icon     varchar(50)   default ''                not null comment '菜单图标',
    remark        varchar(200)  default ''                not null comment '备注',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    key           idx_menu_code(menu_code)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统菜单表';

create table sys_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    api_code      varchar(32)   default ''                not null comment '接口编码',
    api_name      varchar(50)   default ''                not null comment '接口名称',
    api_type      tinyint(3) unsigned default 0 not null comment '接口类型',
    api_desc      varchar(200)  default ''                not null comment '接口描述',
    api_url       varchar(100)  default ''                not null comment '请求路径',
    status        tinyint(3) unsigned default 1 not null comment '账号状态(1:启用,2:停用)',
    is_auth       tinyint(3) unsigned default 1 not null comment '是否认证',
    is_open       tinyint(3) unsigned default 1 not null comment '是否公开',
    remark        varchar(200)  default ''                not null comment '备注',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    key           idx_api_code(api_code)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统接口表';

create table sys_user_role
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint        default 0                 not null comment '用户id',
    role_code     varchar(32)                             not null comment '角色编码',
    is_available  tinyint(3) default 1 not null comment '是否可用',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_user_role(user_id, role_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统用户角色表';

create table sys_user_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint        default 0                 not null comment '用户id',
    api_code      varchar(32)                             not null comment '接口编码',
    is_available  tinyint(3) default 1 not null comment '是否可用',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_user_api(user_id, api_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统用户权限表';

create table sys_user_menu
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint        default 0                 not null comment '用户id',
    menu_id       bigint                                  not null comment '菜单id',
    is_available  tinyint(3) default 1 not null comment '是否可用',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_user_menu(user_id, menu_id, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统用户权限表';

create table sys_role_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    role_id       bigint        default 0                 not null comment '角色id',
    api_code      varchar(32)                             not null comment '接口编码',
    is_available  tinyint(3) default 1 not null comment '是否可用',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_role_api(role_id, api_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统角色权限表';

create table sys_role_menu
(
    id            bigint auto_increment comment '主键id'
        primary key,
    role_code     varchar(32)   default ''                not null comment '角色编码',
    menu_id       bigint                                  not null comment '菜单id',
    is_available  tinyint(3) default 1 not null comment '是否可用',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_role_menu(role_code, menu_id, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统角色菜单表';

create table sys_menu_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    menu_id       bigint        default 0                 not null comment '菜单id',
    api_code      varchar(32)                             not null comment '接口编码',
    is_available  tinyint(3) default 1 not null comment '是否可用',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_menu_api(menu_id, api_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统菜单权限表';

create table resource_file
(
    id                bigint auto_increment comment '主键id'
        primary key,
    res_id            bigint                                  not null comment '资源id',
    owner_id          bigint        default 0                 not null comment '所属id',
    position_type     tinyint(3) not null comment '资源位置类型',
    is_public         tinyint(3) default 1 not null comment '是否公开',
    resource_biz_type tinyint(3) not null comment '资源类型',
    file_type         tinyint(3) not null comment '文件类型',
    expire_time       timestamp                               not null comment '过期时间',
    resource_name     varchar(64)   default ''                not null comment '资源名称',
    resource_url      varchar(500)  default ''                not null comment '资源地址',
    resource_path     varchar(500)  default ''                not null comment '资源路径',
    creator           varchar(64)                             not null comment '行记录创建者',
    modifier          varchar(64)                             not null comment '行记录最近更新人',
    created_time      timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time     timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version           tinyint unsigned default 0 not null comment '行版本号',
    extension         varchar(2048) default ''                not null comment '拓展字段',
    deleted           bigint        default 0                 not null comment '是否删除'
) DEFAULT CHARSET = utf8mb4 COMMENT '资源文件表';

create table resource_meta_data
(
    id            bigint auto_increment comment '主键id'
        primary key,
    res_id        bigint                                  not null comment '资源id',
    owner_id      bigint        default 0                 not null comment '所属id',
    file_md5      varchar(255)  default ''                not null comment '文件md5',
    file_sha1     varchar(255)  default ''                not null comment '文件sha1',
    file_size     bigint        default 0                 not null comment '文件大小',
    file_suffix   varchar(10)   default ''                not null comment '文件后缀',
    origin_title  varchar(64)   default ''                not null comment '资源原标题',
    resource_attr varchar(1024) default ''                not null comment '资源属性',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除'
) DEFAULT CHARSET = utf8mb4 COMMENT '资源元数据表';

create table cfg_encrypt
(
    id            bigint auto_increment comment '主键id'
        primary key,
    cipher_text   varchar(255)  default ''                not null comment '密文',
    private_key   varchar(2048) default ''                not null comment '私钥',
    public_key    varchar(2048) default ''                not null comment '公钥',
    original      varchar(255)  default ''                not null comment '原文',
    type          varchar(32)   default ''                not null comment '加密方式',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_cipher(cipher_text, original)
) DEFAULT CHARSET = utf8mb4 COMMENT '加密数据配置表';

-- auto-generated definition
create table sys_import_export_task
(
    id            bigint auto_increment comment 'id'
        primary key,
    task_no       varchar(64) default ''                not null comment '任务号',
    status        int                                   not null comment '任务状态 1 - 待处理 2 - 处理中 3 - 处理成功 4 - 处理失败 5 - 部分成功',
    param         mediumtext                            null comment '参数',
    file_name     varchar(256)                          null comment '文件名称',
    file_url      varchar(256)                          null comment '文件地址',
    ref_file_name varchar(256)                          null comment '关联文件名称',
    ref_file_url  varchar(256)                          null comment '关联文件地址',
    task_type     int                                   not null comment '任务类型 1 - 导入， 2 - 导出',
    biz_type      varchar(64)                           not null comment '详细业务类型',
    error_reason  text                                  null comment '错误原因',
    begin_time    datetime                              null comment '任务开始时间',
    finish_time   datetime                              null comment '任务结束时间',
    env           varchar(64) default ''                not null comment '环境标示',
    creator       varchar(64)                           not null comment '操作人',
    creator_id    bigint                                not null comment '操作人id',
    create_time   timestamp   default CURRENT_TIMESTAMP not null comment '创建时间',
    modify_time   timestamp   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    version       int         default 0                 not null comment '版本号',
    modifier      varchar(64) default ''                not null comment '更新人'
)
    comment '导入导出任务表' charset = utf8mb4;

create index idx_created_time
    on sys_import_export_task (create_time);

create index idx_task_no
    on sys_import_export_task (task_no);

create index idx_task_type
    on sys_import_export_task (task_type, creator_id);


CREATE TABLE `sys_import_template_info`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `template_name` varchar(64) NOT NULL DEFAULT '' COMMENT '模板名称',
    `template_url`  varchar(64) NOT NULL DEFAULT '' COMMENT '模板路径',
    `biz_type`      varchar(64) NOT NULL COMMENT '详细业务类型',
    `status`        int(11) NOT NULL DEFAULT '1' COMMENT '模板状态 1 - 启用 2 - 禁用',
    `creator`       varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `modifier`      varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
    `version`       int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `deleted`       bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_biz_type` (`biz_type`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '导入模板信息';
