package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 2/8/17.
 */

public class GetCompanyDetailsResponse {


    /**
     * success : true
     * error : false
     * data : {"company_details":{"first_name":"pramod","last_name":"kumar","city":"Noida Link Road, Mayur Vihar Phase 1, New Delhi, Delhi, Indi","enterprise_name":"infoicon","about":"nice app","website":"w.google.com","salary":"50\u0013100 employees","company_category":"Hotel / Restaurant","email":"pramod01@infoicon.co.in","patch_video":"http://139.162.164.98/bonjob//public/uploads/enterprise_pic/566ab40ab553b0fba4cf499834dff46d_video.mp4","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/a925e52fedf4d305fcf65719cda26aad_image.png","enterprise_pic":"http://139.162.164.98/bonjob//public/uploads/enterprise_pic/ff7c8ce5503e24a6ac88189c10555e14_image.jpg","user_id":"71","company_lat":"28.605962","company_long":"77.2876351"},"allJobs":[{"job_id":"13","job_title":"Area Director","job_description":"android developer d'équidés","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg","job_location":"Nidamanuru, Vijayawada, Andhra Pradesh, India","contract_type":"Fixed-term contract","education_level":"High-School (professional training)","experience":"Less than 1 year","lang":"Spanish","num_of_hours":"15h/week","salarie":"16 \u20ac/hour","start_date":"11-7-2017","contact_mode":"E-mail:abc@gmai.com","status":"1","added_by":"0","user_id":"71","createdOn":"2017-07-31 06:20:27","updatedOn":"2017-08-02 12:45:54"}]}
     * msg : Data found.
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
         * company_details : {"first_name":"pramod","last_name":"kumar","city":"Noida Link Road, Mayur Vihar Phase 1, New Delhi, Delhi, Indi","enterprise_name":"infoicon","about":"nice app","website":"w.google.com","salary":"50\u0013100 employees","company_category":"Hotel / Restaurant","email":"pramod01@infoicon.co.in","patch_video":"http://139.162.164.98/bonjob//public/uploads/enterprise_pic/566ab40ab553b0fba4cf499834dff46d_video.mp4","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/a925e52fedf4d305fcf65719cda26aad_image.png","enterprise_pic":"http://139.162.164.98/bonjob//public/uploads/enterprise_pic/ff7c8ce5503e24a6ac88189c10555e14_image.jpg","user_id":"71","company_lat":"28.605962","company_long":"77.2876351"}
         * allJobs : [{"job_id":"13","job_title":"Area Director","job_description":"android developer d'équidés","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg","job_location":"Nidamanuru, Vijayawada, Andhra Pradesh, India","contract_type":"Fixed-term contract","education_level":"High-School (professional training)","experience":"Less than 1 year","lang":"Spanish","num_of_hours":"15h/week","salarie":"16 \u20ac/hour","start_date":"11-7-2017","contact_mode":"E-mail:abc@gmai.com","status":"1","added_by":"0","user_id":"71","createdOn":"2017-07-31 06:20:27","updatedOn":"2017-08-02 12:45:54"}]
         */

        private CompanyDetailsBean company_details;
        private List<AllJobsBean> allJobs;

        public CompanyDetailsBean getCompany_details() {
            return company_details;
        }

        public void setCompany_details(CompanyDetailsBean company_details) {
            this.company_details = company_details;
        }

        public List<AllJobsBean> getAllJobs() {
            return allJobs;
        }

        public void setAllJobs(List<AllJobsBean> allJobs) {
            this.allJobs = allJobs;
        }

        public static class CompanyDetailsBean {
            /**
             * first_name : pramod
             * last_name : kumar
             * city : Noida Link Road, Mayur Vihar Phase 1, New Delhi, Delhi, Indi
             * enterprise_name : infoicon
             * about : nice app
             * website : w.google.com
             * salary : 50100 employees
             * company_category : Hotel / Restaurant
             * email : pramod01@infoicon.co.in
             * patch_video : http://139.162.164.98/bonjob//public/uploads/enterprise_pic/566ab40ab553b0fba4cf499834dff46d_video.mp4
             * user_pic : http://139.162.164.98/bonjob//public/uploads/user_pic/a925e52fedf4d305fcf65719cda26aad_image.png
             * enterprise_pic : http://139.162.164.98/bonjob//public/uploads/enterprise_pic/ff7c8ce5503e24a6ac88189c10555e14_image.jpg
             * user_id : 71
             * company_lat : 28.605962
             * company_long : 77.2876351
             */

            private String first_name;
            private String last_name;
            private String city;
            private String enterprise_name;
            private String about;
            private String website;
            private String salary;
            private String company_category;
            private String email;
            private String patch_video;
            private String patch_video_thumbnail;
            private String user_pic;
            private String enterprise_pic;
            private String user_id;
            private String company_lat;
            private String company_long;

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

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getEnterprise_name() {
                return enterprise_name;
            }

            public void setEnterprise_name(String enterprise_name) {
                this.enterprise_name = enterprise_name;
            }

            public String getPatch_video_thumbnail() {
                return patch_video_thumbnail;
            }

            public void setPatch_video_thumbnail(String patch_video_thumbnail) {
                this.patch_video_thumbnail = patch_video_thumbnail;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }

            public String getSalary() {
                return salary;
            }

            public void setSalary(String salary) {
                this.salary = salary;
            }

            public String getCompany_category() {
                return company_category;
            }

            public void setCompany_category(String company_category) {
                this.company_category = company_category;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPatch_video() {
                return patch_video;
            }

            public void setPatch_video(String patch_video) {
                this.patch_video = patch_video;
            }

            public String getUser_pic() {
                return user_pic;
            }

            public void setUser_pic(String user_pic) {
                this.user_pic = user_pic;
            }

            public String getEnterprise_pic() {
                return enterprise_pic;
            }

            public void setEnterprise_pic(String enterprise_pic) {
                this.enterprise_pic = enterprise_pic;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getCompany_lat() {
                return company_lat;
            }

            public void setCompany_lat(String company_lat) {
                this.company_lat = company_lat;
            }

            public String getCompany_long() {
                return company_long;
            }

            public void setCompany_long(String company_long) {
                this.company_long = company_long;
            }
        }

        public static class AllJobsBean {
            /**
             * job_id : 13
             * job_title : Area Director
             * job_description : android developer d'équidés
             * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg
             * job_location : Nidamanuru, Vijayawada, Andhra Pradesh, India
             * contract_type : Fixed-term contract
             * education_level : High-School (professional training)
             * experience : Less than 1 year
             * lang : Spanish
             * num_of_hours : 15h/week
             * salarie : 16 €/hour
             * start_date : 11-7-2017
             * contact_mode : E-mail:abc@gmai.com
             * status : 1
             * added_by : 0
             * user_id : 71
             * createdOn : 2017-07-31 06:20:27
             * updatedOn : 2017-08-02 12:45:54
             */

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
