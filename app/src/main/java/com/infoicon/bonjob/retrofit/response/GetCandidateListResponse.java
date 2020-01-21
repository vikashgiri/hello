package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 20/7/17.
 */

public class GetCandidateListResponse {

    /**
     * success : true
     * error : false
     * data : {"appliedList":[{"user_id":"92","aplied_id":"38","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:19:03","updatedOn":"2017-09-13 12:19:03","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg","job_title":"Femme de chambre","current_status":"","experience":["2 - Boulanger","3 - Barman"]}],"archivedList":[{"user_id":"92","aplied_id":"38","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:19:03","updatedOn":"2017-09-13 12:19:03","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg","job_title":"Femme de chambre","current_status":"","experience":["2 - Boulanger","3 - Barman"]},{"user_id":"93","aplied_id":"39","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:20:49","updatedOn":"2017-09-13 12:20:49","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/f904a0607f2728d391a346be4fda681c_image.jpg","job_title":"Femme de chambre","current_status":"","experience":[]},{"user_id":"43","aplied_id":"40","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:27:19","updatedOn":"2017-09-13 12:27:19","first_name":"THEO","last_name":"Regnault","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/63f1856d58dba46022b2ae56e1694e9b_image.jpg","job_title":"Femme de chambre","current_status":"Étudiant","experience":["2 - Boulanger","3 - Barman"]},{"user_id":"94","aplied_id":"42","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:30:21","updatedOn":"2017-09-13 12:30:21","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/24c44d5bcac38c1679662653136c2651_image.jpg","job_title":"Femme de chambre","current_status":"","experience":[]}],"current_date":"2017-09-18 04:52:54"}
     * msg : Data Found
     */

    private boolean success;
    private boolean error;
    private DataBean data;
    private String msg;
    private String active_user;

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
         * appliedList : [{"user_id":"92","aplied_id":"38","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:19:03","updatedOn":"2017-09-13 12:19:03","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg","job_title":"Femme de chambre","current_status":"","experience":["2 - Boulanger","3 - Barman"]}]
         * archivedList : [{"user_id":"92","aplied_id":"38","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:19:03","updatedOn":"2017-09-13 12:19:03","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg","job_title":"Femme de chambre","current_status":"","experience":["2 - Boulanger","3 - Barman"]},{"user_id":"93","aplied_id":"39","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:20:49","updatedOn":"2017-09-13 12:20:49","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/f904a0607f2728d391a346be4fda681c_image.jpg","job_title":"Femme de chambre","current_status":"","experience":[]},{"user_id":"43","aplied_id":"40","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:27:19","updatedOn":"2017-09-13 12:27:19","first_name":"THEO","last_name":"Regnault","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/63f1856d58dba46022b2ae56e1694e9b_image.jpg","job_title":"Femme de chambre","current_status":"Étudiant","experience":["2 - Boulanger","3 - Barman"]},{"user_id":"94","aplied_id":"42","job_id":"38","employer_id":"84","status":"0","createdOn":"2017-09-13 12:30:21","updatedOn":"2017-09-13 12:30:21","first_name":"sushant","last_name":"km","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/24c44d5bcac38c1679662653136c2651_image.jpg","job_title":"Femme de chambre","current_status":"","experience":[]}]
         * current_date : 2017-09-18 04:52:54
         */

        private String current_date;
        private List<AppliedListBean> appliedList;
        private List<ArchivedListBean> archivedList;

        public String getCurrent_date() {
            return current_date;
        }

        public void setCurrent_date(String current_date) {
            this.current_date = current_date;
        }

        public List<AppliedListBean> getAppliedList() {
            return appliedList;
        }

        public void setAppliedList(List<AppliedListBean> appliedList) {
            this.appliedList = appliedList;
        }

        public List<ArchivedListBean> getArchivedList() {
            return archivedList;
        }

        public void setArchivedList(List<ArchivedListBean> archivedList) {
            this.archivedList = archivedList;
        }

        public static class AppliedListBean {
            /**
             * user_id : 92
             * aplied_id : 38
             * job_id : 38
             * employer_id : 84
             * status : 0
             * createdOn : 2017-09-13 12:19:03
             * updatedOn : 2017-09-13 12:19:03
             * first_name : sushant
             * last_name : km
             * user_pic : http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg
             * job_title : Femme de chambre
             * current_status :
             * experience : ["2 - Boulanger","3 - Barman"]
             */

            private String user_id;
            private String aplied_id;
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

            public String getCurrent_status_name() {
                return current_status_name;
            }

            public void setCurrent_status_name(String current_status_name) {
                this.current_status_name = current_status_name;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getAplied_id() {
                return aplied_id;
            }

            public void setAplied_id(String aplied_id) {
                this.aplied_id = aplied_id;
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
        }

        public static class ArchivedListBean {
            /**
             * user_id : 92
             * aplied_id : 38
             * job_id : 38
             * employer_id : 84
             * status : 0
             * createdOn : 2017-09-13 12:19:03
             * updatedOn : 2017-09-13 12:19:03
             * first_name : sushant
             * last_name : km
             * user_pic : http://139.162.164.98/bonjob//public/uploads/user_pic/faa24b183d92f9123b5f060e7ba6a6cb_image.jpg
             * job_title : Femme de chambre
             * current_status :
             * experience : ["2 - Boulanger","3 - Barman"]
             */

            private String user_id;
            private String aplied_id;
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

            public String getCurrent_status_name() {
                return current_status_name;
            }

            public void setCurrent_status_name(String current_status_name) {
                this.current_status_name = current_status_name;
            }

            private String experience;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getAplied_id() {
                return aplied_id;
            }

            public void setAplied_id(String aplied_id) {
                this.aplied_id = aplied_id;
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
        }
    }
}
