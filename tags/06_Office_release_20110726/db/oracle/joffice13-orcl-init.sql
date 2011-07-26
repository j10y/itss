insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE)
values (1, 'host', '主机Host', '主机IP', '系统邮件配置', 1, '192.168.1.11');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE)
values (2, 'username', '用户名', '邮件发送的邮箱用户名', '系统邮件配置', 1, 'lyy');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE)
values (3, 'password', '密码', '邮件发送的邮箱密码', '系统邮件配置', 1, '111111');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE)
values (4, 'from', '来自', '发送人的邮箱或用户名', '系统邮件配置', 1, 'lyy');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE)
values (5, 'goodsStockUser', '用户帐号', '当库存产生警报时，接收消息的人员', '行政管理配置', 1, 'admin');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE)
values (6, 'codeConfig', '验证码', '登录时是否需要验证码', '验证码配置', 2, '1');

insert into department (DEPID, DEPNAME, DEPDESC, DEPLEVEL, PARENTID, PATH, PHONE, FAX)
values (1, '信息部门', '维护系统', 2, 0, '0.1.', '', '');

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (1, null, '收件箱', 0, 1, '0.1.', 1, 1);

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (2, null, '发件箱', 0, 1, '0.2.', 1, 2);

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (3, null, '草稿箱', 0, 1, '0.3.', 1, 3);

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (4, null, '垃圾箱', 0, 1, '0.4.', 1, 4);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (1, '北京', 2, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (2, '上海', 2, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (3, '天津', 2, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (4, '重庆', 2, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (5, '河北', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (6, '山西', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (7, '内蒙古', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (8, '辽宁', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (9, '吉林', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (10, '黑龙江', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (11, '江苏', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (12, '浙江', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (13, '安徽', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (14, '福建', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (15, '江西', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (16, '山东', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (17, '河南', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (18, '湖北', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (19, '湖南', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (20, '广东', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (21, '广西', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (22, '海南', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (23, '四川', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (24, '贵州', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (25, '云南', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (26, '西藏', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (27, '陕西', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (28, '甘肃', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (29, '青海', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (30, '宁夏', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (31, '新疆', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (32, '台湾', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (33, '港澳', 1, 0);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (34, '石家庄', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (35, '唐山', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (36, '秦皇岛', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (37, '邯郸', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (38, '邢台', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (39, '保定', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (40, '张家口', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (41, '承德', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (42, '沧州', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (43, '廊坊', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (44, '衡水', 2, 5);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (45, '太原', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (46, '大同', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (47, '阳泉', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (48, '长治', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (49, '晋城', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (50, '朔州', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (51, '晋中', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (52, '运城', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (53, '忻州', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (54, '临汾', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (55, '吕梁', 2, 6);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (56, '呼和浩特', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (57, '包头', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (58, '乌海', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (59, '赤峰', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (60, '通辽', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (61, '鄂尔多斯', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (62, '呼伦贝尔', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (63, '巴彦淖尔', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (64, '乌兰察布', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (65, '兴安', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (66, '锡林郭勒', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (67, '阿拉善', 2, 7);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (68, '沈阳', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (69, '大连', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (70, '鞍山', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (71, '抚顺', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (72, '本溪', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (73, '丹东', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (74, '锦州', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (75, '营口', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (76, '阜新', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (77, '辽阳', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (78, '盘锦', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (79, '铁岭', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (80, '朝阳', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (81, '葫芦岛', 2, 8);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (82, '长春', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (83, '吉林', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (84, '四平', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (85, '辽源', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (86, '通化', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (87, '白山', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (88, '松原', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (89, '白城', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (90, '延边', 2, 9);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (91, '哈尔滨', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (92, '齐齐哈尔', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (93, '鸡西', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (94, '鹤岗', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (95, '双鸭山', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (96, '大庆', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (97, '伊春', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (98, '佳木斯', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (99, '七台河', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (100, '牡丹江', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (101, '黑河', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (102, '绥化', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (103, '大兴安岭', 2, 10);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (104, '南京', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (105, '无锡', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (106, '徐州', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (107, '常州', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (108, '苏州', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (109, '南通', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (110, '连云港', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (111, '淮安', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (112, '盐城', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (113, '扬州', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (114, '镇江', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (115, '泰州', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (116, '宿迁', 2, 11);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (117, '杭州', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (118, '宁波', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (119, '温州', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (120, '嘉兴', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (121, '湖州', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (122, '绍兴', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (123, '金华', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (124, '衢州', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (125, '舟山', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (126, '台州', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (127, '丽水', 2, 12);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (128, '合肥', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (129, '芜湖', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (130, '蚌埠', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (131, '淮南', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (132, '马鞍山', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (133, '淮北', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (134, '铜陵', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (135, '安庆', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (136, '黄山', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (137, '滁州', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (138, '阜阳', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (139, '宿州', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (140, '巢湖', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (141, '六安', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (142, '亳州', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (143, '池州', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (144, '宣城', 2, 13);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (145, '福州', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (146, '厦门', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (147, '莆田', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (148, '三明', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (149, '泉州', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (150, '漳州', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (151, '南平', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (152, '龙岩', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (153, '宁德', 2, 14);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (154, '南昌', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (155, '景德镇', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (156, '萍乡', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (157, '九江', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (158, '新余', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (159, '鹰潭', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (160, '赣州', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (161, '吉安', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (162, '宜春', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (163, '抚州', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (164, '上饶', 2, 15);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (165, '济南', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (166, '青岛', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (167, '淄博', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (168, '枣庄', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (169, '东营', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (170, '烟台', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (171, '潍坊', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (172, '济宁', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (173, '泰安', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (174, '日照', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (175, '莱芜', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (176, '临沂', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (177, '德州', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (178, '聊城', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (179, '滨州', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (180, '菏泽', 2, 16);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (181, '郑州', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (182, '开封', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (183, '洛阳', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (184, '平顶山', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (185, '焦作', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (186, '鹤壁', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (187, '新乡', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (188, '安阳', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (189, '濮阳', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (190, '许昌', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (191, '渭河', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (192, '三门峡', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (193, '南阳', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (194, '商丘', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (195, '信阳', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (196, '周口', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (197, '驻马店', 2, 17);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (198, '武汉', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (199, '黄石', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (200, '襄樊', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (201, '十堰', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (202, '荆州', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (203, '宜昌', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (204, '荆门', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (205, '鄂州', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (206, '孝感', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (207, '黄冈', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (208, '咸宁', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (209, '随州', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (210, '恩施', 2, 18);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (211, '长沙', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (212, '株洲', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (213, '湘潭', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (214, '衡阳', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (215, '邵阳', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (216, '岳阳', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (217, '常德', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (218, '张家界', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (219, '溢阳', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (220, '郴州', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (221, '永州', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (222, '怀化', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (223, '娄底', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (224, '湘西', 2, 19);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (225, '广州', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (226, '深圳', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (227, '珠海', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (228, '汕头', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (229, '韶关', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (230, '佛山', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (231, '江门', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (232, '湛江', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (233, '茂名', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (234, '肇庆', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (235, '惠州', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (236, '梅州', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (237, '汕尾', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (238, '河源', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (239, '阳江', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (240, '清远', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (241, '东莞', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (242, '中山', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (243, '潮州', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (244, '揭阳', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (245, '云浮', 2, 20);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (246, '南宁', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (247, '柳州', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (248, '桂林', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (249, '梧州', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (250, '北海', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (251, '防城港', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (252, '钦州', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (253, '贵港', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (254, '玉林', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (255, '百色', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (256, '贺州', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (257, '河池', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (258, '来宾', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (259, '崇左', 2, 21);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (260, '白沙黎族自治县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (261, '西沙群岛', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (262, '儋州', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (263, '屯昌县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (264, '安定县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (265, '琼中黎族苗族自治县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (266, '昌江黎族自治县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (267, '东方', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (268, '三亚', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (269, '中沙群岛的岛礁及其海域', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (270, '琼海', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (271, '澄迈县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (272, '五指山', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (273, '海口', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (274, '文昌', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (275, '陵水黎族自治县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (276, '保亭黎族苗族自治县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (277, '南沙群岛', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (278, '乐东黎族自治县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (279, '临高县', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (280, '万宁', 2, 22);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (281, '成都', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (282, '自贡', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (283, '攀枝花', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (284, '泸州', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (285, '德阳', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (286, '绵阳', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (287, '广元', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (288, '遂宁', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (289, '内江', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (290, '乐山', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (291, '南充', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (292, '宜宾', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (293, '广安', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (294, '达州', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (295, '眉山', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (296, '雅安', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (297, '巴中', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (298, '资阳', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (299, '阿坝', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (300, '甘孜', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (301, '凉山', 2, 23);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (302, '贵阳', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (303, '六盘水', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (304, '遵义', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (305, '安顺', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (306, '铜仁', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (307, '毕节', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (308, '黔西南', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (309, '黔东南', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (310, '黔南', 2, 24);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (311, '昆明', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (312, '曲靖', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (313, '玉溪', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (314, '保山', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (315, '昭通', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (316, '丽江', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (317, '普洱', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (318, '临沧', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (319, '文山', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (320, '红河', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (321, '西双版纳', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (322, '楚雄', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (323, '大理', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (324, '德宏', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (325, '怒江', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (326, '迪庆', 2, 25);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (327, '拉萨', 2, 26);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (328, '昌都', 2, 26);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (329, '山南', 2, 26);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (330, '日喀则', 2, 26);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (331, '那曲', 2, 26);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (332, '阿里', 2, 26);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (333, '林芝', 2, 26);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (334, '西安', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (335, '铜川', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (336, '宝鸡', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (337, '咸阳', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (338, '渭南', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (339, '延安', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (340, '汉中', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (341, '榆林', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (342, '安康', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (343, '商洛', 2, 27);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (344, '兰州', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (345, '嘉峪关', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (346, '金昌', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (347, '白银', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (348, '天水', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (349, '武威', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (350, '张掖', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (351, '平凉', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (352, '酒泉', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (353, '庆阳', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (354, '定西', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (355, '陇南', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (356, '临夏', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (357, '甘南', 2, 28);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (358, '西宁', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (359, '海东', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (360, '海北', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (361, '黄南', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (362, '海南', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (363, '果洛', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (364, '玉树', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (365, '海西', 2, 29);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (366, '银川', 2, 30);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (367, '石嘴山', 2, 30);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (368, '吴忠', 2, 30);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (369, '固原', 2, 30);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (370, '中卫', 2, 30);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (371, '乌鲁木齐', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (372, '克拉玛依', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (373, '吐鲁番', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (374, '哈密', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (375, '和田', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (376, '阿克苏', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (377, '喀什', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (378, '克孜勒苏柯尔克孜', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (379, '巴音郭楞蒙古', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (380, '昌吉', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (381, '博尔塔拉蒙古', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (382, '伊犁哈萨克', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (383, '塔城', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (384, '阿勒泰', 2, 31);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (385, '台北', 2, 32);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (386, '高雄', 2, 32);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (387, '基隆', 2, 32);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (388, '台中', 2, 32);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (389, '台南', 2, 32);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (390, '新竹', 2, 32);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (391, '香港', 2, 33);

insert into region (REGIONID, REGIONNAME, REGIONTYPE, PARENTID)
values (392, '澳门', 2, 33);

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (149, '_ArchiveTypeTempQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (150, '_ArchivesTypeAdd', '添加类别');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (151, '_ArchivesTypeEdit', '修改类别');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (152, '_ArchivesTypeDel', '删除类别');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (153, '_ArchivesTempAdd', '添加模板');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (154, '_ArchivesTempEdit', '编辑模板');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (155, '_ArchviesTempDel', '删除模板');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (156, '_AchivesDrafAdd', '发文');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (157, '_ArchivesDrafmQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (158, '_ArchivesDrafmEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (159, '_ArchivesDrafmDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (160, '_ArchivesIssueQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (161, '_ArchivesIssueEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (162, '_ArchivesIssueLeadQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (163, '_ArchivesIssueLeadEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (164, '_ArchivesIssueChargeQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (165, '_ArchivesIssueChargeEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (166, '_ArchivesIssueProofQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (167, '_ArchivesIssueProofEdit', '管理附件');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (168, '_ArchivesDocumentQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (169, '_ArchivesIssueMonitorQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (170, '_ArchivesIssueHasten', '催办');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (171, '_ArchivesIssueManageQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (172, '_ArchivesIssueSearchQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (173, '_DocHistoryQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (174, '_DocHistoryDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (175, '_ArchivesSignQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (176, '_ArchivesSignUp', '签收');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (177, '_ArchivesRecQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (178, '_ArchivesRecAdd', '添加');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (179, '_ArchivesRecEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (180, '_ArchivesRecDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (181, '_ArchivesHandleQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (182, '_LeaderReadQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (183, '_ArchDispatchQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (184, '_ArchUndertakeQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (185, '_ArchivesRecCtrlQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (186, '_ArchivesRecHasten', '催办');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (187, '_ArchReadQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (188, '_ArchRecTypeQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (189, '_ArchRecTypeAdd', '添加');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (190, '_ArchRecTypeEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (191, '_ArchRecTypeDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (192, '_ReportTemplateQuery', '查看报表');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (193, '_ReportTemplateAdd', '添加报表');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (194, '_ReportTemplateEdit', '编辑报表');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (195, '_ReportParamSetting', '参数设置');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (196, '_ReportTemplateDel', '删除报表');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (197, '_DictionaryQuery', '查看字典');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (198, '_DictionaryAdd', '添加');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (199, '_DictionaryEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (200, '_DictionaryDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (201, '_CustomerSendMail', '发送邮件');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (202, '_JobQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (203, '_JobAdd', '添加');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (204, '_JobEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (205, '_JobDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (206, '_JobRec', '恢复职位');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (207, '_EmpProfileReg', '登记');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (208, '_EmpProfileQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (209, '_EmpProfileAdd', '登记');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (210, '_EmpProfileEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (211, '_EmpProfileDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (212, '_EmpProfileCheck', '审核');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (213, '_EmpProfileRec', '恢复档案');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (214, '_SalaryItemQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (215, '_SalaryItemAdd', '添加');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (216, '_SalaryItemEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (217, '_SalaryItemDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (218, '_StandSalaryReg', '制定');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (219, '_StandSalaryQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (220, '_StandSalaryAdd', '制定');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (221, '_StandSalaryEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (222, '_StandSalaryDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (223, '_StandSalaryCheck', '审核');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (224, '_SalaryPayoffReg', '登记');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (225, '_SalaryPayoffQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (226, '_SalaryPayoffAdd', '登记');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (227, '_SalaryPayoffEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (228, '_SalaryPayoffDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (229, '_SalaryPayoffCheck', '审核');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (230, '_JobChangeReg', '登记');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (231, '_JobChangeQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (232, '_JobChangeAdd', '登记');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (233, '_JobChangeEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (234, '_JobChangeDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (235, '_JobChangeCheck', '审核');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (236, '_HireIssueQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (237, '_HireIssueAdd', '添加');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (238, '_HireIssueEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (239, '_HireIssueDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (240, '_HireIssueCheck', '审核');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (241, '_ResumeQuery', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (242, '_ResumeAdd', '添加');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (243, '_ResumeEdit', '编辑');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (244, '_ResumeDel', '删除');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (1, '_AppUserQuery', '查看账号');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (2, '_AppUserAdd', '添加账号');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (3, '_AppUserEdit', '编辑账号');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (4, '_AppUserDel', '删除账号');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (5, '_AppRoleList', '查看角色');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (6, '_AppRoleAdd', '添加角色');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (7, '_AppRoleEdit', '编辑角色');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (8, '_AppRoleDel', '删除角色');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (9, '_AppRoleGrant', '授权角色');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (10, '_DepartmentQuery', '查看部门');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (11, '_DepartmentAdd', '新建部门');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (12, '_DepartmentEdit', '修改部门');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (13, '_DepartmentDel', '删除部门');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (14, '_FileAttachQuery', '查看附件');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (15, '_FileAttachEdit', '编辑附件');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (16, '_FileAttachDel', '删除附件');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (17, '_CompanyEdit', '公司信息修改');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (18, '_FlowQuery', '查看流程');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (19, '_ProTypeManage', '流程类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (20, '_FlowAdd', '发布流程');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (21, '_FlowEdit', '编辑流程');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (22, '_FlowDel', '删除流程');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (23, '_FlowCheck', '查看');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (24, '_FlowSetting', '人员设置');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (25, '_DocFolderSharedManage', '公共文件夹管理');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (26, '_DocPrivilegeQuery', '查看权限');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (27, '_DocPrivilegeAdd', '添加权限');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (28, '_DocPrivilegeEdit', '编辑权限');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (29, '_DocPrivilegeDel', '删除权限');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (30, '_PlanTypeQuery', '查看类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (31, '_PlanTypeAdd', '添加类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (32, '_PlanTypeEdit', '编辑类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (33, '_PlanTypeDel', '删除类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (34, '_NewDepPlan', '新建部门任务计划');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (35, '_NewsQuery', '查看新闻');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (36, '_NewsAdd', '添加新闻');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (37, '_NewsEdit', '编辑新闻');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (38, '_NewsDel', '删除新闻');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (39, '_NewsCommentQuery', '查看评论');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (40, '_NewsCommentDel', '删除评论');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (41, '_NewsTypeQuery', '查看新闻类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (42, '_NewsTypeAdd', '添加新闻类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (43, '_NewsTypeEdit', '修改新闻类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (44, '_NewsTypeDel', '删除新闻类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (45, '_NoticeQuery', '查看公告');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (46, '_NoticeAdd', '添加公告');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (47, '_NoticeEdit', '编辑公告');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (48, '_NoticeDel', '删除公告');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (49, '_HolidayRecordQuery', '查看假期设置');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (50, '_HolidayRecordAdd', '添加假期设置');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (51, '_HolidayRecordEdit', '修改假期设置');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (52, '_HolidayRecordDel', '删除假期设置');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (53, '_DutySectonQuery', '查看班次定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (54, '_DutySectonAdd', '添加班次定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (55, '_DutySectonEdit', '修改班次定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (56, '_DutySectonDel', '删除班次定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (57, '_DutySystemQuery', '查看班制定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (58, '_DutySystemAdd', '添加班制定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (59, '_DutySystemEdit', '修改班制定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (60, '_DutySystemDel', '删除班制定义');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (61, '_DutyQuery', '查看排班');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (62, '_DutyAdd', '添加排班');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (63, '_DutyEdit', '修改排班');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (64, '_DutyDel', '删除排班');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (65, '_DutyRegisterQuery', '查看考勤信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (66, '_DutyRegisterAdd', '补签');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (67, '_DutyRegisterDel', '删除考勤信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (68, '_CustomerQuery', '查看客户信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (69, '_CustomerAdd', '添加客户信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (70, '_CustomerEdit', '编辑客户信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (71, '_CustomerDel', '删除客户信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (72, '_CusLinkmanQuery', '查看联系人信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (73, '_CusLinkmanAdd', '添加联系人');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (74, '_CusLinkmanEdit', '编辑联系人');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (75, '_CusLinkmanDel', '删除联系人');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (76, '_CusConnectionQuery', '查看交往信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (77, '_CusConnectionAdd', '添加交往信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (78, '_CusConnectionEdit', '编辑交往信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (79, '_CusConnectionDel', '删除交往信息');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (80, '_ProjectQuery', '查看项目');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (81, '_ProjectAdd', '添加项目');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (82, '_ProjectEdit', '编辑项目');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (83, '_ProjectDel', '删除项目');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (84, '_ContractQuery', '查看合同');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (85, '_ContractAdd', '添加合同');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (86, '_ContractEdit', '编辑合同');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (87, '_ContractDel', '删除合同');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (88, '_ProductQuery', '查看产品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (89, '_ProductAdd', '添加产品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (90, '_ProductEdit', '编辑产品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (91, '_ProductDel', '删除产品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (92, '_ProviderQuery', '查看供应商');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (93, '_ProviderAdd', '添加供应商');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (94, '_ProviderEdit', '编辑供应商');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (95, '_ProviderDel', '删除供应商');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (96, '_OfficeGoodsQuery', '查看办公用品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (97, '_OfficeGoodsTypeManage', '用品类型管理');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (98, '_OfficeGoodsAdd', '添加用品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (99, '_OfficeGoodsEdit', '编辑用品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (100, '_OfficeGoodsDel', '删除用品');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (101, '_InStockQuery', '查看入库记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (102, '_InStockAdd', '添加入库记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (103, '_InStockEdit', '编辑入库记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (104, '_InStockDel', '删除入库记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (105, '_GoodsApplyQuery', '查看申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (106, '_GoodsApplyAdd', '添加申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (107, '_GoodsApplyEdit', '编辑申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (108, '_GoodsApplyDel', '删除申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (109, '_CarQuery', '查看车辆');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (110, '_CarAdd', '添加车辆');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (111, '_CarEdit', '编辑车辆');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (112, '_CarDel', '删除车辆');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (113, '_CarRepairQuery', '查看维修记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (114, '_CarRepairAdd', '添加维修记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (115, '_CarRepairEdit', '编辑维修记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (116, '_CarRepairDel', '删除维修记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (117, '_CarApplyQuery', '查看车辆申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (118, '_CarApplyAdd', '添加申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (119, '_CarApplyEdit', '编辑申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (120, '_CarApplyDel', '删除申请记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (121, '_DepreTypeQuery', '查看折算类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (122, '_DepreTypeAdd', '添加类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (123, '_DepreTypeEdit', '编辑类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (124, '_DepreTypeDel', '删除类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (125, '_FixedAssetsQuery', '查看固定资产');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (126, '_AssetsTypeManage', '资产类型管理');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (127, '_FixedAssetsAdd', '添加资产');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (128, '_FixedAssetsEdit', '编辑资产');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (129, '_FixedAssetsDel', '删除资产');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (130, '_Depreciate', '进行折算');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (131, '_DepreRecordQuery', '查看折算记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (132, '_BookTypeQuery', '查看类型');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (133, '_BookTypeAdd', '添加图书类别');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (134, '_BookTypeEdit', '编辑图书类别');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (135, '_BookTypeDel', '删除图书类别');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (136, '_BookQuery', '查看图书');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (137, '_BookAdd', '添加图书');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (138, '_BookEdit', '编辑图书');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (139, '_BookDel', '删除图书');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (140, '_BookBorrowQuery', '查看记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (141, '_BookBorrowAdd', '添加借阅记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (142, '_BookBorrowEdit', '编辑借阅记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (143, '_BookReturn', '归还');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (144, '_BookBorrowDel', '删除借阅记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (145, '_BookReturnQuery', '查看记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (146, '_BookReturnAdd', '添加归还记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (147, '_BookReturnEdit', '编辑归还记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (148, '_BookReturnDel', '删除归还记录');

insert into app_function (FUNCTIONID, FUNKEY, FUNNAME)
values (249, '_ArchFlowConfEdit', '修改配置');



insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (-1, '超级管理员', '超级管理员,具有所有权限', 1, '__ALL', 0);

insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (1, '[人事经理]', '管理人事的经理', 1, 'SystemSetting,AppUserView,_AppUserQuery,_AppUserAdd,_AppUserEdit,_AppUserDel,DepartmentView,_DepartmentQuery,_DepartmentAdd,_DepartmentEdit,_DepartmentDel,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,DutyManager,Duty,HolidayRecordView,_HolidayRecordQuery,_HolidayRecordAdd,_HolidayRecordEdit,_HolidayRecordDel,DutySectionView,_DutySectonQuery,_DutySectonAdd,_DutySectonEdit,_DutySectonDel,DutySystemView,_DutySystemQuery,_DutySystemAdd,_DutySystemEdit,_DutySystemDel,DutyView,_DutyQuery,_DutyAdd,_DutyEdit,_DutyDel,DutyRegisterView,_DutyRegisterQuery,_DutyRegisterAdd,_DutyRegisterDel', 0);

insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (2, '[行政经理]', '管理行政', 1, 'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,Administrator,GoodManeger,OfficeGoodsManageView,_OfficeGoodsQuery,_OfficeGoodsTypeManage,_OfficeGoodsAdd,_OfficeGoodsEdit,_OfficeGoodsDel,InStockView,_InStockQuery,_InStockAdd,_InStockEdit,_InStockDel,GoodsApplyView,_GoodsApplyQuery,_GoodsApplyAdd,_GoodsApplyEdit,_GoodsApplyDel,CarManeger,CarView,_CarQuery,_CarAdd,_CarEdit,_CarDel,CartRepairView,_CarRepairQuery,_CarRepairAdd,_CarRepairEdit,_CarRepairDel,CarApplyView,_CarApplyQuery,_CarApplyAdd,_CarApplyEdit,_CarApplyDel,FixedAssetsManage,DepreTypeView,_DepreTypeQuery,_DepreTypeAdd,_DepreTypeEdit,_DepreTypeDel,FixedAssetsManageView,_FixedAssetsQuery,_AssetsTypeManage,_FixedAssetsAdd,_FixedAssetsEdit,_FixedAssetsDel,_Depreciate,DepreRecordView,_DepreRecordQuery,BookManager,BookTypeView,_BookTypeQuery,_BookTypeAdd,_BookTypeEdit,_BookTypeDel,BookManageView,_BookQuery,_BookAdd,_BookEdit,_BookDel,BookBorrowView,_BookBorrowQuery,_BookBorrowAdd,_BookBorrowEdit,_BookReturn,_BookBorrowDel,BookReturnView,_BookReturnQuery,_BookReturnAdd,_BookReturnEdit,_BookReturnDel', 0);

insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (3, '[文档管理员]', '管理文档', 1, 'SystemSetting,PublicDocument,NewPublicDocumentForm,DocFolderSharedView,_DocFolderSharedManage,_DocPrivilegeQuery,_DocPrivilegeAdd,_DocPrivilegeEdit,_DocPrivilegeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', 0);

insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (4, '[信息管理员]', '管理新闻公告等信息', 1, 'SystemSetting,Task,PlanTypeView,_PlanTypeQuery,_PlanTypeAdd,_PlanTypeEdit,_PlanTypeDel,NewWorkPlanForm,_NewDepPlan,Info,NewsView,_NewsQuery,_NewsAdd,_NewsEdit,_NewsDel,NewsCommentView,_NewsCommentQuery,_NewsCommentDel,NewsTypeView,_NewsTypeQuery,_NewsTypeAdd,_NewsTypeEdit,_NewsTypeDel,NoticeView,_NoticeQuery,_NoticeAdd,_NoticeEdit,_NoticeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', 0);

insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (5, '[客户经理]', '管理客户信息', 1, 'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,customer,CustomerView,_CustomerQuery,_CustomerAdd,_CustomerEdit,_CustomerDel,CusLinkmanView,_CusLinkmanQuery,_CusLinkmanAdd,_CusLinkmanEdit,_CusLinkmanDel,CusConnectionView,_CusConnectionQuery,_CusConnectionAdd,_CusConnectionEdit,_CusConnectionDel,ProjectView,_ProjectQuery,_ProjectAdd,_ProjectEdit,_ProjectDel,ContractView,_ContractQuery,_ContractAdd,_ContractEdit,_ContractDel,ProductView,_ProductQuery,_ProductAdd,_ProductEdit,_ProductDel,ProviderView,_ProviderQuery,_ProviderAdd,_ProviderEdit,_ProviderDel', 0);

insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (6, '公文管理', '公文管理', 1, 'Archive,ArchFlowConfView,_ArchFlowConfEdit,ArchiveIssue,ArchiveTypeTempView,_ArchiveTypeTempQuery,_ArchivesTypeAdd,_ArchivesTypeEdit,_ArchivesTypeDel,_ArchivesTempAdd,_ArchivesTempEdit,_ArchviesTempDel,ArchivesDraftView,_AchivesDrafAdd,ArchivesDraftManage,_ArchivesDrafmQuery,_ArchivesDrafmEdit,_ArchivesDrafmDel,ArchivesIssueAudit,_ArchivesIssueQuery,_ArchivesIssueEdit,ArchivesIssueLead,_ArchivesIssueLeadQuery,_ArchivesIssueLeadEdit,ArchivesIssueCharge,_ArchivesIssueChargeQuery,_ArchivesIssueChargeEdit,ArchivesIssueProof,_ArchivesIssueProofQuery,_ArchivesIssueProofEdit,ArchivesDocument,_ArchivesDocumentQuery,ArchivesIssueMonitor,_ArchivesIssueMonitorQuery,_ArchivesIssueHasten,ArchivesIssueManage,_ArchivesIssueManageQuery,ArchivesIssueSearch,_ArchivesIssueSearchQuery,DocHistoryView,_DocHistoryQuery,_DocHistoryDel,ArchiveReceive,ArchivesSignView,_ArchivesSignQuery,_ArchivesSignUp,ArchivesRecView,_ArchivesRecQuery,_ArchivesRecAdd,_ArchivesRecEdit,_ArchivesRecDel,ArchivesHandleView,_ArchivesHandleQuery,LeaderReadView,_LeaderReadQuery,ArchDispatchView,_ArchDispatchQuery,ArchUndertakeView,_ArchUndertakeQuery,ArchivesRecCtrlView,_ArchivesRecCtrlQuery,_ArchivesRecHasten,ArchReadView,_ArchReadQuery,ArchRecTypeView,_ArchRecTypeQuery,_ArchRecTypeAdd,_ArchRecTypeEdit,_ArchRecTypeDel', 0);

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (383, 147, '/admin/getBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (384, 148, '/admin/listReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (385, 148, '/admin/multiDelBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (573, 230, '/hrm/saveJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (574, 230, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (575, 230, '/hrm/comboJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (576, 230, '/hrm/comboStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (577, 231, '/hrm/listJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (578, 232, '/hrm/saveJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (579, 232, '/hrm/listJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (580, 232, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (581, 232, '/hrm/comboJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (582, 232, '/hrm/comboStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (583, 233, '/hrm/saveJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (584, 233, '/hrm/listJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (585, 233, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (586, 233, '/hrm/getJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (587, 233, '/hrm/comboJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (588, 233, '/hrm/comboStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (589, 234, '/hrm/listJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (590, 234, '/hrm/multiDelJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (591, 235, '/hrm/loadJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (592, 235, '/hrm/checkJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (593, 235, '/hrm/listJobChange.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (594, 236, '/hrm/listHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (595, 236, '/hrm/loadHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (596, 237, '/hrm/listHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (597, 237, '/hrm/saveHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (598, 237, '/hrm/loadHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (599, 238, '/hrm/listHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (600, 238, '/hrm/saveHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (601, 238, '/hrm/loadHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (602, 238, '/hrm/getHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (603, 239, '/hrm/multiDelHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (604, 239, '/hrm/loadHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (605, 239, '/hrm/listHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (606, 240, '/hrm/multiDelHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (607, 240, '/hrm/checkHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (608, 240, '/hrm/loadHireIssue.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (609, 241, '/hrm/listResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (610, 242, '/hrm/listResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (611, 242, '/hrm/saveResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (612, 242, '/hrm/delphotoResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (613, 243, '/hrm/getResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (614, 243, '/hrm/listResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (615, 243, '/hrm/saveResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (616, 243, '/hrm/delphotoResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (617, 244, '/hrm/multiDelResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (618, 244, '/hrm/listResume.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (386, 149, '/archive/treeArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (387, 149, '/archive/listArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (388, 150, '/archive/treeArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (389, 150, '/archive/listArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (390, 150, '/archive/saveArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (391, 151, '/archive/treeArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (392, 151, '/archive/listArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (393, 151, '/archive/saveArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (394, 151, '/archive/getArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (395, 152, '/archive/treeArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (396, 152, '/archive/listArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (397, 152, '/archive/multiDelArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (398, 153, '/archive/treeArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (399, 153, '/archive/listArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (400, 153, '/archive/saveArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (401, 153, '/archive/comboArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (402, 154, '/archive/treeArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (403, 154, '/archive/listArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (404, 154, '/archive/saveArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (405, 154, '/archive/comboArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (406, 154, '/archive/getArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (407, 155, '/archive/treeArchivesType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (408, 155, '/archive/listArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (409, 155, '/archive/multiDelArchTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (410, 156, '/archive/getFlowArchFlowConf.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (411, 157, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (412, 158, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (413, 158, '/archive/getFlowArchFlowConf.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (414, 158, '/archive/getArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (415, 159, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (416, 159, '/archive/multiDelArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (417, 160, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (418, 161, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (419, 161, '/archive/getArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (420, 162, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (421, 163, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (422, 163, '/archive/getArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (423, 164, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (424, 165, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (425, 165, '/archive/getArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (426, 166, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (427, 167, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (428, 167, '/archive/getArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (429, 168, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (430, 169, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (431, 170, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (432, 170, '/archive/hastenArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (433, 171, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (434, 172, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (435, 173, '/archive/listDocHistory.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (436, 174, '/archive/listDocHistory.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (437, 174, '/archive/multiDelDocHistory.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (438, 175, '/archive/listArchivesDep.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (439, 176, '/archive/getArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (440, 176, '/archive/listArchivesDep.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (441, 176, '/archive/getFlowArchFlowConf.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (442, 177, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (443, 178, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (444, 178, '/archive/getFlowArchFlowConf.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (445, 179, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (446, 179, '/archive/getFlowArchFlowConf.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (447, 179, '/archive/getArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (448, 180, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (449, 180, '/archive/multiDelArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (450, 181, '/archive/listArchivesHandle.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (451, 182, '/archive/listLeaderRead.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (452, 183, '/archive/listArchDispatch.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (453, 184, '/archive/listArchDispatch.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (454, 185, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (455, 186, '/archive/listArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (456, 186, '/archive/hastenArchives.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (457, 187, '/archive/listArchDispatch.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (458, 188, '/archive/listArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (459, 189, '/archive/listArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (460, 189, '/archive/saveArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (461, 190, '/archive/listArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (462, 190, '/archive/saveArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (463, 190, '/archive/getArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (464, 191, '/archive/listArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (465, 191, '/archive/multiDelArchRecType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (466, 17, '/system/delphotoCompany.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (467, 192, '/system/listReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (468, 192, 'report/report.jsp');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (469, 193, '/system/listReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (470, 193, '/system/saveReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (471, 194, '/system/listReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (472, 194, '/system/getReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (473, 195, '/system/loadReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (474, 195, '/system/submitReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (475, 195, '/system/listReportParam.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (476, 195, '/system/multiDelReportParam.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (477, 195, '/system/saveReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (478, 195, '/system/saveReportParam.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (479, 195, '/system/getReportParam.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (480, 196, '/system/listReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (481, 196, '/system/multiDelReportTemplate.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (482, 197, '/system/listDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (483, 198, '/system/listDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (484, 198, '/system/saveDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (485, 198, '/system/itemsDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (486, 199, '/system/listDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (487, 199, '/system/saveDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (488, 199, '/system/itemsDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (489, 199, '/system/getDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (490, 200, '/system/listDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (491, 200, '/system/multiDelDictionary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (492, 201, '/customer/loadVm.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (493, 201, '/customer/send.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (494, 110, '/admin/delphotoCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (495, 111, '/admin/delphotoCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (496, 202, '/hrm/listJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (497, 203, '/hrm/listJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (498, 203, '/hrm/saveJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (499, 204, '/hrm/listJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (500, 204, '/hrm/saveJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (501, 204, '/hrm/getJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (502, 205, '/hrm/listJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (503, 205, '/hrm/multiDelJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (504, 206, '/hrm/listJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (505, 206, '/hrm/recoveryJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (506, 207, '/hrm/saveEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (507, 207, '/hrm/numberEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (508, 207, '/hrm/comboJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (509, 207, '/hrm/comboStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (510, 208, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (511, 209, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (512, 209, '/hrm/saveEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (513, 209, '/hrm/delphotoEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (514, 209, '/hrm/numberEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (515, 209, '/hrm/comboJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (516, 209, '/hrm/comboStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (517, 210, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (518, 210, '/hrm/saveEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (519, 210, '/hrm/numberEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (520, 210, '/hrm/delphotoEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (521, 210, '/hrm/comboJob.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (522, 210, '/hrm/comboStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (523, 210, '/hrm/getEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (524, 211, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (525, 211, '/hrm/multiDelEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (526, 212, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (527, 212, '/hrm/checkEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (528, 213, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (529, 213, '/hrm/recoveryEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (530, 214, '/hrm/listSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (531, 215, '/hrm/listSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (532, 215, '/hrm/saveSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (533, 216, '/hrm/listSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (534, 216, '/hrm/saveSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (535, 216, '/hrm/getSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (536, 217, '/hrm/listSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (537, 217, '/hrm/multiDelSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (538, 218, '/hrm/listSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (539, 218, '/hrm/saveStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (540, 218, '/hrm/numberStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (541, 218, '/hrm/listStandSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (542, 219, '/hrm/listStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (543, 220, '/hrm/listSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (544, 220, '/hrm/listStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (545, 220, '/hrm/saveStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (546, 220, '/hrm/numberStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (547, 220, '/hrm/listStandSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (548, 221, '/hrm/listStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (549, 221, '/hrm/listSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (550, 221, '/hrm/saveStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (551, 221, '/hrm/getStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (552, 221, '/hrm/listStandSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (553, 222, '/hrm/listStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (554, 222, '/hrm/multiDelStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (555, 223, '/hrm/listStandSalaryItem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (556, 223, '/hrm/getStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (557, 223, '/hrm/listStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (558, 223, '/hrm/checkStandSalary.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (559, 224, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (560, 224, '/hrm/saveSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (561, 225, '/hrm/listSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (562, 226, '/hrm/listSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (563, 226, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (564, 226, '/hrm/saveSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (565, 227, '/hrm/listSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (566, 227, '/hrm/listEmpProfile.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (567, 227, '/hrm/saveSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (568, 227, '/hrm/getSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (569, 228, '/hrm/listSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (570, 228, '/hrm/multiDelSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (571, 229, '/hrm/checkSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (572, 229, '/hrm/listSalaryPayoff.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (1, 1, '/system/listAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (2, 2, '/system/listAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (3, 2, '/system/chooseRolesAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (4, 2, '/system/selectedRolesAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (5, 2, '/system/listDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (6, 3, '/system/listAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (7, 3, '/system/chooseRolesAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (8, 3, '/system/selectedRolesAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (9, 3, '/system/listDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (10, 4, '/system/listAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (11, 4, '/system/multiDelAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (12, 5, '/system/listAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (13, 6, '/system/listAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (14, 6, '/system/saveAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (15, 7, '/system/listAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (16, 7, '/system/saveAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (17, 7, '/system/getAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (18, 8, '/system/listAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (19, 8, '/system/mulDelAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (20, 9, '/system/listAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (21, 9, '/system/grantXmlAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (22, 9, '/system/getAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (23, 9, '/system/grantAppRole.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (24, 10, '/system/listDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (25, 10, '/system/selectAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (26, 11, '/system/listDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (27, 11, '/system/addDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (28, 11, '/system/selectAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (29, 11, '/system/saveAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (30, 12, '/system/listDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (31, 12, '/system/addDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (32, 12, '/system/detailDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (33, 12, '/system/selectAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (34, 12, '/system/saveAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (35, 13, '/system/listDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (36, 13, '/system/removeDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (37, 13, '/system/selectAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (38, 13, '/system/saveAppUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (39, 14, '/system/listFileAttach.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (40, 15, '/system/saveFileAttach.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (41, 15, '/system/listFileAttach.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (42, 15, '/system/getFileAttach.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (43, 16, '/system/listFileAttach.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (44, 16, '/system/multiDelFileAttach.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (45, 17, '/system/listCompany.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (46, 17, '/system/addCompany.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (47, 18, '/flow/rootProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (48, 18, '/flow/listProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (49, 18, '/flow/processDetail.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (50, 19, '/flow/rootProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (51, 19, '/flow/saveProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (52, 19, '/flow/removeProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (53, 19, '/flow/getProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (54, 20, '/flow/rootProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (55, 20, '/flow/listProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (56, 20, '/flow/saveProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (57, 20, '/flow/listProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (58, 20, '/flow/getProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (59, 21, '/flow/rootProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (60, 21, '/flow/listProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (61, 21, '/flow/saveProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (62, 21, '/flow/listProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (63, 21, '/flow/getProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (64, 22, '/flow/rootProType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (65, 22, '/flow/listProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (66, 22, '/flow/multiDelProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (67, 23, '/flow/processDetail.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (68, 24, '/flow/saveProUserAssign.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (69, 24, '/flow/listProUserAssign.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (70, 25, '/document/saveDocFolder.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (71, 25, '/document/getDocFolder.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (72, 25, '/document/removeDocFolder.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (73, 26, '/document/listDocPrivilege.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (74, 27, '/document/listDocPrivilege.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (75, 27, '/document/addDocPrivilege.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (76, 28, '/document/listDocPrivilege.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (77, 28, '/document/changeDocPrivilege.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (78, 29, '/document/listDocPrivilege.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (79, 29, '/document/multiDelDocPrivilege.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (80, 30, '/task/listPlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (81, 31, '/task/listPlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (82, 31, '/task/savePlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (83, 32, '/task/listPlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (84, 32, '/task/savePlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (85, 32, '/task/getPlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (86, 33, '/task/listPlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (87, 33, '/task/multiDelPlanType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (88, 35, '/info/categoryNews.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (89, 35, '/info/treeNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (90, 36, '/info/categoryNews.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (91, 36, '/info/treeNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (92, 36, '/info/saveNews.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (93, 37, '/info/categoryNews.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (94, 37, '/info/treeNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (95, 37, '/info/saveNews.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (96, 38, '/info/categoryNews.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (97, 38, '/info/treeNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (98, 38, '/info/multiDelNews.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (99, 40, '/info/multiDelNewsComment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (100, 41, '/info/listNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (101, 42, '/info/listNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (102, 42, '/info/addNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (103, 43, '/info/listNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (104, 43, '/info/addNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (105, 43, '/info/detailNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (106, 43, '/info/sortNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (107, 44, '/info/listNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (108, 44, '/info/removeNewsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (109, 46, '/info/saveNotice.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (110, 47, '/info/saveNotice.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (111, 48, '/info/multiDelNotice.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (112, 49, '/personal/listHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (113, 50, '/personal/listHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (114, 50, '/personal/saveHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (115, 51, '/personal/listHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (116, 51, '/personal/getHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (117, 51, '/personal/saveHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (118, 52, '/personal/listHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (119, 52, '/personal/multiDelHolidayRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (120, 53, '/personal/listDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (121, 54, '/personal/listDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (122, 54, '/personal/saveDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (123, 55, '/personal/listDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (124, 55, '/personal/saveDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (125, 55, '/personal/getDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (126, 56, '/personal/listDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (127, 56, '/personal/multiDelDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (128, 57, '/personal/listDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (129, 58, '/personal/listDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (130, 58, '/personal/settingDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (131, 58, '/personal/saveDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (132, 59, '/personal/listDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (133, 59, '/personal/getDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (134, 59, '/personal/saveDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (135, 60, '/personal/listDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (136, 60, '/personal/multiDelDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (137, 61, '/personal/listDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (138, 62, '/personal/listDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (139, 62, '/personal/saveDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (140, 62, '/personal/comboDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (141, 63, '/personal/listDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (142, 63, '/personal/saveDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (143, 63, '/personal/comboDutySystem.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (144, 63, '/personal/getDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (145, 64, '/personal/listDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (146, 64, '/personal/multiDelDuty.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (147, 65, '/personal/listDutyRegister.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (148, 66, '/personal/listDutyRegister.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (149, 66, '/personal/saveDutyRegister.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (150, 66, '/personal/comboDutySection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (151, 67, '/personal/listDutyRegister.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (152, 67, '/personal/multiDelDutyRegister.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (342, 138, '/admin/treeBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (343, 138, '/admin/getBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (344, 139, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (345, 139, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (346, 139, '/admin/multiDelBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (347, 140, '/admin/listBorrowBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (348, 141, '/admin/listBorrowBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (349, 141, '/admin/saveBorrowBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (350, 141, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (351, 141, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (352, 141, '/admin/getBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (353, 141, '/admin/getBorrowSnBookSn.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (354, 142, '/admin/listBorrowBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (355, 142, '/admin/saveBorrowBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (356, 142, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (357, 142, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (358, 142, '/admin/getBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (359, 142, '/admin/getBorrowSnBookSn.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (360, 142, '/admin/getBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (361, 143, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (362, 143, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (363, 143, '/admin/getReturnSnBookSn.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (364, 143, '/admin/getBorRetTimeBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (365, 143, '/admin/listReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (366, 143, '/admin/saveReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (367, 143, '/admin/getBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (368, 144, '/admin/listBorrowBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (369, 144, '/admin/multiDelBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (370, 145, '/admin/listReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (371, 146, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (372, 146, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (373, 146, '/admin/getReturnSnBookSn.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (374, 146, '/admin/getBorRetTimeBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (375, 146, '/admin/listReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (376, 146, '/admin/saveReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (377, 147, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (378, 147, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (379, 147, '/admin/getReturnSnBookSn.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (380, 147, '/admin/getBorRetTimeBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (381, 147, '/admin/listReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (382, 147, '/admin/saveReturnBookBorRet.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (626, 249, '/archive/saveArchFlowConf.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (627, 249, '/archive/getArchFlowConf.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (628, 249, '/archive/depListArchRecUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (629, 249, '/archive/selectArchRecUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (630, 249, '/archive/saveArchRecUser.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (631, 249, '/flow/listProDefinition.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (153, 68, '/customer/listCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (154, 69, '/customer/listCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (155, 69, '/customer/saveCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (156, 69, '/customer/checkCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (157, 69, '/customer/numberCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (158, 69, '/system/listRegion.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (159, 70, '/customer/listCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (160, 70, '/customer/saveCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (161, 70, '/customer/checkCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (162, 70, '/customer/numberCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (163, 70, '/system/listRegion.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (164, 70, '/customer/getCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (165, 71, '/customer/listCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (166, 71, '/customer/multiDelCustomer.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (167, 72, '/customer/listCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (168, 73, '/customer/listCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (169, 73, '/customer/saveCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (170, 74, '/customer/listCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (171, 74, '/customer/saveCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (172, 74, '/customer/getCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (173, 75, '/customer/listCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (174, 75, '/customer/multiDelCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (175, 76, '/customer/listCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (176, 77, '/customer/listCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (177, 77, '/customer/saveCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (178, 77, '/customer/findCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (179, 78, '/customer/listCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (180, 78, '/customer/saveCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (181, 78, '/customer/findCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (182, 78, '/customer/getCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (183, 79, '/customer/listCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (184, 79, '/customer/multiDelCusConnection.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (185, 80, '/customer/listProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (186, 81, '/customer/listProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (187, 81, '/customer/saveProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (188, 81, '/customer/numberProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (189, 81, '/customer/findCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (190, 82, '/customer/listProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (191, 82, '/customer/saveProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (192, 82, '/customer/numberProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (193, 82, '/customer/findCusLinkman.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (194, 82, '/customer/getProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (195, 83, '/customer/listProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (196, 83, '/customer/multiDelProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (197, 84, '/customer/listContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (198, 85, '/customer/listContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (199, 85, '/customer/saveContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (200, 85, '/customer/getProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (201, 85, '/customer/removeFileContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (202, 85, '/customer/listContractConfig.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (203, 85, '/customer/multiDelContractConfig.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (204, 86, '/customer/listContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (205, 86, '/customer/saveContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (206, 86, '/customer/getProject.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (207, 86, '/customer/removeFileContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (208, 86, '/customer/listContractConfig.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (209, 86, '/customer/multiDelContractConfig.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (210, 86, '/customer/getContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (211, 87, '/customer/listContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (212, 87, '/customer/multiDelContract.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (213, 88, '/customer/listProduct.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (214, 89, '/customer/saveProduct.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (215, 89, '/customer/listProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (216, 90, '/customer/listProduct.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (217, 90, '/customer/getProduct.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (218, 90, '/customer/saveProduct.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (219, 91, '/customer/listProduct.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (220, 91, '/customer/multiDelProduct.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (221, 92, '/customer/listProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (222, 93, '/customer/saveProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (223, 94, '/customer/listProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (224, 94, '/customer/getProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (225, 94, '/customer/saveProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (226, 95, '/customer/listProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (227, 95, '/customer/multiDelProvider.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (228, 96, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (229, 96, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (230, 97, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (231, 97, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (232, 97, '/admin/multiDelOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (233, 97, '/admin/saveOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (234, 97, '/admin/getOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (235, 98, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (236, 98, '/admin/saveOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (237, 98, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (238, 99, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (239, 99, '/admin/saveOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (240, 99, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (241, 99, '/admin/getOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (242, 100, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (243, 100, '/admin/multiDelOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (244, 101, '/admin/listInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (245, 102, '/admin/listInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (246, 102, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (247, 102, '/admin/saveInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (248, 102, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (249, 103, '/admin/listInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (250, 103, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (251, 103, '/admin/saveInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (252, 103, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (253, 103, '/admin/getInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (254, 104, '/admin/listInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (255, 104, '/admin/multiDelInStock.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (256, 105, '/admin/listGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (257, 106, '/admin/listGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (258, 106, '/admin/saveGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (259, 106, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (260, 106, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (261, 107, '/admin/listGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (262, 107, '/admin/saveGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (263, 107, '/admin/listOfficeGoods.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (264, 107, '/admin/treeOfficeGoodsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (265, 107, '/admin/getGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (266, 108, '/admin/listGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (267, 108, '/admin/multiDelGoodsApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (268, 109, '/admin/listCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (269, 110, '/admin/listCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (270, 110, '/admin/saveCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (271, 111, '/admin/listCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (272, 111, '/admin/saveCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (273, 111, '/admin/getCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (274, 112, '/admin/listCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (275, 112, '/admin/multiDelCar.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (276, 113, '/admin/listCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (277, 114, '/admin/listCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (278, 114, '/admin/saveCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (279, 115, '/admin/listCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (280, 115, '/admin/saveCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (281, 115, '/admin/getCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (282, 116, '/admin/listCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (283, 116, '/admin/multiDelCartRepair.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (284, 117, '/admin/listCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (285, 118, '/admin/listCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (286, 118, '/admin/saveCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (287, 119, '/admin/listCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (288, 119, '/admin/saveCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (289, 119, '/admin/getCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (290, 120, '/admin/listCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (291, 120, '/admin/multiDelCarApply.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (292, 121, '/admin/listDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (293, 122, '/admin/listDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (294, 122, '/admin/saveDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (295, 123, '/admin/listDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (296, 123, '/admin/saveDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (297, 123, '/admin/getDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (298, 124, '/admin/listDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (299, 124, '/admin/multiDelDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (300, 125, '/admin/listFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (301, 125, '/admin/treeAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (302, 126, '/admin/treeAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (303, 126, '/admin/multiDelAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (304, 126, '/admin/saveAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (305, 126, '/admin/getAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (306, 127, '/admin/listFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (307, 127, '/system/selectDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (308, 127, '/admin/treeAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (309, 127, '/admin/saveFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (310, 127, '/admin/comboxAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (311, 127, '/admin/comboxDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (312, 128, '/admin/listFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (313, 128, '/admin/treeAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (314, 128, '/system/selectDepartment.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (315, 128, '/admin/saveFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (316, 128, '/admin/comboxAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (317, 128, '/admin/comboxDepreType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (318, 128, '/admin/getFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (319, 129, '/admin/listFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (320, 129, '/admin/treeAssetsType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (321, 129, '/admin/multiDelFixedAssets.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (322, 130, '/admin/depreciateDepreRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (323, 130, '/admin/workDepreRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (324, 131, '/admin/listDepreRecord.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (325, 132, '/admin/listBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (326, 133, '/admin/listBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (327, 133, '/admin/saveBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (328, 134, '/admin/listBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (329, 134, '/admin/saveBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (330, 134, '/admin/getBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (331, 135, '/admin/listBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (332, 135, '/admin/multiDelBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (333, 136, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (334, 136, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (335, 137, '/admin/listBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (336, 137, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (337, 137, '/admin/saveBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (338, 137, '/admin/treeBook.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (339, 138, '/admin/listBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (340, 138, '/admin/treeBookType.do');

insert into fun_url (URLID, FUNCTIONID, URLPATH)
values (341, 138, '/admin/saveBookType.do');


insert into role_fun (ROLEID, FUNCTIONID)
values (1, 1);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 2);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 3);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 4);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 10);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 11);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 12);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 13);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 34);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 49);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 50);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 51);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 52);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 53);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 54);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 55);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 56);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 57);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 58);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 59);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 60);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 61);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 62);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 63);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 64);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 65);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 66);

insert into role_fun (ROLEID, FUNCTIONID)
values (1, 67);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 34);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 96);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 97);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 98);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 99);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 100);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 101);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 102);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 103);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 104);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 105);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 106);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 107);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 108);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 109);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 110);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 111);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 112);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 113);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 114);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 115);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 116);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 117);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 118);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 119);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 120);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 121);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 122);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 123);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 124);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 125);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 126);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 127);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 128);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 129);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 130);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 131);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 132);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 133);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 134);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 135);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 136);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 137);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 138);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 139);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 140);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 141);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 142);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 143);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 144);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 145);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 146);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 147);

insert into role_fun (ROLEID, FUNCTIONID)
values (2, 148);

insert into role_fun (ROLEID, FUNCTIONID)
values (3, 25);

insert into role_fun (ROLEID, FUNCTIONID)
values (3, 26);

insert into role_fun (ROLEID, FUNCTIONID)
values (3, 27);

insert into role_fun (ROLEID, FUNCTIONID)
values (3, 28);

insert into role_fun (ROLEID, FUNCTIONID)
values (3, 29);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 30);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 31);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 32);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 33);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 34);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 35);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 36);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 37);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 38);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 39);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 40);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 41);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 42);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 43);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 44);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 45);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 46);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 47);

insert into role_fun (ROLEID, FUNCTIONID)
values (4, 48);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 34);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 68);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 69);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 70);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 71);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 72);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 73);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 74);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 75);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 76);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 77);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 78);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 79);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 80);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 81);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 82);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 83);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 84);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 85);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 86);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 87);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 88);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 89);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 90);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 91);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 92);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 93);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 94);

insert into role_fun (ROLEID, FUNCTIONID)
values (5, 95);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 149);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 150);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 151);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 152);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 153);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 154);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 155);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 156);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 157);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 158);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 159);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 160);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 161);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 162);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 163);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 164);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 165);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 166);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 167);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 168);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 169);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 170);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 171);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 172);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 173);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 174);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 175);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 176);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 177);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 178);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 179);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 180);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 181);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 182);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 183);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 184);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 185);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 186);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 187);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 188);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 189);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 190);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 191);

insert into role_fun (ROLEID, FUNCTIONID)
values (6, 249);




insert into app_user (USERID, USERNAME, TITLE, PASSWORD, EMAIL, DEPID, POSITION, PHONE, MOBILE, FAX, ADDRESS, ZIP, PHOTO, ACCESSIONTIME, STATUS, EDUCATION, FULLNAME, DELFLAG)
values (1, 'admin', 1, 'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=', 'csx@jee-soft.cn', 1, '', '', '', '', '', '', '', to_date('18-12-2009', 'dd-mm-yyyy'), 1, '', '超级管理员', 0);

insert into app_user (USERID, USERNAME, TITLE, PASSWORD, EMAIL, DEPID, POSITION, PHONE, MOBILE, FAX, ADDRESS, ZIP, PHOTO, ACCESSIONTIME, STATUS, EDUCATION, FULLNAME, DELFLAG)
values (-1, 'system', 1, '0', '152@163.com', 1, '', '', '', '', '', '', '', to_date('18-12-2009', 'dd-mm-yyyy'), 0, '', '系统', 1);

insert into user_role (roleId,userId) values(-1,1);

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (1, '宗教信仰', '佛教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (2, '宗教信仰', '道教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (3, '宗教信仰', '基督宗教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (4, '宗教信仰', '天主教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (5, '宗教信仰', '伊斯兰教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (6, '宗教信仰', '犹太教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (7, '宗教信仰', '孔教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (8, '宗教信仰', '神道教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (9, '宗教信仰', '耆那教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (10, '宗教信仰', '印度教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (11, '宗教信仰', '东正教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (12, '宗教信仰', '新教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (13, '宗教信仰', '锡克教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (14, '宗教信仰', '琐罗亚斯德教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (15, '宗教信仰', '巴哈伊教', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (16, '宗教信仰', '其它', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (17, '民族', '汉族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (18, '民族', '阿昌族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (19, '民族', '白族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (20, '民族', '保安族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (21, '民族', '布朗族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (22, '民族', '布依族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (23, '民族', '朝鲜族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (24, '民族', '达斡族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (25, '民族', '傣族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (26, '民族', '德昂族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (27, '民族', '侗族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (28, '民族', '东乡族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (29, '民族', '独龙族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (30, '民族', '鄂伦春族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (31, '民族', '俄罗斯族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (32, '民族', '鄂温克族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (33, '民族', '高山族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (34, '民族', '仡佬族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (35, '民族', '哈尼族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (36, '民族', '啥萨克族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (37, '民族', '赫哲族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (38, '民族', '回族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (39, '民族', '基诺族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (40, '民族', '京族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (41, '民族', '景颇族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (42, '民族', '柯尔克孜族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (43, '民族', '拉祜族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (44, '民族', '黎族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (45, '民族', '僳僳族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (46, '民族', '珞巴族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (47, '民族', '满族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (48, '民族', '毛南族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (49, '民族', '门巴族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (50, '民族', '蒙古族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (51, '民族', '苗族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (52, '民族', '仫佬族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (53, '民族', '纳西族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (54, '民族', '怒族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (55, '民族', '普米族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (56, '民族', '羌族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (57, '民族', '撒拉族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (58, '民族', '畲族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (59, '民族', '水族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (60, '民族', '塔吉克族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (61, '民族', '塔塔尔族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (62, '民族', '土族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (63, '民族', '土家族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (64, '民族', '佤族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (65, '民族', '维吾尔族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (66, '民族', '乌孜别克族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (67, '民族', '锡伯族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (68, '民族', '瑶族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (69, '民族', '彝族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (70, '民族', '藏族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (71, '民族', '壮族', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (72, '学历', '博士', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (73, '学历', '研究生', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (74, '学历', '本科', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (75, '学历', '大专', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (76, '学历', '中专', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (77, '学历', '初中', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (78, '学历', '小学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (79, '学历', '其它', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (80, '政治面貌', '群众', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (81, '政治面貌', '共青团员', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (82, '政治面貌', '中共党员', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (83, '国籍', '中华人民共和国', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (84, '国籍', '美国', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (85, '国籍', '俄罗斯', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (86, '国籍', '日本', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (87, '国籍', '韩国', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (88, '国籍', '新加波', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (89, '国籍', '马来西亚', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (90, '国籍', '英国', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (91, '国籍', '德国', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (92, '国籍', '意大利', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (93, '国籍', '澳大利亚', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (94, '国籍', '巴西', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (95, '专业', '管理科学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (96, '专业', '信息管理和信息系统', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (97, '专业', '工业工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (98, '专业', '工程管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (99, '专业', '农业经理管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (100, '专业', '工商管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (101, '专业', '市场营销', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (102, '专业', '会计学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (103, '专业', '涉外会计', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (104, '专业', '会计电算化', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (105, '专业', '财政金融', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (106, '专业', '财务管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (107, '专业', '技术经济', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (108, '专业', '文秘', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (109, '专业', '国际商务', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (110, '专业', '物流管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (111, '专业', '行政管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (112, '专业', '公共事业管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (113, '专业', '旅游管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (114, '专业', '宾馆/酒店管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (115, '专业', '人力资源管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (116, '专业', '公共关系学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (117, '专业', '物业管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (118, '专业', '房地产经营管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (119, '专业', '劳动与社会保障', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (120, '专业', '土地资源管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (121, '专业', '图书档案学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (122, '专业', '计算机科学与技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (123, '专业', '计算机应用', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (124, '专业', '计算机信息管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (125, '专业', '计算机网络', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (126, '专业', '电子商务', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (127, '专业', '通信工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (128, '专业', '电气工程及其自动化', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (129, '专业', '软件工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (130, '专业', '自动化', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (131, '专业', '电子信息工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (132, '专业', '电子科学与技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (133, '专业', '电子信息科学与技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (134, '专业', '微电子学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (135, '专业', '光信息科学与技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (136, '专业', '机械设计制造及其自动化', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (137, '专业', '材料成型及控制工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (138, '专业', '工业设计', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (139, '专业', '过程装备与控制工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (140, '专业', '机械电子工程/机电一体化', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (141, '专业', '模具设计与制造', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (142, '专业', '机械制造工艺与设备', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (143, '专业', '测控技术与仪器', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (144, '专业', '热能与动力工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (145, '专业', '核工程与核技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (146, '专业', '电力系统及自动化', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (147, '专业', '制冷与低温技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (148, '专业', '冶金工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (149, '专业', '金属材料工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (150, '专业', '无机非金属料工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (151, '专业', '高分子材料与工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (152, '专业', '材料物理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (153, '专业', '材料化学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (154, '专业', '材料科学与工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (155, '专业', '食品科学与工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (156, '专业', '轻化工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (157, '专业', '包装工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (158, '专业', '印刷工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (159, '专业', '纺织工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (160, '专业', '服装设计与工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (161, '专业', '建筑学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (162, '专业', '城市规划', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (163, '专业', '园林规划与设计', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (164, '专业', '土木工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (165, '专业', '道路与桥梁', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (166, '专业', '建设环境与设备工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (167, '专业', '给水排水工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (168, '专业', '供热通风与空调工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (169, '专业', '工业与民用建筑', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (170, '专业', '室内装潢设计', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (171, '专业', '建筑工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (172, '专业', '工程造价管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (173, '专业', '力学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (174, '专业', '应用力学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (175, '专业', '环境科学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (176, '专业', '生态学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (177, '专业', '环境工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (178, '专业', '安全工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (179, '专业', '制药工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (180, '专业', '交通运输', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (181, '专业', '交通工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (182, '专业', '油气储运工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (183, '专业', '飞行技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (184, '专业', '航海技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (185, '专业', '轮机工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (186, '专业', '汽车工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (187, '专业', '飞行器设计与工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (188, '专业', '飞行器动力工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (189, '专业', '飞行器制造工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (190, '专业', '飞行器环境与生命保障工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (191, '专业', '船舶与海洋工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (192, '专业', '水利水电工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (193, '专业', '水文与水资源工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (194, '专业', '港口航道与海岸工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (195, '专业', '测绘工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (196, '专业', '公安技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (197, '专业', '武器系统与发射工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (198, '专业', '探测制导与控制技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (199, '专业', '弹药工程与爆炸技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (200, '专业', '数学与应用数学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (201, '专业', '信息与计算科学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (202, '专业', '物理学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (203, '专业', '应用物理学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (204, '专业', '化学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (205, '专业', '应用化学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (206, '专业', '化学工程与工艺', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (207, '专业', '精细化工', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (208, '专业', '化工设备与机械', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (209, '专业', '生物工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (210, '专业', '生物医学工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (211, '专业', '生物科学,技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (212, '专业', '天文学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (213, '专业', '地质学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (214, '专业', '宝石鉴定与加工', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (215, '专业', '地理科学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (216, '专业', '地球物理学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (217, '专业', '大气科学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (218, '专业', '海洋科学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (219, '专业', '地矿', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (220, '专业', '石油工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (221, '专业', '经济学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (222, '专业', '国际经济与贸易', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (223, '专业', '财政学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (224, '专业', '金融学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (225, '专业', '经济管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (226, '专业', '经济信息管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (227, '专业', '工业外贸', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (228, '专业', '国际金融', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (229, '专业', '投资经济管理', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (230, '专业', '统计学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (231, '专业', '审计学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (232, '专业', '中国语言文学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (233, '专业', '英语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (234, '专业', '俄语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (235, '专业', '德语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (236, '专业', '法语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (237, '专业', '日语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (238, '专业', '西班牙语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (239, '专业', '阿拉伯语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (240, '专业', '朝鲜语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (241, '专业', '其它外语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (242, '专业', '新闻学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (243, '专业', '广播电视新闻', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (244, '专业', '广告学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (245, '专业', '编辑出版学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (246, '专业', '外贸英语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (247, '专业', '商务英语', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (248, '专业', '音乐,舞蹈,作曲', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (249, '专业', '绘画,艺术设计', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (250, '专业', '戏剧,表演', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (251, '专业', '导演,广播电视编导', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (252, '专业', '戏剧影视文学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (253, '专业', '戏剧影视美术设计', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (254, '专业', '摄影,动画', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (255, '专业', '播音,主持,录音', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (256, '专业', '服装设计', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (257, '专业', '法学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (258, '专业', '马克思主义理论', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (259, '专业', '社会学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (260, '专业', '政治学与行政学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (261, '专业', '国际政治', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (262, '专业', '外交学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (263, '专业', '思想政治教育', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (264, '专业', '公安学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (265, '专业', '经济法', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (266, '专业', '国际经济法', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (267, '专业', '哲学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (268, '专业', '逻辑学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (269, '专业', '宗教学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (270, '专业', '教育学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (271, '专业', '学前教育', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (272, '专业', '体育学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (273, '专业', '基础医学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (274, '专业', '预防医学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (275, '专业', '临床医学与医学技术', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (276, '专业', '口腔医学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (277, '专业', '中医学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (278, '专业', '法医学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (279, '专业', '护理学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (280, '专业', '药学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (281, '专业', '心理学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (282, '专业', '医学检验', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (283, '专业', '植物生产', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (284, '专业', '农学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (285, '专业', '园艺', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (286, '专业', '植物保护学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (287, '专业', '茶学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (288, '专业', '草业科学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (289, '专业', '森林资源', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (290, '专业', '环境生态', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (291, '专业', '园林', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (292, '专业', '动物生产', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (293, '专业', '动物医学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (294, '专业', '水产类', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (295, '专业', '农业工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (296, '专业', '林业工程', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (297, '专业', '历史学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (298, '专业', '考古学', '');

insert into dictionary (DICID, ITEMNAME, ITEMVALUE, DESCP)
values (299, '专业', '博物馆学', '');

