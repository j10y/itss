var modLoader = new function(){
    this.Module;
    this.MaskTemplate;
    this.Mask;
        
    var _pidCount;
    var _Modules;
    var _Startup;
    var _StartupEvent;
    var _ModuleLoaded;
    
    this.Initialize = function(){
        _pidCount = 0;
        _Modules = new Array(0);
        _Startup = false;
        
        this.MaskTemplate = 'Loading {0}.  Please Wait...';
        this.Mask = new Ext.LoadMask(Ext.getBody(), {
            msg: String.format(this.MaskTemplate, 'Site Loader')
        });
        
        this.Module = {
            ID: 0
            , FileName: null
            , Name: null
            , loaded: false
            , Chained: false
            , CallBack: null
        };
    }
    
    this.setLoadMask = function(name) {
        this.Mask = new Ext.LoadMask(Ext.getBody(), {
            msg: String.format(this.MaskTemplate, name)
        });
    }
    
    this.addModule = function(fileName, name) {
        var module = {
            FileName: fileName
            , Name: name
            , Chained: true
        };
        
        Ext.applyIf(module, this.Module);
        _Modules.push(module);
    }
    
    this.loadModules = function(fn) {
        for (var i=0; i<_Modules.length; i++) {
            if (_Modules[i].loaded === false && _Modules[i].Chained === true) {
                this.loadModule(_Modules[i]);
                return true;
            }
        }
        
        this.Mask.hide();
        
        if (typeof fn == 'function')
            fn();
    }
    
    this.loadModule = function(module) {
        Ext.applyIf(module, this.Module);        
        module.ID = ++_pidCount;
        
        if (!this.exists(module))
            _Modules.push(module);
        
        this.setLoadMask(module.Name);
        this.Mask.show();
        
        var head = getHeadEl();
        var script = document.createElement('script');
        script.setAttribute('src', module.FileName);
        script.setAttribute('type', 'text/javascript');
        script.setAttribute('id', 'pid-' + (module.ID));
        
        if (Ext.isIE)
            script.onreadystatechange = function(evt){ 
                        if (this.readyState == 'complete' || this.readyState == 'loaded') 
                            modLoader.modLoaded(evt, this);
            };
        else    //for the rest of the real world.
            Ext.EventManager.addListener(script, 'load', modLoader.modLoaded, this);
        
        head.appendChild(script);
        
        if (!module.Chained)
            this.Mask.hide();
    }
    
    this.killModuleId = function(id) {
        var head = getHeadEl();
        var script = document.getElementById('pid-'+id);
        
        if (Ext.isEmpty(script))
            throw "Module not found."
            
        head.removeChild(script);
        
        for (var i=0; i<_Modules.length; i++) {
            if (_Modules[i].ID == id) {
                _Modules.splice(i,1);
                return;
            }
        }
    }
    
    this.killModule = function(module) {
        if (Ext.isEmpty(module) || Ext.isEmpty(module.ID))
            throw "Malformed parameters";
            
        this.killModuleId(module.ID);
    }
    
    this.getByFileName = function(filename) {
        for (var i=0; i<_Modules.length; i++)
            if (_Modules[i].FileName === filename)
                return _Modules[i];
    }
    
    this.getByName = function(name) {
        for (var i=0; i<_Modules.length; i++)
            if (_Modules[i].Name === name)
                return _Modules[i];
    }
    
    this.getById = function(id) {
        for (var i=0; i<_Modules.length; i++)
            if (_Modules[i].ID === id)
                return _Modules[i];
    }
    
    this.exists = function(module) {
        for (var i=0; i<_Modules.length; i++)
            if (_Modules[i].FileName === module.FileName)
                return true;
        
        return false;
    }
    
    this.modLoaded = function(evt, el, options) {
        var id = parseInt(el.id.replace('pid-',''));
        var module = this.getById(id);
        
        module.loaded = true;
        
        if (typeof module.CallBack == 'function')
            module.CallBack();
        
        if (module.Chained)
            this.loadModules();
        
        if (_pidCount == _Modules.length && typeof _StartupEvent == 'function' && !_Startup) {
            _Startup = true;
            _StartupEvent();
        }
        
        if (typeof _ModuleLoaded == 'function')
            _ModuleLoaded();
    }
    
    this.OnStartupCompleted = function(fn) {
        _StartupEvent = fn;
    }
    
    this.OnModuleLoaded = function(fn) {
        _ModuleLoaded = fn;
    }
    
    function getHeadEl() {
        var head = document.getElementsByTagName('head')[0];
        if (Ext.isEmpty(head))
            throw "Document contains no head element";
        
        return head;
    }
    
    this.Initialize();
} 

