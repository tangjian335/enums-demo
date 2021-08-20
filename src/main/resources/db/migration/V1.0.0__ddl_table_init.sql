-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `gender` tinyint(4) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '唐某','1','普通员工');
INSERT INTO `user` VALUES ('2', '马某','2','直接领导');
INSERT INTO `user` VALUES ('3', '廖某','1','事业线领导');
INSERT INTO `user` VALUES ('4', '吴某','2','事业线领导');
INSERT INTO `user` VALUES ('5', '曾某','1','总裁');



