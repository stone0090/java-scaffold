MERGE INTO `jws_user`(id, username, password, nickname) VALUES ('1', 'stone', '123456', '小方方'); -- REPLACE INTO
MERGE INTO `jws_user`(id, username, password, nickname) VALUES ('2', 'sophie', '123456', '小黄黄'); -- REPLACE INTO
MERGE INTO `jws_user`(id, username, password, nickname) VALUES ('3', 'tommy', '123456', '小黑黑'); -- REPLACE INTO
MERGE INTO `jws_role`(id, role_code, role_name) VALUES ('1', 'admin', '管理员'); -- REPLACE INTO
MERGE INTO `jws_role`(id, role_code, role_name) VALUES ('2', 'visitor', '游客'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, permission_code, permission_name, permission_url) VALUES ('1', 'insert_user', '新增用户', '/demo/user/add'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, permission_code, permission_name, permission_url) VALUES ('2', 'delete_user', '删除用户', '/demo/user/remove'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, permission_code, permission_name, permission_url) VALUES ('3', 'update_user', '更新用户', '/demo/user/edit'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, permission_code, permission_name, permission_url) VALUES ('4', 'select_one_user', '查询单个用户', '/demo/user/get'); -- REPLACE INTO
MERGE INTO `jws_permission`(id, permission_code, permission_name, permission_url) VALUES ('5', 'select_all_user', '查询全部用户', '/demo/user/list'); -- REPLACE INTO
MERGE INTO `jws_user_role_relation`(id, username, role_code) VALUES ('1', 'stone', 'admin'); -- REPLACE INTO
MERGE INTO `jws_user_role_relation`(id, username, role_code) VALUES ('2', 'sophie', 'admin'); -- REPLACE INTO
MERGE INTO `jws_user_role_relation`(id, username, role_code) VALUES ('3', 'tommy', 'visitor'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, role_code, permission_code) VALUES ('1', 'admin', 'insert_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, role_code, permission_code) VALUES ('2', 'admin', 'delete_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, role_code, permission_code) VALUES ('3', 'admin', 'update_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, role_code, permission_code) VALUES ('4', 'admin', 'select_one_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, role_code, permission_code) VALUES ('5', 'admin', 'select_all_user'); -- REPLACE INTO
MERGE INTO `jws_role_permission_relation`(id, role_code, permission_code) VALUES ('6', 'visitor', 'select_all_user'); -- REPLACE INTO