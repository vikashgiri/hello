package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 6/7/17.
 */

public class GetRegistrationRecruiterResponse {

    /**
     * success : true
     * error : false
     * data : [{"first_name":"Rec","last_name":"Mok","email":"zuk26@gmail.com","user_id":"320","authKey":"1518437615148869","email_notification":"1","mobile_notification":"1","mobile_number":"","password":"e10adc3949ba59abbe56e057f20f883e","chat_password":"e10adc3949ba59abbe56e057f20f883e","address":"Noida","enterprise_name":"Zuko"}]
     * msg : Successfully registred
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
         * first_name : Rec
         * last_name : Mok
         * email : zuk26@gmail.com
         * user_id : 320
         * authKey : 1518437615148869
         * email_notification : 1
         * mobile_notification : 1
         * mobile_number :
         * password : e10adc3949ba59abbe56e057f20f883e
         * chat_password : e10adc3949ba59abbe56e057f20f883e
         * address : Noida
         * enterprise_name : Zuko
         */

        private String first_name;
        private String last_name;
        private String email;
        private String user_id;
        private String authKey;
        private String email_notification;
        private String mobile_notification;
        private String mobile_number;
        private String password;
        private String chat_password;
        private String address;
        private String enterprise_name;
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

        public String getAuthKey() {
            return authKey;
        }

        public void setAuthKey(String authKey) {
            this.authKey = authKey;
        }

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

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getChat_password() {
            return chat_password;
        }

        public void setChat_password(String chat_password) {
            this.chat_password = chat_password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
        }
    }
}
