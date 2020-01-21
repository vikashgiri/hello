package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 20/7/17.
 */

public class GetHiredCandidateResponse {


    /**
     * success : true
     * error : false
     * data : [{"aplied_id":"20","user_id":"42","job_id":"17","employer_id":"13","status":"3","createdOn":"2018-01-15 06:48:44","updatedOn":"2018-01-15 06:49:15","first_name":"pramod","last_name":"kumar","user_pic":"https://bonjob.co//public/uploads/user_pic/ac6efc1de77deedf040a6259277125a7_image.jpg","job_title":"Attaché(e) commercial(e)","current_status":"Apprenti","experience":"","job_image":"","job_description":""},{"aplied_id":"21","user_id":"105","job_id":"17","employer_id":"13","status":"3","createdOn":"2018-01-15 07:48:21","updatedOn":"2018-01-15 07:48:35","first_name":"Avi","last_name":"Jais","user_pic":"https://bonjob.co//public/uploads/user_pic/dd066a8b8ea818ba00b7d0f95d91ff1f_image.jpg","job_title":"Attaché(e) commercial(e)","current_status":"Étudiant","experience":"","job_image":"","job_description":""}]
     * msg : Données trouvées
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
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

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * aplied_id : 20
         * user_id : 42
         * job_id : 17
         * employer_id : 13
         * status : 3
         * createdOn : 2018-01-15 06:48:44
         * updatedOn : 2018-01-15 06:49:15
         * first_name : pramod
         * last_name : kumar
         * user_pic : https://bonjob.co//public/uploads/user_pic/ac6efc1de77deedf040a6259277125a7_image.jpg
         * job_title : Attaché(e) commercial(e)
         * current_status : Apprenti
         * experience :
         * job_image :
         * job_description :
         */

        private String aplied_id;
        private String user_id;
        private String job_id;
        private String employer_id;
        private String status;
        private String createdOn;
        private String updatedOn;
        private String first_name;
        private String last_name;
        private String user_pic;
        private String job_title;
        private String current_status;
        private String current_status_name;

        private String experience;
        private String job_image;
        private String job_description;

        public String getCurrent_status_name() {
            return current_status_name;
        }

        public void setCurrent_status_name(String current_status_name) {
            this.current_status_name = current_status_name;
        }

        public String getAplied_id() {
            return aplied_id;
        }

        public void setAplied_id(String aplied_id) {
            this.aplied_id = aplied_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getEmployer_id() {
            return employer_id;
        }

        public void setEmployer_id(String employer_id) {
            this.employer_id = employer_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
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

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getCurrent_status() {
            return current_status;
        }

        public void setCurrent_status(String current_status) {
            this.current_status = current_status;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
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
