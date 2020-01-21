package com.infoicon.bonjob.retrofit.response;

/**
 * Created by infoicona on 13/9/17.
 */

public class GetNotiEmailResponse {

    /**
     * success : true
     * error : false
     * data : {"email_notification":"1","mobile_notification":"2"}
     * msg : Data found.
     */

    private boolean success;
    private boolean error;
    private DataBean data;
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
        /**
         * email_notification : 1
         * mobile_notification : 2
         */

        private String email_notification;
        private String mobile_notification;

        public String getEmail_notification() {
            return email_notification;
        }

        public void setEmail_notification(String email_notification) {
            this.email_notification = email_notification;
        }

        public String getMobile_notification() {
            return mobile_notification;
        }

        public void setMobile_notification(String mobile_notification) {
            this.mobile_notification = mobile_notification;
        }
    }
}
