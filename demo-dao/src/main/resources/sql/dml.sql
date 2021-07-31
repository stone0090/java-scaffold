MERGE INTO `jws_user`(id, gmt_create, gmt_modified, username, password) VALUES ('1', now(), now(), 'stone', '123456'); -- REPLACE INTO
MERGE INTO `jws_user`(id, gmt_create, gmt_modified, username, password) VALUES ('2', now(), now(), 'tommy', '123456'); -- REPLACE INTO
MERGE INTO `jws_role`(id, gmt_create, gmt_modified, role_code, role_name) VALUES ('1', now(), now(), 'admin', '管理员'); -- REPLACE INTO
MERGE INTO `jws_role`(id, gmt_create, gmt_modified, role_code, role_name) VALUES ('2', now(), now(), 'visitor', '游客'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, gmt_create, gmt_modified, permission_code, permission_name, permission_url) VALUES ('1', now(), now(), 'insert_user', '新增用户', '/user/add'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, gmt_create, gmt_modified, permission_code, permission_name, permission_url) VALUES ('2', now(), now(), 'delete_user', '删除用户', '/user/remove'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, gmt_create, gmt_modified, permission_code, permission_name, permission_url) VALUES ('3', now(), now(), 'update_user', '更新用户', '/user/edit'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, gmt_create, gmt_modified, permission_code, permission_name, permission_url) VALUES ('4', now(), now(), 'select_one_user', '查询单个用户', '/user/get'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, gmt_create, gmt_modified, permission_code, permission_name, permission_url) VALUES ('5', now(), now(), 'select_all_user', '查询全部用户', '/user/list'); -- REPLACE INTO
MERGE INTO `jws_user_role_relation`(id, gmt_create, gmt_modified, username, role_code) VALUES ('1', now(), now(), 'stone', 'admin'); -- REPLACE INTO
MERGE INTO `jws_user_role_relation`(id, gmt_create, gmt_modified, username, role_code) VALUES ('2', now(), now(), 'tommy', 'visitor'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, gmt_create, gmt_modified, role_code, permission_code) VALUES ('1', now(), now(), 'admin', 'insert_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, gmt_create, gmt_modified, role_code, permission_code) VALUES ('2', now(), now(), 'admin', 'delete_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, gmt_create, gmt_modified, role_code, permission_code) VALUES ('3', now(), now(), 'admin', 'update_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, gmt_create, gmt_modified, role_code, permission_code) VALUES ('4', now(), now(), 'admin', 'select_one_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, gmt_create, gmt_modified, role_code, permission_code) VALUES ('5', now(), now(), 'admin', 'select_all_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, gmt_create, gmt_modified, role_code, permission_code) VALUES ('6', now(), now(), 'visitor', 'select_all_user'); -- REPLACE INTO