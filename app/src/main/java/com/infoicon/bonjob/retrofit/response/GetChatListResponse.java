package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 14/9/17.
 */

public class GetChatListResponse {

    /**
     * success : true
     * error : false
     * data : [{"first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg","user_id":"92","job_title":"Femme de chambre","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/2c8778dd53beb518f8b3c7fecfa736da_image.jpg","job_description":"new entry 123456"},{"first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/f904a0607f2728d391a346be4fda681c_image.jpg","user_id":"93","job_title":"Femme de chambre","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/2c8778dd53beb518f8b3c7fecfa736da_image.jpg","job_description":"new entry 123456"},{"first_name":"Theo","last_name":"Regnault","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/1d3af62404b41f88555a8f7e293582af_image.jpg","user_id":"43","job_title":null,"job_image":null,"job_description":null},{"first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/24c44d5bcac38c1679662653136c2651_image.jpg","user_id":"94","job_title":"Femme de chambre","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/2c8778dd53beb518f8b3c7fecfa736da_image.jpg","job_description":"new entry 123456"},{"first_name":"Jerry","last_name":"The Mouse","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/88e0de2f8aa2cf00bf5b514621dd3255_image.jpg","user_id":"42","job_title":null,"job_image":null,"job_description":null}]
     * msg : Contact List
     */

    private boolean success;
    private boolean error;
    private String msg;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    private int totalUsers;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    private String active_user;
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
         * first_name : sushant
         * last_name : km
         * user_pic : http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg
         * user_id : 92
         * job_title : Femme de chambre
         * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/2c8778dd53beb518f8b3c7fecfa736da_image.jpg
         * job_description : new entry 123456
         */

        private String first_name;
        private String last_name;
        private String user_pic;
        private String user_id;
        private String job_title;
        private String job_image;
        private String job_description;

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

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getJob_image() {
            return job_image;
        }

        public void setJob_image(String job_image) {
            this.job_image = job_image;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
        }
    }
}
