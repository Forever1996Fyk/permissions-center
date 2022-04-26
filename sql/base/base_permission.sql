
create table sys_user
(
    id            bigint                                        not null comment '主键id'
        primary key,
    user_id       bigint                                        not null comment '用户id',
    name          varchar(32)                                   not null comment '用户姓名',
    account       varchar(20)         default ''                not null comment '登录账号',
    nick_name     varchar(8)          default ''                not null comment '昵称',
    password      varchar(100)        default ''                not null comment '登录密码',
    phone         varchar(11)         default ''                not null comment '手机号',
    email         varchar(50)         default ''                not null comment '邮箱',
    sex           tinyint(3) unsigned default 1                 not null comment '性别(1: 男, 2: 女)',
    avatar        varchar(255)        default ''                not null comment '用于头像路径',
    status        tinyint(3) unsigned default 1                 not null comment '账号状态(1:正常,2:停用,3:注销)',
    creator       varchar(64)                                   not null comment '行记录创建者',
    modifier      varchar(64)                                   not null comment '行记录最近更新人',
    created_time  timestamp           default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp           default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned    default 0                 not null comment '行版本号',
    extension     varchar(2048)       default ''                not null comment '拓展字段',
    deleted       bigint              default 0                 not null comment '是否删除',
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
    role_code     varchar(16)      default ''                not null comment '角色编码',
    role_name     varchar(32)      default ''                not null comment '角色名称',
    remark        varchar(200)     default ''                not null comment '备注',
    creator       varchar(64)                                not null comment '行记录创建者',
    modifier      varchar(64)                                not null comment '行记录最近更新人',
    created_time  timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0                 not null comment '行版本号',
    extension     varchar(2048)    default ''                not null comment '拓展字段',
    deleted       bigint           default 0                 not null comment '是否删除'
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统角色表';

create table sys_menu
(
    id            bigint auto_increment comment '主键id'
        primary key,
    parent_id     bigint              default 0                 not null comment '父菜单id',
    menu_code     varchar(16)         default ''                not null comment '菜单编码',
    menu_name     varchar(32)         default ''                not null comment '菜单名称',
    menu_type     tinyint             default 1                 not null comment '菜单类型',
    menu_order    tinyint(3)          default 0                 not null comment '菜单序号',
    component     varchar(100)        default ''                not null comment '组件路径',
    menu_path     varchar(100)        default ''                not null comment '菜单url',
    open_type     tinyint(3) unsigned default 1                 not null comment '打开方式(1:页面内,2:外链)',
    hidden        tinyint(3) unsigned default 0                 not null comment '是否隐藏',
    menu_icon     varchar(50)         default ''                not null comment '菜单图标',
    remark        varchar(200)        default ''                not null comment '备注',
    creator       varchar(64)                                   not null comment '行记录创建者',
    modifier      varchar(64)                                   not null comment '行记录最近更新人',
    created_time  timestamp           default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp           default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned    default 0                 not null comment '行版本号',
    extension     varchar(2048)       default ''                not null comment '拓展字段',
    deleted       bigint              default 0                 not null comment '是否删除',
    key idx_menu_code(menu_code)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统菜单表';

create table sys_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    api_code      varchar(32)         default ''                not null comment '接口编码',
    api_name      varchar(50)         default ''                not null comment '接口名称',
    api_type      tinyint(3) unsigned         default 0                not null comment '接口类型',
    api_desc      varchar(200)        default ''                not null comment '接口描述',
    api_url       varchar(100)        default ''                not null comment '请求路径',
    status        tinyint(3) unsigned default 1                 not null comment '账号状态(1:启用,2:停用)',
    is_auth       tinyint(3) unsigned default 1                 not null comment '是否认证',
    is_open       tinyint(3) unsigned default 1                 not null comment '是否公开',
    remark        varchar(200)        default ''                not null comment '备注',
    creator       varchar(64)                                   not null comment '行记录创建者',
    modifier      varchar(64)                                   not null comment '行记录最近更新人',
    created_time  timestamp           default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp           default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned    default 0                 not null comment '行版本号',
    extension     varchar(2048)       default ''                not null comment '拓展字段',
    deleted       bigint              default 0                 not null comment '是否删除',
    key idx_api_code(api_code)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统接口表';

create table sys_user_role
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint           default 0                 not null comment '用户id',
    role_code     varchar(32)                                not null comment '角色编码',
    is_available      tinyint(3)    default 1                           not null comment '是否可用',
    creator       varchar(64)                                not null comment '行记录创建者',
    modifier      varchar(64)                                not null comment '行记录最近更新人',
    created_time  timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0                 not null comment '行版本号',
    extension     varchar(2048)    default ''                not null comment '拓展字段',
    deleted       bigint           default 0                 not null comment '是否删除',
    unique key uniq_user_role(user_id, role_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统用户角色表';

create table sys_user_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint           default 0                 not null comment '用户id',
    api_code      varchar(32)                                not null comment '接口编码',
    is_available      tinyint(3)    default 1                           not null comment '是否可用',
    creator       varchar(64)                                not null comment '行记录创建者',
    modifier      varchar(64)                                not null comment '行记录最近更新人',
    created_time  timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0                 not null comment '行版本号',
    extension     varchar(2048)    default ''                not null comment '拓展字段',
    deleted       bigint           default 0                 not null comment '是否删除',
    unique key uniq_user_api(user_id, api_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统用户权限表';

create table sys_user_menu
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint           default 0                 not null comment '用户id',
    menu_id       bigint                               not null comment '菜单id',
    is_available      tinyint(3)    default 1                           not null comment '是否可用',
    creator       varchar(64)                                not null comment '行记录创建者',
    modifier      varchar(64)                                not null comment '行记录最近更新人',
    created_time  timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0                 not null comment '行版本号',
    extension     varchar(2048)    default ''                not null comment '拓展字段',
    deleted       bigint           default 0                 not null comment '是否删除',
    unique key uniq_user_menu(user_id, menu_id, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统用户权限表';

create table sys_role_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    role_id       bigint           default 0                 not null comment '角色id',
    api_code      varchar(32)                                not null comment '接口编码',
    is_available      tinyint(3)    default 1                           not null comment '是否可用',
    creator       varchar(64)                                not null comment '行记录创建者',
    modifier      varchar(64)                                not null comment '行记录最近更新人',
    created_time  timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0                 not null comment '行版本号',
    extension     varchar(2048)    default ''                not null comment '拓展字段',
    deleted       bigint           default 0                 not null comment '是否删除',
    unique key uniq_role_api(role_id, api_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统角色权限表';

create table sys_role_menu
(
    id            bigint auto_increment comment '主键id'
        primary key,
    role_code     varchar(32)           default ''                 not null comment '角色编码',
    menu_id       bigint                               not null comment '菜单id',
    is_available      tinyint(3)    default 1                           not null comment '是否可用',
    creator       varchar(64)                                not null comment '行记录创建者',
    modifier      varchar(64)                                not null comment '行记录最近更新人',
    created_time  timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0                 not null comment '行版本号',
    extension     varchar(2048)    default ''                not null comment '拓展字段',
    deleted       bigint           default 0                 not null comment '是否删除',
    unique key uniq_role_menu(role_code, menu_id, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统角色菜单表';

create table sys_menu_api
(
    id            bigint auto_increment comment '主键id'
        primary key,
    menu_id       bigint           default 0                 not null comment '菜单id',
    api_code      varchar(32)                                not null comment '接口编码',
    is_available      tinyint(3)    default 1                           not null comment '是否可用',
    creator       varchar(64)                                not null comment '行记录创建者',
    modifier      varchar(64)                                not null comment '行记录最近更新人',
    created_time  timestamp        default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0                 not null comment '行版本号',
    extension     varchar(2048)    default ''                not null comment '拓展字段',
    deleted       bigint           default 0                 not null comment '是否删除',
    unique key uniq_menu_api(menu_id, api_code, deleted)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '系统菜单权限表';


