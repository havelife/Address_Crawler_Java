/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50517
Source Host           : localhost:3306
Source Database       : addresscrawler

Target Server Type    : MYSQL
Target Server Version : 50517
File Encoding         : 65001

Date: 2011-11-21 21:27:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `address`
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `smladd` varchar(255) DEFAULT NULL,
  `bigadd` varchar(255) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('4', 'xx街xxx号码', '枣园%%%', '大兴', '北京');
INSERT INTO `address` VALUES ('5', null, null, null, 'Shandongjkkjjfkdsa');

-- ----------------------------
-- Table structure for `fenye`
-- ----------------------------
DROP TABLE IF EXISTS `fenye`;
CREATE TABLE `fenye` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) DEFAULT NULL,
  `page` int(10) DEFAULT NULL,
  `content` blob,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fenye
-- ----------------------------

-- ----------------------------
-- Table structure for `office`
-- ----------------------------
DROP TABLE IF EXISTS `office`;
CREATE TABLE `office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of office
-- ----------------------------

-- ----------------------------
-- Table structure for `url`
-- ----------------------------
DROP TABLE IF EXISTS `url`;
CREATE TABLE `url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` text,
  `state` varchar(50) DEFAULT NULL,
  `pcity` varchar(100) DEFAULT NULL,
  `hcity` varchar(100) DEFAULT NULL,
  `pdistrict` varchar(100) DEFAULT NULL,
  `hdistrict` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of url
-- ----------------------------
