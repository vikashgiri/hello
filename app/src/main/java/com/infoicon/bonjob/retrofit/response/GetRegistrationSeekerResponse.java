package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 28/6/17.
 */

public class GetRegistrationSeekerResponse {

    /**
     * success : true
     * error : false
     * data : [{"first_name":"dytrytry","last_name":"ytrytry","username":"ytrytry@ccc.rerew","email":"ytrytry@ccc.rerew","user_id":"63"}]
     * msg : Successfully registered.
     */

    private boolean success;
    private boolean error;
    private String msg;
    private List<DataBean> data;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * first_name : dytrytry
         * last_name : ytrytry
         * username : ytrytry@ccc.rerew
         * email : ytrytry@ccc.rerew
         * user_id : 63
         */

        private String first_name;
        private String last_name;
        private String username;
        private String email;
        private String user_id;
        private String user_pic;

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
