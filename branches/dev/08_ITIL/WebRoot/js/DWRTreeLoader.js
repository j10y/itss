/**
 * @constructor
 * @param {Object}
 *            config A config object
 * @cfg dwrCall the DWR function to call when loading the nodes
 */
Ext.tree.DWRTreeLoader = function(config) {
  Ext.tree.DWRTreeLoader.superclass.constructor.call(this, config);
};

Ext.extend(Ext.tree.DWRTreeLoader, Ext.tree.TreeLoader, {
	load : function(node, callback){
		if(this.clearOnLoad){
			while(node.firstChild){
				node.removeChild(node.firstChild);
			}
		}
		if(node.attributes.children){ // preloaded json children
			var cs = node.attributes.children;
			for(var i = 0, len = cs.length; i < len; i++){
	        	node.appendChild(this.createNode(cs[i]));
	      	}
	      	if(typeof callback == "function"){
	    		callback();
	     	}
        }else if(this.dwrCall){
     		this.requestData(node, callback);
     	}
	},
	/**
	 * Performs the actual load request
	 * 
	 * @param {Object}
	 *            node node for which child elements should be retrieved
	 * @param {Function}
	 *            callback function that should be called before executing the
	 *            DWR call
	 */
	requestData : function(node, callback) {
		if (this.fireEvent("beforeload", this, node, callback) !== false) {
			var callParams = new Array();
			var success = this.handleResponse.createDelegate(this, [node, callback], 1);
	    	var error = this.handleFailure.createDelegate(this, [node, callback], 1);
	    	var params = this.getParams(node);
	      	for (key in params) {
				callParams.push(params[key]);
			}
	      	callParams.push({callback:success, errorHandler:error});
	
	      	this.transId=true;
	      	this.dwrCall.apply(this, callParams);
    	} else {
      		// if the load is cancelled, make sure we notify
      		// the node that we are done
      		if (typeof callback == "function") {
        		callback();
      		}
 		}
	},
	/**
	 * Override this to add custom request parameters. Default adds the node id as
	 * first and only parameter
	 */
	getParams : function(node) {
		return {
			id : node.id//,
			//searchParam : node.attributes.searchParam
		};
	},
	/**
	 * Process the response that server sent back via DWR.
	 * 
	 * @param {Object}
	 *            response data that was sent back by the server that contains the
	 *            child nodes
	 * @param {Object}
	 *            node parent node to which child nodes will be appended
	 * @param {Function}
	 *            callback callback that will be performed after appending the nodes
	 */
	processResponse : function(response, node, callback){
		try {
		    if(response == null){
		    	callback(this,null);
		    	return;
		    }
    		for(var i = 0; i < response.length; i++){
           		var n = this.createNode(response[i]);
            	if(n){
               		node.appendChild(n);
            	}
        	}
            if(typeof callback == "function"){
           		callback(this, node);
        	}
      	}catch(e){
         	this.handleFailure(response);
      	}
    },
	/**
	 * Handles a sucessful response.
	 * 
	 * @param {Object}
	 *            response data that was sent back by the server that contains the
	 *            child nodes
	 * @param {Object}
	 *            node parent node to which child nodes will be appended
	 * @param {Function}
	 *            callback callback that will be performed after appending the nodes
	 */
    handleResponse : function(response, node, callback){
        this.transId = false;
        this.processResponse(response, node, callback);
        this.fireEvent("load", this, node, response);
    },
	/**
	 * Handles load error
	 * 
	 * @param {Object}
	 *            response data that was sent back by the server that contains the
	 *            child nodes
	 * @param {Object}
	 *            node parent node to which child nodes will be appended
	 * @param {Function}
	 *            callback callback that will be performed after appending the nodes
	 */
    handleFailure : function(response, node, callback){
        this.transId = false;
        this.fireEvent("loadexception", this, node, response);
        if(typeof callback == "function"){
            callback(this, node);
        }
    }
});  
