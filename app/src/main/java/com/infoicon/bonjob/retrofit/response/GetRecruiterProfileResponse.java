package com.infoicon.bonjob.retrofit.response;

/**
 * Created by infoicona on 20/7/17.
 */

public class GetRecruiterProfileResponse {


    /**
     * success : true
     * error : false
     * data : {"first_name":"pramod","last_name":"kumar","city":"Noida City Center, Noida, Uttar Pradesh, India","enterprise_name":"infoicon","about":"","website":"www.google.com","salary":"50\u0013100 employees","company_category":"Cafe, Bar, Brasserie","email":"pramod01@infoicon.co.in","patch_video":"http://139.162.164.98/bonjob//public/uploads/enterprise_pic/4768834b86c2144fd9cf72a160efe361_video.mp4","user_pic":"http://139.162.164.98/bonjob//public/uploads/enterprise_pic/4768834b86c2144fd9cf72a160efe361_image.jpg","enterprise_pic":"http://139.162.164.98/bonjob//public/uploads/enterprise_pic/4768834b86c2144fd9cf72a160efe361_image.jpg","user_id":"71"}
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
         * first_name : pramod
         * last_name : kumar
         * city : Noida City Center, Noida, Uttar Pradesh, India
         * enterprise_name : infoicon
         * about :
         * website : www.google.com
         * salary : 50100 employees
         * company_category : Cafe, Bar, Brasserie
         * email : pramod01@infoicon.co.in
         * patch_video : http://139.162.164.98/bonjob//public/uploads/enterprise_pic/4768834b86c2144fd9cf72a160efe361_video.mp4
         * user_pic : http://139.162.164.98/bonjob//public/uploads/enterprise_pic/4768834b86c2144fd9cf72a160efe361_image.jpg
         * enterprise_pic : http://139.162.164.98/bonjob//public/uploads/enterprise_pic/4768834b86c2144fd9cf72a160efe361_image.jpg
         * user_id : 71
         * "company_lat": "28.605962",
         * "company_long": "77.2876351"
         */

        private String first_name;
        private String last_name;
        private String city;
        private String enterprise_name;
        private String about;
        private String website;
        private String salary;
        private String salary_name;
        private String company_category;
        private String company_category_name;
        private String email;
        private String patch_video;
        private String user_pic;
        private String enterprise_pic;
        private String user_id;
        private String company_lat;
        private String company_long;
        private String patch_video_thumbnail;
        private boolean isUserPicChange;
        private boolean isEnterPrisePicChange;
        private boolean isVideoChange;

        public String getSalary_name() {
            return salary_name;
        }

        public void setSalary_name(String salary_name) {
            this.salary_name = salary_name;
        }

        public String getCompany_category_name() {
            return company_category_name;
        }

        public void setCompany_category_name(String company_category_name) {
            this.company_category_name = company_category_name;
        }

        public boolean isVideoChange() {
            return isVideoChange;
        }

        public void setVideoChange(boolean videoChange) {
            isVideoChange = videoChange;
        }



        public String getPatch_video_thumbnail() {
            return patch_video_thumbnail;
        }

        public void setPatch_video_thumbnail(String patch_video_thumbnail) {
            this.patch_video_thumbnail = patch_video_thumbnail;
        }


        public boolean isUserPicChange() {
            return isUserPicChange;
        }

        public void setUserPicChange(boolean userPicChange) {
            isUserPicChange = userPicChange;
        }

        public boolean isEnterPrisePicChange() {
            return isEnterPrisePicChange;
        }

        public void setEnterPrisePicChange(boolean enterPrisePicChange) {
            isEnterPrisePicChange = enterPrisePicChange;
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
    }
}
