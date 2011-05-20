PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
            //minTabWidth:100,
            //resizeTabs:true,
            title:tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
            border : false, 
            //tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			//bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa,panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title:panelTitle,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	}, 
	
	
<#--生成form模板-->
<#macro creatFormPanel funName modelName panelName formTitle objName readonlyFlag item itemLoad formMapping button>
 ${funName}: function() {
		this.${objName}= new Ext.form.FormPanel({
			id : '${panelName}',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			reader : ${formMapping},
			title : "${formTitle}",
			items : ${item}
			 <#if "${button}"!="">
			  ,buttons :${button}
			</#if>
		});
		 if (this.dataId != "0") {
			this.${objName}.load({
				url : webContext+'/extjs/panelData?method=findPanelDataById&panelName=${panelName}&dataId='+ this.dataId,
				timeout : 30,
				success : function(action, form) {
				${itemLoad}
				}
			});
		}
		return this.${objName};
	},
</#macro>
<#--生成gridPanel模板-->
<#macro creatGridPanel funName panelName gridTitle objName fcolumnPropName pcolumnPropName readonlyFlag columnItem  fields button>
${funName}:function(){
		var sm = new Ext.grid.CheckboxSelectionModel();
		 this.store = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                    url : webContext+'/extjs/pageData?method=pageQuery&panelname='+"${panelName}",
                    root:"data",
                    fields:${fields},
                    totalProperty:"rowCount",
                    remoteSort:false,             
                   	timeout:8000
                    //sortInfo:{field:'id',direction:'ASC'}
                    });
		var viewConfig = Ext.apply({
			forceFit : true
		   }, this.gridViewConfig);
		this.formValue = '';
		this.${objName}= new Ext.grid.EditorGridPanel({
			id : '${panelName}',
			store : this.store,
			sm : sm,
			columns:${columnItem},
			title :"${gridTitle}",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true
			<#if "${button}"!="">
			  ,tbar :${button}
			</#if>
		});
			if (this.dataId != '0') {
					var pcnameValue = "";
					var fcnameObj = Ext.getCmp("${pcolumnPropName}");
					if(fcnameObj!=undefined){
					   pcnameValue = Ext.getCmp("${pcolumnPropName}").getValue();
					}
					var str = '{' + '\"' + "${fcolumnPropName}" + '\"' + ':' + '\"'
							+ pcnameValue + '\"' + '}';// 这是要随时变得
					var param = eval('(' + str + ')');
					param.methodCall = 'query';
					// alert(str);
					this.store.load({
						params : param
					});
				}
		return this.${objName};
	},
</#macro>

<#--生成普通的grid-->
<#macro creatCommonGrid funName panelName gridTitle objName fcolumnPropName pcolumnPropName readonlyFlag columnItem  fields button>
${funName}:function(){
	   var sm = new Ext.grid.CheckboxSelectionModel();
	   this.store = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                    url : webContext+'/extjs/pageData?method=pageQuery&panelname='+"${panelName}",
                    root:"data",
                    fields:${fields},
                    totalProperty:"rowCount",
                    remoteSort:false,             
                   	timeout:8000
                    //sortInfo:{field:'id',direction:'ASC'}
                    });
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.${objName}= new Ext.grid.GridPanel({
			id : '${panelName}',
			store : this.store,
			sm : sm,
			columns:${columnItem},
			title :"${gridTitle}",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true
		   <#if "${button}"!="">
			  ,tbar :${button}
			</#if>
			
		});
			if (this.dataId != '0') {
				
					var pcnameValue = "";
					var fcnameObj = Ext.getCmp("${pcolumnPropName}");
					if(fcnameObj!=undefined){
					   pcnameValue = Ext.getCmp("${pcolumnPropName}").getValue();
					}
					var str = '{' + '\"' + "${fcolumnPropName}" + '\"' + ':' + '\"'
							+ pcnameValue + '\"' + '}';// 这是要随时变得
							
					if(this.model=="flm_requireFormPage"|| "${panelName}"=="configItemListPanel"){ //
						var str = '{\"configItemRequireId\":\"' + this.dataId + '\"}';
					}
					
					var param = eval('(' + str + ')');
					param.methodCall = 'query';
					// alert(str);
					this.store.load({
						params : param
					});
				}
		return this.${objName};
	},
</#macro>
<#macro creatChild childPanel pareName>
	<#--定义form中的变量-->
		<#assign fuNa="">
		<#assign obNa="">
	<#--定义grid中的变量-->
		<#assign funA="">
		<#assign obnA="">
	<#list childPanel.childPagePanels as child>
		  <@creatChild  childPanel=child pareName="${childPanel.panelname}"/>
	</#list>
	<#--下面是子panel具体面板的构造-->
	<#if "${childPanel.xtype}"=="form">
	    <#assign fuNa="getForm"+pareName+"${childPanel.panelname}">
	    <#assign obNa="form"+pareName+"${childPanel.panelname}">
		<@creatFormPanel 
			funName=fuNa
			modelName="${model.modelName}" 
			panelName="${childPanel.panelname}" 
		    formTitle="${childPanel.panelTitle}"
			objName=obNa
			readonlyFlag="${childPanel.readonlyFlag}"
			item="${childPanel.item}"
			itemLoad="${panels.itemLoad}"
			formMapping="${childPanel.formMapping}"
			button="${childPanel.button}"
		/> 
	<#elseif "${childPanel.xtype}"=="editorgrid">
	    <#assign funA="getGrid"+pareName+"${childPanel.panelname}">
	    <#assign obnA="grid"+pareName+"${childPanel.panelname}">
        <@creatGridPanel 
	        funName=funA
	        panelName="${childPanel.panelname}" 
	        gridTitle="${childPanel.panelTitle}" 
	        objName=obnA
	        fcolumnPropName="${childPanel.fcolumnPropName}" 
	        pcolumnPropName="${childPanel.pcolumnPropName}"
	        readonlyFlag="${childPanel.readonlyFlag}"
	        columnItem="${childPanel.columnItem}"
	        fields="${childPanel.fields}"
	        button="${childPanel.button}"
        />
    <#elseif "${childPanel.xtype}"=="grid">
	    <#assign funA="getGridCom"+"${childPanel.panelname}">
	    <#assign obnA="gridCom"+"${childPanel.panelname}">
        <@creatCommonGrid 
	        funName=funA
	        panelName="${childPanel.panelname}" 
	        gridTitle="${childPanel.panelTitle}" 
	        objName=obnA
	        fcolumnPropName="${childPanel.fcolumnPropName}" 
	        pcolumnPropName="${childPanel.pcolumnPropName}"
	        readonlyFlag="${childPanel.readonlyFlag}"
	        columnItem="${childPanel.columnItem}"
	        fields="${childPanel.fields}"
	        button="${childPanel.button}"
        />
    </#if>
</#macro>
<#--start-->
<#--定义form中的变量-->
	<#assign fuNa="">
	<#assign obNa="">
<#--定义grid中的变量-->
	<#assign funA="">
	<#assign obnA="">	
<#list model.panels as panels>
	<#list panels.childPagePanels as child>
	      <@creatChild  childPanel=child pareName="${panels.panelname}"/>
	</#list>
    <#--注意字符串的比较必须得加上双引号否则会报错的-->
	<#if "${panels.xtype}"=="form">
	    <#assign fuNa="getForm"+"${panels.panelname}">
	    <#assign obNa="form"+"${panels.panelname}">
		<@creatFormPanel 
			funName=fuNa
			modelName="${model.modelName}" 
			panelName="${panels.panelname}" 
		    formTitle="${panels.panelTitle}"
			objName=obNa
			readonlyFlag="${panels.readonlyFlag}"
			item="${panels.item}"
			itemLoad="${panels.itemLoad}"
			formMapping="${panels.formMapping}"
			button="${panels.button}"
		/> 
	<#elseif "${panels.xtype}"=="editorgrid">
	    <#assign funA="getGrid"+"${panels.panelname}">
	    <#assign obnA="grid"+"${panels.panelname}">
        <@creatGridPanel 
	        funName=funA
	        panelName="${panels.panelname}" 
	        gridTitle="${panels.panelTitle}" 
	        objName=obnA
	        fcolumnPropName="${panels.fcolumnPropName}" 
	        pcolumnPropName="${panels.pcolumnPropName}"
	        readonlyFlag="${panels.readonlyFlag}"
	        columnItem="${panels.columnItem}"
	        fields="${panels.fields}"
	        button="${panels.button}"
        />
     <#elseif "${panels.xtype}"=="grid">
	    <#assign funA="getGridCom"+"${panels.panelname}">
	    <#assign obnA="gridCom"+"${panels.panelname}">
        <@creatCommonGrid 
	        funName=funA
	        panelName="${panels.panelname}" 
	        gridTitle="${panels.panelTitle}" 
	        objName=obnA
	        fcolumnPropName="${panels.fcolumnPropName}" 
	        pcolumnPropName="${panels.pcolumnPropName}"
	        readonlyFlag="${panels.readonlyFlag}"
	        columnItem="${panels.columnItem}"
	        fields="${panels.fields}"
	        button="${panels.button}"
        />
    </#if>
</#list>
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
	    var items = new Array();
	    var pa = new Array();
		  this.pa = pa;
		var gd = new Array();
		  this.gd = gd;
		var temp = new Array();
		  this.temp = temp;
		var formname = new Array();
		  this.formname=formname;
		var gridname = new Array();
		  this.gridname=gridname;
		this.model="${model.modelName}";
			this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			}
			<#if "${model.button}"!="">
			  ,items : ${model.button}
			</#if>
			};
		
<#macro saveChild childPanel paraXtype pareName ArryName ObjName paraArry Flag groupTitle>
    <#assign non="no">
    <#list childPanel.childPagePanels as child>
      <#assign non="yes">
	</#list> 
    <#if "${non}"=="yes">
      <#assign tm=ArryName+"tm">
	  <#assign childName=ObjName+"cm">
	  <#assign flag="">
      var ${tm}=new Array();
      var ${childName}="";
	     <#list childPanel.childPagePanels as child>
	          <#assign flag=flag+"f">
		      <@saveChild 
		       childPanel=child  
		       paraXtype="${childPanel.xtype}"
		       pareName="${childPanel.panelname}"
		       ArryName="${tm}"
		       ObjName="${childName}"
		       paraArry="${paraArry}"
		       Flag="${flag}"
		       groupTitle="${childPanel.panelTitle}"
		       />
		 </#list> 
    <#else>
		    <#if "${childPanel.xtype}"=="form">
			     this.getForm${pareName}${childPanel.panelname}();
			     this.pa.push(this.form${pareName}${childPanel.panelname});
			     this.formname.push("${childPanel.panelname}");
			     ${ArryName}.push(this.form${pareName}${childPanel.panelname});
			<#elseif "${childPanel.xtype}"=="editorgrid">
			     this.getGrid${pareName}${childPanel.panelname}();
			     this.gd.push(this.grid${pareName}${childPanel.panelname});
			     this.gridname.push("${childPanel.panelname}");
			     ${ArryName}.push(this.grid${pareName}${childPanel.panelname});
			<#elseif "${childPanel.xtype}"=="grid">
			     this.getGridCom${pareName}${childPanel.panelname}();
			     this.gd.push(this.gridCom${pareName}${childPanel.panelname});
			     this.gridname.push("${childPanel.panelname}");
			     ${ArryName}.push(this.gridCom${pareName}${childPanel.panelname});
			</#if>
			<#if paraXtype=="tabpanel">
			         ${ObjName}= this.getTabpanel(${ArryName},"${groupTitle}");
			<#else>
			         ${ObjName}= this.getPanel(${ArryName},"${groupTitle}");
			</#if>
			<#if "${Flag}"=="f">
			       ${paraArry}.push(${ObjName});
			<#else>
			       ${paraArry}[${paraArry}.length-1]=${ObjName};
			</#if>
    </#if>
</#macro>
<#list model.panels as panels>
    <#assign non="no">
	<#list panels.childPagePanels as child>
	<#assign non="yes">
	</#list>
	<#if "${non}"=="yes">
	  <#assign tm="tm">
	  <#assign childName="cm">
	  <#assign demp="temp">
	  <#assign flag="">
      var ${tm}=new Array();
      var ${childName}="";
		<#list panels.childPagePanels as child>
		    <#assign flag=flag+"f">
		      <@saveChild 
		       childPanel=child  
		       paraXtype="${panels.xtype}"
		       pareName="${panels.panelname}"
		       ArryName="${tm}"
		       ObjName="${childName}"
		       paraArry="${demp}"
		       Flag="${flag}"
		       groupTitle="${panels.panelTitle}"
		       />
		</#list>
	<#else>
		<#if "${panels.xtype}"=="form">
		       this.getForm${panels.panelname}();
		       this.pa.push(this.form${panels.panelname});
		       this.formname.push("${panels.panelname}");
		       temp.push(this.form${panels.panelname});
		<#elseif "${panels.xtype}"=="editorgrid">
		       this.getGrid${panels.panelname}();
		       this.gd.push(this.grid${panels.panelname});
		       this.gridname.push("${panels.panelname}");
		       temp.push(this.grid${panels.panelname});
	    <#elseif "${panels.xtype}"=="grid">
		       this.getGridCom${panels.panelname}();
		       this.gd.push(this.gridCom${panels.panelname});
		       this.gridname.push("${panels.panelname}");
		       temp.push(this.gridCom${panels.panelname});
		</#if>
	</#if>
</#list>
<#if "${model.pagePathType}"=="tabpanel">
          items.push(this.getTabpanel(temp));
<#else>
          items = temp;
</#if>
		items.push(this.buttons);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})