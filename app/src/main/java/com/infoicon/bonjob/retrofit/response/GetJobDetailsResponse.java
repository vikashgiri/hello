package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 2/8/17.
 */

public class GetJobDetailsResponse
{


    /**
     * success : true
     * error : false
     * data : [{"job_title":"1 barman / 2 chefs de rang / 2 commis de salle","job_title_id":"0","job_description":"Les '' Relais d'Alsace Taverne Karlsbräu '' de Metz recrute pour le Marché de Noël 2018/2019\r\n\r\n5 CDD à pouvoir de fin novembre à début janvier.","job_image":"http://172.104.8.51/bonjob/public/uploads/job_image//a54db6e83b670f4bfd4e0c1e684c87fd_image.jpg","user_id":"2832","job_id":"3786","job_location":"Metz, 57000","contract_type":"6","contract_type_name":"Fixed-term contract","education_level":"1","education_level_name":"No diploma","experience":"0","experience_name":"","lang":"0","lang_name":"","num_of_hours":"7","num_of_hours_name":"<30h / week","salarie":"2","salarie_name":"Minimum wage","start_date":"18/11/2018","contact_mode":"BonJob Chat:BonJob Chat","enterprise_name":"Les Relais d'Alsace Taverne Karlsbräu","company_lat":"","company_long":"6.175715599999999","website":null,"offer_posted_on":"2019-05-27 06:06:03","jobLink":"http://172.104.8.51/bonjob/site/jobOffer/Mzc4Ng==","origin":"bonjob","redirect_url":"","language_id":"2","apply_on":"","applyStatus":""}]
     * current_time : 2019-06-05 11:57:33
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
         * job_title : 1 barman / 2 chefs de rang / 2 commis de salle
         * job_title_id : 0
         * job_description : Les '' Relais d'Alsace Taverne Karlsbräu '' de Metz recrute pour le Marché de Noël 2018/2019

         5 CDD à pouvoir de fin novembre à début janvier.
         * job_image : http://172.104.8.51/bonjob/public/uploads/job_image//a54db6e83b670f4bfd4e0c1e684c87fd_image.jpg
         * user_id : 2832
         * job_id : 3786
         * job_location : Metz, 57000
         * contract_type : 6
         * contract_type_name : Fixed-term contract
         * education_level : 1
         * education_level_name : No diploma
         * experience : 0
         * experience_name :
         * lang : 0
         * lang_name :
         * num_of_hours : 7
         * num_of_hours_name : <30h / week
         * salarie : 2
         * salarie_name : Minimum wage
         * start_date : 18/11/2018
         * contact_mode : BonJob Chat:BonJob Chat
         * enterprise_name : Les Relais d'Alsace Taverne Karlsbräu
         * company_lat :
         * company_long : 6.175715599999999
         * website : null
         * offer_posted_on : 2019-05-27 06:06:03
         * jobLink : http://172.104.8.51/bonjob/site/jobOffer/Mzc4Ng==
         * origin : bonjob
         * redirect_url :
         * language_id : 2
         * apply_on :
         * applyStatus :
         */

        private String job_title;
        private String job_title_id;
        private String job_description;
        private String job_image;
        private String user_id;
        private String job_id;
        private String job_location;
        private String contract_type;
        private String contract_type_name;
        private String education_level;
        private String education_level_name;
        private String experience;
        private String experience_name;
        private String lang;
        private String lang_name;
        private String num_of_hours;
        private String num_of_hours_name;
        private String salarie;
        private String salarie_name;
        private String start_date;
        private String contact_mode;
        private String enterprise_name;
        private String company_lat;
        private String company_long;
        private String website;
        private String offer_posted_on;
        private String jobLink;
        private String origin;
        private String redirect_url;
        private String language_id;
        private String apply_on;
        private String applyStatus;

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getJob_title_id() {
            return job_title_id;
        }

        public void setJob_title_id(String job_title_id) {
            this.job_title_id = job_title_id;
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

        public String getContract_type_name() {
            return contract_type_name;
        }

        public void setContract_type_name(String contract_type_name) {
            this.contract_type_name = contract_type_name;
        }

        public String getEducation_level() {
            return education_level;
        }

        public void setEducation_level(String education_level) {
            this.education_level = education_level;
        }

        public String getEducation_level_name() {
            return education_level_name;
        }

        public void setEducation_level_name(String education_level_name) {
            this.education_level_name = education_level_name;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getExperience_name() {
            return experience_name;
        }

        public void setExperience_name(String experience_name) {
            this.experience_name = experience_name;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getLang_name() {
            return lang_name;
        }

        public void setLang_name(String lang_name) {
            this.lang_name = lang_name;
        }

        public String getNum_of_hours() {
            return num_of_hours;
        }

        public void setNum_of_hours(String num_of_hours) {
            this.num_of_hours = num_of_hours;
        }

        public String getNum_of_hours_name() {
            return num_of_hours_name;
        }

        public void setNum_of_hours_name(String num_of_hours_name) {
            this.num_of_hours_name = num_of_hours_name;
        }

        public String getSalarie() {
            return salarie;
        }

        public void setSalarie(String salarie) {
            this.salarie = salarie;
        }

        public String getSalarie_name() {
            return salarie_name;
        }

        public void setSalarie_name(String salarie_name) {
            this.salarie_name = salarie_name;
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

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
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

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getOffer_posted_on() {
            return offer_posted_on;
        }

        public void setOffer_posted_on(String offer_posted_on) {
            this.offer_posted_on = offer_posted_on;
        }

        public String getJobLink() {
            return jobLink;
        }

        public void setJobLink(String jobLink) {
            this.jobLink = jobLink;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getApply_on() {
            return apply_on;
        }

        public void setApply_on(String apply_on) {
            this.apply_on = apply_on;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }
    }
/*
    *//**
     * success : true
     * error : false
     * data : [{"job_title":"Adjoint(e) de direction","job_title_id":"1","job_description":"","job_image":"","user_id":"3656","job_id":"4548","job_location":"Noida, 201304","contract_type":"2","contract_type_name":"Interim","education_level":"3","education_level_name":"Vocational training","experience":"2","experience_name":"Less than 1 year","lang":"3","lang_name":"Spanish","num_of_hours":"4","num_of_hours_name":"<15h / week","salarie":"11","salarie_name":"30 \u20ac/hour","start_date":null,"contact_mode":"BonJob Chat:BonJob Chat","enterprise_name":"Henry Ka Ghar","company_lat":"","company_long":"77.391029","website":"www.website.com","offer_posted_on":"2019-04-23 07:45:39","jobLink":"http://172.104.8.51/bonjob/site/jobOffer/NDU0OA==","origin":"bonjob","redirect_url":"","apply_on":"","applyStatus":""}]
     * current_time : 2019-05-09 11:28:27
     * msg : Data found
     * active_user : 1
     *//*

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
        *//**
         * job_title : Adjoint(e) de direction
         * job_title_id : 1
         * job_description :
         * job_image :
         * user_id : 3656
         * job_id : 4548
         * job_location : Noida, 201304
         * contract_type : 2
         * contract_type_name : Interim
         * education_level : 3
         * education_level_name : Vocational training
         * experience : 2
         * experience_name : Less than 1 year
         * lang : 3
         * lang_name : Spanish
         * num_of_hours : 4
         * num_of_hours_name : <15h / week
         * salarie : 11
         * salarie_name : 30 €/hour
         * start_date : null
         * contact_mode : BonJob Chat:BonJob Chat
         * enterprise_name : Henry Ka Ghar
         * company_lat :
         * company_long : 77.391029
         * website : www.website.com
         * offer_posted_on : 2019-04-23 07:45:39
         * jobLink : http://172.104.8.51/bonjob/site/jobOffer/NDU0OA==
         * origin : bonjob
         * redirect_url :
         * apply_on :
         * applyStatus :
         *//*

        private String job_title;
        private String job_title_id;
        private String job_description;
        private String job_image;
        private String user_id;
        private String job_id;
        private String job_location;
        private String contract_type;
        private String contract_type_name;
        private String education_level;
        private String education_level_name;
        private String experience;
        private String experience_name;
        private String lang;
        private String lang_name;
        private String num_of_hours;
        private String num_of_hours_name;
        private String salarie;
        private String salarie_name;
        private Object start_date;
        private String contact_mode;
        private String enterprise_name;
        private String company_lat;
        private String company_long;
        private String website;
        private String offer_posted_on;
        private String jobLink;
        private String origin;
        private String redirect_url;
        private String apply_on;
        private String applyStatus;

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getJob_title_id() {
            return job_title_id;
        }

        public void setJob_title_id(String job_title_id) {
            this.job_title_id = job_title_id;
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

        public String getContract_type_name() {
            return contract_type_name;
        }

        public void setContract_type_name(String contract_type_name) {
            this.contract_type_name = contract_type_name;
        }

        public String getEducation_level() {
            return education_level;
        }

        public void setEducation_level(String education_level) {
            this.education_level = education_level;
        }

        public String getEducation_level_name() {
            return education_level_name;
        }

        public void setEducation_level_name(String education_level_name) {
            this.education_level_name = education_level_name;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getExperience_name() {
            return experience_name;
        }

        public void setExperience_name(String experience_name) {
            this.experience_name = experience_name;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getLang_name() {
            return lang_name;
        }

        public void setLang_name(String lang_name) {
            this.lang_name = lang_name;
        }

        public String getNum_of_hours() {
            return num_of_hours;
        }

        public void setNum_of_hours(String num_of_hours) {
            this.num_of_hours = num_of_hours;
        }

        public String getNum_of_hours_name() {
            return num_of_hours_name;
        }

        public void setNum_of_hours_name(String num_of_hours_name) {
            this.num_of_hours_name = num_of_hours_name;
        }

        public String getSalarie() {
            return salarie;
        }

        public void setSalarie(String salarie) {
            this.salarie = salarie;
        }

        public String getSalarie_name() {
            return salarie_name;
        }

        public void setSalarie_name(String salarie_name) {
            this.salarie_name = salarie_name;
        }

        public Object getStart_date() {
            return start_date;
        }

        public void setStart_date(Object start_date) {
            this.start_date = start_date;
        }

        public String getContact_mode() {
            return contact_mode;
        }

        public void setContact_mode(String contact_mode) {
            this.contact_mode = contact_mode;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
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

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getOffer_posted_on() {
            return offer_posted_on;
        }

        public void setOffer_posted_on(String offer_posted_on) {
            this.offer_posted_on = offer_posted_on;
        }

        public String getJobLink() {
            return jobLink;
        }

        public void setJobLink(String jobLink) {
            this.jobLink = jobLink;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getApply_on() {
            return apply_on;
        }

        public void setApply_on(String apply_on) {
            this.apply_on = apply_on;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }
    }
*/
    /*

    *//**
     * success : true
     * error : false
     * data : [{"job_title":"Femme de chambre","job_description":"facultatif","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/ec2ce565e86f3353f71d8ff4c20231a9_image.jpg","user_id":"54","job_id":"12","job_location":"Mare Palu, Méautis, France","contract_type":"CDD","education_level":"CAP","experience":"Moins d'1 an","lang":"Anglais","num_of_hours":"10h/semaine","salarie":"16 \u20ac/heure","start_date":"11-7-2017","contact_mode":"Téléphone:0667215749","enterprise_name":"Cooking Co.","company_lat":"","company_long":"","website":"www.cookingco.in","offer_posted_on":"2017-09-11 11:42:19","apply_on":"2017-08-04 12:49:52"}]
     * current_time : 2017-09-11 13:03:05
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
         * job_title : Femme de chambre
         * job_description : facultatif
         * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/ec2ce565e86f3353f71d8ff4c20231a9_image.jpg
         * user_id : 54
         * job_id : 12
         * job_location : Mare Palu, Méautis, France
         * contract_type : CDD
         * education_level : CAP
         * experience : Moins d'1 an
         * lang : Anglais
         * num_of_hours : 10h/semaine
         * salarie : 16 €/heure
         * start_date : 11-7-2017
         * contact_mode : Téléphone:0667215749
         * enterprise_name : Cooking Co.
         * company_lat :
         * company_long :
         * website : www.cookingco.in
         * offer_posted_on : 2017-09-11 11:42:19
         * apply_on : 2017-08-04 12:49:52
         *//*

        private String job_title;
        private String job_description;
        private String job_image;
        private String user_id;
        private String job_id;
        private String job_location;
        private String contract_type;
        private String education_level;
        private String experience;
        private String lang;
        private String num_of_hours;
        private String salarie;
        private String start_date;
        private String contact_mode;
        private String enterprise_name;
        private String company_lat;
        private String company_long;
        private String website;
        private String offer_posted_on;
        private String jobLink;
        private String apply_on;
        private String applyStatus;
        private String origin;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
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
        public String getJobLink() {
            return jobLink;
        }

        public void setJobLink(String jobLink) {
            this.jobLink = jobLink;
        }

        public String getJob_image() {
            return job_image;
        }

        public void setJob_image(String job_image) {
            this.job_image = job_image;
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

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
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

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getOffer_posted_on() {
            return offer_posted_on;
        }

        public void setOffer_posted_on(String offer_posted_on) {
            this.offer_posted_on = offer_posted_on;
        }

        public String getApply_on() {
            return apply_on;
        }

        public void setApply_on(String apply_on) {
            this.apply_on = apply_on;
        }
    }*/
}
