package com.infoicon.bonjob.seeker.profile;

import java.util.List;

/**
 * Created by info on 20/8/18.
 */

public class GetEducationDataResponce {

    /**
     * success : true
     * error : false
     * educationLevels : [{"education_content_id":"2","education_id":"1","education_title":"Sans diplôme","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:44"},{"education_content_id":"4","education_id":"2","education_title":"CAP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:58"},{"education_content_id":"6","education_id":"3","education_title":"BEP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:12"},{"education_content_id":"8","education_id":"4","education_title":"BP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:21"},{"education_content_id":"10","education_id":"5","education_title":"BAC","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:37"},{"education_content_id":"12","education_id":"6","education_title":"BAC Pro","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:01"},{"education_content_id":"14","education_id":"7","education_title":"BTS","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:16"},{"education_content_id":"16","education_id":"8","education_title":"DUT","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:33"},{"education_content_id":"18","education_id":"9","education_title":"Licence","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:45"},{"education_content_id":"20","education_id":"10","education_title":"Master et +","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:06"},{"education_content_id":"22","education_id":"11","education_title":"Autre","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:22"}]
     * msg : Données trouvées
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<EducationLevelsBean> educationLevels;

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

    public List<EducationLevelsBean> getEducationLevels() {
        return educationLevels;
    }

    public void setEducationLevels(List<EducationLevelsBean> educationLevels) {
        this.educationLevels = educationLevels;
    }

    public static class EducationLevelsBean {
        /**
         * education_content_id : 2
         * education_id : 1
         * education_title : Sans diplôme
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 05:16:44
         */

        private String education_content_id;
        private String education_id;
        private String education_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getEducation_content_id() {
            return education_content_id;
        }

        public void setEducation_content_id(String education_content_id) {
            this.education_content_id = education_content_id;
        }

        public String getEducation_id() {
            return education_id;
        }

        public void setEducation_id(String education_id) {
            this.education_id = education_id;
        }

        public String getEducation_title() {
            return education_title;
        }

        public void setEducation_title(String education_title) {
            this.education_title = education_title;
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
