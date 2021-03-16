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

 Date: 16/03/2021 18:42:50
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of domain
-- ----------------------------

-- ----------------------------
-- Table structure for entity
-- ----------------------------
DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bg_color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shape` int NULL DEFAULT NULL,
  `domain_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of entity
-- ----------------------------

-- ----------------------------
-- Table structure for relationship
-- ----------------------------
DROP TABLE IF EXISTS `relationship`;
CREATE TABLE `relationship`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `from_id` int NULL DEFAULT NULL,
  `to_id` int NULL DEFAULT NULL,
  `domain_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of relationship
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
