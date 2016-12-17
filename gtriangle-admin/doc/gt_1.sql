/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.37 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `sso_app` */

DROP TABLE IF EXISTS `sso_app`;

CREATE TABLE `sso_app` (
  `appId` varchar(100) NOT NULL COMMENT 'appId',
  `appName` varchar(100) DEFAULT NULL COMMENT 'app名称',
  `appUrl` varchar(2000) DEFAULT NULL COMMENT 'appUrl',
  `appLoginSave` varchar(100) DEFAULT NULL COMMENT '登录信息保存方式',
  `appMainUrl` varchar(2000) DEFAULT NULL COMMENT '主页登链',
  `appType` tinyint(4) DEFAULT NULL COMMENT '应用类型',
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台应用表';

/*Data for the table `sso_app` */

insert  into `sso_app`(`appId`,`appName`,`appUrl`,`appLoginSave`,`appMainUrl`,`appType`) values ('zank_mgt','zank_mgt',NULL,NULL,NULL,1);

/*Table structure for table `sso_employee` */

DROP TABLE IF EXISTS `sso_employee`;

CREATE TABLE `sso_employee` (
  `ssoEmpId` bigint(20) NOT NULL COMMENT '账号ID',
  `empName` varchar(100) NOT NULL COMMENT '登录账号',
  `password` varchar(32) DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '加密盐',
  `registerTime` datetime NOT NULL COMMENT '注册时间',
  `registerIp` varchar(100) NOT NULL DEFAULT '127.0.0.1' COMMENT '注册ip',
  `errorTime` datetime DEFAULT NULL COMMENT '登录错误时间',
  `errorCount` int(11) DEFAULT '0' COMMENT '登录错误次数',
  `errorIp` varchar(50) DEFAULT NULL COMMENT '登录错误IP',
  `lastLoginTime` datetime DEFAULT NULL COMMENT 'lastLoginTime',
  `lastLoginIp` varchar(50) DEFAULT '127.0.0.1' COMMENT 'lastLoginIp',
  `loginCount` int(11) DEFAULT '0' COMMENT 'loginCount',
  PRIMARY KEY (`ssoEmpId`),
  UNIQUE KEY `uniq_username` (`empName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台账号表';

/*Data for the table `sso_employee` */

insert  into `sso_employee`(`ssoEmpId`,`empName`,`password`,`salt`,`registerTime`,`registerIp`,`errorTime`,`errorCount`,`errorIp`,`lastLoginTime`,`lastLoginIp`,`loginCount`) values (27,'15666666666','39a5a071a8b5dbe335458cb4f1a2fe5f','1b9ae39c3a0343920c735d00a46d511a','2016-12-06 16:07:39','127.0.0.1',NULL,0,NULL,'2016-12-17 16:40:34','127.0.0.1',122),(3194583662798848,'15604090129','8ed3508312e4cb55965ce239151c882c','44ca347d3078063240cf9a3d5941ef81','2016-03-29 17:44:58','100.109.192.94',NULL,0,NULL,'2016-12-17 12:03:58','127.0.0.1',460),(3876509436422144,'13478659803','ecbebd3337ed13a82e30d8e102f04e9e','3af9d9e47216becf6a580ba7e0aec32c','2016-11-25 14:30:21','127.0.0.1',NULL,0,NULL,'2016-12-12 15:01:01','127.0.0.1',6),(3876602444883968,'13478659804','4c3a7fa6f8e51595effee56ce724de93','2bba98309d43687176895c8fc2dea8ba','2016-11-25 15:17:39','127.0.0.1',NULL,0,NULL,NULL,'127.0.0.1',0),(3876603612014592,'13478659805','39bc065d96f9ecbc4c0bf93a5c95cbfc','5484b2b985280e018650080148019309','2016-11-25 15:18:15','127.0.0.1',NULL,0,NULL,NULL,'127.0.0.1',0),(3876608992094208,'13465987842','3ff5d7a0fdf593edcade47706d556fb2','02a579a7bb7025adba884185b8132336','2016-11-25 15:20:59','127.0.0.1',NULL,0,NULL,NULL,'127.0.0.1',0),(3876645833123840,'15251842714','86270cbf4c227fba29bafea2e92ea91c','bfa8c43f31378407c1e4b2d387ebaf78','2016-11-25 15:39:43','127.0.0.1','2016-12-06 13:37:29',4,'127.0.0.1','2016-12-06 13:34:37','127.0.0.1',14),(3876977832699904,'13478659806','a8f0fd6d3eabb9ff837d687973bca481','a8949194e43339b3716f8448fe7e321b','2016-11-25 18:28:35','127.0.0.1',NULL,0,NULL,'2016-11-25 18:41:28','127.0.0.1',1),(3877043457796096,'15141163536','1d08c066c7c34d6abe27e538c78e67fe','ed9cc410ec176df051350944581be797','2016-11-25 19:01:58','0:0:0:0:0:0:0:1',NULL,0,NULL,'2016-11-25 20:34:23','0:0:0:0:0:0:0:1',19),(3878905676376064,'13478652333','9e64ae6ecd157d23e0064c6f2428012c','607dfdc9738b6274112acf87f5232a56','2016-11-26 10:49:08','127.0.0.1',NULL,0,NULL,NULL,'127.0.0.1',0),(3878907367565312,'13479879878','2ff15e6c99caf084656a9ec54cb92745','37ea4a94e259fa5856147556de50422f','2016-11-26 10:50:00','127.0.0.1',NULL,0,NULL,NULL,'127.0.0.1',0),(3878910371768320,'15604087987','9adf71b538b1c59a60ed94067615193a','d07b16b7d4df9f2c86c451036c5d9494','2016-11-26 10:51:32','127.0.0.1',NULL,0,NULL,NULL,'127.0.0.1',0);

/*Table structure for table `sso_employee_app` */

DROP TABLE IF EXISTS `sso_employee_app`;

CREATE TABLE `sso_employee_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `ssoEmpId` bigint(20) DEFAULT NULL COMMENT '账号ID',
  `appId` varchar(100) DEFAULT NULL COMMENT '应用ID',
  `locked` tinyint(4) DEFAULT '0' COMMENT '状态',
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_app` (`appId`),
  KEY `FK_fk_sso_user` (`ssoEmpId`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COMMENT='账号应用表';

/*Data for the table `sso_employee_app` */

insert  into `sso_employee_app`(`id`,`ssoEmpId`,`appId`,`locked`,`created`) values (1,3194583662798848,'zank_mgt',0,NULL),(31,3876509436422144,'zank_mgt',0,'2016-11-25 14:30:21'),(32,3876602444883968,'zank_mgt',0,'2016-11-25 15:17:39'),(33,3876603612014592,'zank_mgt',0,'2016-11-25 15:18:15'),(34,3876608992094208,'zank_mgt',1,'2016-11-25 15:20:59'),(35,3876645833123840,'zank_mgt',0,'2016-11-25 15:39:43'),(36,3876977832699904,'zank_mgt',0,'2016-11-25 18:28:35'),(37,3877043457796096,'zank_mgt',0,'2016-11-25 19:01:58'),(38,3878905676376064,'zank_mgt',0,'2016-11-26 10:49:08'),(39,3878907367565312,'zank_mgt',0,'2016-11-26 10:50:00'),(40,3878910371768320,'zank_mgt',0,'2016-11-26 10:51:32'),(41,27,'zank_mgt',0,'2016-12-06 16:07:39');

/*Table structure for table `sys_function` */

DROP TABLE IF EXISTS `sys_function`;

CREATE TABLE `sys_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `funcName` varchar(30) DEFAULT NULL COMMENT '资源名称',
  `funcKey` varchar(100) DEFAULT NULL COMMENT '资源KEY',
  `isAtom` bit(1) DEFAULT NULL COMMENT '原子标示',
  `isOpen` bit(1) DEFAULT b'1' COMMENT '是否开放',
  `moduleId` bigint(20) NOT NULL COMMENT '模块ID',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '内部备注',
  `dataStatus` bit(1) NOT NULL DEFAULT b'1' COMMENT '删除标示',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_funcKey` (`funcKey`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COMMENT='系统功能权限表';

/*Data for the table `sys_function` */

insert  into `sys_function`(`id`,`funcName`,`funcKey`,`isAtom`,`isOpen`,`moduleId`,`pid`,`remark`,`dataStatus`) values (1,'用户管理','user:module','\0','',1,0,'一级菜单',''),(2,'设置','cfg:mng','\0','',9,0,'一级菜单',''),(3,'员工管理','cfg:mng.emp','\0','',9,2,'二级菜单',''),(4,'角色管理','cfg:mng.role','\0','',9,2,'二级菜单',''),(5,'敏感词管理','cfg:mng.keyword','\0','',9,2,'二级菜单',''),(6,'字典管理','cfg:mng.dic','\0','',9,2,'二级菜单',''),(7,'修改密码','cfg:mng.password','\0','',9,2,'二级菜单',''),(8,'用户列表','user:mng.list','\0','',1,1,NULL,''),(9,'用户举报','user:mng.report','\0','',1,1,NULL,''),(10,'认证管理','auth:module','\0','',2,0,'一级菜单',''),(11,'头像认证','auth:mng.headimg','\0','',2,10,NULL,''),(12,'实名认证','auth:mng.realname','\0','',2,10,NULL,''),(13,'图片管理','review:img.module','\0','',3,0,'一级菜单',''),(14,'照片审核','review:mng.photo:list','\0','',3,13,NULL,''),(15,'头像审核','review:mng.headimg.list','\0','',3,13,NULL,''),(16,'直播审核','review:live.module','\0','',4,0,'一级菜单',''),(17,'直播审核','review:mng.live.list','\0','',4,16,NULL,''),(18,'直播举报','review:mng.live.report','\0','',4,16,NULL,''),(19,'直播运营','business:module','\0','',5,0,'一级菜单',''),(20,'主播管理','business:mng.anchor','\0','',5,19,'二级菜单',''),(21,'所有主播','business:mng.anchor.all','\0','',5,20,NULL,''),(22,'我的主播','business:mng.anchor.my','\0','',5,20,NULL,''),(23,'置顶主播','business:mng.anchor.top','\0','',5,20,NULL,''),(24,'封禁主播','business:mng.anchor.forbid','\0','',5,20,NULL,''),(25,'直播统计','business:mng.live','\0','',5,19,'二级菜单',''),(26,'所有主播','business:mng.live.all','\0','',5,25,NULL,''),(27,'我的主播','business:mng.live.my','\0','',5,25,NULL,''),(28,'经纪人收入','business:mng.broker','\0','',5,19,'二级菜单',''),(29,'所有经纪人','business:mng.broker.all','\0','',5,28,NULL,''),(30,'我的收入','business:mng.broker.my','\0','',5,28,NULL,''),(31,'公共消息','business:mng.msg','\0','',5,19,NULL,''),(32,'主播操作权限','business:mng.anchor.detail','\0','',5,19,NULL,''),(33,'设置热门','business:mng.anchor.detail.hot','\0','',5,32,NULL,''),(34,'设置置顶','business:mng.anchor.detail.recommend','\0','',5,32,NULL,''),(35,'设置权重系数','business:mng.anchor.detail.weight','\0','',5,32,NULL,''),(36,'设置其他经纪人签约信息','business:mng.anchor.detail.other','\0','',5,32,NULL,'\0'),(37,'提现','business:mng.anchor.detail.draw','\0','',5,32,NULL,''),(38,'封禁/解封','business:mng.anchor.detail.block','\0','',5,32,NULL,''),(39,'提现管理','finance:module','\0','',6,0,'一级菜单',''),(40,'广告管理','adv:module','\0','',7,0,'一级菜单',''),(41,'图片广告','adv:mng.img','\0','',7,40,'二级菜单',''),(42,'开机广告','adv:mng.img.boot','\0','',7,41,NULL,''),(43,'附近的人广告','adv:mng.img.nearby','\0','',7,41,NULL,''),(44,'直播广告','adv:mng.img.live','\0','',7,41,NULL,''),(45,'站内信广告','adv:mng.mail','\0','',7,40,'二级菜单',''),(46,'操作日志','log:module','\0','',8,0,'一级菜单',''),(47,'所有日志','log:mng.all','\0','',8,46,'二级菜单',''),(48,'我的日志','log:mng.my','\0','',8,46,'二级菜单',''),(49,'直播管理','cfg:mng.live','\0','',9,2,'二级菜单',''),(52,'禁止提现','business:mng.anchor.detail.undraw','\0','',5,32,NULL,'');

/*Table structure for table `sys_module` */

DROP TABLE IF EXISTS `sys_module`;

CREATE TABLE `sys_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模块ID',
  `moduleName` varchar(100) NOT NULL COMMENT '模块名称',
  `priority` int(11) NOT NULL COMMENT '排序',
  `isInternal` bit(1) DEFAULT b'0' COMMENT '是否是内部模块',
  `moduleKey` varchar(30) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='系统模块表';

/*Data for the table `sys_module` */

insert  into `sys_module`(`id`,`moduleName`,`priority`,`isInternal`,`moduleKey`,`pid`) values (1,'会员模块',10,'\0',NULL,NULL),(9,'设置模块',0,'\0',NULL,NULL);

/*Table structure for table `t_b_emp_role` */

DROP TABLE IF EXISTS `t_b_emp_role`;

CREATE TABLE `t_b_emp_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `empId` bigint(20) DEFAULT NULL COMMENT '账号ID',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_user` (`empId`,`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3924811998767105 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系';

/*Data for the table `t_b_emp_role` */

insert  into `t_b_emp_role`(`id`,`empId`,`roleId`) values (3924811998767104,27,3876508497160192),(3876631575603200,3194583662798848,0),(3908221893683200,3876509436422144,3876508497160192),(3876602447931392,3876602444883968,3876508497160192),(3876603614832640,3876603612014592,3876508497160192),(3876631240681472,3876608992094208,3876609727113216),(3876645835941888,3876645833123840,3876508497160192),(3876977835485184,3876977832699904,3876609727113216),(3877168842582016,3877043457796096,3876508497160192),(3878905679849472,3878905676376064,3876508497160192),(3878907370317824,3878907367565312,3878906612951040),(3878910374815744,3878910371768320,3878906612951040),(3908222184171520,3907843440355328,3876508497160192);

/*Table structure for table `t_b_employee` */

DROP TABLE IF EXISTS `t_b_employee`;

CREATE TABLE `t_b_employee` (
  `id` bigint(20) NOT NULL COMMENT '账号ID',
  `empName` varchar(100) DEFAULT NULL COMMENT '用户名',
  `realName` varchar(30) DEFAULT NULL COMMENT '用户名称',
  `empType` varchar(20) DEFAULT 'S_YHLX_EMP' COMMENT '用户类型',
  `isRemoteWarn` bit(1) DEFAULT b'0' COMMENT '是否异地提醒 1:是 0:否',
  `created` datetime DEFAULT NULL,
  `source` varchar(30) DEFAULT NULL COMMENT '注册来源',
  `headImage` varchar(255) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`empName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `t_b_employee` */

insert  into `t_b_employee`(`id`,`empName`,`realName`,`empType`,`isRemoteWarn`,`created`,`source`,`headImage`) values (27,'15666666666','刘洋','S_YHLX_BROKER','','2016-12-06 16:07:39','pc-web',NULL),(3194583662798848,'15604090129','高岩','S_YHLX_ADMIN','\0','2016-11-12 13:53:57','pc-web',NULL),(3876509436422144,'13478659803','王刚','S_YHLX_EMP','','2016-11-25 14:30:21','pc-web',NULL),(3876602444883968,'13478659804','weg','S_YHLX_EMP','','2016-11-25 15:17:39','pc-web',NULL),(3876603612014592,'13478659805','wgg','S_YHLX_EMP','','2016-11-25 15:18:15','pc-web',NULL),(3876608992094208,'13465987842','测试','S_YHLX_EMP','','2016-11-25 15:20:59','pc-web',NULL),(3876645833123840,'15251842714','管理员2','S_YHLX_EMP','','2016-11-25 15:39:43','pc-web',NULL),(3876977832699904,'13478659806','我的敏感词','S_YHLX_EMP','','2016-11-25 18:28:35','pc-web',NULL),(3877043457796096,'15141163536','李成杰','S_YHLX_EMP','\0','2016-11-25 19:01:58','pc-web',NULL),(3878905676376064,'13478652333','我的员工信息啊啊啊啊','S_YHLX_EMP','','2016-11-26 10:49:08','pc-web',NULL),(3878907367565312,'13479879878','是的范文例文士大夫士','S_YHLX_EMP','','2016-11-26 10:50:00','pc-web',NULL),(3878910371768320,'15604087987','是的氛围良好找到合适','S_YHLX_EMP','','2016-11-26 10:51:32','pc-web',NULL);

/*Table structure for table `t_b_perm_url` */

DROP TABLE IF EXISTS `t_b_perm_url`;

CREATE TABLE `t_b_perm_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级ID',
  `permName` varchar(30) NOT NULL COMMENT '权限名称',
  `permType` tinyint(4) NOT NULL DEFAULT '1' COMMENT '权限类型1菜单，2功能点',
  `permUrl` varchar(50) DEFAULT NULL COMMENT '菜单路径',
  `router` varchar(255) DEFAULT NULL COMMENT '路由地址',
  `target` varchar(50) DEFAULT NULL COMMENT 'html跳转方式',
  `sorter` int(11) DEFAULT NULL COMMENT '排序',
  `selected` tinyint(4) DEFAULT NULL COMMENT '是否选中',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `iconHover` varchar(100) DEFAULT NULL COMMENT '图标hover',
  `funcId` bigint(20) NOT NULL COMMENT '功能ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NUQ_PERM_URL` (`permUrl`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COMMENT='权限URL映射表';

/*Data for the table `t_b_perm_url` */

insert  into `t_b_perm_url`(`id`,`pid`,`permName`,`permType`,`permUrl`,`router`,`target`,`sorter`,`selected`,`icon`,`iconHover`,`funcId`) values (1,0,'用户管理',1,NULL,'','_self',111,NULL,'icon-users','user',1),(2,0,'设置',1,NULL,'','_self',103,NULL,'icon-settings','config',2),(3,2,'员工管理',1,NULL,'config_emp_list.html','_self',15,NULL,NULL,NULL,3),(4,2,'角色管理',1,NULL,'config_role_list.html','_self',14,NULL,NULL,NULL,4),(5,2,'敏感词管理',1,NULL,'config_sensitive_list.html','_self',13,NULL,NULL,NULL,5),(6,2,'字典管理',1,NULL,'config_dict_list.html','_self',12,NULL,NULL,NULL,6),(7,2,'修改密码',1,NULL,'config_emp_pwd.html','_self',10,NULL,NULL,NULL,7),(8,1,'用户列表',1,NULL,'user_list.html',NULL,10,NULL,NULL,NULL,8),(9,1,'用户举报',3,NULL,'user_report.html',NULL,9,NULL,NULL,NULL,9),(10,0,'认证管理',3,NULL,NULL,NULL,110,NULL,'icon-user-following','auth',10),(11,10,'头像认证',1,NULL,'headimage_auth.html',NULL,10,NULL,NULL,NULL,11),(12,10,'实名认证',1,NULL,'realname_auth.html',NULL,9,NULL,NULL,NULL,12),(13,0,'图片管理',1,NULL,NULL,NULL,109,NULL,'icon-picture','pic_review',13),(14,13,'照片审核',1,NULL,'photo_pic_review.html',NULL,10,NULL,NULL,NULL,14),(15,13,'头像审核',1,NULL,'headimage_pic_review.html',NULL,9,NULL,NULL,NULL,15),(16,0,'直播审核',1,NULL,NULL,NULL,108,NULL,'icon-camcorder','live_li',16),(17,16,'直播审核',1,NULL,'live_li_review.html',NULL,10,NULL,NULL,NULL,17),(18,16,'直播举报',1,NULL,'live_li_report.html',NULL,9,NULL,NULL,NULL,18),(19,0,'直播运营',1,NULL,NULL,NULL,107,NULL,'icon-paper-plane','operative',19),(20,19,'主播管理',1,NULL,'anchor_manage_operative.html',NULL,10,NULL,NULL,NULL,20),(21,20,'所有主播',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,21),(22,20,'我的主播',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,22),(23,20,'置顶主播',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,23),(24,20,'封禁主播',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,24),(25,19,'直播统计',1,NULL,'live_tj_operative.html',NULL,9,NULL,NULL,NULL,25),(26,25,'所有主播',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,26),(27,25,'我的主播',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,27),(28,19,'经纪人收入',1,NULL,'agent_income_operative.html',NULL,8,NULL,NULL,NULL,28),(29,28,'所有经纪人',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,29),(30,28,'我的收入',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,30),(31,19,'公共消息',1,NULL,'live_msg_operative.html',NULL,7,NULL,NULL,NULL,31),(32,19,'主播操作权限',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,32),(33,32,'设置热门',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,33),(34,32,'设置置顶',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,34),(35,32,'设置权重系数',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,35),(36,32,'设置其他经纪人签约信息',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,36),(37,32,'提现',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,37),(38,32,'封禁/解封',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,38),(39,0,'提现管理',3,NULL,'cash_manage.html',NULL,106,NULL,'icon-credit-card','cash',39),(40,0,'广告管理',3,NULL,NULL,NULL,105,NULL,'icon-social-dropbox','adv',40),(41,40,'图片广告',1,NULL,'adv_manage_photo.html',NULL,10,NULL,NULL,NULL,41),(42,41,'开机广告',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,42),(43,41,'附近的人广告',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,43),(44,41,'直播广告',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,44),(45,40,'站内信广告',1,NULL,NULL,NULL,9,NULL,NULL,NULL,45),(46,0,'操作日志',1,NULL,NULL,NULL,104,NULL,'icon-notebook','operator_log',46),(47,46,'所有日志',1,NULL,'operator_log_all.html',NULL,10,NULL,NULL,NULL,47),(48,46,'我的日志',1,NULL,'operator_log_my.html',NULL,NULL,NULL,NULL,NULL,48),(49,2,'直播管理',1,NULL,'config_live_cfg.html',NULL,11,NULL,NULL,NULL,49);

/*Table structure for table `t_b_role` */

DROP TABLE IF EXISTS `t_b_role`;

CREATE TABLE `t_b_role` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `roleName` varchar(30) NOT NULL COMMENT '角色名称',
  `roleMemo` varchar(30) DEFAULT NULL COMMENT '角色描述',
  `isSystem` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否是系统角色',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `dataStatus` bit(1) NOT NULL DEFAULT b'1' COMMENT '删除标示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

/*Data for the table `t_b_role` */

insert  into `t_b_role`(`id`,`roleName`,`roleMemo`,`isSystem`,`created`,`updated`,`dataStatus`) values (0,'管理员','管理员','','2016-02-02 14:49:05',NULL,''),(3876508497160192,'角色名称1',NULL,'','2016-11-25 14:29:52',NULL,''),(3876609727113216,'2',NULL,'','2016-11-25 15:21:22',NULL,''),(3878906612951040,'我的骄傲色所答非所问离开家设定',NULL,'','2016-11-26 10:49:37',NULL,'');

/*Table structure for table `t_b_role_function` */

DROP TABLE IF EXISTS `t_b_role_function`;

CREATE TABLE `t_b_role_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `funcId` bigint(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3933182916405250 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

/*Data for the table `t_b_role_function` */

insert  into `t_b_role_function`(`id`,`roleId`,`funcId`) values (3859028500686848,3859018113689600,1),(3859028500686849,3859018113689600,8),(3859028500686850,3859018113689600,9),(3859028500686851,3859018113689600,2),(3859028500686852,3859018113689600,3),(3859028500686853,3859018113689600,4),(3859028500719616,3859018113689600,5),(3859028500719617,3859018113689600,6),(3859116286584832,0,1),(3859116286584833,0,8),(3859116286584834,0,9),(3859116286584835,0,2),(3859116286584836,0,3),(3859116286584837,0,4),(3859116286584838,0,5),(3859116286584839,0,6),(3859116286584840,0,7),(3859116286584841,0,10),(3859116286584842,0,11),(3859116286584843,0,12),(3859116286584844,0,13),(3859116286584845,0,16),(3859116286584846,0,17),(3859116286584847,0,18),(3859116286584848,0,19),(3859116286584849,0,20),(3859116286584850,0,21),(3859116286584851,0,22),(3859116286584852,0,23),(3859116286584853,0,24),(3859116286584854,0,25),(3859116286584855,0,26),(3859116286584856,0,27),(3859116286584857,0,28),(3859116286584858,0,29),(3859116286584859,0,30),(3859116286584860,0,31),(3859116286584861,0,32),(3859116286584862,0,33),(3859116286584863,0,34),(3859116286584864,0,35),(3859116286584865,0,36),(3859116286584866,0,37),(3859116286584867,0,38),(3859116286584868,0,39),(3859116286584869,0,40),(3859116286584870,0,41),(3859116286584871,0,42),(3859116286584872,0,43),(3859116286584873,0,44),(3859116286584874,0,45),(3859116286584875,0,46),(3859116286584876,0,47),(3859116286584877,0,48),(3859119430313984,3856902035777536,19),(3859119430313985,3856902035777536,20),(3859119430346752,3856902035777536,21),(3859119430346753,3856902035777536,22),(3859119430346754,3856902035777536,25),(3859119430346755,3856902035777536,26),(3859119430346756,3856902035777536,27),(3859119430346757,3856902035777536,28),(3859119430346758,3856902035777536,29),(3859119430346759,3856902035777536,30),(3859119430346760,0,14),(3859119430346761,0,15),(3867906763597824,3856930103896064,19),(3867906763597825,3856930103896064,31),(3867906763597826,3856930103896064,1),(3867906763597827,3856930103896064,8),(3867906763597828,3856930103896064,9),(3871694024189952,3867909131610112,10),(3871694024222720,3867909131610112,11),(3871694024222721,3867909131610112,12),(3871694024222722,3867909131610112,19),(3871694024222723,3867909131610112,20),(3871694024222724,3867909131610112,21),(3871694024222725,3867909131610112,22),(3871694024222726,3867909131610112,23),(3871694024222727,3867909131610112,24),(3871694024222728,3867909131610112,25),(3871694024222729,3867909131610112,26),(3871694024222730,3867909131610112,27),(3871694024222731,3867909131610112,28),(3871694024222732,3867909131610112,29),(3871694024222733,3867909131610112,30),(3871694024222734,3867909131610112,31),(3871694024255488,3867909131610112,32),(3871694024255489,3867909131610112,33),(3871694024255490,3867909131610112,34),(3871694024255491,3867909131610112,35),(3871694024255492,3867909131610112,36),(3871694024255493,3867909131610112,37),(3871694024255494,3867909131610112,38),(3871694024255495,3867909131610112,40),(3871694024255496,3867909131610112,41),(3871694024288256,3867909131610112,42),(3871694024288257,3867909131610112,43),(3871694024288258,3867909131610112,44),(3871694024288259,3867909131610112,45),(3871694024288260,3867909131610112,16),(3871694024288261,3867909131610112,17),(3871694024288262,3867909131610112,18),(3871694024288263,3867909131610112,39),(3876978430355456,3876609727113216,13),(3876978430388224,3876609727113216,15),(3876978430388225,3876609727113216,46),(3876978430388226,3876609727113216,47),(3876978430388227,3876609727113216,48),(3877217156671495,0,49),(3933182916339712,3876508497160192,1),(3933182916339713,3876508497160192,8),(3933182916339714,3876508497160192,9),(3933182916339715,3876508497160192,2),(3933182916339716,3876508497160192,3),(3933182916339717,3876508497160192,4),(3933182916339718,3876508497160192,5),(3933182916339719,3876508497160192,6),(3933182916339720,3876508497160192,7),(3933182916339721,3876508497160192,49),(3933182916339722,3876508497160192,10),(3933182916339723,3876508497160192,11),(3933182916339724,3876508497160192,12),(3933182916339725,3876508497160192,16),(3933182916339726,3876508497160192,17),(3933182916339727,3876508497160192,18),(3933182916372480,3876508497160192,19),(3933182916372481,3876508497160192,20),(3933182916372482,3876508497160192,21),(3933182916372483,3876508497160192,22),(3933182916372484,3876508497160192,23),(3933182916372485,3876508497160192,24),(3933182916372486,3876508497160192,25),(3933182916372487,3876508497160192,27),(3933182916372488,3876508497160192,26),(3933182916372489,3876508497160192,31),(3933182916372490,3876508497160192,28),(3933182916372491,3876508497160192,30),(3933182916372492,3876508497160192,29),(3933182916372493,3876508497160192,32),(3933182916372494,3876508497160192,33),(3933182916372495,3876508497160192,34),(3933182916372496,3876508497160192,35),(3933182916372497,3876508497160192,37),(3933182916372498,3876508497160192,38),(3933182916372499,3876508497160192,46),(3933182916372500,3876508497160192,47),(3933182916372501,3876508497160192,48),(3933182916372502,3876508497160192,13),(3933182916372503,3876508497160192,14),(3933182916372504,3876508497160192,15),(3933182916372505,3876508497160192,39),(3933182916372506,3876508497160192,40),(3933182916372507,3876508497160192,41),(3933182916372508,3876508497160192,42),(3933182916372509,3876508497160192,43),(3933182916405248,3876508497160192,44),(3933182916405249,3876508497160192,45);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
