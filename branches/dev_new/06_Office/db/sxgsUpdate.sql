--add select flow by arch type
alter table archives_type add `processDefId` bigint(20) REFERENCES `pro_definition` (`defId`);
alter table arch_rec_type add `processDefId` bigint(20) REFERENCES `pro_definition` (`defId`);
alter table archives_type add `processDefName` varchar(256);
alter table arch_rec_type add `processDefName` varchar(256);

--add noticeNew model


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `notice_news_type`
-- ----------------------------
DROP TABLE IF EXISTS `notice_news_type`;
CREATE TABLE `notice_news_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL,
  `sn` int(11) NOT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='通知（新）类型';

-- ----------------------------
-- Records of notice_news_type
-- ----------------------------
INSERT INTO notice_news_type VALUES ('1', '测试类别', '1');

-- ----------------------------
-- Table structure for `notice_news`
-- ----------------------------
DROP TABLE IF EXISTS `notice_news`;
CREATE TABLE `notice_news` (
  `newsId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `typeId` bigint(20) NOT NULL,
  `subjectIcon` varchar(128) DEFAULT NULL,
  `subject` varchar(128) NOT NULL COMMENT '新闻标题',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `replyCounts` int(11) DEFAULT NULL,
  `viewCounts` int(11) DEFAULT NULL COMMENT '浏览数',
  `issuer` varchar(32) NOT NULL,
  `content` text NOT NULL COMMENT '内容',
  `updateTime` datetime NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '\r\n            0=待审核\r\n            1=审核通过',
  `isDeskImage` smallint(6) DEFAULT NULL COMMENT '是否为图片新闻',
  PRIMARY KEY (`newsId`),
  KEY `FK_NS_R_NT` (`typeId`),
  CONSTRAINT `FK_NNS_R_NNT` FOREIGN KEY (`typeId`) REFERENCES `notice_news_type` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='通知（新）';

-- ----------------------------
-- Records of notice_news
-- ----------------------------
INSERT INTO notice_news VALUES ('1', '1', 'info/noticeNews/201107/43d7fa4b20a94cd2b82544770e981cd2.jpg', '测试新闻', '超级管理员', '2011-07-27 11:49:29', '0', '6', '超级管理员', '<p>你好啊，美女<a href=\"http:///userfiles/file/%E5%9F%BA%E4%BA%8E%E8%A7%92%E8%89%B2%E8%AE%BF%E9%97%AE%E6%8E%A7%E5%88%B6%E7%9A%84%E7%90%86%E8%AE%BA%E4%B8%8E%E5%BA%94%E7%94%A8%E7%A0%94%E7%A9%B6.pdf\">/userfiles/file/%E5%9F%BA%E4%BA%8E%E8%A7%92%E8%89%B2%E8%AE%BF%E9%97%AE%E6%8E%A7%E5%88%B6%E7%9A%84%E7%90%86%E8%AE%BA%E4%B8%8E%E5%BA%94%E7%94%A8%E7%A0%94%E7%A9%B6.pdf</a></p>', '2011-07-27 11:58:54', '1', '1');

-- ----------------------------
-- Table structure for `notice_news_comment`
-- ----------------------------
DROP TABLE IF EXISTS `notice_news_comment`;
CREATE TABLE `notice_news_comment` (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `newsId` bigint(20) NOT NULL,
  `content` varchar(500) NOT NULL,
  `createtime` datetime NOT NULL,
  `fullname` varchar(32) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `flag` smallint(6) NOT NULL,  
  PRIMARY KEY (`commentId`),
  KEY `FK_NC_R_AU` (`userId`),
  KEY `FK_NC_R_NS` (`newsId`),
  CONSTRAINT `FK_NNC_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_NNC_R_NNS` FOREIGN KEY (`newsId`) REFERENCES `notice_news` (`newsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_news_comment
-- ----------------------------


--add noticenews file upload function


SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `archives_doc`
-- ----------------------------
DROP TABLE IF EXISTS `noticenews_doc`;
CREATE TABLE `noticenews_doc` (
  `docId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fileId` bigint(20) DEFAULT NULL,
  `noticeNewsId` bigint(20) DEFAULT NULL,
  `creator` varchar(64) DEFAULT NULL COMMENT '拟稿人',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '拟稿人ID',
  `docName` varchar(128) NOT NULL COMMENT '文档名称',
  `docPath` varchar(128) NOT NULL COMMENT '文档路径',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`docId`),
  KEY `FK_NNSC_R_FA` (`fileId`),
  KEY `FK_NNSD_R_NNS` (`noticeNewsId`),
  CONSTRAINT `FK_NNSC_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_NNSD_R_NNS` FOREIGN KEY (`noticeNewsId`) REFERENCES `notice_news` (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
