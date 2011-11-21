<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*,net.shopin.*,org.jasig.cas.client.authentication.AttributePrincipal" pageEncoding="utf-8"%>
<html>
<head>
<%
	AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal(); 
	String username = principal.getName(); 
	request.setAttribute("CURRENTUSER", username);
%>
<%@include file="/_lib/loading/loading.inc" %>

<title>统一用户管理系统</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/_lib/ext/ux/TabCloseMenu.js"></script>
<style type="text/css">
    body, td { font-size:11px; } 
    .x-tree-elbow {display: none;}
    .x-tree-elbow-end {display: none;}
    .x-tab-panel-noborder .x-tab-panel-header-noborder {border-width: 0 0 0px 0;}
    .ext-webkit .x-small-editor .x-form-text{padding-top:1px;font-size:100%;}

    .x-btn button{
        font : bold 12px arial,tahoma,verdana,helvetica;
    }

    .x-tab-strip span.x-tab-strip-text {
        color: black;
        font: normal normal normal 11px/normal tahoma, arial, helvetica;
        font-size : 11px;
    }

    .x-tab-strip-active span.x-tab-strip-text {
        color: black;
        //font-weight: bold;
        font-size : 11px;
    }
</style>

<%--<script type="text/javascript">var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));</script>--%>

<script type="text/javascript">

    Ext.override(Ext.Button, {
        onFocus:function(e) { if(e && e.target) { e.target.blur(); }},
        // private
        initButtonEl : function(btn, btnEl){
            this.el = btn;
            this.setIcon(this.icon);
            this.setText(this.text);
            this.setIconClass(this.iconCls);
            if(Ext.isDefined(this.tabIndex)){
                btnEl.dom.tabIndex = this.tabIndex;
            }
            if(this.tooltip){
                this.setTooltip(this.tooltip, true);
            }

            if(this.handleMouseEvents){
                this.mon(btn, {
                    scope: this,
                    mouseover: this.onMouseOver,
                    mousedown: this.onMouseDown
                });

                // new functionality for monitoring on the document level
                //this.mon(btn, 'mouseout', this.onMouseOut, this);
            }

            if(this.menu){
                this.mon(this.menu, {
                    scope: this,
                    show: this.onMenuShow,
                    hide: this.onMenuHide
                });
            }

            if(this.repeat){
                var repeater = new Ext.util.ClickRepeater(btn, Ext.isObject(this.repeat) ? this.repeat : {});
                this.mon(repeater, 'click', this.onClick, this);
            }
            this.mon(btn, this.clickEvent, this.onClick, this);
        }
    });

    var webContext = '${pageContext.request.contextPath}';
    //Ext.ux.IconLoader.reg('forward.gif', 'class.gif', 'details.gif');

    var mainPanel = new Ext.TabPanel({
        region: 'center', // a center region is ALWAYS required for border layout
        id : 'mainPanel',
        border: false,
        autoDestroy: false,
        enableTabScroll:false,
        activeTab: 0,
        margins:'0 5 0 0',
        deferredRender: true,
        layoutOnTabChange: true,
        plugins: new Ext.ux.TabCloseMenu(),
        items: [
            {
                title: '',
                header:false,
                xtype: 'panel',
                layout:'table',
                //autoLoad:{url: '${pageContext.request.contextPath}/help/deskTop.jsp',scripts:true},
                border: true,
                frame: true,
                closable: false,
                autoScroll: true,
                //html: '<IFRAME SRC="${pageContext.request.contextPath}/help/deskTop.jsp" width="100%" height="100%" frameborder="0"></IFRAME>'
                html: 'http://www.baidu.com" width="100%" height="100%" frameborder="0"></IFRAME>'
            }
        ]
    });

    var MAX_TABS = 6;

    var iframeBoxes = [];
    for(var i=0; i<MAX_TABS; i++) {
        var boxId = 'iframe_box_'+(i<10?"0":"")+i;
        iframeBoxes[i] = new Ext.BoxComponent({
            id: boxId,
            iconCls: 'icon-class',
            closable:true,
            autoScroll:false,
            getFrame : function() {
                //用这个方法同时支持html和onRender两种方式
                if(!this.el) return;
                return this.el.dom.tagName=="IFRAME" ? this.el.dom : this.el.dom.firstChild;
            },
            showFrame : function(tabTitle, menuId, targetUrl) {
                this.used = true;
                this.menuId = menuId;
                this.title = tabTitle;
                this.targetUrl = targetUrl;
                var f = this.getFrame();
                if(!f) {
                    this.html = '<IFRAME ID="'+this.id+'_iframe" SRC="'+targetUrl+'" width="100%" height="100%" frameborder="0"></IFRAME>'
                } else {
                    this.el.dom.style.display='';
                    f.src = targetUrl;
                }
            },
            hideFrame : function() {
                this.used = false;
                delete this.menuId;
                this.el.dom.style.display='none'; //设置了autoDestroy=false后, 只移除了页签, iframe仍然在tab的位置那里, 会挡住后面的.
                var f = this.getFrame();
                f.src = "javascript:false";
            },
            listeners:{
                "beforedestroy": function() {
                    //already hide in remove
                    //if(this.rendered) this.el.dom.src="javascript:false";
                }
/*
                , "resize": function() {
                    if(this.getFrame().contentWindow._topPanel) {
                        this.getFrame().contentWindow._topPanel.doLayout();
                    }
                }
            }
            ,onRender : function(ct, position) {
                this.el = ct.createChild({tag: 'iframe', name: this.id+"_frame", id: this.id+"_frame", width:"100%", height:"100%", frameBorder: 0, src: this.targetUrl});
*/
            }
        })
    }

    function getTabByMenuId(id) {
        for(var i=0; i<MAX_TABS; i++) {
            if(iframeBoxes[i].menuId == id) {
                return iframeBoxes[i];
            }
        }
    }

    function addTab(id, tabTitle, targetUrl) {
        //alert('id = ' + id + ' tabTitle = ' + tabTitle + ' targetUrl = ' + targetUrl);
        var found;
        for(var i=0; i<MAX_TABS; i++) {
            if(!iframeBoxes[i].used) {
                found = iframeBoxes[i];
                break;
            }
        }

        if(!found) {
            //alert("您最多只能打开"+MAX_TABS+"个窗口, 最早访问的'"+iframeBoxes[0].title+"'自动关闭了.");
            mainPanel.remove(iframeBoxes[0]);
            found = iframeBoxes[0];
        }

        //title要在add之前设置.
        found.title = tabTitle;
        mainPanel.add(found); //先add还是先show有区别
        found.showFrame(tabTitle, id, targetUrl);
        return found;
    }

    function updateTab(id, title, url) {
        if(url.substring(0,1)!='/' && url.indexOf("://")<0) url='<%=request.getContextPath()%>/'+url;

        var tab = getTabByMenuId(id);
        //tab存在则设置为活动并刷新页面，tab不存在则新增
        if (tab) {
            tab.getFrame().src=url; //contentWindow.location.reload(); //用reload可能内存好些
        } else {
            tab = addTab(id, title, url);
            //mainPanel.setActiveTab(tab);
        }
        //防止切换回原来的tab时还闪一下上次的内容.
        new Ext.util.DelayedTask(function() { mainPanel.setActiveTab(tab) }).delay(100);
    }

    mainPanel.on("beforeremove", function(ct, comp) {
        comp.hideFrame();

        //comp.el.dom.width = 0; comp.el.dom.height=0;
        //comp.el.dom.contentWindow.document.body.removeNode(true)
        //window.open("close.html", comp.el.dom.id);
        //window.open("javascript:false", comp.el.dom.id);
        //comp.el.dom.contentWindow.document.body.innerHTML=""
        //window.open("close.html", comp.el.dom.id);
        //comp.el.dom = null;
        //delete comp.el.dom;
        //return false;
    });

    mainPanel.on("afterrender", function() {typeof _loading == "object" && _loading.stop()});
    sortFunc = function(a, b) { if(!a||!b) return 0; if(!a.used && !b.used) return a.id<b.id? -1:1; if(!a.used) return -1; if(!b.used) return 1; return (a.lastActiveTime || 0) - (b.lastActiveTime || 0)  }
    mainPanel.on("tabchange", function(ct, tab) {
        if(!tab) return;
        tab.lastActiveTime = new Date(); 
        iframeBoxes.sort(sortFunc);
    });
    mainPanel.on("beforedestroy", function(ct, tab) {
    });

    Ext.EventManager.on(window, "beforeunload", function() {
        for(var i=0; i<MAX_TABS; i++) {
            iframeBoxes[i].destroy(true);
            iframeBoxes[i] = null;
        }

        iframeBoxes = null;
        delete iframeBoxes;

    }, iframeBoxes, {single:true});

</script>

<script type="text/javascript">

    Ext.onReady(function() {

        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        //Ext.ux.IconLoader.reg("user_delete.gif")

        var items = new Array();
        var hasMenus = false;

        //menuDwr.getFirstGradeMenuList(function(datas) {
            /*for (var i = 0; i < datas.length; i++) {
                if (i > 0) items.push("-");
                var data = datas[i];
                var item = new Ext.Button({
                    id : data.menuId,
                    enableToggle: true,
                    iconCls : "icon-folder-go",
                    text : data.menuName,

                    toggleHandler: function(button, state) {
                        Ext.each(button.ownerCt.items, function(btn, idx) {
                            var btn = button.ownerCt.items.get(idx);
                            if (btn.enableToggle && btn != button) {
                                btn.toggle(false, true);
                            }
                        });
                    },
                    handler : function() {
                        Ext.getCmp('west-panel').expand(true);
                        Ext.getCmp('west-panel').setTitle(this.text);
                        var menuId = this.id;
                        //alert(menuId);
                        // 导航菜单面板组
                        var panels = [];

                        menuDwr.getChildMenuListByParentId(menuId, function(datas) {
                            if (datas.length > 0) {
                                Ext.getCmp('west-panel').removeAll();
                                for (var i = 0; i < datas.length; i++) {
                                    var menuItemId = datas[i].menuId;
                                    var menuText = datas[i].menuName;
                                    var loader = new Ext.tree.TreeLoader({
                                        url : '<%=request.getContextPath()%>/menu/loadMenus?id=' + menuItemId
                                    });
                                    loader.on('beforeload', function(treeloader, node) {
                                        treeloader.baseParams = {
                                            id : node.id,
                                            method : 'tree'
                                        };
                                    });

                                    var item = new Ext.tree.TreePanel({
                                        //title : '<b>' + datas[i].menuName + '</b>',
                                        title : '<div style="font: bold 12px arial;">' + datas[i].menuName + '</div>',
                                        id : datas[i].menuId,
                                        //region : 'center',
                                        animate : true,
                                        containerScroll : true,
                                        //collapsible : true,
                                        padding : '2px 0px 0px 0px',
                                        overflow : 'auto',
                                        loader : loader,
                                        lines : false,
                                        border:false,
                                        rootVisible : false,
                                        //containerScroll : true,
                                        //autoScroll : true,
                                        //margins :{top:0, right:0, bottom:0, left:0},
                                        root : new Ext.tree.AsyncTreeNode({
                                            id : menuItemId,
                                            text : menuText,
                                            expanded : true
                                        }),
                                        autoScroll : true
                                        // loader: new Ext.tree.DWRTreeLoader({dataUrl:menuDwr.getChildMenuListByParentId(menuId)})
                                    });

                                    Ext.getCmp('west-panel').add(item);
                                }
                            } else {
                                Ext.getCmp('west-panel').removeAll();
                                Ext.getCmp('west-panel').add(new Ext.Panel({}));
                            }
                            Ext.getCmp('west-panel').doLayout();
                        });
                    }
                });
                items.push(item);
            }*/
            if(items.length>0){
                hasMenus = true;
            }
            items.push("-");items.push("->");

            items.push({
                text: '${CURRENTUSER}',
                iconCls:"icon-user",
                xtype:'button',
                handleMouseEvents :false
            });
            items.push({
                text: new Date(),
                iconCls:"icon-time",
                xtype:'button',
                handleMouseEvents :false
            });
            
            items.push({
                text: '注销',
                iconCls:"icon-user-delete",
                handler: function() {
                    Ext.MessageBox.confirm("确认", "您确认要注销？", function(bool){
                        if(bool=='yes') {
                            Ext.Ajax.request({
                                url: "<%=request.getContextPath()%>/j_spring_security_logout",
                                callback: function() {
                                    document.location = webContext + "/" + cfg_system_security_loginform;
                                }
                            });
                        }
                    });
                }
            })

        //});

        var viewport = new Ext.Viewport({
            layout: 'border',
            applyTo: Ext.getBody(),
            items: [
                {
                    region: 'west',
                    id: 'west-panel',
                    header : false, //you can set to true 
                    collapseMode: 'mini',
                    split: true,
                    width: 170,
                    minSize: 135,
                    maxSize: 400,
                    collapsible: true,
                    collapsed : true,
                    retion : 'west',
                    layout : 'accordion',
                    margins: '0 0 0 5'
                },
                {
                    ref: 'banner',
                    xtype: 'panel',
                    header: false,
                    region : 'north',
                    border:true,
                    split: true,
                    collapsible:true,
                    collapseMode: 'mini',
                    height : 'auto',
                    html : '<div id="up" style="background:url(../_images/header_back_telecom.jpg) no-repeat left top #37ADD5;height:57px;border:1px solid #fff;"></div>',
                    bbar: {
                        enableOverflow:true,
                        items:items
                    },
                    listeners: {
                        collapse: function() {
                            this.refOwner.footer.collapse();
                        },
                        expand: function() {
                            this.refOwner.footer.expand();
                        }
                    }
                },
                {
                    ref: 'footer',
                    region: 'south',
                    xtype: 'panel',
                    baseCls: 'x-plain',
                    border: false,
                    collapseMode:'mini',
                    html:'<p style="margin: 5px;font-size:12px" align="center" >Copyright &copy; 1998 - ' + new Date().getFullYear() + ' 上品商业</p>'
                },
                mainPanel
            ]
        });


        /*if(!hasMenus){

            Ext.MessageBox.show({
                title: "提示",
                msg: "没有菜单",
                width:300,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.INFO
            });
        }*/

        /*if (items[0]) items[0].handler();*/

        /*menuDwr.getUserStatus(function(result){
            var msg;
            if(!result || '0:1' == result || '0:0' == result){
            } else {
                msg = '';
                if(result.indexOf('1:')!=-1) {
                    var num = result.substr(2,result.indexOf(',2') == -1?result.length:result.indexOf(',2')-2) ;
                    msg = i18n('c.msg.account.expired',num);
                    msg += '<p>';
                }  
                if(result.indexOf('2:')!=-1) {
                    var num = result.substr(result.indexOf(',2')==-1?2:result.indexOf(',2')+3,result.length) ;
                    msg += i18n('c.msg.pwd.expired',num);
                }
            }
            if(msg){
                var window = new Ext.Window({
                    // contentEl : Ext.getBody(),
                    width : 220,
                    height : 150,
                    html : msg,
                    title : i18n('c.msg.promopt'),
                    closeAction:"hide"
                });
                window.show();
                window.getEl().alignTo(Ext.getBody(), 'br-br');
                setTimeout(function(){window.hide();},1000000);
            }
        });*/
    });
</script>
</head>
<body></body>
</html>