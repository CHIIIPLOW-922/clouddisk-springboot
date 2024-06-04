### clouddisk网盘系统设计

#### 系统架构

- 前端框架： Electron、Vue3、ElementPlus(UI库)、Axios(接口请求)
- 后端框架：SpringBoot、Mybatis-plus(orm)
- 数据库：MYSQL、Redis
- OSS（对象存储系统）:MinIO



#### 设计思路

系统包含模块：用户模块、文件模块、后台管理、文件分享模块(待定后面看着做)

用户模块设计：用户id用雪花算法生成(确保被拿到id也不会暴露系统使用用户数量)，用户可以通过邮箱验收验证码来注册用户，用户昵称，用户头像path，用户密码通过md5加密并存入数据库，存入用户账号、用户加密后密码、注册邮箱。登录时可以通过邮箱登录（邮箱登录就需要验证码而不输入密码）或者账号登录需要输入密码，验证码就存入redis并设置过期时间。还有设计字段存入用户头像地址，因为是网盘系统，所以给用户设置网盘空间以及网盘使用空间字段。系统管理需要知道用户注册时间以及用户最后在线时间

```mysql
CREATE TABLE IF NOT EXISTS `user` (
	`id` bigint NOT NULL COMMENT 'user id',
	`user_name` VARCHAR(20) DEFAULT NULL COMMENT 'username',
	`user_nickname` VARCHAR(20) DEFAULT NULL COMMENT 'nickname',
	`user_avatar_path` VARCHAR(255) DEFAULT NULL COMMENT 'avatar path',
	`email` VARCHAR(255) DEFAULT NULL COMMENT 'email',
	`password` VARCHAR(50) DEFAULT NULL COMMENT 'password',
	`salt` VARCHAR(50) DEFAULT NULL COMMENT 'salt',
	`register_time` DATETIME DEFAULT NULL COMMENT 'register time',
	`last_online_time` DATETIME DEFAULT NULL COMMENT 'last online time',
	`used_disk_space` bigint DEFAULT '0' COMMENT 'used space',
	`total_disk_space` bigint DEFAULT NULL COMMENT 'total space',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uni_email` (`email`),
	UNIQUE KEY `uni_user_name` (`user_name`),
	KEY `idx_nick_name` (`user_nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User Table';
```

文件模块设计：文件ID用雪花算法生成，文件模块需要关联用户id以便于连表查询对应文件所属用户，需要文件名字段记录文件名称，以及文件大小、文件类型、文件为目录文件夹还是普通文件、需要一个字段确定是否有上级文件夹、文件路径(存在MinIO中的路径)、文件上传时间、文件修改时间、文件上传状态、文件回收站时间、是否被删除
表设计
```mysql
CREATE TABLE IF NOT EXISTS `file` (
    `id` bigint NOT NULL COMMENT 'file_id',
    `user_id` bigint NOT NULL COMMENT  'user id',
    `file_name` VARCHAR(255) DEFAULT '未命名' COMMENT 'file name',
    `file_size` bigint DEFAULT NULL COMMENT 'file size',
    `file_type` tinyint(1) DEFAULT NULL COMMENT 'file type',
    `file_path` VARCHAR(255) DEFAULT NULL COMMENT 'file path',
    `folder_bool` tinyint(1) DEFAULT NULL COMMENT 'is folder?',
    `file_folder` bigint DEFAULT NULL COMMENT 'file folder',
    `upload_status` tinyint(1) DEFAULT NULL COMMENT 'upload_status',
    `upload_time` DATETIME DEFAULT NULL COMMENT 'upload time',
    `modify_time` DATETIME DEFAULT NULL COMMENT 'modify time',
    `recycling_time` DATETIME DEFAULT NULL COMMENT 'recycling time',
    `delete_flag` TINYINT(1) DEFAULT '0' COMMENT 'delete flag 0:normal、1:recycling、2:deleted',
    primary key (`id`),
    key `idx_user_id` (`user_id`),
    key `idx_file_folder` (`file_folder`),
    key `idx_upload_time` (`upload_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='File';
```

后台管理模块设计：运用于网盘后台，管理员ID依旧用雪花生成，管理员账号、管理员加密密码、管理员昵称、管理员创建时间、管理员修改时间
```mysql
CREATE TABLE IF NOT EXISTS `admin` (
    `id` bigint NOT NULL COMMENT 'admin id',
    `admin_account` VARCHAR(20) DEFAULT NULL COMMENT 'admin account',
    `admin_password` VARCHAR(50) DEFAULT NULL COMMENT 'admin password',
    `salt` VARCHAR(50) DEFAULT NULL COMMENT 'salt',
    `admin_nickname` VARCHAR(20) DEFAULT NULL COMMENT 'admin nickname',
    `create_time` DATETIME DEFAULT NULL COMMENT 'create time',
    `modify_time` DATETIME DEFAULT NULL COMMENT 'modify time',
    primary key (`id`),
    UNIQUE KEY `uni_admin_name` (`admin_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Admin';
```

