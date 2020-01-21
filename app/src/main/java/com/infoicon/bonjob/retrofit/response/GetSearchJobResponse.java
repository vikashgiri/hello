package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 26/7/17.
 */

public class GetSearchJobResponse {


    /**
     * success : truezzz
     * error : false
     * data : {"allJobs":[{"enterprise_name":"Cooking Co.","job_id":"12","job_title":"Area Director","job_description":"Chandra Testing Job ","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/9b7931fd0aa926c0ed19397ef1d7f2ff_image.jpg","job_location":"Noida","contract_type":"Fixed-term contract","education_level":"No diploma","experience":"Less than 1 year","lang":"English","num_of_hours":"<10h / week","salarie":"Minimum wage","start_date":"02/04/1982","contact_mode":"Bonjob App:","status":"1","added_by":"0","user_id":"54","createdOn":"2017-07-28 06:19:59","updatedOn":"2017-07-28 06:19:59"},{"enterprise_name":"infoicon","job_id":"13","job_title":"Area Director","job_description":"android developer required","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg","job_location":"Noida City Center, Noida, Uttar Pradesh, India","contract_type":"Interim","education_level":"High-School (professional training)","experience":"Less than 1 year","lang":"Portuguese","num_of_hours":"15h/week","salarie":"16 \u20ac/hour","start_date":"6-6-2017","contact_mode":"Phone:3232123123","status":"1","added_by":"0","user_id":"71","createdOn":"2017-07-31 06:20:27","updatedOn":"2017-08-02 11:41:12"}]}
     * msg : Data found.
     */

    private boolean success;
    private boolean error;
    private DataBean data;
    private String msg;
    private String active_user;
    private String user_lat;
    private String user_location;
    private String user_long;

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getUser_long() {
        return user_long;
    }

    public void setUser_long(String user_long) {
        this.user_long = user_long;
    }

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
        private List<AllJobsBean> allJobs;

        public List<AllJobsBean> getAllJobs() {
            return allJobs;
        }

        public void setAllJobs(List<AllJobsBean> allJobs) {
            this.allJobs = allJobs;
        }

        public static class AllJobsBean {
            /**
             * enterprise_name : Cooking Co.
             * job_id : 12
             * job_title : Area Director
             * job_description : Chandra Testing Job
             * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/9b7931fd0aa926c0ed19397ef1d7f2ff_image.jpg
             * job_location : Noida
             * contract_type : Fixed-term contract
             * education_level : No diploma
             * experience : Less than 1 year
             * lang : English
             * num_of_hours : <10h / week
             * salarie : Minimum wage
             * start_date : 02/04/1982
             * contact_mode : Bonjob App:
             * status : 1
             * added_by : 0
             * user_id : 54
             * createdOn : 2017-07-28 06:19:59
             * updatedOn : 2017-07-28 06:19:59
             * apply_on :
             */
            private String app_advertisement_id;
            private String title;
            private String advertisement_image;
            private String url;
            private String advertisement_file;
            private String advertisement_type;
            private String     file_type;

            public String getAdvertisement_file() {
                return advertisement_file;
            }

            public void setAdvertisement_file(String advertisement_file) {
                this.advertisement_file = advertisement_file;
            }

            public String getAdvertisement_type() {
                return advertisement_type;
            }

            public void setAdvertisement_type(String advertisement_type) {
                this.advertisement_type = advertisement_type;
            }

            public String getFile_type() {
                return file_type;
            }

            public void setFile_type(String file_type) {
                this.file_type = file_type;
            }

            public String getApp_advertisement_id() {
                return app_advertisement_id;
            }

            public void setApp_advertisement_id(String app_advertisement_id) {
                this.app_advertisement_id = app_advertisement_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAdvertisement_image() {
                return advertisement_image;
            }

            public void setAdvertisement_image(String advertisement_image) {
                this.advertisement_image = advertisement_image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            private String enterprise_name;
            private String job_id;
            private String job_title;
            private String job_description;
            private String job_image;
            private String job_location;
            private String contract_type;
            private String education_level;
            private String experience;
            private String lang;
            private String num_of_hours;
            private String salarie;
            private String start_date;
            private String contact_mode;
            private String status;
            private String added_by;
            private String user_id;
            private String createdOn;
            private String updatedOn;
            private String apply_on;
            private String origin;
            private String redirect_url;

            public String getRedirect_url()
            {
                return redirect_url;
            }

            public void setRedirect_url(String redirect_url) {
                this.redirect_url = redirect_url;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getApply_on() {
                return apply_on;
            }

            public void setApply_on(String apply_on) {
                this.apply_on = apply_on;
            }

            public String getEnterprise_name() {
                return enterprise_name;
            }

            public void setEnterprise_name(String enterprise_name) {
                this.enterprise_name = enterprise_name;
            }

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

            public String getJob_location() {
                return job_location;
            }

            public void setJob_location(String job_location) {
                this.job_location = job_location;
            }

            public String getContract_type() {
                return contract_type;
            }

            public void setContract_type(String contract_type) {
                this.contract_type = contract_type;
            }

            public String getEducation_level() {
                return education_level;
            }

            public void setEducation_level(String education_level) {
                this.education_level = education_level;
            }

            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
            }

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public String getNum_of_hours() {
                return num_of_hours;
            }

            public void setNum_of_hours(String num_of_hours) {
                this.num_of_hours = num_of_hours;
            }

            public String getSalarie() {
                return salarie;
            }

            public void setSalarie(String salarie) {
                this.salarie = salarie;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getContact_mode() {
                return contact_mode;
            }

            public void setContact_mode(String contact_mode) {
                this.contact_mode = contact_mode;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAdded_by() {
                return added_by;
            }

            public void setAdded_by(String added_by) {
                this.added_by = added_by;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
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
        }
    }
}
