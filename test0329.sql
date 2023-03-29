/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : 127.0.0.1:3306
 Source Schema         : test0329

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 30/03/2023 01:09:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for balance
-- ----------------------------
DROP TABLE IF EXISTS `balance`;
CREATE TABLE `balance`  (
  `bid` int(0) NOT NULL AUTO_INCREMENT COMMENT '余额记录表变动id',
  `balance` double(255, 2) NULL DEFAULT NULL COMMENT '余额',
  `uid` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `changetime` datetime(0) NULL DEFAULT NULL COMMENT '余额变动时的时间',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '余额变动说明',
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of balance
-- ----------------------------
INSERT INTO `balance` VALUES (14, 1000.00, 1, '2023-03-30 00:45:16', '存了1000元');
INSERT INTO `balance` VALUES (15, 877.00, 1, '2023-03-30 00:46:55', '消费123.0元');
INSERT INTO `balance` VALUES (16, 1000.00, 1, '2023-03-30 00:47:03', '退款123.0元');
INSERT INTO `balance` VALUES (17, 876.50, 1, '2023-03-30 00:48:09', '消费123.5元');
INSERT INTO `balance` VALUES (18, 897.50, 1, '2023-03-30 00:48:19', '退款21.0元');
INSERT INTO `balance` VALUES (19, 742.30, 1, '2023-03-30 00:51:23', '消费155.2元');
INSERT INTO `balance` VALUES (20, 626.80, 1, '2023-03-30 00:51:55', '消费115.5元');
INSERT INTO `balance` VALUES (21, 726.80, 1, '2023-03-30 00:52:01', '退款100.0元');
INSERT INTO `balance` VALUES (22, 611.30, 1, '2023-03-30 01:04:24', '消费115.5元');
INSERT INTO `balance` VALUES (23, 495.80, 1, '2023-03-30 01:05:28', '消费115.5元');
INSERT INTO `balance` VALUES (24, 611.30, 1, '2023-03-30 01:05:43', '退款115.5元');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `uid` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('root', 'root', 1);

SET FOREIGN_KEY_CHECKS = 1;
