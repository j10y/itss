alter table hr_pa_kpipbc add column lineManager bigint default null comment 'ֱ���ϼ�';
alter table hr_pa_kpipbc2user add column lineManager bigint default null comment 'ֱ���ϼ�';
alter table hr_pa_kpiitem2user add column remark varchar(1000) default null comment 'ʵ��������';
alter table hr_pa_authpbcitem add column remark varchar(1000) default null comment 'ʵ��������';
alter table hr_pa_kpipbc_hist add column lineManager bigint default null comment 'ֱ���ϼ�';
alter table hr_pa_kpipbc2usercmp add column lineManager bigint default null comment 'ֱ���ϼ�';
alter table hr_pa_kpiitem2usercmp add column remark varchar(1000) default null comment 'ʵ��������';