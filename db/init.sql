# 默认密码 admin , 默认手机号 18111111111
INSERT INTO sys_user (id, username, password, real_name, avatar, gender, email, mobile, org_id, super_admin, status,
                      tenant_id, version, deleted, creator, create_time, updater, update_time)
VALUES (1, 'admin', '{bcrypt}$2a$10$bsVuHDhN/tMw78adDp0TF.uZ0NzotMMc6cP3rofPTDTCzq.XXkUvC', null, null, null, null,
        'f877247a1eb044ab12b6eedfbe3213f8', null, 1, 1, null, null, 0, null, NOW(), null, null);

INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (1, 0, '系统设置', null, null, 0, 0, 'SettingOutlined', 1, 0, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (2, 1, '菜单管理', 'sys/menu/index', null, 0, 0, '', 0, 0, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (3, 2, '查看列表', '', 'sys:menu:list', 2, 0, '', 0, 0, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (4, 2, '新增或修改', '', 'sys:menu:add,sys:menu:update,sys:role:info', 1, 0, '', 1, 0, 0, 1, NOW(), null,
        '2024-10-17 22:46:40');
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (6, 2, '删除', '', 'sys:menu:del', 1, 0, '', 3, 0, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (8, 1, '用户管理', 'sys/user/index', null, 0, 0, 'SmileFilled', 2, null, 0, 1, NOW(), null,
        '2024-10-20 19:03:15');
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (9, 1, '角色管理', 'sys/role/index', null, 0, 0, 'PartitionOutlined', 1, null, 0, 1, NOW(), null,
        '2024-10-20 19:06:23');
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (12, 2, '查看', null, 'sys:menu:info', 2, 0, null, 0, null, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (13, 9, '新增或修改', null, 'sys:role:add,sys:role:update,sys:role:info', 1, 0, null, 0, null, 0, 1, NOW(),
        null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (14, 9, '查看列表', null, 'sys:role:list', 2, 0, null, 0, null, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (15, 9, '查看', null, 'sys:role:info', 2, 0, null, 0, null, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (16, 9, '删除', null, 'sys:role:del', 1, 0, null, 0, null, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (17, 8, '添加或修改', null, 'sys:user:add,sys:user:update,sys:user:info', 1, 0, null, 0, null, 0, 1, NOW(),
        null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (18, 8, '查看列表', null, 'sys:user:list', 2, 0, null, 0, null, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (19, 8, '查看', null, 'sys:user:info', 2, 0, null, 0, null, 0, 1, NOW(), null, null);
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted,
                      creator, create_time, updater, update_time)
VALUES (20, 8, '删除', null, 'sys:user:del', 1, 0, null, 0, null, 0, 1, NOW(), null, null);
