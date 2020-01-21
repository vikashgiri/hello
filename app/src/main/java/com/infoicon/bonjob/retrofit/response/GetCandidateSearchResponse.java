package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 4/9/17.
 */

public class GetCandidateSearchResponse {

    /**
     * success : true
     * error : false
     * allCandidates : [{"first_name":"Prmd","last_name":"Kumr","id":"65","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/59c29e198df361f135996eb17fe9b74e_image.jpg","skills":null,"education_level":"High-School","mobility":"Yes","current_status":"Active","lattitude":null,"longitude":null,"city":"Noida-Greater Noida Expressway, Gautam Buddha Park, Noida, U","position_held":"Team leader (collective rest)","industry_type":"","experience":"2","seeker_lang_name":"German","lang_proficiency":"Intermediate","matchingPercent":22},{"first_name":"Tho","last_name":"Regnault","id":"74","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/a973fb242420c54dd29de6484ca3fb26_image.png","skills":null,"education_level":"BTS","mobility":"Oui","current_status":"Demandeur d'emploi","lattitude":null,"longitude":null,"city":"Sainteny, France","position_held":"Chef d'equipe (rest. rapide)","industry_type":"","experience":"3","seeker_lang_name":"French","lang_proficiency":"Actuel","matchingPercent":11},{"first_name":"Tho","last_name":"Regnault","id":"74","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/a973fb242420c54dd29de6484ca3fb26_image.png","skills":null,"education_level":"BTS","mobility":"Oui","current_status":"Demandeur d'emploi","lattitude":null,"longitude":null,"city":"Sainteny, France","position_held":"Chef d'equipe (rest. rapide)","industry_type":"","experience":"3","seeker_lang_name":"English","lang_proficiency":"Dbutant","matchingPercent":11},{"first_name":"Tho","last_name":"Regnault","id":"74","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/a973fb242420c54dd29de6484ca3fb26_image.png","skills":null,"education_level":"BTS","mobility":"Oui","current_status":"Demandeur d'emploi","lattitude":null,"longitude":null,"city":"Sainteny, France","position_held":"Chef d'equipe (rest. rapide)","industry_type":"","experience":"3","seeker_lang_name":"German","lang_proficiency":"Dbutant","matchingPercent":11},{"first_name":"Vishal \t","last_name":"Kumar","id":"42","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/0a8a746bc9fb3bc3a7dcc5574713df15_image.jpg","skills":"Service","education_level":"Master and above","mobility":"Yes","current_status":"Apprentice","lattitude":null,"longitude":null,"city":"New Delhi","position_held":"Barman","industry_type":"","experience":"4","seeker_lang_name":"German","lang_proficiency":"Beginner","matchingPercent":44},{"first_name":"Vishal \t","last_name":"Kumar","id":"42","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/0a8a746bc9fb3bc3a7dcc5574713df15_image.jpg","skills":"Service","education_level":"Master and above","mobility":"Yes","current_status":"Apprentice","lattitude":null,"longitude":null,"city":"New Delhi","position_held":"Barman","industry_type":"","experience":"4","seeker_lang_name":"Portuguese","lang_proficiency":"Intermediate","matchingPercent":44},{"first_name":"Vishal \t","last_name":"Kumar","id":"42","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/0a8a746bc9fb3bc3a7dcc5574713df15_image.jpg","skills":"Service","education_level":"Master and above","mobility":"Yes","current_status":"Apprentice","lattitude":null,"longitude":null,"city":"New Delhi","position_held":"Barman","industry_type":"","experience":"4","seeker_lang_name":"French","lang_proficiency":"Fluent","matchingPercent":44},{"first_name":"Vishal \t","last_name":"Kumar","id":"42","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/0a8a746bc9fb3bc3a7dcc5574713df15_image.jpg","skills":"Service","education_level":"Master and above","mobility":"Yes","current_status":"Apprentice","lattitude":null,"longitude":null,"city":"New Delhi","position_held":"Barman","industry_type":"","experience":"4","seeker_lang_name":"Chinese","lang_proficiency":"Advanced","matchingPercent":44},{"first_name":"Vishal \t","last_name":"Kumar","id":"42","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/0a8a746bc9fb3bc3a7dcc5574713df15_image.jpg","skills":"Service","education_level":"Master and above","mobility":"Yes","current_status":"Apprentice","lattitude":null,"longitude":null,"city":"New Delhi","position_held":"Barman","industry_type":"","experience":"3","seeker_lang_name":"German","lang_proficiency":"Beginner","matchingPercent":44},{"first_name":"Vishal \t","last_name":"Kumar","id":"42","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/0a8a746bc9fb3bc3a7dcc5574713df15_image.jpg","skills":"Service","education_level":"Master and above","mobility":"Yes","current_status":"Apprentice","lattitude":null,"longitude":null,"city":"New Delhi","position_held":"Barman","industry_type":"","experience":"3","seeker_lang_name":"Portuguese","lang_proficiency":"Intermediate","matchingPercent":44}]
     * company_lat : null
     * company_long : null
     * msg : Data found.
     */

    private boolean success;
    private boolean error;
    private Object company_lat;
    private Object company_long;
    private String msg;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    private String active_user;
    private List<AllCandidatesBean> allCandidates;

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

    public Object getCompany_lat() {
        return company_lat;
    }

    public void setCompany_lat(Object company_lat) {
        this.company_lat = company_lat;
    }

    public Object getCompany_long() {
        return company_long;
    }

    public void setCompany_long(Object company_long) {
        this.company_long = company_long;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AllCandidatesBean> getAllCandidates() {
        return allCandidates;
    }

    public void setAllCandidates(List<AllCandidatesBean> allCandidates) {
        this.allCandidates = allCandidates;
    }

    public static class AllCandidatesBean {
        /**
         * first_name : Prmd
         * last_name : Kumr
         * id : 65
         * user_pic : http://139.162.164.98/bonjob//public/uploads/user_pic/59c29e198df361f135996eb17fe9b74e_image.jpg
         * skills : null
         * education_level : High-School
         * mobility : Yes
         * current_status : Active
         * lattitude : null
         * longitude : null
         * city : Noida-Greater Noida Expressway, Gautam Buddha Park, Noida, U
         * position_held : Team leader (collective rest)
         * industry_type :
         * experience : 2
         * seeker_lang_name : German
         * lang_proficiency : Intermediate
         * matchingPercent : 22
         */











        private String first_name;
        private String last_name;
        private String id;
        private String user_pic;
        private Object skills;
        private String skills_name;

        private String education_level;
        private String education_level_name;
        private String mobility;
        private String mobility_name;
        private String current_status;
        private String current_status_name;
        private Object lattitude;
        private Object longitude;
        private String city;
        private String position_held;
        private String industry_type;
        private String experience;
        private String seeker_lang_name;
        private String lang_proficiency;
        private int matchingPercent;

        public String getSkills_name() {
            return skills_name;
        }

        public void setSkills_name(String skills_name) {
            this.skills_name = skills_name;
        }

        public String getEducation_level_name() {
            return education_level_name;
        }

        public void setEducation_level_name(String education_level_name) {
            this.education_level_name = education_level_name;
        }

        public String getMobility_name() {
            return mobility_name;
        }

        public void setMobility_name(String mobility_name) {
            this.mobility_name = mobility_name;
        }

        public String getCurrent_status_name() {
            return current_status_name;
        }

        public void setCurrent_status_name(String current_status_name) {
            this.current_status_name = current_status_name;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public Object getSkills() {
            return skills;
        }

        public void setSkills(Object skills) {
            this.skills = skills;
        }

        public String getEducation_level() {
            return education_level;
        }

        public void setEducation_level(String education_level) {
            this.education_level = education_level;
        }

        public String getMobility() {
            return mobility;
        }

        public void setMobility(String mobility) {
            this.mobility = mobility;
        }

        public String getCurrent_status() {
            return current_status;
        }

        public void setCurrent_status(String current_status) {
            this.current_status = current_status;
        }

        public Object getLattitude() {
            return lattitude;
        }

        public void setLattitude(Object lattitude) {
            this.lattitude = lattitude;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPosition_held() {
            return position_held;
        }

        public void setPosition_held(String position_held) {
            this.position_held = position_held;
        }

        public String getIndustry_type() {
            return industry_type;
        }

        public void setIndustry_type(String industry_type) {
            this.industry_type = industry_type;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getSeeker_lang_name() {
            return seeker_lang_name;
        }

        public void setSeeker_lang_name(String seeker_lang_name) {
            this.seeker_lang_name = seeker_lang_name;
        }

        public String getLang_proficiency() {
            return lang_proficiency;
        }

        public void setLang_proficiency(String lang_proficiency) {
            this.lang_proficiency = lang_proficiency;
        }

        public int getMatchingPercent() {
            return matchingPercent;
        }

        public void setMatchingPercent(int matchingPercent) {
            this.matchingPercent = matchingPercent;
        }
    }
}
