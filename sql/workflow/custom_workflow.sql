create table workflow_process
(
    id                     bigint auto_increment comment '主键id'
        primary key,
    name                   varchar(64)   default ''                not null comment '流程名称',
    process_definition_key varchar(64)   default ''                not null comment '流程key',
    process_definition_id  varchar(64)   default ''                not null comment '流程id',
    process_deploy_id      varchar(64)   default ''                not null comment '流程部署id',
    model_id               varchar(64)   default ''                not null comment '流程模型id',
    form_code              varchar(64)   default ''                not null comment '表单编码',
    process_icon           varchar(255)  default ''                not null comment '流程icon',
    process_category       varchar(64)   default ''                not null comment '流程类别',
    status                 tinyint(3) default 0 not null comment '流程状态',
    tenant_id              varchar(64)   default ''                not null comment '租户id',
    creator                varchar(64)                             not null comment '行记录创建者',
    modifier               varchar(64)                             not null comment '行记录最近更新人',
    created_time           timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time          timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version                tinyint unsigned default 0 not null comment '行版本号',
    extension              varchar(2048) default ''                not null comment '拓展字段',
    deleted                bigint        default 0                 not null comment '是否删除'
) DEFAULT CHARSET = utf8mb4 COMMENT '流程设计表';

create table workflow_form_model
(
    id            bigint auto_increment comment '主键id'
        primary key,
    name          varchar(64)   default ''                not null comment '表单名称',
    code          varchar(64)   default ''                not null comment '表单编码',
    form_type     tinyint(3) default 0 not null comment '表单类型',
    content       longtext                                not null comment '表单内容',
    tenant_id     varchar(64)   default ''                not null comment '租户id',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       tinyint unsigned default 0 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    unique key uniq_code(code, deleted)
) DEFAULT CHARSET = utf8mb4 COMMENT '工作流表单模型';

-- auto-generated definition
create table workflow_form_process
(
    id                        bigint auto_increment comment '主键id'
        primary key,
    workflow_process_id       bigint        default 0                 not null comment '流程id',
    process_definition_id     varchar(64)   default ''                not null comment '流程定义id',
    task_key                  varchar(64)   default ''                not null comment '任务key',
    form_content              varchar(64)   default ''                not null comment '表单编码',
    business_title_expression varchar(255)  default ''                not null comment '业务标题表达式',
    tenant_id                 varchar(64)   default ''                not null comment '租户id',
    creator                   varchar(64)                             not null comment '行记录创建者',
    modifier                  varchar(64)                             not null comment '行记录最近更新人',
    created_time              timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time             timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version                   tinyint unsigned default 0 not null comment '行版本号',
    extension                 varchar(2048) default ''                not null comment '拓展字段',
    deleted                   bigint        default 0                 not null comment '是否删除',
    constraint uniq_code
        unique (task_key, deleted)
) comment '工作流表单流程关联表' charset = utf8mb4;



create table workflow_process_listener
(
    id              bigint auto_increment comment '主键id'
        primary key,
    listener_name   varchar(64)   default ''                not null comment '监听器名称',
    listener_type   varchar(32)   default ''                not null comment '监听器类型',
    event           varchar(32)   default ''                not null comment '事件',
    execute_type    varchar(32)   default ''                not null comment '执行类型',
    execute_content varchar(255)  default ''                not null comment '执行内容',
    tenant_id       varchar(64)   default ''                not null comment '租户id',
    creator         varchar(64)                             not null comment '行记录创建者',
    modifier        varchar(64)                             not null comment '行记录最近更新人',
    created_time    timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time   timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version         tinyint unsigned default 0 not null comment '行版本号',
    extension       varchar(2048) default ''                not null comment '拓展字段',
    deleted         bigint        default 0                 not null comment '是否删除'
) DEFAULT CHARSET = utf8mb4 COMMENT '流程监听器';

create table workflow_process_expression
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    expression_name    varchar(64)   default ''                not null comment '监听器名称',
    expression_code    varchar(64)   default ''                not null comment '监听器编码',
    expression_content varchar(255)  default ''                not null comment '执行内容',
    tenant_id          varchar(64)   default ''                not null comment '租户id',
    creator            varchar(64)                             not null comment '行记录创建者',
    modifier           varchar(64)                             not null comment '行记录最近更新人',
    created_time       timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time      timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version            tinyint unsigned default 0 not null comment '行版本号',
    extension          varchar(2048) default ''                not null comment '拓展字段',
    deleted            bigint        default 0                 not null comment '是否删除'
) DEFAULT CHARSET = utf8mb4 COMMENT '流程表达式';

create table workflow_business
(
    id                     bigint auto_increment comment '主键id'
        primary key,
    process_definition_key varchar(64)   default ''                not null comment '流程定义key',
    process_definition_id  varchar(64)   default ''                not null comment '流程定义id',
    process_instance_id    varchar(64)   default ''                not null comment '流程实例id',
    process_id             bigint(20) default 0 not null comment '流程id',
    business_param_json    varchar(2048) default ''                not null comment '业务参数json',
    business_key           varchar(128)  default ''                not null comment '业务key',
    proposer               varchar(128)  default ''                not null comment '申请人',
    flow_status            tinyint(3) default 0 not null comment '流程状态(启动  撤回  驳回  审批中  审批通过  审批异常)',
    handler                varchar(1024) default ''                not null comment '处理人',
    task_id                varchar(64)   default ''                not null comment '当前节点id',
    task_name              varchar(128)  default ''                not null comment '当前节点名称',
    tenant_id              varchar(64)   default ''                not null comment '租户id',
    creator                varchar(64)                             not null comment '行记录创建者',
    modifier               varchar(64)                             not null comment '行记录最近更新人',
    created_time           timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time          timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version                tinyint unsigned default 0 not null comment '行版本号',
    extension              varchar(2048) default ''                not null comment '拓展字段',
    deleted                bigint        default 0                 not null comment '是否删除',
    key                    idx_process_id(process_id),
    unique key uniq_business_key(business_key),
    constraint uniq_instance_id
        unique (process_instance_id)
) DEFAULT CHARSET = utf8mb4 COMMENT '工作流业务表';

create table workflow_task_assign_rule
(
    id                     bigint auto_increment comment '主键id'
        primary key,
    process_definition_key varchar(64)   default ''                not null comment '流程定义key',
    process_definition_id  varchar(64)   default ''                not null comment '流程定义id',
    task_definition_id     varchar(64)   default ''                not null comment '任务定义id',
    task_definition_key    varchar(64)   default ''                not null comment '任务定义key',
    type                   tinyint(3) default 0 not null comment '规则类型',
    options                text null comment '规则集合',
    tenant_id              varchar(64)   default ''                not null comment '租户id',
    creator                varchar(64)                             not null comment '行记录创建者',
    modifier               varchar(64)                             not null comment '行记录最近更新人',
    created_time           timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time          timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version                tinyint unsigned default 0 not null comment '行版本号',
    extension              varchar(2048) default ''                not null comment '拓展字段',
    deleted                bigint        default 0                 not null comment '是否删除',
    unique key uniq_task_definition(task_definition_id, task_definition_key)
) DEFAULT CHARSET = utf8mb4 COMMENT '工作流任务处理人规则表';

-- auto-generated definition
create table workflow_business_task_form
(
    id                        bigint auto_increment comment '主键id'
        primary key,
    workflow_business_id      bigint        default 0                 not null comment '流程业务id',
    task_key                  varchar(64)   default ''                not null comment '任务key',
    name                      varchar(64)   default ''                not null comment '表单名称',
    code                      varchar(64)   default ''                not null comment '表单编码',
    form_type                 tinyint(3) default 0 not null comment '表单类型',
    content                   longtext                                not null comment '表单内容',
    business_title_expression varchar(255)  default ''                not null comment '业务标题表达式',
    tenant_id                 varchar(64)   default ''                not null comment '租户id',
    creator                   varchar(64)                             not null comment '行记录创建者',
    modifier                  varchar(64)                             not null comment '行记录最近更新人',
    created_time              timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time             timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version                   tinyint unsigned default 0 not null comment '行版本号',
    extension                 varchar(2048) default ''                not null comment '拓展字段',
    deleted                   bigint        default 0                 not null comment '是否删除',
    constraint uniq_code
        unique (task_key, workflow_business_id, deleted)
) comment '工作流业务表单任务关联表' charset = utf8mb4;


