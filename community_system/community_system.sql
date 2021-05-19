/*
 Navicat Premium Data Transfer

 Source Server         : pressed.top
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : pressed.top:3306
 Source Schema         : community_system

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 19/05/2021 21:36:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community`  (
  `cId` int(11) NOT NULL AUTO_INCREMENT,
  `cName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '社区名称',
  `cProvince` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份',
  `cCity` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `cStreet` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道',
  `cTel` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `cHead` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  PRIMARY KEY (`cId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for members
-- ----------------------------
DROP TABLE IF EXISTS `members`;
CREATE TABLE `members`  (
  `mId` int(11) NOT NULL AUTO_INCREMENT,
  `mName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名',
  `mPhone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `mSex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `mAge` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `mHouseNumber` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '楼房号',
  `mWorkUnit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `mCarNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `mIsHousehold` tinyint(1) NULL DEFAULT 1 COMMENT '是否为业主 default 1',
  `cId` int(11) NULL DEFAULT NULL COMMENT '社区id',
  PRIMARY KEY (`mId`) USING BTREE,
  INDEX `cId`(`cId`) USING BTREE,
  CONSTRAINT `members_ibfk_1` FOREIGN KEY (`cId`) REFERENCES `community` (`cId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `rId` int(11) NOT NULL AUTO_INCREMENT,
  `mId` int(11) NOT NULL COMMENT '用户id',
  `cId` int(11) NOT NULL COMMENT '社区id',
  `rIsOutCity` tinyint(1) NULL DEFAULT 0 COMMENT '是否一周内离开城市',
  `rIsFromForeign` tinyint(1) NULL DEFAULT 0 COMMENT '是否一个月内来自境外',
  `rIsHousehold` tinyint(1) NULL DEFAULT 1 COMMENT '是否业主',
  `rRecordTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '进入时间',
  PRIMARY KEY (`rId`) USING BTREE,
  INDEX `mId`(`mId`) USING BTREE,
  INDEX `cId`(`cId`) USING BTREE,
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`mId`) REFERENCES `members` (`mId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `record_ibfk_2` FOREIGN KEY (`cId`) REFERENCES `community` (`cId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for record_vo
-- ----------------------------
DROP VIEW IF EXISTS `record_vo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `record_vo` AS select `record`.`rRecordTime` AS `rRecordTime`,`record`.`rIsHosehold` AS `rIsHosehold`,`record`.`rIsOutCity` AS `rIsOutCity`,`record`.`rIsFromForeign` AS `rIsFromForeign`,`record`.`rId` AS `rId`,`community`.`cName` AS `cName`,`members`.`mName` AS `mName`,`members`.`mPhone` AS `mPhone`,`community`.`cTel` AS `cTel`,`community`.`cStreet` AS `cStreet` from ((`members` join `community`) join `record` on(((`community`.`cId` = `record`.`cId`) and (`members`.`mId` = `record`.`mId`))));

SET FOREIGN_KEY_CHECKS = 1;
