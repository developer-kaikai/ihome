package com.shixun.ihome.json;

public class ResultBase<T> {
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_FAIL = 400;
    private static final String MSG_SUCCESS = "success";
    private static final String MSG_FAIL = "fail";

    private int code;
    private T data;
    private String msg;


    public static ResultBase success(Object data){
        return new ResultBase(CODE_SUCCESS, MSG_SUCCESS, data);
    }

    public static ResultBase success(){
        return new ResultBase(CODE_SUCCESS, MSG_SUCCESS);
    }

    public static ResultBase fail(){
        return new ResultBase(CODE_FAIL, MSG_FAIL);
    }

    public static ResultBase fail(String msg){
        return new ResultBase(CODE_FAIL, msg);
    }

    public ResultBase(int code, String msg,  T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultBase(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
