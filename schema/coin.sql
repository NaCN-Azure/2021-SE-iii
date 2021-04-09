/*
 Navicat Premium Data Transfer

 Source Server         : NaCN
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : coin

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 09/04/2021 14:28:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for domain
-- ----------------------------
DROP TABLE IF EXISTS `domain`;
CREATE TABLE `domain`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of domain
-- ----------------------------
INSERT INTO `domain` VALUES (8, 'as');

-- ----------------------------
-- Table structure for nodetype
-- ----------------------------
DROP TABLE IF EXISTS `nodetype`;
CREATE TABLE `nodetype`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `domain_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nodetype
-- ----------------------------
INSERT INTO `nodetype` VALUES (1, 'Char', '#dc44fw', 8);

SET FOREIGN_KEY_CHECKS = 1;
