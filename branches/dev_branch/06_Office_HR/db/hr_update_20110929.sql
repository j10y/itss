alter table hr_pa_kpipbc add column lineManager bigint default null comment '直接上级';
alter table hr_pa_kpipbc2user add column lineManager bigint default null comment '直接上级';
alter table hr_pa_kpiitem2user add column remark varchar(1000) default null comment '实际完成情况';
alter table hr_pa_authpbcitem add column remark varchar(1000) default null comment '实际完成情况';
alter table hr_pa_kpipbc_hist add column lineManager bigint default null comment '直接上级';
alter table hr_pa_kpipbc2usercmp add column lineManager bigint default null comment '直接上级';
alter table hr_pa_kpiitem2usercmp add column remark varchar(1000) default null comment '实际完成情况';