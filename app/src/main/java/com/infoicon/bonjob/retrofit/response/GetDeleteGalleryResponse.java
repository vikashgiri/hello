package com.infoicon.bonjob.retrofit.response;

/**
 * Created by infoicona on 4/7/17.
 */

public class GetDeleteGalleryResponse {

    /**
     * success : true
     * error : false
     * data : null
     * msg : Image deleted successfully
     */

    private boolean success;
    private boolean error;
    private Object data;
    private String msg;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    private String active_user;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
