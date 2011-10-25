alter table job add column band bigint(20) default null comment 'band';
alter table job add column seq bigint(20) default null comment '序列';
alter table job add column race bigint(20) default null comment '族群';

alter table emp_profile add column empType bigint(20) default null comment '用工形式 来源于字典表';
alter table emp_profile add column practiceRecorddatetime varchar(1024) default null comment '实习记录';
alter table emp_profile add column contractBeginDate datetime default null comment '合同签署日期';
alter table emp_profile add column contractEndDate datetime default null comment '合同截止日期';

alter table stand_salary add column baseMoney decimal default null comment '固定工资';