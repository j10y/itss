EventDealPanel = Ext.extend(Ext.Panel, {
    id : "eventDealPanel",
    closable : true,
    autoScroll:true,
    farme:true,
        layoutConfig : {
                columns : 1
            },
    border:true,
    width:762,
    layout : 'table',
   
    deal : function(){///
              var record = this.grid.getSelectionModel().getSelected();
              var records = this.grid.getSelectionModel().getSelections();
                if(!record){
                    Ext.Msg.alert("提示","请先选择要修改的行！");
                    return;
                }
                if(records.length>1){
                    Ext.Msg.alert("提示","修改时只能选择一行！");
                    return;
                }
               var pdataId=record.get('Problem$id');    
               var conn = Ext.lib.Ajax.getConnectionObject().conn;
               conn.open("POST", webContext+'/problemAction_findProblemStatus.action?eventId='+pdataId,false);
               conn.send(null); 
               var result = Ext.decode(conn.responseText);
               var pStatus=result.STATUS;
                 if(pStatus=='dealing'){
                     window.location =webContext+'/user/event/transactionFlow/engineerprobleminfo.jsp?pdataId='+pdataId + '&EventId='+this.dataId + '&taskId=' + this.taskId;
                 }else{
                     window.location =webContext+'/user/event/transactionFlow/skipengineerprobleminfo.jsp?pdataId='+pdataId+'&EventId='+this.dataId+'&taskId=' + this.taskId;
                }
    },
    //删除问题
    remove : function() {///
        var records = this.grid.getSelectionModel().getSelections();
        if (records.length==0) {
            Ext.Msg.alert("提示", "请先选择要删除的记录！");
            return;
        }
        Ext.Msg.confirm('提示', '您确定要进行删除操作吗？', function(button) {
            if (button == 'yes') {
                var problemsId=new Array();
                for(var i=0;i<records.length;i++){
                    problemsId.push(records[i].get('Problem$id'));
                }
                problemsId=Ext.encode(problemsId);
                Ext.Ajax.request({
                        url :webContext + '/problemAction_removeProblems.action',
                        params : {problemsId:problemsId},
                        success:function(response){
                            if(response.responseText==''){
                                Ext.Msg.alert("提示","删除成功！",function(){
                                    Ext.getCmp("problemgrid").getStore().reload();
                                });
                            }
                            else 
                               Ext.Msg.alert("提示","编号为:"+response.responseText+"的问题已关联配置项，不允许删除！");
                       },scope:this
                        
                    });
                }
        }, this);

    },

    // 创建问题
 create : function() {///
        var da = new DataAction();
        var data=da.getPanelElementsForAdd("problemForm_pagepanel");
         for(var i=0;i<data.length;i++){
             if(data[i].id=="Problem$problemCisn"){
               data.remove(data[i]);
             }
        }
        var dataform = this.splitOwn(data);
        var envForm = new Ext.form.FormPanel({
            id:"evformaa",
            layout : 'table',
            width : 580,
            height : 250,
                layoutConfig : {
                    columns : 4
                },
                defaults : {
                    bodyStyle : 'padding:5px'
                },
            frame : true,
            items : dataform

        });
        Ext.getCmp("Problem$summary").allowBlank = false;
        var win = new Ext.Window({
                title : '添加问题',
                width : 598,
                height : 323,
                modal : true,
                //maximizable : true,
                autoScroll : true,
                items : [envForm],
                closeAction : 'hide',
                bodyStyle : 'padding:2px',
                buttons : [{
                    text : '保存',
                    id : 'submitBookInfo',
                    handler :function() {
                       if (Ext.getCmp('evformaa').form.isValid()) {
                       var panelparam = Ext.encode(getFormParam('evformaa'));
                       Ext.Ajax.request({
                            url :webContext + '/problemAction_saveProblem.action',
                            params: { eid:this.dataId,
                                     panelparam:panelparam
                                     },
                            method:'post', 
                            success:function(){
                           Ext.MessageBox.alert("提示","问题保存成功！",function(){
                                
                                Ext.getCmp('problemgrid').store.reload();
                               win.close()
                           });
                               
                          },
                           failure:function(response,options){
                             Ext.MessageBox.alert("提示","保存数据失败！",function(){ Ext.getCmp("submitBookInfo").enable();});
                          }
                      });
                      }else{
                          Ext.MessageBox.alert("提示","请填写相关信息！",function(){
                           Ext.getCmp("submitBookInfo").enable();
                          });
                      }
                    },
                    scope : this
                },{
                    text : '关闭',
                    handler : function() {
                        win.hide();
                    },
                    scope : this
                }]

            });
        Ext.getCmp("submitBookInfo").on("click",function(obj){
              Ext.getCmp("submitBookInfo").disable();
              });
        win.show();
        
     
    },
    splitOwn : function(data) {
        var labellen = 85;
        var itemlen = 200;
        var throulen = 20;
        if(Ext.isIE){
                  throulen = 450;
        }
        else{
                  throulen = 540;
        }
        if (data == null || data.length == 0) {
                  return null;
        }
        var hid = 0;
        var hidd = new Array();
        var longData = new Array();
        var longitems = 0;
        //alert(this.dataId);         
        for (i = 0; i < data.length; i++) {                         
                  
                  data[i].style = data[i].style == null ? "" : data[i].style;
                  if (data[i].xtype == "textarea") {
                            data[i].style += "'margin:5 0 5 0;'";
                  }
                  if (data[i].xtype == "hidden") {
                            hidd[hid] = data[i];
                            hid++;
                            continue;
                  }

                  //alert(data[i].width+data[i].name);
                  if (data[i].width == null || data[i].width == 'null'
                                      || data[i].width == "") {
                            data[i].style += "width:" + itemlen + "px";
                            data[i].width = itemlen;
                  } else {
                            if (data[i].width == "9999") {//通栏  
                                     // alert("data");
                                      if ((i-hid+longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
                                                longData[2 * (i - hid) - 1].colspan = 3;                                                            
                                      }
                                      else{//多占一栏
                                                longitems ++;
                                      }
                                      data[i].colspan = 3;//本栏贯通                                            
                                      data[i].width = throulen;
                                      if(data[i].xtype == "textarea"){
                                      data[i].height = 150;
                                      }
                                      data[i].style += "width:" + throulen + "px;";
                            } else {//正常长度，半栏 
                                      data[i].style += "width:" + itemlen + "px";
                                      data[i].width = itemlen;                                              
                            }
                  }
                  //alert(data[i].width+data[i].name);
                  longData[2 * (i - hid)] = {
                            html : data[i].fieldLabel + ":",
                            cls : 'common-text',
                            style : 'width:' + labellen + ';text-align:right'
                  };
                  longData[2 * (i - hid) + 1] = data[i];                      
        }
        for (i = 0; i < hidd.length; i++) {
                  longData[longData.length] = hidd[i];
        }
        return longData;
          },
    items:this.items,
    initComponent : function() {
        this.fpd=new EventRelationPanel({EventId:this.dataId}).erp;///
        var da = new DataAction();
        var obj=da.getListPanelElementsForHead("problemList_pagepanel");
        var sm =new Ext.grid.CheckboxSelectionModel();
        var columns= new Array();
        var fields = new Array();
        columns[0]=sm;
        for(var i=0;i<obj.length;i++){
            var headItem = obj[i];
            var title = headItem.header;
            var alignStyle ='left';
            var propertyName = headItem.dataIndex;
            var isHidden=false;
            if(propertyName=="Problem$id"){
             isHidden =true;
            }
        var columnItem={
            header:title,
            dataIndex:propertyName,
            sortable:true,
            hidden:isHidden,   
            align:alignStyle
        }
        //2010-05-04 add by huzh for 页面优化 begin
         if(propertyName=="Problem$id"){
				columnItem.width=70;
			}
			if(propertyName=="Problem$problemCisn"){
				columnItem.width=100;
			}
			if(propertyName=="Problem$summary"){
				columnItem.width=200;
			}
			if(propertyName=="Problem$status"){
				columnItem.width=120;
			}
			if(propertyName=="Problem$closedDate"){
				columnItem.width=110;
			}
			if(propertyName=="Problem$submitUser"){
				columnItem.width=120;
			}
		//2010-05-04 add by huzh for 页面优化 end
        columns[i+1]=columnItem;
        fields[i]=propertyName;
        }
        this.storeMapping=fields;
        this.cm=new Ext.grid.ColumnModel(columns);
//        alert(this.dataId);
        this.problemStore = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                    url: webContext+'/problemAction_listProblem.action?eventId='+this.dataId,
                    root:"data",
                    fields:fields,
                    totalProperty:"rowCount",
                    remoteSort:false          
              });                 
        this.problemStore.paramNames.sort="orderBy";
        this.problemStore.paramNames.dir="orderType";
        this.problemStore.paramNames.pageSize=5;
        this.cm.defaultSortable=true;
        var viewConfig = Ext.apply({
            forceFit : true
        }, this.gridViewConfig);
        this.pageBar = new Ext.PagingToolbar({
            pageSize :5, 
            store : this.problemStore,
            displayInfo : true,
            displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
            emptyMsg : "无显示数据"
        });
        this.grid = new Ext.grid.GridPanel({
            id : 'problemgrid',
            store : this.problemStore,
            cm : this.cm,
            sm : sm,
            frame : true,
            autoScroll : true, 
            collapsible : true,
            trackMouseOver : false,
            loadMask : true,
            height: 200,
            width : 752,    
            title:"问题",
            tbar : ['   ', {
                text : '新增问题 ',
                handler : this.create,
                scope : this,
                iconCls : 'add'
            },'-',{
                text : '删除问题',
                handler : this.remove,
                scope : this,
                iconCls : 'remove'
            },'&nbsp;|&nbsp;&nbsp;&nbsp;【<font color=red>双击问题行可以查看问题详细信息</font>】'
                ],
            bbar : this.pageBar
        });
        
        
         this.PanelProblem = new Ext.Panel({
            id : "anelProblem",
//            title :'事件处理页面',
            align : 'center',
            layout : 'table',
            border : false,
            defaults : {
                bodyStyle : 'padding:5px'
            },
            width : 762,
//            height : 450,
            frame : true,
            layoutConfig : {
                columns : 1
            },
            items : [this.fpd,this.grid]
        }); 
        this.items=[this.PanelProblem];
        this.grid.on("rowdblclick",this.deal,this);
        this.grid.on("headerdblclick", this.fitWidth, this);
        var params = {
            'mehtodCall' : 'query',
             'start' : 0    
        };
        this.problemStore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
        this.problemStore.removeAll();
        this.problemStore.load({
            params : params
        }); 
        EventDealPanel.superclass.initComponent.call(this);
    },
    fitWidth : function(grid, columnIndex, e) {
        var c = columnIndex;
        var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
        for (var i = 0, i = grid.store.getCount(); i < l; i++) {
            w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
        }
        grid.colModel.setColumnWidth(c, w);
    }

});
