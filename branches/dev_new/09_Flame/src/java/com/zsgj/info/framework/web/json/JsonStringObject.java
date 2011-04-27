package com.zsgj.info.framework.web.json;

public class JsonStringObject {

	private String jsonString = null;
    
    public JsonStringObject(String jsonString){
        this.jsonString = jsonString;
    }

    public String toString() {
        return jsonString;
    }

    public String toJSONString() {
        return jsonString;
    }

}
