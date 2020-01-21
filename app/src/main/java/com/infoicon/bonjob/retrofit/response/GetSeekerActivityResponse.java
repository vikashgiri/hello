package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 25/7/17.
 */

public class GetSeekerActivityResponse {
    /**
     * success : true
     * error : false
     * data : [{"job_id":"122","job_title":"Back Office Assistant","job_description":"","job_image":"","activity_title":"Votre candidature a expirÃ©.","activity_id":"8","createdOn":"2017-10-27 07:00:03"},{"job_id":"45","job_title":"wordpress engineer","job_description":"wp","job_image":"","activity_title":"Votre candidature a expirÃ©.","activity_id":"6","createdOn":"2017-10-27 07:00:02"},{"job_id":"122","job_title":"Back Office Assistant","job_description":"","job_image":"","activity_title":"You applied to a job offer.","activity_id":"4","createdOn":"2017-10-27 06:40:59"},{"job_id":"121","job_title":"Assistant General Manager","job_description":"assistance general manager job","job_image":"","activity_title":"You have been hired.","activity_id":"3","createdOn":"2017-10-27 05:29:41"},{"job_id":"121","job_title":"Assistant General Manager","job_description":"assistance general manager job","job_image":"","activity_title":"Candidate is selected.","activity_id":"2","createdOn":"2017-10-27 05:28:29"},{"job_id":"121","job_title":"Assistant General Manager","job_description":"assistance general manager job","job_image":"","activity_title":"You applied to a job offer.","activity_id":"1","createdOn":"2017-10-27 05:01:23"}]
     * current_time : 2017-10-27 07:27:13
     * msg : Data found
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String current_time;
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

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
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
         * job_id : 122
         * job_title : Back Office Assistant
         * job_description :
         * job_image :
         * activity_title : Votre candidature a expirÃ©.
         * activity_id : 8
         * selectedByEmployer: 3
         * createdOn : 2017-10-27 07:00:03
         */

        private String job_id;
        private String job_title;
        private String job_description;
        private String job_image;
        private String activity_title;
        private String activity_id;
        private String selectedByEmployer;
        private String createdOn;

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
        }

        public String getJob_image() {
            return job_image;
        }

        public void setJob_image(String job_image) {
            this.job_image = job_image;
        }

        public String getActivity_title() {
            return activity_title;
        }

        public void setActivity_title(String activity_title) {
            this.activity_title = activity_title;
        }
        public String getSelectedByEmployer() {
            return selectedByEmployer;
        }

        public void setSelectedByEmployer(String selectedByEmployer) {
            this.selectedByEmployer = selectedByEmployer;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }
    }







   /* *//**
     * success : true
     * error : false
     * data : [{"aplied_id":"8","user_id":"43","job_id":"13","employer_id":"71","status":"1","createdOn":"2017-08-04 11:38:49","updatedOn":"2017-08-16 07:51:53","job_title":"Femme de chambre","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg"},{"aplied_id":"9","user_id":"43","job_id":"12","employer_id":"54","status":"0","createdOn":"2017-08-04 12:49:52","updatedOn":"2017-08-04 12:49:52","job_title":"Femme de chambre","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/9b7931fd0aa926c0ed19397ef1d7f2ff_image.jpg"},{"aplied_id":"10","user_id":"43","job_id":"14","employer_id":"71","status":"0","createdOn":"2017-08-05 07:01:22","updatedOn":"2017-08-05 07:01:22","job_title":"Femme de chambre","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/29621051adc62369196660a16db32c30_image.png"},{"aplied_id":"11","user_id":"43","job_id":"15","employer_id":"71","status":"0","createdOn":"2017-08-05 08:36:08","updatedOn":"2017-08-05 08:36:08","job_title":"Femme de chambre","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/13e29764d3432fdb7aca4dc3517f2196_image.jpg"},{"aplied_id":"12","user_id":"43","job_id":"18","employer_id":"71","status":"0","createdOn":"2017-08-15 12:00:21","updatedOn":"2017-08-15 12:00:21","job_title":"Barman","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/d50da8ef41f8bafb293557d15716c896_image.jpg"},{"aplied_id":"13","user_id":"43","job_id":"23","employer_id":"54","status":"0","createdOn":"2017-08-18 10:52:20","updatedOn":"2017-08-18 10:52:20","job_title":"Barman","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/931fd727e37e88f98debee3ab296217d_image.jpg"},{"aplied_id":"14","user_id":"43","job_id":"27","employer_id":"81","status":"0","createdOn":"2017-08-18 21:22:46","updatedOn":"2017-08-18 21:22:46","job_title":"Hôte / Hôtesse d'accueil","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/bf4258a6991d2cfe6af598ad9e7e0b3a_image.jpg"}]
     * current_time : 2017-08-19 08:43:26
     * msg : Data Found
     *//*

    private boolean success;
    private boolean error;
    private String current_time;
    private String msg;
    private String active_user;
    private List<DataBean> data;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }



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

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
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
        *//**
         * aplied_id : 8
         * user_id : 43
         * job_id : 13
         * employer_id : 71
         * status : 1
         * createdOn : 2017-08-04 11:38:49
         * updatedOn : 2017-08-16 07:51:53
         * job_title : Femme de chambre
         * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg
         *//*

        private String aplied_id;
        private String user_id;
        private String job_id;
        private String employer_id;
        private String status;
        private String createdOn;
        private String updatedOn;
        private String job_title;
        private String job_image;

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
    }*/
}
