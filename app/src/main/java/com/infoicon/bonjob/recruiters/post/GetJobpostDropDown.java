package com.infoicon.bonjob.recruiters.post;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by info on 28/8/18.
 */

public class GetJobpostDropDown implements Parcelable {

    /**
     * success : true
     * error : false
     * contractTypes : [{"contract_content_id":"2","contract_id":"1","contract_title":"CDI","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:14:20"},{"contract_content_id":"4","contract_id":"2","contract_title":"Intérim","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:14:33"},{"contract_content_id":"6","contract_id":"3","contract_title":"Apprentissage","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:14:49"},{"contract_content_id":"8","contract_id":"4","contract_title":"Stage/Alternance","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:15:17"},{"contract_content_id":"10","contract_id":"5","contract_title":"Franchise/Indépendant","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:15:46"},{"contract_content_id":"12","contract_id":"6","contract_title":"CDD","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:02"}]
     * educationLevels : [{"education_content_id":"2","education_id":"1","education_title":"Sans diplôme","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:44"},{"education_content_id":"4","education_id":"2","education_title":"CAP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:58"},{"education_content_id":"6","education_id":"3","education_title":"BEP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:12"},{"education_content_id":"8","education_id":"4","education_title":"BP","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:21"},{"education_content_id":"10","education_id":"5","education_title":"BAC","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:37"},{"education_content_id":"12","education_id":"6","education_title":"BAC Pro","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:01"},{"education_content_id":"14","education_id":"7","education_title":"BTS","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:16"},{"education_content_id":"16","education_id":"8","education_title":"DUT","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:33"},{"education_content_id":"18","education_id":"9","education_title":"Licence","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:45"},{"education_content_id":"20","education_id":"10","education_title":"Master et +","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:06"},{"education_content_id":"22","education_id":"11","education_title":"Autre","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:22"}]
     * experiences : [{"experience_content_id":"2","experience_id":"1","experience_title":"Sans expérience","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:28:57"},{"experience_content_id":"4","experience_id":"2","experience_title":"Moins d'1 an","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:29:16"},{"experience_content_id":"6","experience_id":"3","experience_title":"1-2 ans","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:29:34"},{"experience_content_id":"8","experience_id":"4","experience_title":"3-4 ans","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:30:06"},{"experience_content_id":"10","experience_id":"5","experience_title":"5 ans et +","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:30:16"}]
     * jobLanguages : [{"job_language_content_id":"2","job_language_id":"1","job_language_title":"Français","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:08:39"},{"job_language_content_id":"4","job_language_id":"2","job_language_title":"Anglais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:08:57"},{"job_language_content_id":"6","job_language_id":"3","job_language_title":"Espagnol","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:17"},{"job_language_content_id":"8","job_language_id":"4","job_language_title":"Allemand","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:30"},{"job_language_content_id":"10","job_language_id":"5","job_language_title":"Portugais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:43"},{"job_language_content_id":"12","job_language_id":"6","job_language_title":"Chinois","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:09:55"},{"job_language_content_id":"14","job_language_id":"7","job_language_title":"Japonais","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:09"},{"job_language_content_id":"16","job_language_id":"8","job_language_title":"Arabe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:22"},{"job_language_content_id":"18","job_language_id":"9","job_language_title":"Russe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:35"},{"job_language_content_id":"20","job_language_id":"10","job_language_title":"Hindi","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 10:10:44"}]
     * numberOfHours : [{"hours_content_id":"2","hours_id":"1","hours_title":"Temps plein","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:22:55"},{"hours_content_id":"4","hours_id":"2","hours_title":"<10h/semaine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:23:14"},{"hours_content_id":"6","hours_id":"3","hours_title":"10h/semaine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:24:11"},{"hours_content_id":"8","hours_id":"4","hours_title":"<15h/semaine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:24:25"},{"hours_content_id":"10","hours_id":"5","hours_title":"<20h/semaine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:24:42"},{"hours_content_id":"12","hours_id":"6","hours_title":"<25h/semaine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:25:04"},{"hours_content_id":"14","hours_id":"7","hours_title":"<30h/semaine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:25:25"}]
     * salaries : [{"salary_content_id":"2","salary_id":"1","salary_title":"A discuter","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:49:53"},{"salary_content_id":"4","salary_id":"2","salary_title":"Smic horaire","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:50:13"},{"salary_content_id":"6","salary_id":"3","salary_title":"10 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:51:04"},{"salary_content_id":"8","salary_id":"4","salary_title":"12 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:51:17"},{"salary_content_id":"10","salary_id":"5","salary_title":"14 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:51:40"},{"salary_content_id":"12","salary_id":"6","salary_title":"16 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:51:51"},{"salary_content_id":"14","salary_id":"7","salary_title":"18 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:52:03"},{"salary_content_id":"16","salary_id":"8","salary_title":"20 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:52:16"},{"salary_content_id":"18","salary_id":"9","salary_title":"22 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:52:29"},{"salary_content_id":"20","salary_id":"10","salary_title":"25 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:52:42"},{"salary_content_id":"22","salary_id":"11","salary_title":"30 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:52:56"},{"salary_content_id":"24","salary_id":"12","salary_title":"+30 \u20ac/heure","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:53:08"}]
     * msg : Données trouvées
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<ContractTypesBean> contractTypes;
    private List<EducationLevelsBean> educationLevels;
    private List<ExperiencesBean> experiences;
    private List<JobLanguagesBean> jobLanguages;
    private List<NumberOfHoursBean> numberOfHours;
    private List<SalariesBean> salaries;

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

    public List<ContractTypesBean> getContractTypes() {
        return contractTypes;
    }

    public void setContractTypes(List<ContractTypesBean> contractTypes) {
        this.contractTypes = contractTypes;
    }

    public List<EducationLevelsBean> getEducationLevels() {
        return educationLevels;
    }

    public void setEducationLevels(List<EducationLevelsBean> educationLevels) {
        this.educationLevels = educationLevels;
    }

    public List<ExperiencesBean> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperiencesBean> experiences) {
        this.experiences = experiences;
    }

    public List<JobLanguagesBean> getJobLanguages() {
        return jobLanguages;
    }

    public void setJobLanguages(List<JobLanguagesBean> jobLanguages) {
        this.jobLanguages = jobLanguages;
    }

    public List<NumberOfHoursBean> getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(List<NumberOfHoursBean> numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public List<SalariesBean> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<SalariesBean> salaries) {
        this.salaries = salaries;
    }

    public static class ContractTypesBean {
        /**
         * contract_content_id : 2
         * contract_id : 1
         * contract_title : CDI
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 05:14:20
         */

        private String contract_content_id;
        private String contract_id;
        private String contract_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getContract_content_id() {
            return contract_content_id;
        }

        public void setContract_content_id(String contract_content_id) {
            this.contract_content_id = contract_content_id;
        }

        public String getContract_id() {
            return contract_id;
        }

        public void setContract_id(String contract_id) {
            this.contract_id = contract_id;
        }

        public String getContract_title() {
            return contract_title;
        }

        public void setContract_title(String contract_title) {
            this.contract_title = contract_title;
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

    public static class NumberOfHoursBean {
        /**
         * hours_content_id : 2
         * hours_id : 1
         * hours_title : Temps plein
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 05:22:55
         */

        private String hours_content_id;
        private String hours_id;
        private String hours_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getHours_content_id() {
            return hours_content_id;
        }

        public void setHours_content_id(String hours_content_id) {
            this.hours_content_id = hours_content_id;
        }

        public String getHours_id() {
            return hours_id;
        }

        public void setHours_id(String hours_id) {
            this.hours_id = hours_id;
        }

        public String getHours_title() {
            return hours_title;
        }

        public void setHours_title(String hours_title) {
            this.hours_title = hours_title;
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

    public static class SalariesBean {
        /**
         * salary_content_id : 2
         * salary_id : 1
         * salary_title : A discuter
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 05:49:53
         */

        private String salary_content_id;
        private String salary_id;
        private String salary_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getSalary_content_id() {
            return salary_content_id;
        }

        public void setSalary_content_id(String salary_content_id) {
            this.salary_content_id = salary_content_id;
        }

        public String getSalary_id() {
            return salary_id;
        }

        public void setSalary_id(String salary_id) {
            this.salary_id = salary_id;
        }

        public String getSalary_title() {
            return salary_title;
        }

        public void setSalary_title(String salary_title) {
            this.salary_title = salary_title;
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
        dest.writeList(this.contractTypes);
        dest.writeList(this.educationLevels);
        dest.writeList(this.experiences);
        dest.writeList(this.jobLanguages);
        dest.writeList(this.numberOfHours);
        dest.writeList(this.salaries);
    }

    public GetJobpostDropDown() {
    }

    protected GetJobpostDropDown(Parcel in) {
        this.success = in.readByte() != 0;
        this.error = in.readByte() != 0;
        this.msg = in.readString();
        this.active_user = in.readString();
        this.contractTypes = new ArrayList<ContractTypesBean>();
        in.readList(this.contractTypes, ContractTypesBean.class.getClassLoader());
        this.educationLevels = new ArrayList<EducationLevelsBean>();
        in.readList(this.educationLevels, EducationLevelsBean.class.getClassLoader());
        this.experiences = new ArrayList<ExperiencesBean>();
        in.readList(this.experiences, ExperiencesBean.class.getClassLoader());
        this.jobLanguages = new ArrayList<JobLanguagesBean>();
        in.readList(this.jobLanguages, JobLanguagesBean.class.getClassLoader());
        this.numberOfHours = new ArrayList<NumberOfHoursBean>();
        in.readList(this.numberOfHours, NumberOfHoursBean.class.getClassLoader());
        this.salaries = new ArrayList<SalariesBean>();
        in.readList(this.salaries, SalariesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetJobpostDropDown> CREATOR = new Parcelable.Creator<GetJobpostDropDown>() {
        @Override
        public GetJobpostDropDown createFromParcel(Parcel source) {
            return new GetJobpostDropDown(source);
        }

        @Override
        public GetJobpostDropDown[] newArray(int size) {
            return new GetJobpostDropDown[size];
        }
    };
}
