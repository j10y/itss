alter table emp_profile add column chestCardNumber varchar(50) default null comment '胸卡号码';

alter table emp_profile add column censusRegisterType varchar(20) default null comment '户籍性质';

alter table emp_profile add column firstTryUserId bigint default null comment '初试人UserId';

alter table emp_profile add column firstTryUser varchar(20) default null comment '初试人';

alter table emp_profile add column secondTryUserId bigint default null comment '复试人UserId';

alter table emp_profile add column secondTryUser varchar(20) default null comment '复试人';

alter table emp_profile add column contractRenewalRecord varchar(1024) default null comment '合同续签记录';

alter table emp_profile add column isOrientation int default null comment '是否接受入职培训';

alter table emp_profile add column renewalBeginDate datetime default null comment '合同签署日期（第一次续签）';

alter table emp_profile add column renewalEndDate datetime default null comment '合同截止日期（第一次续签）';

alter table emp_profile add column seRenewalBeginDate datetime default null comment '合同签署日期（第二次续签）';

alter table emp_profile add column seRenewalEndDate datetime default null comment '合同截止日期（第二次续签）';

alter table emp_profile add column isOpenEnded int default null comment '是否签订无固定期限';


alter table emp_profile_hist add column chestCardNumber varchar(50) default null comment '胸卡号码';

alter table emp_profile_hist add column censusRegisterType varchar(20) default null comment '户籍性质';

alter table emp_profile_hist add column firstTryUserId bigint default null comment '初试人UserId';

alter table emp_profile_hist add column firstTryUser varchar(20) default null comment '初试人';

alter table emp_profile_hist add column secondTryUserId bigint default null comment '复试人UserId';

alter table emp_profile_hist add column secondTryUser varchar(20) default null comment '复试人';

alter table emp_profile_hist add column contractRenewalRecord varchar(1024) default null comment '合同续签记录';

alter table emp_profile_hist add column isOrientation int default null comment '是否接受入职培训';

alter table emp_profile_hist add column renewalBeginDate datetime default null comment '合同签署日期（第一次续签）';

alter table emp_profile_hist add column renewalEndDate datetime default null comment '合同截止日期（第一次续签）';

alter table emp_profile_hist add column seRenewalBeginDate datetime default null comment '合同签署日期（第二次续签）';

alter table emp_profile_hist add column seRenewalEndDate datetime default null comment '合同截止日期（第二次续签）';

alter table emp_profile_hist add column isOpenEnded int default null comment '是否签订无固定期限';