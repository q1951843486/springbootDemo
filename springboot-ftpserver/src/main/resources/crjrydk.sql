/*
Navicat MySQL Data Transfer

Source Server         : 10.200.1.159
Source Server Version : 50642
Source Host           : 10.200.1.159:3306
Source Database       : bfzd_dev

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2020-03-12 19:27:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crjrydk
-- ----------------------------
DROP TABLE IF EXISTS `crjrydk`;
CREATE TABLE `crjrydk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `xm` varchar(255) DEFAULT NULL,
  `gjdqdm` varchar(255) DEFAULT NULL,
  `xbdm` varchar(255) DEFAULT NULL,
  `csrq` varchar(255) DEFAULT NULL,
  `zjlbdm` varchar(255) DEFAULT NULL,
  `zjhm` varchar(255) DEFAULT NULL,
  `qzzldm` varchar(255) DEFAULT NULL,
  `crrqsj` varchar(255) DEFAULT NULL,
  `crkadm` varchar(255) DEFAULT NULL,
  `kamc` varchar(255) DEFAULT NULL,
  `jtgjbs` varchar(255) DEFAULT NULL,
  `qwgjdqdm` varchar(255) DEFAULT NULL,
  `crjsydm` varchar(255) DEFAULT NULL,
  `crbz` varchar(255) DEFAULT NULL,
  `sfzh` varchar(255) DEFAULT NULL,
  `lxdh` varchar(255) DEFAULT NULL,
  `qfdw` varchar(255) DEFAULT NULL,
  `qfdwmc` varchar(255) DEFAULT NULL,
  `hkszddm` varchar(255) DEFAULT NULL,
  `hkszdmc` varchar(255) DEFAULT NULL,
  `lzgjdqdm` varchar(255) DEFAULT NULL,
  `lzgjdqmc` varchar(255) DEFAULT NULL,
  `zdydm` varchar(255) DEFAULT NULL,
  `wybs` varchar(255) DEFAULT NULL,
  `jtfsdm` varchar(255) DEFAULT NULL,
  `filename` varchar(2000) DEFAULT NULL,
  `createtime` varchar(200) DEFAULT NULL,
  `inputtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
