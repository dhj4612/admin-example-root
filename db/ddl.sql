create table sys_menu
(
    id          int auto_increment comment 'id'
        primary key,
    pid         int          null comment '上级ID',
    name        varchar(200) null comment '菜单名称',
    url         varchar(200) null comment '菜单URL',
    authority   varchar(500) null comment '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)',
    type        int          null comment '类型   0：菜单   1：按钮   2：接口',
    open_style  int          null comment '打开方式   0：内部   1：外部',
    icon        varchar(50)  null comment '菜单图标',
    sort        int          null comment '排序',
    version     int          null comment '版本号',
    deleted     int          null comment '删除标识  0：正常   1：已删除',
    creator     int          null comment '创建者',
    create_time datetime     null comment '创建时间',
    updater     int          null comment '更新者',
    update_time datetime     null comment '更新时间'
)
    comment '菜单管理';

create index idx_pid
    on sys_menu (pid);


create table sys_role
(
    id          int auto_increment comment 'id'
        primary key,
    name        varchar(50)  null comment '角色名称',
    role_code   varchar(50)  null comment '角色编码',
    remark      varchar(100) null comment '备注',
    data_scope  int          null comment '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据',
    org_id      int          null comment '机构ID',
    tenant_id   int          null comment '租户ID',
    version     int          null comment '版本号',
    deleted     int          null comment '删除标识  0：正常   1：已删除',
    creator     int          null comment '创建者',
    create_time datetime     null comment '创建时间',
    updater     int          null comment '更新者',
    update_time datetime     null comment '更新时间'
)
    comment '角色管理';

create index idx_org_id
    on sys_role (org_id);


create table sys_role_menu
(
    id          int auto_increment comment 'id'
        primary key,
    role_id     int      null comment '角色ID',
    menu_id     int      null comment '菜单ID',
    version     int      null comment '版本号',
    deleted     int      null comment '删除标识  0：正常   1：已删除',
    creator     int      null comment '创建者',
    create_time datetime null comment '创建时间',
    updater     int      null comment '更新者',
    update_time datetime null comment '更新时间'
)
    comment '角色菜单关系';

create index idx_menu_id
    on sys_role_menu (menu_id);

create index idx_role_id
    on sys_role_menu (role_id);


create table sys_user
(
    id          int auto_increment comment 'id'
        primary key,
    username    varchar(50)  not null comment '用户名',
    password    varchar(500) null comment '密码',
    real_name   varchar(50)  null comment '姓名',
    avatar      varchar(200) null comment '头像',
    gender      int          null comment '性别   0：男   1：女   2：未知',
    email       varchar(500) null comment '邮箱',
    mobile      varchar(500) null comment '手机号',
    org_id      int          null comment '机构ID',
    super_admin int          null comment '超级管理员   0：否   1：是',
    status      int          null comment '状态  0：停用   1：正常',
    tenant_id   int          null comment '租户ID',
    version     int          null comment '版本号',
    deleted     int          null comment '删除标识  0：正常   1：已删除',
    creator     int          null comment '创建者',
    create_time datetime     null comment '创建时间',
    updater     int          null comment '更新者',
    update_time datetime     null comment '更新时间'
)
    comment '用户管理';

create table sys_user_role
(
    id          int auto_increment comment 'id'
        primary key,
    role_id     int      null comment '角色ID',
    user_id     int      null comment '用户ID',
    version     int      null comment '版本号',
    deleted     int      null comment '删除标识  0：正常   1：已删除',
    creator     int      null comment '创建者',
    create_time datetime null comment '创建时间',
    updater     int      null comment '更新者',
    update_time datetime null comment '更新时间'
)
    comment '用户角色关系';

create index idx_role_id
    on sys_user_role (role_id);

create index idx_user_id
    on sys_user_role (user_id);
