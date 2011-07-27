--add select flow by arch type
alter table archives_type add `processDefId` bigint(20) REFERENCES `pro_definition` (`defId`);
alter table arch_rec_type add `processDefId` bigint(20) REFERENCES `pro_definition` (`defId`);
alter table archives_type add `processDefName` varchar(256);
alter table arch_rec_type add `processDefName` varchar(256);