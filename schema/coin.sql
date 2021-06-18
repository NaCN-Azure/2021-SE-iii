/*
 Navicat Premium Data Transfer

 Source Server         : server
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 106.15.93.81:3306
 Source Schema         : coin

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 18/06/2021 15:25:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for colors
-- ----------------------------
DROP TABLE IF EXISTS `colors`;
CREATE TABLE `colors`  (
  `id` int UNSIGNED NOT NULL,
  `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of colors
-- ----------------------------
INSERT INTO `colors` VALUES (1, '#409EFF');
INSERT INTO `colors` VALUES (2, '#67C23A');
INSERT INTO `colors` VALUES (3, '#909399');
INSERT INTO `colors` VALUES (4, '#FFC300');
INSERT INTO `colors` VALUES (5, '#FF5733');
INSERT INTO `colors` VALUES (6, '#900C3F');
INSERT INTO `colors` VALUES (7, '#581845');
INSERT INTO `colors` VALUES (8, '#C70039');
INSERT INTO `colors` VALUES (9, '#FF00FF');
INSERT INTO `colors` VALUES (10, '#DC143C');
INSERT INTO `colors` VALUES (11, '#800080');
INSERT INTO `colors` VALUES (12, '#7B68EE');
INSERT INTO `colors` VALUES (13, '#EE82EE');
INSERT INTO `colors` VALUES (14, '#CD5C5C');
INSERT INTO `colors` VALUES (15, '#CD853F');
INSERT INTO `colors` VALUES (16, '#DAA520');
INSERT INTO `colors` VALUES (17, '#2F4F4F');
INSERT INTO `colors` VALUES (18, '#66CDAA');
INSERT INTO `colors` VALUES (19, '#ADFF2F');
INSERT INTO `colors` VALUES (20, '#0000CD');
INSERT INTO `colors` VALUES (21, '#4682B4');
INSERT INTO `colors` VALUES (22, '#00CED1');
INSERT INTO `colors` VALUES (23, '#778899');
INSERT INTO `colors` VALUES (24, '#CC3399');
INSERT INTO `colors` VALUES (25, '#9933CC');
INSERT INTO `colors` VALUES (26, '#336633');
INSERT INTO `colors` VALUES (27, '#ABCDEF');

-- ----------------------------
-- Table structure for domain
-- ----------------------------
DROP TABLE IF EXISTS `domain`;
CREATE TABLE `domain`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of domain
-- ----------------------------
INSERT INTO `domain` VALUES (29, 'Apokemon.csv', '1405699923307753473', '2021-06-18 09:50:00', '2021-06-18 09:50:00');
INSERT INTO `domain` VALUES (30, '宝可梦A', '1405694659021492225', '2021-06-18 13:16:56', '2021-06-18 13:16:56');
INSERT INTO `domain` VALUES (31, '化学A', '1405694659021492225', '2021-06-18 13:51:28', '2021-06-18 13:51:28');
INSERT INTO `domain` VALUES (32, '软院人物关系A', '1405699923307753473', '2021-06-18 14:42:42', '2021-06-18 14:42:42');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of nodetype
-- ----------------------------
INSERT INTO `nodetype` VALUES (95, '草', '#409EFF', 29);
INSERT INTO `nodetype` VALUES (96, '地区', '#67C23A', 29);
INSERT INTO `nodetype` VALUES (97, '火', '#909399', 29);
INSERT INTO `nodetype` VALUES (98, '水', '#FFC300', 29);
INSERT INTO `nodetype` VALUES (99, '电', '#FF5733', 29);
INSERT INTO `nodetype` VALUES (100, '一般', '#900C3F', 29);
INSERT INTO `nodetype` VALUES (101, '超能', '#581845', 29);
INSERT INTO `nodetype` VALUES (102, '雷', '#C70039', 29);
INSERT INTO `nodetype` VALUES (103, '草', '#409EFF', 30);
INSERT INTO `nodetype` VALUES (104, '地区', '#67C23A', 30);
INSERT INTO `nodetype` VALUES (105, '火', '#909399', 30);
INSERT INTO `nodetype` VALUES (106, '水', '#FFC300', 30);
INSERT INTO `nodetype` VALUES (107, '电', '#FF5733', 30);
INSERT INTO `nodetype` VALUES (108, '一般', '#900C3F', 30);
INSERT INTO `nodetype` VALUES (109, '超能', '#581845', 30);
INSERT INTO `nodetype` VALUES (110, '雷', '#C70039', 30);
INSERT INTO `nodetype` VALUES (111, '烃', '#409EFF', 31);
INSERT INTO `nodetype` VALUES (112, '无机物', '#67C23A', 31);
INSERT INTO `nodetype` VALUES (113, '醇', '#909399', 31);
INSERT INTO `nodetype` VALUES (114, '醛', '#FFC300', 31);
INSERT INTO `nodetype` VALUES (115, '酸', '#FF5733', 31);
INSERT INTO `nodetype` VALUES (116, '酯', '#900C3F', 31);
INSERT INTO `nodetype` VALUES (117, '盐', '#581845', 31);
INSERT INTO `nodetype` VALUES (118, '糖', '#C70039', 31);
INSERT INTO `nodetype` VALUES (121, '老师', '#409EFF', 32);
INSERT INTO `nodetype` VALUES (122, '老老师', '#67C23A', 32);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_disabled` tinyint(1) NOT NULL DEFAULT 0,
  `sign` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int NULL DEFAULT 0,
  `is_vip` tinyint(1) NOT NULL DEFAULT 0,
  `vip_end_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1405694659021492225', '15809571672', 'bc3c71fecaf8a1553978b496edf36d56', 'NSY', 'https://heap-coin.oss-cn-beijing.aliyuncs.com/default.PNG', 0, NULL, 0, 0, NULL, '2021-06-18 01:12:24', '2021-06-18 01:12:24');
INSERT INTO `user` VALUES ('1405696813300293633', '18921873921', 'b341689f56efb9a19d2edace6af34878', 'condor', 'https://heap-coin.oss-cn-beijing.aliyuncs.com/default.PNG', 0, NULL, 0, 0, NULL, '2021-06-18 09:20:12', '2021-06-18 09:20:12');
INSERT INTO `user` VALUES ('1405699923307753473', '15160332697', '0ad7cd42f036b33fdb38603469806a40', 'hwh', 'https://heap-coin.oss-cn-beijing.aliyuncs.com/default.PNG', 0, NULL, 0, 0, NULL, '2021-06-18 09:32:33', '2021-06-18 09:32:33');

SET FOREIGN_KEY_CHECKS = 1;
