CREATE TABLE sys_user
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    username    varchar(50) NOT NULL COMMENT '用户名',
    password    varchar(100) COMMENT '密码',
    real_name   varchar(50) COMMENT '姓名',
    avatar      varchar(200) COMMENT '头像',
    gender      tinyint COMMENT '性别   0：男   1：女   2：未知',
    email       varchar(100) COMMENT '邮箱',
    mobile      varchar(20) COMMENT '手机号',
    org_id      bigint COMMENT '机构ID',
    super_admin tinyint COMMENT '超级管理员   0：否   1：是',
    status      tinyint COMMENT '状态  0：停用   1：正常',
    tenant_id   bigint COMMENT '租户ID',
    version     int COMMENT '版本号',
    deleted     tinyint COMMENT '删除标识  0：正常   1：已删除',
    creator     bigint COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    updater     bigint COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='用户管理';

create table sys_role
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    name        varchar(50) COMMENT '角色名称',
    role_code   varchar(50) COMMENT '角色编码',
    remark      varchar(100) COMMENT '备注',
    data_scope  tinyint COMMENT '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据',
    org_id      bigint COMMENT '机构ID',
    tenant_id   bigint COMMENT '租户ID',
    version     int COMMENT '版本号',
    deleted     tinyint COMMENT '删除标识  0：正常   1：已删除',
    creator     bigint COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    updater     bigint COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    primary key (id),
    key idx_org_id (org_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='角色管理';

create table sys_user_role
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    role_id     bigint COMMENT '角色ID',
    user_id     bigint COMMENT '用户ID',
    version     int COMMENT '版本号',
    deleted     tinyint COMMENT '删除标识  0：正常   1：已删除',
    creator     bigint COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    updater     bigint COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    primary key (id),
    key idx_role_id (role_id),
    key idx_user_id (user_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='用户角色关系';

create table sys_menu
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    pid         bigint COMMENT '上级ID',
    name        varchar(200) COMMENT '菜单名称',
    url         varchar(200) COMMENT '菜单URL',
    authority   varchar(500) COMMENT '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)',
    type        tinyint COMMENT '类型   0：菜单   1：按钮   2：接口',
    open_style  tinyint COMMENT '打开方式   0：内部   1：外部',
    icon        varchar(50) COMMENT '菜单图标',
    sort        int COMMENT '排序',
    version     int COMMENT '版本号',
    deleted     tinyint COMMENT '删除标识  0：正常   1：已删除',
    creator     bigint COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    updater     bigint COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    primary key (id),
    key idx_pid (pid)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='菜单管理';

create table sys_role_menu
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    role_id     bigint COMMENT '角色ID',
    menu_id     bigint COMMENT '菜单ID',
    version     int COMMENT '版本号',
    deleted     tinyint COMMENT '删除标识  0：正常   1：已删除',
    creator     bigint COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    updater     bigint COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    primary key (id),
    key idx_role_id (role_id),
    key idx_menu_id (menu_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='角色菜单关系';
