/*
 * Ext JS Library 2.0.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

/**
 * @class Ext.TabPanel
 * <p>A basic tab container. Tab panels can be used exactly like a standard {@link Ext.Panel} for layout
 * purposes, but also have special support for containing child Panels that get automatically converted into tabs.</p>
 * <p>There is no actual tab class &mdash; each tab is simply an {@link Ext.Panel}.  However, when rendered in a
 * TabPanel, each child Panel can fire additional events that only exist for tabs and are not available to other
 * Panels. These are:</p>
 * <ul>
 * <li><b>activate</b>: Fires when this Panel becomes the active tab.
 * <div class="mdetail-params">
 *      <strong style="font-weight: normal;">Listeners will be called with the following arguments:</strong>
 *      <ul><li><code>tab</code> : Panel<div class="sub-desc">The tab that was activated</div></li></ul>
 *  </div></li>
 * <li><b>deactivate</b>: Fires when this Panel that was the active tab becomes deactivated.
 * <div class="mdetail-params">
 *      <strong style="font-weight: normal;">Listeners will be called with the following arguments:</strong>
 *      <ul><li><code>tab</code> : Panel<div class="sub-desc">The tab that was deactivated</div></li></ul>
 *  </div></li>
 * </ul>
 * <p>There are several methods available for creating TabPanels. The output of the following examples should be
 * exactly the same. The tabs can be created and rendered completely in code, as in this example:</p>
 * <pre><code>
var tabs = new Ext.TabPanel({
    renderTo: Ext.getBody(),
    activeTab: 0,
    items: [{
        title: 'Tab 1',
        html: 'A simple tab'
    },{
        title: 'Tab 2',
        html: 'Another one'
    }]
});
</pre></code>
  * <p>TabPanels can also be rendered from markup in a couple of ways.  See the {@link #autoTabs} example for
  * rendering entirely from markup that is already structured correctly as a TabPanel (a container div with
  * one or more nested tab divs with class 'x-tab'). You can also render from markup that is not strictly
  * structured by simply specifying by id which elements should be the container and the tabs. Using this method,
  * tab content can be pulled from different elements within the page by id regardless of page structure.  Note
  * that the tab divs in this example contain the class 'x-hide-display' so that they can be rendered deferred
  * without displaying outside the tabs. You could alternately set {@link #deferredRender} to false to render all
  * content tabs on page load. For example:
  * <pre><code>
var tabs = new Ext.TabPanel({
    renderTo: 'my-tabs',
    activeTab: 0,
    items:[
        {contentEl:'tab1', title:'Tab 1'},
        {contentEl:'tab2', title:'Tab 2'}
    ]
});

// Note that the tabs do not have to be nested within the container (although they can be)
&lt;div id="my-tabs">&lt;/div>
&lt;div id="tab1" class="x-hide-display">A simple tab&lt;/div>
&lt;div id="tab2" class="x-hide-display">Another one&lt;/div>
</pre></code>
 * @extends Ext.Panel
 * @constructor
 * @param {Object} config The configuration options
 */
Ext.TabPanel = Ext.extend(Ext.Panel,  {
    /**
     * @cfg {Boolean} layoutOnTabChange Set to true to do a layout of tab items as tabs are changed.
     */
    /**
     * @cfg {Boolean} monitorResize True to automatically monitor window resize events and rerender the layout on
     * browser resize (defaults to true).
     */
    monitorResize : true,
    /**
     * @cfg {Boolean} deferredRender Internally, the TabPanel uses a {@link Ext.layout.CardLayout} to manage its tabs.
     * This property will be passed on to the layout as its {@link Ext.layout.CardLayout#deferredRender} config value,
     * determining whether or not each tab is rendered only when first accessed (defaults to true).
     */
    deferredRender : true,
    /**
     * @cfg {Number} tabWidth The initial width in pixels of each new tab (defaults to 120).
     */
    tabWidth: 120,
    /**
     * @cfg {Number} minTabWidth The minimum width in pixels for each tab when {@link #resizeTabs} = true (defaults to 30).
     */
    minTabWidth: 30,
    /**
     * @cfg {Boolean} resizeTabs True to automatically resize each tab so that the tabs will completely fill the
     * tab strip (defaults to false).  Setting this to true may cause specific widths that might be set per tab to
     * be overridden in order to fit them all into view (although {@link #minTabWidth} will always be honored).
     */
    resizeTabs:false,
    /**
     * @cfg {Boolean} enableTabScroll True to enable scrolling to tabs that may be invisible due to overflowing the
     * overall TabPanel width. Only available with tabs on top. (defaults to false).
     */
    enableTabScroll: false,
    /**
     * @cfg {Number} scrollIncrement The number of pixels to scroll each time a tab scroll button is pressed (defaults
     * to 100, or if {@link #resizeTabs} = true, the calculated tab width).  Only applies when {@link #enableTabScroll} = true.
     */
    scrollIncrement : 0,
    /**
     * @cfg {Number} scrollRepeatInterval Number of milliseconds between each scroll while a tab scroll button is
     * continuously pressed (defaults to 400).
     */
    scrollRepeatInterval : 400,
    /**
     * @cfg {Float} scrollDuration The number of milliseconds that each scroll animation should last (defaults to .35).
     * Only applies when {@link #animScroll} = true.
     */
    scrollDuration : .35,
    /**
     * @cfg {Boolean} animScroll True to animate tab scrolling so that hidden tabs slide smoothly into view (defaults
     * to true).  Only applies when {@link #enableTabScroll} = true.
     */
    animScroll : true,
    /**
     * @cfg {String} tabPosition The position where the tab strip should be rendered (defaults to 'top').  The only
     * other supported value is 'bottom'.  Note that tab scrolling is only supported for position 'top'.
     */
    tabPosition: 'top',
    /**
     * @cfg {String} baseCls The base CSS class applied to the panel (defaults to 'x-tab-panel').
     */
    baseCls: 'x-tab-panel',
    /**
     * @cfg {Boolean} autoTabs
     * <p>True to query the DOM for any divs with a class of 'x-tab' to be automatically converted
     * to tabs and added to this panel (defaults to false).  Note that the query will be executed within the scope of
     * the container element only (so that multiple tab panels from markup can be supported via this method).</p>
     * <p>This method is only possible when the markup is structured correctly as a container with nested
     * divs containing the class 'x-tab'. To create TabPanels without these limitations, or to pull tab content from
     * other elements on the page, see the example at the top of the class for generating tabs from markup.</p>
     * <p>There are a couple of things to note when using this method:<ul>
     * <li>When using the autoTabs config (as opposed to passing individual tab configs in the TabPanel's
     * {@link #items} collection), you must use {@link #applyTo} to correctly use the specified id as the tab container.
     * The autoTabs method <em>replaces</em> existing content with the TabPanel components.</li>
     * <li>Make sure that you set {@link #deferredRender} to false so that the content elements for each tab will be
     * rendered into the TabPanel immediately upon page load, otherwise they will not be transformed until each tab
     * is activated and will be visible outside the TabPanel.</li>
     * </ul>Example usage:</p>
     * <pre><code>
var tabs = new Ext.TabPanel({
    applyTo: 'my-tabs',
    activeTab: 0,
    deferredRender: false,
    autoTabs: true
});

// This markup will be converted to a TabPanel from the code above
&lt;div id="my-tabs">
    &lt;div class="x-tab" title="Tab 1">A simple tab&lt;/div>
    &lt;div class="x-tab" title="Tab 2">Another one&lt;/div>
&lt;/div>
</code></pre>
     */
    autoTabs : false,
    /**
     * @cfg {String} autoTabSelector The CSS selector used to search for tabs in existing markup when {@link #autoTabs}
     * = true (defaults to 'div.x-tab').  This can be any valid selector supported by {@link Ext.DomQuery#select}.
     * Note that the query will be executed within the scope of this tab panel only (so that multiple tab panels from
     * markup can be supported on a page).
     */
    autoTabSelector:'div.x-tab',
    /**
     * @cfg {String/Number} activeTab A string id or the numeric index of the tab that should be initially
     * activated on render (defaults to none).
     */
    activeTab : null,
    /**
     * @cfg {Number} tabMargin The number of pixels of space to calculate into the sizing and scrolling of tabs. If you
     * change the margin in CSS, you will need to update this value so calculations are correct with either resizeTabs
     * or scrolling tabs. (defaults to 2)
     */
    tabMargin : 2,
    /**
     * @cfg {Boolean} plain True to render the tab strip without a background container image (defaults to false).
     */
    plain: false,
    /**
     * @cfg {Number} wheelIncrement For scrolling tabs, the number of pixels to increment on mouse wheel scrolling (defaults to 20).
     */
    wheelIncrement : 20,

    /*
     * This is a protected property used when concatenating tab ids to the TabPanel id for internal uniqueness.
     * It does not generally need to be changed, but can be if external code also uses an id scheme that can
     * potentially clash with this one.
     */
    idDelimiter : '__',

    // private
    itemCls : 'x-tab-item',

    // private config overrides
    elements: 'body',
    headerAsText: false,
    frame: false,
    hideBorders:true,

    // private
    initComponent : function(){
        this.frame = false;
        Ext.TabPanel.superclass.initComponent.call(this);
        this.addEvents(
            /**
             * @event beforetabchange
             * Fires before the active tab changes. Handlers can return false to cancel the tab change.
             * @param {TabPanel} this
             * @param {Panel} newTab The tab being activated
             * @param {Panel} currentTab The current active tab
             */
            'beforetabchange',
            /**
             * @event tabchange
             * Fires after the active tab has changed.
             * @param {TabPanel} this
             * @param {Panel} tab The new active tab
             */
            'tabchange',
            /**
             * @event contextmenu
             * Fires when the original browser contextmenu event originated from a tab element.
             * @param {TabPanel} this
             * @param {Panel} tab The target tab
             * @param {EventObject} e
             */
            'contextmenu'
        );
        this.setLayout(new Ext.layout.CardLayout({
            deferredRender: this.deferredRender
        }));
        if(this.tabPosition == 'top'){
            this.elements += ',header';
            this.stripTarget = 'header';
        }else {
            this.elements += ',footer';
            this.stripTarget = 'footer';
        }
        if(!this.stack){
            this.stack = Ext.TabPanel.AccessStack();
        }
        this.initItems();
    },

    // private
    render : function(){
        Ext.TabPanel.superclass.render.apply(this, arguments);
        if(this.activeTab !== undefined){
            var item = this.activeTab;
            delete this.activeTab;
            this.setActiveTab(item);
        }
    },

    // private
    onRender : function(ct, position){
        Ext.TabPanel.superclass.onRender.call(this, ct, position);

        if(this.plain){
            var pos = this.tabPosition == 'top' ? 'header' : 'footer';
            this[pos].addClass('x-tab-panel-'+pos+'-plain');
        }

        var st = this[this.stripTarget];

        this.stripWrap = st.createChild({cls:'x-tab-strip-wrap', cn:{
            tag:'ul', cls:'x-tab-strip x-tab-strip-'+this.tabPosition}});
        this.stripSpacer = st.createChild({cls:'x-tab-strip-spacer'});
        this.strip = new Ext.Element(this.stripWrap.dom.firstChild);

        this.edge = this.strip.createChild({tag:'li', cls:'x-tab-edge'});
        this.strip.createChild({cls:'x-clear'});

        this.body.addClass('x-tab-panel-body-'+this.tabPosition);

        if(!this.itemTpl){
            var tt = new Ext.Template(
                 '<li class="{cls}" id="{id}"><a class="x-tab-strip-close" onclick="return false;"></a>',
                 '<a class="x-tab-right" href="#" onclick="return false;"><em class="x-tab-left">',
                 '<span class="x-tab-strip-inner"><span class="x-tab-strip-text {iconCls}">{text}</span></span>',
                 '</em></a></li>'
            );
            tt.disableFormats = true;
            tt.compile();
            Ext.TabPanel.prototype.itemTpl = tt;
        }

        this.items.each(this.initTab, this);
    },

    // private
    afterRender : function(){
        Ext.TabPanel.superclass.afterRender.call(this);
        if(this.autoTabs){
            this.readTabs(false);
        }
    },

    // private
    initEvents : function(){
        Ext.TabPanel.superclass.initEvents.call(this);
        this.on('add', this.onAdd, this);
        this.on('remove', this.onRemove, this);

        this.strip.on('mousedown', this.onStripMouseDown, this);
        this.strip.on('click', this.onStripClick, this);
        this.strip.on('contextmenu', this.onStripContextMenu, this);
        if(this.enableTabScroll){
            this.strip.on('mousewheel', this.onWheel, this);
        }
    },

    // private
    findTargets : function(e){
        var item = null;
        var itemEl = e.getTarget('li', this.strip);
        if(itemEl){
            item = this.getComponent(itemEl.id.split(this.idDelimiter)[1]);
            if(item.disabled){
                return {
                    close : null,
                    item : null,
                    el : null
                };
            }
        }
        return {
            close : e.getTarget('.x-tab-strip-close', this.strip),
            item : item,
            el : itemEl
        };
    },

    // private
    onStripMouseDown : function(e){
        e.preventDefault();
        if(e.button != 0){
            return;
        }
        var t = this.findTargets(e);
        if(t.close){
            this.remove(t.item);
            return;
        }
        if(t.item && t.item != this.activeTab){
            this.setActiveTab(t.item);
        }
    },

    // private
    onStripClick : function(e){
        var t = this.findTargets(e);
        if(!t.close && t.item && t.item != this.activeTab){
            this.setActiveTab(t.item);
        }
    },

    // private
    onStripContextMenu : function(e){
        e.preventDefault();
        var t = this.findTargets(e);
        if(t.item){
            this.fireEvent('contextmenu', this, t.item, e);
        }
    },

    /**
     * True to scan the markup in this tab panel for autoTabs using the autoTabSelector
     * @param {Boolean} removeExisting True to remove existing tabs
     */
    readTabs : function(removeExisting){
        if(removeExisting === true){
            this.items.each(function(item){
                this.remove(item);
            }, this);
        }
        var tabs = this.el.query(this.autoTabSelector);
        for(var i = 0, len = tabs.length; i < len; i++){
            var tab = tabs[i];
            var title = tab.getAttribute('title');
            tab.removeAttribute('title');
            this.add({
                title: title,
                el: tab
            });
        }
    },

    // private
    initTab : function(item, index){
        var before = this.strip.dom.childNodes[index];
        var cls = item.closable ? 'x-tab-strip-closable' : '';
        if(item.disabled){
            cls += ' x-item-disabled';
        }
        if(item.iconCls){
            cls += ' x-tab-with-icon';
        }
        var p = {
            id: this.id + this.idDelimiter + item.getItemId(),
            text: item.title,
            cls: cls,
            iconCls: item.iconCls || ''
        };
        var el = before ?
                 this.itemTpl.insertBefore(before, p) :
                 this.itemTpl.append(this.strip, p);

        Ext.fly(el).addClassOnOver('x-tab-strip-over');

        if(item.tabTip){
            Ext.fly(el).child('span.x-tab-strip-text', true).qtip = item.tabTip;
        }
        item.on('disable', this.onItemDisabled, this);
        item.on('enable', this.onItemEnabled, this);
        item.on('titlechange', this.onItemTitleChanged, this);
        item.on('beforeshow', this.onBeforeShowItem, this);
    },

    // private
    onAdd : function(tp, item, index){
        this.initTab(item, index);
        if(this.items.getCount() == 1){
            this.syncSize();
        }
        this.delegateUpdates();
    },

    // private
    onBeforeAdd : function(item){
        var existing = item.events ? (this.items.containsKey(item.getItemId()) ? item : null) : this.items.get(item);
        if(existing){
            this.setActiveTab(item);
            return false;
        }
        Ext.TabPanel.superclass.onBeforeAdd.apply(this, arguments);
        var es = item.elements;
        item.elements = es ? es.replace(',header', '') : es;
        item.border = (item.border === true);
    },

    // private
    onRemove : function(tp, item){
        Ext.removeNode(this.getTabEl(item));
        this.stack.remove(item);
        if(item == this.activeTab){
            var next = this.stack.next();
            if(next){
                this.setActiveTab(next);
            }else{
                this.setActiveTab(0);
            }
        }
        this.delegateUpdates();
    },

    // private
    onBeforeShowItem : function(item){
        if(item != this.activeTab){
            this.setActiveTab(item);
            return false;
        }
    },

    // private
    onItemDisabled : function(item){
        var el = this.getTabEl(item);
        if(el){
            Ext.fly(el).addClass('x-item-disabled');
        }
        this.stack.remove(item);
    },

    // private
    onItemEnabled : function(item){
        var el = this.getTabEl(item);
        if(el){
            Ext.fly(el).removeClass('x-item-disabled');
        }
    },

    // private
    onItemTitleChanged : function(item){
        var el = this.getTabEl(item);
        if(el){
            Ext.fly(el).child('span.x-tab-strip-text', true).innerHTML = item.title;
        }
    },

    /**
     * Gets the DOM element for tab strip item which activates the
     * child panel with the specified ID. Access this to change the visual treatment of the
     * item, for example by changing the CSS class name.
     * @param {Panel} tab The tab
     * @return {HTMLElement} The DOM node
     */
    getTabEl : function(item){
        var itemId = (typeof item === 'number')?this.items.items[item].getItemId() : item.getItemId();
        return document.getElementById(this.id+this.idDelimiter+itemId);
    },

    // private
    onResize : function(){
        Ext.TabPanel.superclass.onResize.apply(this, arguments);
        this.delegateUpdates();
    },

    /**
     * Suspends any internal calculations or scrolling while doing a bulk operation. See {@link #endUpdate}
     */
    beginUpdate : function(){
        this.suspendUpdates = true;
    },

    /**
     * Resumes calculations and scrolling at the end of a bulk operation. See {@link #beginUpdate}
     */
    endUpdate : function(){
        this.suspendUpdates = false;
        this.delegateUpdates();
    },

    /**
     * Hides the tab strip item for the passed tab
     * @param {Number/String/Panel} item The tab index, id or item
     */
    hideTabStripItem : function(item){
        item = this.getComponent(item);
        var el = this.getTabEl(item);
        if(el){
            el.style.display = 'none';
            this.delegateUpdates();
        }
    },

    /**
     * Unhides the tab strip item for the passed tab
     * @param {Number/String/Panel} item The tab index, id or item
     */
    unhideTabStripItem : function(item){
        item = this.getComponent(item);
        var el = this.getTabEl(item);
        if(el){
            el.style.display = '';
            this.delegateUpdates();
        }
    },

    // private
    delegateUpdates : function(){
        if(this.suspendUpdates){
            return;
        }
        if(this.resizeTabs && this.rendered){
            this.autoSizeTabs();
        }
        if(this.enableTabScroll && this.rendered){
            this.autoScrollTabs();
        }
    },

    // private
    autoSizeTabs : function(){
        var count = this.items.length;
        var ce = this.tabPosition != 'bottom' ? 'header' : 'footer';
        var ow = this[ce].dom.offsetWidth;
        var aw = this[ce].dom.clientWidth;

        if(!this.resizeTabs || count < 1 || !aw){ // !aw for display:none
            return;
        }

        var each = Math.max(Math.min(Math.floor((aw-4) / count) - this.tabMargin, this.tabWidth), this.minTabWidth); // -4 for float errors in IE
        this.lastTabWidth = each;
        var lis = this.stripWrap.dom.getElementsByTagName('li');
        for(var i = 0, len = lis.length-1; i < len; i++) { // -1 for the "edge" li
            var li = lis[i];
            var inner = li.childNodes[1].firstChild.firstChild;
            var tw = li.offsetWidth;
            var iw = inner.offsetWidth;
            inner.style.width = (each - (tw-iw)) + 'px';
        }
    },

    // private
    adjustBodyWidth : function(w){
        if(this.header){
            this.header.setWidth(w);
        }
        if(this.footer){
            this.footer.setWidth(w);
        }
        return w;
    },

    /**
     * Sets the specified tab as the active tab. This method fires the {@link #beforetabchange} event which
     * can return false to cancel the tab change.
     * @param {String/Panel} tab The id or tab Panel to activate
     */
    setActiveTab : function(item){
        item = this.getComponent(item);
        if(!item || this.fireEvent('beforetabchange', this, item, this.activeTab) === false){
            return;
        }
        if(!this.rendered){
            this.activeTab = item;
            return;
        }
        if(this.activeTab != item){
            if(this.activeTab){
                var oldEl = this.getTabEl(this.activeTab);
                if(oldEl){
                    Ext.fly(oldEl).removeClass('x-tab-strip-active');
                }
                this.activeTab.fireEvent('deactivate', this.activeTab);
            }
            var el = this.getTabEl(item);
            Ext.fly(el).addClass('x-tab-strip-active');
            this.activeTab = item;
            this.stack.add(item);

            this.layout.setActiveItem(item);
            if(this.layoutOnTabChange && item.doLayout){
                item.doLayout();
            }
            if(this.scrolling){
                this.scrollToTab(item, this.animScroll);
            }

            item.fireEvent('activate', item);
            this.fireEvent('tabchange', this, item);
        }
    },

    /**
     * Gets the currently active tab.
     * @return {Panel} The active tab
     */
    getActiveTab : function(){
        return this.activeTab || null;
    },

    /**
     * Gets the specified tab by id.
     * @param {String} id The tab id
     * @return {Panel} The tab
     */
    getItem : function(item){
        return this.getComponent(item);
    },

    // private
    autoScrollTabs : function(){
        var count = this.items.length;
        var ow = this.header.dom.offsetWidth;
        var tw = this.header.dom.clientWidth;

        var wrap = this.stripWrap;
        var cw = wrap.dom.offsetWidth;
        var pos = this.getScrollPos();
        var l = this.edge.getOffsetsTo(this.stripWrap)[0] + pos;

        if(!this.enableTabScroll || count < 1 || cw < 20){ // 20 to prevent display:none issues
            return;
        }
        if(l <= tw){
            wrap.dom.scrollLeft = 0;
            wrap.setWidth(tw);
            if(this.scrolling){
                this.scrolling = false;
                this.header.removeClass('x-tab-scrolling');
                this.scrollLeft.hide();
                this.scrollRight.hide();
            }
        }else{
            if(!this.scrolling){
                this.header.addClass('x-tab-scrolling');
            }
            tw -= wrap.getMargins('lr');
            wrap.setWidth(tw > 20 ? tw : 20);
            if(!this.scrolling){
                if(!this.scrollLeft){
                    this.createScrollers();
                }else{
                    this.scrollLeft.show();
                    this.scrollRight.show();
                }
            }
            this.scrolling = true;
            if(pos > (l-tw)){ // ensure it stays within bounds
                wrap.dom.scrollLeft = l-tw;
            }else{ // otherwise, make sure the active tab is still visible
                this.scrollToTab(this.activeTab, false);
            }
            this.updateScrollButtons();
        }
    },

    // private
    createScrollers : function(){
        var h = this.stripWrap.dom.offsetHeight;

        // left
        var sl = this.header.insertFirst({
            cls:'x-tab-scroller-left'
        });
        sl.setHeight(h);
        sl.addClassOnOver('x-tab-scroller-left-over');
        this.leftRepeater = new Ext.util.ClickRepeater(sl, {
            interval : this.scrollRepeatInterval,
            handler: this.onScrollLeft,
            scope: this
        });
        this.scrollLeft = sl;

        // right
        var sr = this.header.insertFirst({
            cls:'x-tab-scroller-right'
        });
        sr.setHeight(h);
        sr.addClassOnOver('x-tab-scroller-right-over');
        this.rightRepeater = new Ext.util.ClickRepeater(sr, {
            interval : this.scrollRepeatInterval,
            handler: this.onScrollRight,
            scope: this
        });
        this.scrollRight = sr;
    },

    // private
    getScrollWidth : function(){
        return this.edge.getOffsetsTo(this.stripWrap)[0] + this.getScrollPos();
    },

    // private
    getScrollPos : function(){
        return parseInt(this.stripWrap.dom.scrollLeft, 10) || 0;
    },

    // private
    getScrollArea : function(){
        return parseInt(this.stripWrap.dom.clientWidth, 10) || 0;
    },

    // private
    getScrollAnim : function(){
        return {duration:this.scrollDuration, callback: this.updateScrollButtons, scope: this};
    },

    // private
    getScrollIncrement : function(){
        return this.scrollIncrement || (this.resizeTabs ? this.lastTabWidth+2 : 100);
    },

    /**
     * Scrolls to a particular tab if tab scrolling is enabled
     * @param {Panel} item The item to scroll to
     * @param {Boolean} animate True to enable animations
     */

    scrollToTab : function(item, animate){
        if(!item){ return; }
        var el = this.getTabEl(item);
        var pos = this.getScrollPos(), area = this.getScrollArea();
        var left = Ext.fly(el).getOffsetsTo(this.stripWrap)[0] + pos;
        var right = left + el.offsetWidth;
        if(left < pos){
            this.scrollTo(left, animate);
        }else if(right > (pos + area)){
            this.scrollTo(right - area, animate);
        }
    },

    // private
    scrollTo : function(pos, animate){
        this.stripWrap.scrollTo('left', pos, animate ? this.getScrollAnim() : false);
        if(!animate){
            this.updateScrollButtons();
        }
    },

    onWheel : function(e){
        var d = e.getWheelDelta()*this.wheelIncrement*-1;
        e.stopEvent();

        var pos = this.getScrollPos();
        var newpos = pos + d;
        var sw = this.getScrollWidth()-this.getScrollArea();

        var s = Math.max(0, Math.min(sw, newpos));
        if(s != pos){
            this.scrollTo(s, false);
        }
    },

    // private
    onScrollRight : function(){
        var sw = this.getScrollWidth()-this.getScrollArea();
        var pos = this.getScrollPos();
        var s = Math.min(sw, pos + this.getScrollIncrement());
        if(s != pos){
            this.scrollTo(s, this.animScroll);
        }
    },

    // private
    onScrollLeft : function(){
        var pos = this.getScrollPos();
        var s = Math.max(0, pos - this.getScrollIncrement());
        if(s != pos){
            this.scrollTo(s, this.animScroll);
        }
    },

    // private
    updateScrollButtons : function(){
        var pos = this.getScrollPos();
        this.scrollLeft[pos == 0 ? 'addClass' : 'removeClass']('x-tab-scroller-left-disabled');
        this.scrollRight[pos >= (this.getScrollWidth()-this.getScrollArea()) ? 'addClass' : 'removeClass']('x-tab-scroller-right-disabled');
    }

    /**
     * @cfg {Boolean} collapsible
     * @hide
     */
    /**
     * @cfg {String} header
     * @hide
     */
    /**
     * @cfg {Boolean} headerAsText
     * @hide
     */
    /**
     * @property header
     * @hide
     */
    /**
     * @property title
     * @hide
     */
});
Ext.reg('tabpanel', Ext.TabPanel);

/**
 * Sets the specified tab as the active tab. This method fires the {@link #beforetabchange} event which
 * can return false to cancel the tab change.
 * @param {String/Panel} tab The id or tab Panel to activate
 * @method activate
 */
Ext.TabPanel.prototype.activate = Ext.TabPanel.prototype.setActiveTab;

// private utility class used by TabPanel
Ext.TabPanel.AccessStack = function(){
    var items = [];
    return {
        add : function(item){
            items.push(item);
            if(items.length > 10){
                items.shift();
            }
        },

        remove : function(item){
            var s = [];
            for(var i = 0, len = items.length; i < len; i++) {
                if(items[i] != item){
                    s.push(items[i]);
                }
            }
            items = s;
        },

        next : function(){
            return items.pop();
        }
    };
};

