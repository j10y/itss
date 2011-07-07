package com.htsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.model.system.SysConfig;
import com.htsoft.oa.service.system.SysConfigService;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class SysConfigAction extends BaseAction {

	@Resource
	private SysConfigService sysConfigService;
	private SysConfig sysConfig;
	private Long configId;

	public Long getConfigId() {
		/* 34 */return this.configId;
	}

	public void setConfigId(Long configId) {
		/* 38 */this.configId = configId;
	}

	public SysConfig getSysConfig() {
		/* 42 */return this.sysConfig;
	}

	public void setSysConfig(SysConfig sysConfig) {
		/* 46 */this.sysConfig = sysConfig;
	}

	public String list() {
		/* 54 */QueryFilter filter = new QueryFilter(getRequest());
		/* 55 */List<SysConfig> list = this.sysConfigService.getAll(filter);

		/* 57 */Type type = new TypeToken<List<SysConfig>>() {
		}
		/* 57 */.getType();
		/* 58 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 59 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 61 */Gson gson = new Gson();
		/* 62 */buff.append(gson.toJson(list, type));
		/* 63 */buff.append("}");

		/* 65 */this.jsonString = buff.toString();

		/* 67 */return "success";
	}

	public String multiDel() {
		/* 75 */String[] ids = getRequest().getParameterValues("ids");
		/* 76 */if (ids != null) {
			/* 77 */for (String id : ids) {
				/* 78 */this.sysConfigService.remove(new Long(id));
			}
		}

		/* 82 */this.jsonString = "{success:true}";

		/* 84 */return "success";
	}

	public String get() {
		/* 92 */SysConfig sysConfig = (SysConfig) this.sysConfigService
				.get(this.configId);

		/* 94 */Gson gson = new Gson();

		/* 96 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 97 */sb.append(gson.toJson(sysConfig));
		/* 98 */sb.append("}");
		/* 99 */setJsonString(sb.toString());

		/* 101 */return "success";
	}

	public String save() {
		/* 107 */Map con = AppUtil.getSysConfig();
		/* 108 */Map map = getRequest().getParameterMap();
		/* 109 */Iterator it = map.entrySet().iterator();
		/* 110 */while (it.hasNext()) {
			/* 111 */Map.Entry entry = (Map.Entry) it.next();
			/* 112 */String key = (String) entry.getKey();
			/* 113 */SysConfig conf = this.sysConfigService.findByKey(key);
			/* 114 */String[] value = (String[]) entry.getValue();
			/* 115 */conf.setDataValue(value[0]);
			/* 116 */this.sysConfigService.save(conf);
			/* 117 */con.remove(key);
			/* 118 */con.put(key, value[0]);
		}

		/* 122 */AppUtil.reloadSysConfig();

		/* 124 */setJsonString("{success:true}");
		/* 125 */return "success";
	}

	public String load() {
		/* 129 */Map conf = this.sysConfigService.findByType();
		/* 130 */Iterator it = conf.entrySet().iterator();
		/* 131 */StringBuffer buff = new StringBuffer("[");
		/* 132 */while (it.hasNext()) {
			/* 133 */Map.Entry type = (Map.Entry) it.next();
			/* 134 */String typeName = (String) type.getKey();
			/* 135 */buff.append("{xtype:'fieldset',title:'" + typeName
					+ "',layout:'form',width:650,items:[");
			/* 136 */List<SysConfig> list = (List) type.getValue();
			/* 137 */if (typeName.equals("验证码配置")) {
				/* 138 */for (SysConfig con : list)
					/* 139 */buff
							.append("{xtype:'container',style:'padding-bottom:3px;',layout:'column',items:[{xtype:'label',style:'font-weight:bold;',text:'")
							/* 140 */.append(con.getConfigName())
							/* 141 */.append(
									":',width:100},{xtype:'combo',mode:'local',editable:false,triggerAction:'all',store:[['1','开启验证码'],['2','屏蔽验证码']],width:300,allowBlank:false,hiddenName:'")
							/* 142 */.append(con.getConfigKey())
							/* 143 */.append("',value:'")
							/* 144 */.append(con.getDataValue())
							/* 145 */.append(
									"'},{xtype:'label',width:200,text:'")
							/* 146 */.append(con.getConfigDesc())
							/* 147 */.append("'}]},");
			} else {
				/* 150 */for (SysConfig con : list) {
					/* 151 */buff
							.append("{xtype:'container',style:'padding-bottom:3px;',layout:'column',items:[{xtype:'label',style:'font-weight:bold;',text:'")
							/* 152 */.append(con.getConfigName())
							/* 153 */.append(
									":',width:100},{xtype:'textfield',width:300,allowBlank:false,id:'")
							/* 154 */.append(con.getConfigKey())
							/* 155 */.append("',name:'")
							/* 156 */.append(con.getConfigKey())
							/* 157 */.append("',value:'")
							/* 158 */.append(con.getDataValue())
							/* 159 */.append(
									"'},{xtype:'label',width:200,text:'")
							/* 160 */.append(con.getConfigDesc())
							/* 161 */.append("'}]},");
				}
			}
			/* 164 */if (list.size() > 0) {
				/* 165 */buff.deleteCharAt(buff.length() - 1);
			}
			/* 167 */buff.append("]},");
		}
		/* 169 */if (conf.size() > 0) {
			/* 170 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 172 */buff.append("]");
		/* 173 */setJsonString("{success:true,data:" + buff.toString() + "}");
		/* 174 */return "success";
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.action.system.SysConfigAction JD-Core Version: 0.6.0
 */