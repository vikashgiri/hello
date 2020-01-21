package com.infoicon.bonjob.seeker.profile;

import java.util.List;

/**
 * Created by info on 20/8/18.
 */

public class GetLanguageResponce {

    /**
     * success : true
     * error : false
     * jobLanguages : [{"job_language_content_id":"2","job_language_id":"1","job_language_title":"Français","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:08:39"},{"job_language_content_id":"4","job_language_id":"2","job_language_title":"Anglais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:08:57"},{"job_language_content_id":"6","job_language_id":"3","job_language_title":"Espagnol","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:17"},{"job_language_content_id":"8","job_language_id":"4","job_language_title":"Allemand","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:30"},{"job_language_content_id":"10","job_language_id":"5","job_language_title":"Portugais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:43"},{"job_language_content_id":"12","job_language_id":"6","job_language_title":"Chinois","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:55"},{"job_language_content_id":"14","job_language_id":"7","job_language_title":"Japonais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:09"},{"job_language_content_id":"16","job_language_id":"8","job_language_title":"Arabe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:22"},{"job_language_content_id":"18","job_language_id":"9","job_language_title":"Russe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:35"},{"job_language_content_id":"20","job_language_id":"10","job_language_title":"Hindi","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:44"}]
     * languageProficiencies : [{"language_proficiency_content_id":"2","language_proficiency_id":"1","language_proficiency_title":"Débutant","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-13 01:15:20"},{"language_proficiency_content_id":"4","language_proficiency_id":"2","language_proficiency_title":"Intermédiaire","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-13 01:14:02"},{"language_proficiency_content_id":"6","language_proficiency_id":"3","language_proficiency_title":"Avancé","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-08-08 10:12:22"},{"language_proficiency_content_id":"8","language_proficiency_id":"4","language_proficiency_title":"Courant","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-08-08 10:12:29"}]
     * msg : Données trouvées
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<JobLanguagesBean> jobLanguages;
    private List<LanguageProficienciesBean> languageProficiencies;

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

    public List<JobLanguagesBean> getJobLanguages() {
        return jobLanguages;
    }

    public void setJobLanguages(List<JobLanguagesBean> jobLanguages) {
        this.jobLanguages = jobLanguages;
    }

    public List<LanguageProficienciesBean> getLanguageProficiencies() {
        return languageProficiencies;
    }

    public void setLanguageProficiencies(List<LanguageProficienciesBean> languageProficiencies) {
        this.languageProficiencies = languageProficiencies;
    }

    public static class JobLanguagesBean {
        /**
         * job_language_content_id : 2
         * job_language_id : 1
         * job_language_title : Français
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 10:08:39
         */

        private String job_language_content_id;
        private String job_language_id;
        private String job_language_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getJob_language_content_id() {
            return job_language_content_id;
        }

        public void setJob_language_content_id(String job_language_content_id) {
            this.job_language_content_id = job_language_content_id;
        }

        public String getJob_language_id() {
            return job_language_id;
        }

        public void setJob_language_id(String job_language_id) {
            this.job_language_id = job_language_id;
        }

        public String getJob_language_title() {
            return job_language_title;
        }

        public void setJob_language_title(String job_language_title) {
            this.job_language_title = job_language_title;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }
    }

    public static class LanguageProficienciesBean {
        /**
         * language_proficiency_content_id : 2
         * language_proficiency_id : 1
         * language_proficiency_title : Débutant
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-13 01:15:20
         */

        private String language_proficiency_content_id;
        private String language_proficiency_id;
        private String language_proficiency_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getLanguage_proficiency_content_id() {
            return language_proficiency_content_id;
        }

        public void setLanguage_proficiency_content_id(String language_proficiency_content_id) {
            this.language_proficiency_content_id = language_proficiency_content_id;
        }

        public String getLanguage_proficiency_id() {
            return language_proficiency_id;
        }

        public void setLanguage_proficiency_id(String language_proficiency_id) {
            this.language_proficiency_id = language_proficiency_id;
        }

        public String getLanguage_proficiency_title() {
            return language_proficiency_title;
        }

        public void setLanguage_proficiency_title(String language_proficiency_title) {
            this.language_proficiency_title = language_proficiency_title;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }
    }
}
