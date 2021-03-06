package com.infoicon.bonjob.retrofit.response;

/**
 * Created by infoicona on 29/6/17.
 */

public class GetEditExperienceResponse {

    /**
     * success : true
     * error : false
     * data : null
     * msg : Experiences updated successfully
     */

    private boolean success;
    private boolean error;
    private Object data;
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
