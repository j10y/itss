<%@ page language="java" pageEncoding="gbk"%>
<html>
	<head>
		<title>欢迎使用IT服务系统</title>		 
		<%@include file="/includefiles.jsp"%>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/infoAdmin/workflow/allTaskPanel.js"></script>

		<style type="text/css">
.x-head {
	background: url(images/titlelog.png) no-repeat left;
	height: 65px;
	background-color: 'blank'
}

html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}

p {
	margin: 5px;
}

.nav {
	background-image: url(images/other/folder_go.png);
}

.cls {
	font-size: 9pt;
}

.common-text {
	font-size: 9pt;
}
</style>
		<script type="text/javascript">
	function getProcessHistory(id){
		window.location.href = webContext + "/workflow/history.do?procid="+id+"&methodCall=list";
	}
	function endProcess(id){
		Ext.Msg.confirm("确认", "您确定要终止流程？ ", function(bool){
			if(bool=="yes"){
				var url = "${pageContext.request.contextPath}/workflow/endprocess.do?id="+id;
			    url += "&methodCall=endProcess";
				Ext.Ajax.request({  
			        url: url,
			        async: false,
			        method: 'POST',
			        success: function(response, opts) {	
			        	obj = Ext.util.JSON.decode(response.responseText);
			        	if(obj.success){
			        		Ext.getCmp("ListTaskPanel").listAll();
						}			         	
			        },
			        failure: function(response, opts) {
			         	Ext.Msg.alert("提示","操作失败");
			        }   
			    });
			}
		});
		
    }
    function reassign(taskId){
    	var win = new Ext.Window({
    		id:'assignWin',
    		title:'指派人',
    		layout:'fit',
    		width:300,
    		height:100,
    		modal:true,
    		items:[{
    			id:'assignForm',
    			xtype:'form',
    			layout:'form',
    			frame:true,
    			items:[{    				
						anchor:"100%",
						fieldLabel:"用户账号",
						width:200,
						maxLength:255,
						id:'userCombo1',
						name:"actor",
						xtype:"combo",
						emptyText:'请输入用户账号,如：zhangsan',
						typeAhead : true,
						defaultParam : "",
						allowBlank:false,
						triggerAction : "all",
						displayField : 'realName',
						valueField : "userName",
						store : new Ext.data.JsonStore({
							url : webContext + '/system/utilAction.do?methodCall=searchComboMessage&className=com.zsgj.info.framework.security.entity.UserInfo&propertyName=userName&nameField=realName&valueField=userName',
							fields : ['userName', 'realName'],
							totalProperty : 'rowCount',
							root : 'data',
							listeners : {
								beforeload : function(store, opt) {
									var param = Ext.getCmp('userCombo').defaultParam;
									if (opt.params['propertyValue'] == undefined) {
										opt.params['propertyValue'] = param;
									}
								}
							}
						}),
						listeners : {
							'beforequery' : function(queryEvent) {
								var param = queryEvent.query;
								this.defaultParam = param;
								Ext.getCmp("userCombo1").store.load({
											params : {
												propertyValue : param,
												start : 1
											}
										});
								return true;
							},
							"blur" : function(combo){
								var nowVal = combo.getRawValue();
								var nowId = combo.getValue();
								if(nowVal==""){
									combo.setValue("")
								}else if(nowId==""){
									combo.setValue("");
									Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
								}
							}
						}						

					}]
    		}],
    		buttonAlign:'center',
    		buttons:[{
    			xtype:'button',
    			text:'确定',
    			iconCls:'cancel',
    			handler:function(){
    				if(Ext.getCmp("userCombo1").isValid()){
    					var userName = Ext.getCmp("userCombo1").getValue();
	    				var url = "${pageContext.request.contextPath}/workflow/reassign.do?id="+taskId;
					    url += "&actor="+userName;
					    url += "&methodCall=reassign";
					    Ext.Ajax.request({  
					        url: url,
					        async: false,
					        method: 'POST',
					        success: function(response, opts) {			        				         	
					         	Ext.Msg.alert("提示","操作成功");
					         	Ext.getCmp("ListTaskPanel").listAll();
					         	Ext.getCmp("assignWin").close();
					        },
					        failure: function(response, opts) {
					         	Ext.Msg.alert("提示","操作失败");
					        }   
					    });
    				}else{
    					Ext.Msg.alert("提示", "请选择指派人");
    				}    				
    			}    		
    		},{
    			xtype:'button',
    			text:'取消',
    			iconCls:'cancel',
    			handler:function(){
    				Ext.getCmp("assignWin").close();
    			}
    		}]
    	});
    	win.show();
    	return;
	    var url = "${pageContext.request.contextPath}/workflow/reassign.do?id="+taskId;
	    url += "&actor="+userName;
	    url += "&methodCall=reassign";
	    Ext.Ajax.request({  
			        url: url,
			        async: false,
			        method: 'POST',
			        success: function(response, opts) {			        				         	
			         	Ext.Msg.alert("提示","操作成功");
			        },
			        failure: function(response, opts) {
			         	Ext.Msg.alert("提示","操作失败");
			        }   
			    });
	  	location = url;
	}
	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/extEngine/resources/css/' + value + '.css');
	}
	function updateTab(id,title, url) {
    	var tab = mainPanel.getItem(id);
  		if(tab){
   			mainPanel.remove(tab);
   		}
    	tab = addTab(id,title,url);
   		mainPanel.setActiveTab(tab);
    }
	
	Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';

	Ext.onReady(function(){
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   var listProcessPanel = new ListProcessPanel();
	   
	   listProcessPanel.render("process");
	   
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[listProcessPanel]
	   });
	   //orderApplyPanel.initData();
	});
	
	Ext.override(Ext.grid.GridView, {
    templates: {
        cell: new Ext.Template(
               '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>',
               '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
               "</td>"
           )
    }
	});
	</script>
	</head>
	<body onload="changeSkin('${userInfo.userViewStyle}');">
		<div id="process" style="height: 100%"></div>
		<div id="hello-win" class="x-hidden">
			<div class="x-window-header">
				Hello Dialog
			</div>
			<div id="hello-tabs">
				<!-- Auto create tab 1 -->
				<div class="x-tab" title="Hello World 1">
					<p>
						Hello...
					</p>
				</div>
				<!-- Auto create tab 2 -->
				<div class="x-tab" title="Hello World 2">
					<p>
						... World!
					</p>
				</div>
			</div>
		</div>

	</body>
</html>
