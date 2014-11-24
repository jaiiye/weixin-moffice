2014.11.22
=============================================================
1.对包名进展整理，将业务和微信sdk进行分离；
2.将企业号agentid独立成配置选项；
3.首页实现根据movestart,moveend实现虚拟click;
4.将留言也没重构为ratchet模板；
5.将validate提示颜色设置为red,更友好一点；

2014.11.20
=============================================================
1.实现任务单功能；
2.每日任务提醒；
3.快速code认证；

2014.11.20
=============================================================
1.修改pom.xml,使其支持maven打包；
2.建立任务单模板

2014.11.20
=============================================================
1.微信根据code获取成员信息，网页权限控制；
2.实现成员之间的相互留言；
3.公司动态页面显示问题；
4.我要留言菜单事件日志记录错误；

2014.11.19
=============================================================
1.实现和公司通讯录的对接
2.增加了关键字回复、实现了菜单事件；
3.增加功能"我是谁"；

2014.11.18
=============================================================
1.增加了消息向数据库的保存功能；
2.增加了分享查看页面；
4.修改了菜单，和未来规划匹配；
5.增加了对位置消息和位置事件的记录；

/*重要联系人*/
SELECT * FROM core_employee WHERE stateType=0 AND (job LIKE '%经理%' OR job LIKE '%总监%')

2014.11.17 
=============================================================
1.修改mock相关类，使其满足对getRequestURL，getReader等函数的调用；
     可满足微信借口的单元测试
2.加密算法有问题（加密后再解密，和原名文，不一致）
     自己增加了方法，进行修正，微信自己的加密方法仍然不知道；
	 
/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.0.90-community-nt : Database - jfinaluib
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jfinaluib` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jfinaluib`;

/*Table structure for table `wx_notice` */

DROP TABLE IF EXISTS `wx_notice`;

CREATE TABLE `wx_notice` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `content` varchar(4000) default NULL,
  `createId` varchar(20) default NULL,
  `createTime` bigint(20) default NULL,
  `startTime` bigint(20) default NULL,
  `endTime` bigint(20) default NULL,
  `state` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Table structure for table `wx_task` */

DROP TABLE IF EXISTS `wx_task`;

CREATE TABLE `wx_task` (
  `id` int(11) NOT NULL auto_increment,
  `fromUserId` varchar(20) default NULL,
  `toUserId` varchar(20) default NULL,
  `title` varchar(200) default NULL,
  `content` varchar(400) default NULL,
  `createTime` bigint(20) NOT NULL,
  `state` int(11) NOT NULL,
  `toUsers` varchar(400) default NULL,
  `parentId` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

/*Table structure for table `wx_taskdetail` */

DROP TABLE IF EXISTS `wx_taskdetail`;

CREATE TABLE `wx_taskdetail` (
  `id` int(11) NOT NULL auto_increment,
  `taskId` int(11) NOT NULL,
  `fromUserId` varchar(20) NOT NULL,
  `toUserId` varchar(20) NOT NULL,
  `title` varchar(200) default NULL,
  `content` varchar(400) default NULL,
  `createTime` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`,`taskId`,`fromUserId`,`toUserId`,`createTime`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.

JFinal action report -------- 2014-11-24 10:48:18 ------------------------------
Controller  : com.dinglan.moffice.TaskController.(TaskController.java:1)
Method      : index
--------------------------------------------------------------------------------

C:\Program Files\AMD APP\bin\x86;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32

	 