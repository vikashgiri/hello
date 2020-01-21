package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 25/7/17.
 */

public class GetMyOfferSeekerResponse {

    /**
     * success : true
     * error : false
     * data : {"appliedList":[{"aplied_id":"14","user_id":"43","job_id":"27","employer_id":"81","status":"0","createdOn":"2017-08-18 21:22:46","updatedOn":"2017-08-18 21:22:46","first_name":"Pramod","last_name":"kumar","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/1be62b3d6fc26e662f96dbfe9b835f3d_image.jpg","job_title":"Hôte / Hôtesse d'accueil","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/bf4258a6991d2cfe6af598ad9e7e0b3a_image.jpg","job_description":"Test","num_of_hours":"25h/semaine"},{"aplied_id":"15","user_id":"43","job_id":"19","employer_id":"54","status":"0","createdOn":"2017-08-19 11:19:33","updatedOn":"2017-08-19 11:19:33","first_name":"Pramod","last_name":"kumar","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/1be62b3d6fc26e662f96dbfe9b835f3d_image.jpg","job_title":"Attaché(e) commercial(e)","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/8f5e6d13962d03ec0772039255f21975_image.jpg","job_description":"Fucuucicc fufuufififi cuicui hxfufuufi fuifuffiifiuxufiuf duhfjffifi duduffujfu fuufufifif iffiuffiif fufuffujffuiffi duduufififi fifujfjcjffi fucuffj","num_of_hours":"20h/semaine"}],"current_time":"2017-08-19 13:03:13"}
     * msg : Data Found
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

    public String getActive_user() {
        return active_user;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public static class DataBean {
        /**
         * appliedList : [{"aplied_id":"14","user_id":"43","job_id":"27","employer_id":"81","status":"0","createdOn":"2017-08-18 21:22:46","updatedOn":"2017-08-18 21:22:46","first_name":"Pramod","last_name":"kumar","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/1be62b3d6fc26e662f96dbfe9b835f3d_image.jpg","job_title":"Hôte / Hôtesse d'accueil","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/bf4258a6991d2cfe6af598ad9e7e0b3a_image.jpg","job_description":"Test","num_of_hours":"25h/semaine"},{"aplied_id":"15","user_id":"43","job_id":"19","employer_id":"54","status":"0","createdOn":"2017-08-19 11:19:33","updatedOn":"2017-08-19 11:19:33","first_name":"Pramod","last_name":"kumar","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/1be62b3d6fc26e662f96dbfe9b835f3d_image.jpg","job_title":"Attaché(e) commercial(e)","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/8f5e6d13962d03ec0772039255f21975_image.jpg","job_description":"Fucuucicc fufuufififi cuicui hxfufuufi fuifuffiifiuxufiuf duhfjffifi duduffujfu fuufufifif iffiuffiif fufuffujffuiffi duduufififi fifujfjcjffi fucuffj","num_of_hours":"20h/semaine"}]
         * current_time : 2017-08-19 13:03:13
         */

        private String current_time;
        private List<AppliedListBean> appliedList;

        public String getCurrent_time() {
            return current_time;
        }

        public void setCurrent_time(String current_time) {
            this.current_time = current_time;
        }

        public List<AppliedListBean> getAppliedList() {
            return appliedList;
        }

        public void setAppliedList(List<AppliedListBean> appliedList) {
            this.appliedList = appliedList;
        }

        public static class AppliedListBean {
            /**
             * aplied_id : 14
             * user_id : 43
             * job_id : 27
             * employer_id : 81
             * status : 0
             * createdOn : 2017-08-18 21:22:46
             * updatedOn : 2017-08-18 21:22:46
             * first_name : Pramod
             * last_name : kumar
             * user_pic : http://139.162.164.98/bonjob//public/uploads/user_pic/1be62b3d6fc26e662f96dbfe9b835f3d_image.jpg
             * job_title : Hôte / Hôtesse d'accueil
             * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/bf4258a6991d2cfe6af598ad9e7e0b3a_image.jpg
             * job_description : Test
             * num_of_hours : 25h/semaine
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
            private String job_image;
            private String job_description;
            private String num_of_hours;

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

            public String getNum_of_hours() {
                return num_of_hours;
            }

            public void setNum_of_hours(String num_of_hours) {
                this.num_of_hours = num_of_hours;
            }
        }
    }
}
