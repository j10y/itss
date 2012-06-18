alter table emp_profile modify column chestCardNumber varchar(50) default null comment '胸卡号码';

alter table emp_profile add column censusRegisterType varchar(20) default null comment '户籍性质';

alter table emp_profile add column firstTryUserId bigint default null comment '初试人UserId';

alter table emp_profile add column firstTryUser varchar(20) default null comment '初试人';

alter table emp_profile add column secondTryUserId bigint default null comment '复试人UserId';

alter table emp_profile add column secondTryUser varchar(20) default null comment '复试人';

alter table emp_profile add column contractRenewalRecord varchar(1024) default null comment '合同续签记录';

alter table emp_profile add column isOrientation int default null comment '是否接受入职培训';