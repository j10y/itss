alter table emp_profile add column leaveReason varchar(1024) default null comment '离职原因';

alter table emp_profile_hist add column leaveReason varchar(1024) default null comment '离职原因';