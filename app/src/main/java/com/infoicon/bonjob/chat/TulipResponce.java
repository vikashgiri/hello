package com.infoicon.bonjob.chat;

public class TulipResponce {

    /**
     * success : true
     * error : false
     * data : {"grant_access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4LTE1NjY1NTMyODgiLCJpc3MiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4Iiwic3ViIjoiQUNkZmNlZjE2MzM1MThhZGMyNjA5ZWMyM2RkODVmMjg1OSIsImV4cCI6MTU2NjU1Njg4OCwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiSnVsaWVuamhoaCBCaG9yYSIsInZpZGVvIjp7InJvb20iOiJ0ZXN0In19fQ.VBTij7YaK6dglEVMHV9gi_UM_iG4gTQE8IQYgXhXHTk","room_name":"test","user_name":"Henry Henry","user_pic":"http://172.104.8.51/bonjob/public/images/default.jpeg"}
     * msg : Twilio Token
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private DataBean data;
    private String msg;
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

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public static class DataBean {
        /**
         * grant_access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4LTE1NjY1NTMyODgiLCJpc3MiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4Iiwic3ViIjoiQUNkZmNlZjE2MzM1MThhZGMyNjA5ZWMyM2RkODVmMjg1OSIsImV4cCI6MTU2NjU1Njg4OCwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiSnVsaWVuamhoaCBCaG9yYSIsInZpZGVvIjp7InJvb20iOiJ0ZXN0In19fQ.VBTij7YaK6dglEVMHV9gi_UM_iG4gTQE8IQYgXhXHTk
         * room_name : test
         * user_name : Henry Henry
         * user_pic : http://172.104.8.51/bonjob/public/images/default.jpeg
         */

        private String grant_access_token;
        private String room_name;
        private String user_name;
        private String user_pic;

        public String getGrant_access_token() {
            return grant_access_token;
        }

        public void setGrant_access_token(String grant_access_token) {
            this.grant_access_token = grant_access_token;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }
    }
}
