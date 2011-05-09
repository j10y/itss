package com.zsgj.itil.workflow.action;

/**
 * 工作流常量
 * @Class Name ActionConstants
 * @Author sa
 * @Create In 2008-11-11
 */
public class Constants {
	static public final String DEF_PROJECT = "itilproject";
	static public final String CDEF_PROJECT = "configItemManager";
	static public final String SDEF_PROJECT = "serviceCatalogueManager";
	static public final String NODEF_PROJECT = "noticeManager";
	static public final String PROCESS_SPECIAL_REQUIRE = "specilaRequire";//个性化需求流程名
	
	static public final String CNODE_AUDIT = "ConfigItemManagerAudit";
	static public final String CNODE_SUBMIT = "AddConfigItem";
	static public final String CNODE_BACK = "GoBack";
	static public final String CNODE_COMFIRM = "UserConfirm";
	
	static public final String SNODE_SUBMIT = "CreateServiceCatalogue";
	static public final String SNODE_INNERAUDIT = "InnerAudit";
	static public final String SNODE_USERAUDIT = "ServiceCataConfirm";
	static public final String SNODE_BACK = "serviceCataBack";
	
	
	static public final String NONODE_AUDIT = "NoticeAudit";
	static public final String NONODE_SUBMIT = "CreateNotice";
	static public final String NONODE_BACK = "NoticeBack";
	
	
	static public final String NODE_SUBMIT = "start";
	static public final String NODE_DEMAND = "demand";
	static public final String NODE_CONFIG = "config";
	static public final String NODE_PLAN = "plan";
	static public final String NODE_CONTRACT = "contract";
	static public final String NODE_CONFIRM = "confirm";
	static public final String NODE_END = "end";
	static public final String NODE_SPECIAL_REQUIRE_APPLY = "applyByUser";//个性化需求流程提交节点
	static public final String NODE_SPECIAL_REQUIRE_END = "end";//个性化需求流程结束节点
}
