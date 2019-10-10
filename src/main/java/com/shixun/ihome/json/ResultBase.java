package com.shixun.ihome.json;

public class ResultBase {
    private int code;
    private Object data;


    public ResultBase (){

    }

    public ResultBase (int code){
        this.code = code;
    }
    public ResultBase(int code, Object data){
        this.code = code;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
