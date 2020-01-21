package com.infoicon.bonjob.retrofit.response;

/**
 * Created by infoicona on 16/9/17.
 */

public class GetLogoutResponse {

    /**
     * success : true
     * error : false
     * data : {}
     * msg : Add successfully.
     */

    private boolean success;
    private boolean error;
    private DataBean data;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
    }
}
