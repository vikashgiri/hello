package com.infoicon.bonjob.recruiters.search;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by info on 28/8/18.
 */

public class GetTargetSearchResponce implements Parcelable {

    /**
     * success : true
     * error : false
     * currentStatuses : [{"seeker_current_status_content_id":"2","seeker_current_status_id":"1","seeker_current_status_title":"Étudiant","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:48:24"},{"seeker_current_status_content_id":"4","seeker_current_status_id":"2","seeker_current_status_title":"Apprenti","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:48:39"},{"seeker_current_status_content_id":"6","seeker_current_status_id":"3","seeker_current_status_title":"Actif","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:48:53"},{"seeker_current_status_content_id":"8","seeker_current_status_id":"4","seeker_current_status_title":"Demandeur d'emploi","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:49:14"},{"seeker_current_status_content_id":"10","seeker_current_status_id":"5","seeker_current_status_title":"Inactif","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:49:29"}]
     * skills : [{"skill_content_id":"2","skill_id":"1","skill_title":"Cuisine - Restauration","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-18 13:06:50"},{"skill_content_id":"4","skill_id":"2","skill_title":"Salle - Service","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-18 13:07:04"},{"skill_content_id":"6","skill_id":"3","skill_title":"Hôtellerie - Hébergement","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-18 13:07:17"}]
     * mobilities : [{"mobility_content_id":"2","mobility_id":"1","mobility_title":"Oui","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-12 07:53:18"},{"mobility_content_id":"4","mobility_id":"2","mobility_title":"Non","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-12 07:53:11"}]
     * educationLevels : [{"education_content_id":"2","education_id":"1","education_title":"Sans diplôme","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:44"},{"education_content_id":"4","education_id":"2","education_title":"CAP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:58"},{"education_content_id":"6","education_id":"3","education_title":"BEP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:12"},{"education_content_id":"8","education_id":"4","education_title":"BP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:21"},{"education_content_id":"10","education_id":"5","education_title":"BAC","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:37"},{"education_content_id":"12","education_id":"6","education_title":"BAC Pro","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:01"},{"education_content_id":"14","education_id":"7","education_title":"BTS","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:16"},{"education_content_id":"16","education_id":"8","education_title":"DUT","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:33"},{"education_content_id":"18","education_id":"9","education_title":"Licence","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:45"},{"education_content_id":"20","education_id":"10","education_title":"Master et +","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:06"},{"education_content_id":"22","education_id":"11","education_title":"Autre","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:22"}]
     * jobLanguages : [{"job_language_content_id":"2","job_language_id":"1","job_language_title":"Français","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:08:39"},{"job_language_content_id":"4","job_language_id":"2","job_language_title":"Anglais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:08:57"},{"job_language_content_id":"6","job_language_id":"3","job_language_title":"Espagnol","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:17"},{"job_language_content_id":"8","job_language_id":"4","job_language_title":"Allemand","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:30"},{"job_language_content_id":"10","job_language_id":"5","job_language_title":"Portugais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:43"},{"job_language_content_id":"12","job_language_id":"6","job_language_title":"Chinois","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:55"},{"job_language_content_id":"14","job_language_id":"7","job_language_title":"Japonais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:09"},{"job_language_content_id":"16","job_language_id":"8","job_language_title":"Arabe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:22"},{"job_language_content_id":"18","job_language_id":"9","job_language_title":"Russe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:35"},{"job_language_content_id":"20","job_language_id":"10","job_language_title":"Hindi","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:44"}]
     * experiences : [{"experience_content_id":"2","experience_id":"1","experience_title":"Sans expérience","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:28:57"},{"experience_content_id":"4","experience_id":"2","experience_title":"Moins d'1 an","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:29:16"},{"experience_content_id":"6","experience_id":"3","experience_title":"1-2 ans","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:29:34"},{"experience_content_id":"8","experience_id":"4","experience_title":"3-4 ans","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:30:06"},{"experience_content_id":"10","experience_id":"5","experience_title":"5 ans et +","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:30:16"}]
     * msg : Données trouvées
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<CurrentStatusesBean> currentStatuses;
    private List<SkillsBean> skills;
    private List<MobilitiesBean> mobilities;
    private List<EducationLevelsBean> educationLevels;
    private List<JobLanguagesBean> jobLanguages;
    private List<ExperiencesBean> experiences;

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

    public List<CurrentStatusesBean> getCurrentStatuses() {
        return currentStatuses;
    }

    public void setCurrentStatuses(List<CurrentStatusesBean> currentStatuses) {
        this.currentStatuses = currentStatuses;
    }

    public List<SkillsBean> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillsBean> skills) {
        this.skills = skills;
    }

    public List<MobilitiesBean> getMobilities() {
        return mobilities;
    }

    public void setMobilities(List<MobilitiesBean> mobilities) {
        this.mobilities = mobilities;
    }

    public List<EducationLevelsBean> getEducationLevels() {
        return educationLevels;
    }

    public void setEducationLevels(List<EducationLevelsBean> educationLevels) {
        this.educationLevels = educationLevels;
    }

    public List<JobLanguagesBean> getJobLanguages() {
        return jobLanguages;
    }

    public void setJobLanguages(List<JobLanguagesBean> jobLanguages) {
        this.jobLanguages = jobLanguages;
    }

    public List<ExperiencesBean> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperiencesBean> experiences) {
        this.experiences = experiences;
    }

    public static class CurrentStatusesBean {
        /**
         * seeker_current_status_content_id : 2
         * seeker_current_status_id : 1
         * seeker_current_status_title : Étudiant
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 05:48:24
         */

        private String seeker_current_status_content_id;
        private String seeker_current_status_id;
        private String seeker_current_status_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getSeeker_current_status_content_id() {
            return seeker_current_status_content_id;
        }

        public void setSeeker_current_status_content_id(String seeker_current_status_content_id) {
            this.seeker_current_status_content_id = seeker_current_status_content_id;
        }

        public String getSeeker_current_status_id() {
            return seeker_current_status_id;
        }

        public void setSeeker_current_status_id(String seeker_current_status_id) {
            this.seeker_current_status_id = seeker_current_status_id;
        }

        public String getSeeker_current_status_title() {
            return seeker_current_status_title;
        }

        public void setSeeker_current_status_title(String seeker_current_status_title) {
            this.seeker_current_status_title = seeker_current_status_title;
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

    public static class SkillsBean {
        /**
         * skill_content_id : 2
         * skill_id : 1
         * skill_title : Cuisine - Restauration
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-18 13:06:50
         */

        private String skill_content_id;
        private String skill_id;
        private String skill_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getSkill_content_id() {
            return skill_content_id;
        }

        public void setSkill_content_id(String skill_content_id) {
            this.skill_content_id = skill_content_id;
        }

        public String getSkill_id() {
            return skill_id;
        }

        public void setSkill_id(String skill_id) {
            this.skill_id = skill_id;
        }

        public String getSkill_title() {
            return skill_title;
        }

        public void setSkill_title(String skill_title) {
            this.skill_title = skill_title;
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

    public static class MobilitiesBean {
        /**
         * mobility_content_id : 2
         * mobility_id : 1
         * mobility_title : Oui
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-12 07:53:18
         */

        private String mobility_content_id;
        private String mobility_id;
        private String mobility_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getMobility_content_id() {
            return mobility_content_id;
        }

        public void setMobility_content_id(String mobility_content_id) {
            this.mobility_content_id = mobility_content_id;
        }

        public String getMobility_id() {
            return mobility_id;
        }

        public void setMobility_id(String mobility_id) {
            this.mobility_id = mobility_id;
        }

        public String getMobility_title() {
            return mobility_title;
        }

        public void setMobility_title(String mobility_title) {
            this.mobility_title = mobility_title;
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

    public static class ExperiencesBean {
        /**
         * experience_content_id : 2
         * experience_id : 1
         * experience_title : Sans expérience
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 05:28:57
         */

        private String experience_content_id;
        private String experience_id;
        private String experience_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getExperience_content_id() {
            return experience_content_id;
        }

        public void setExperience_content_id(String experience_content_id) {
            this.experience_content_id = experience_content_id;
        }

        public String getExperience_id() {
            return experience_id;
        }

        public void setExperience_id(String experience_id) {
            this.experience_id = experience_id;
        }

        public String getExperience_title() {
            return experience_title;
        }

        public void setExperience_title(String experience_title) {
            this.experience_title = experience_title;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeString(this.msg);
        dest.writeString(this.active_user);
        dest.writeList(this.currentStatuses);
        dest.writeList(this.skills);
        dest.writeList(this.mobilities);
        dest.writeList(this.educationLevels);
        dest.writeList(this.jobLanguages);
        dest.writeList(this.experiences);
    }

    public GetTargetSearchResponce() {
    }

    protected GetTargetSearchResponce(Parcel in) {
        this.success = in.readByte() != 0;
        this.error = in.readByte() != 0;
        this.msg = in.readString();
        this.active_user = in.readString();
        this.currentStatuses = new ArrayList<CurrentStatusesBean>();
        in.readList(this.currentStatuses, CurrentStatusesBean.class.getClassLoader());
        this.skills = new ArrayList<SkillsBean>();
        in.readList(this.skills, SkillsBean.class.getClassLoader());
        this.mobilities = new ArrayList<MobilitiesBean>();
        in.readList(this.mobilities, MobilitiesBean.class.getClassLoader());
        this.educationLevels = new ArrayList<EducationLevelsBean>();
        in.readList(this.educationLevels, EducationLevelsBean.class.getClassLoader());
        this.jobLanguages = new ArrayList<JobLanguagesBean>();
        in.readList(this.jobLanguages, JobLanguagesBean.class.getClassLoader());
        this.experiences = new ArrayList<ExperiencesBean>();
        in.readList(this.experiences, ExperiencesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetTargetSearchResponce> CREATOR = new Parcelable.Creator<GetTargetSearchResponce>() {
        @Override
        public GetTargetSearchResponce createFromParcel(Parcel source) {
            return new GetTargetSearchResponce(source);
        }

        @Override
        public GetTargetSearchResponce[] newArray(int size) {
            return new GetTargetSearchResponce[size];
        }
    };
}

