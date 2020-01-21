package com.infoicon.bonjob.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by infoicona on 20/7/17.
 */

public class GetMyOffersRecruiterResponse {

    /**
     * success : true
     * error : false
     * ActiveJobs : [{"job_id":"13","job_title":"Femme de chambre","job_description":"facultatif","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg","job_location":"Mare Palu, Méautis, France","contract_type":"CDD","education_level":"CAP","experience":"Moins d'1 an","lang":"Anglais","num_of_hours":"10h/semaine","salarie":"16 \u20ac/heure","start_date":"11-7-2017","contact_mode":"Téléphone:0667215749","status":"1","added_by":"0","user_id":"71","createdOn":"2017-07-31 06:20:27","updatedOn":"2017-08-18 06:10:37"}]
     * closedJobs : [{"job_id":"14","job_title":"Femme de chambre","job_description":"facultatif","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/29621051adc62369196660a16db32c30_image.png","job_location":"Mare Palu, Méautis, France","contract_type":"CDD","education_level":"CAP","experience":"Moins d'1 an","lang":"Anglais","num_of_hours":"10h/semaine","salarie":"16 \u20ac/heure","start_date":"11-7-2017","contact_mode":"Téléphone:0667215749","status":"2","added_by":"0","user_id":"71","createdOn":"2017-08-05 06:03:28","updatedOn":"2017-08-15 11:57:19"},{"job_id":"15","job_title":"Femme de chambre","job_description":"facultatif","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/13e29764d3432fdb7aca4dc3517f2196_image.jpg","job_location":"Mare Palu, Méautis, France","contract_type":"CDD","education_level":"CAP","experience":"Moins d'1 an","lang":"Anglais","num_of_hours":"10h/semaine","salarie":"16 \u20ac/heure","start_date":"11-7-2017","contact_mode":"Téléphone:0667215749","status":"2","added_by":"0","user_id":"71","createdOn":"2017-08-05 08:35:14","updatedOn":"2017-08-07 22:08:45"},{"job_id":"18","job_title":"Barman","job_description":"C'est un petit travail tranquil","job_image":"http://139.162.164.98/bonjob//public/uploads/job_image/d50da8ef41f8bafb293557d15716c896_image.jpg","job_location":"Carentan, France","contract_type":"CDD","education_level":"CAP","experience":"Moins d'1 an","lang":"Français","num_of_hours":"25h/semaine","salarie":"Smic horaire","start_date":"21-7-2017","contact_mode":"BonJob App:BonJob App","status":"2","added_by":"0","user_id":"71","createdOn":"2017-08-15 11:57:07","updatedOn":"2017-08-18 06:00:16"}]
     * msg : Data found.
     */

    private boolean success;
    private boolean error;
    private String msg;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    private String active_user;
    private List<ActiveJobsBean> ActiveJobs;
    private List<ClosedJobsBean> closedJobs;

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

    public List<ActiveJobsBean> getActiveJobs() {
        return ActiveJobs;
    }

    public void setActiveJobs(List<ActiveJobsBean> ActiveJobs) {
        this.ActiveJobs = ActiveJobs;
    }

    public List<ClosedJobsBean> getClosedJobs() {
        return closedJobs;
    }

    public void setClosedJobs(List<ClosedJobsBean> closedJobs) {
        this.closedJobs = closedJobs;
    }

    public static class ActiveJobsBean implements Parcelable{
        /**
         * job_id : 13
         * job_title : Femme de chambre
         * job_description : facultatif
         * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/0c932cbdd1470125530ec39c54709452_image.jpg
         * job_location : Mare Palu, Méautis, France
         * contract_type : CDD
         * education_level : CAP
         * experience : Moins d'1 an
         * lang : Anglais
         * num_of_hours : 10h/semaine
         * salarie : 16 €/heure
         * start_date : 11-7-2017
         * contact_mode : Téléphone:0667215749
         * status : 1
         * added_by : 0
         * user_id : 71
         * createdOn : 2017-07-31 06:20:27
         * updatedOn : 2017-08-18 06:10:37
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
        private String job_title_id;
        private String user_id;
        private String createdOn;
        private String updatedOn;

        public ActiveJobsBean(Parcel in) {
            job_id = in.readString();
            job_title = in.readString();
            job_description = in.readString();
            job_image = in.readString();
            job_location = in.readString();
            contract_type = in.readString();
            education_level = in.readString();
            experience = in.readString();
            lang = in.readString();
            num_of_hours = in.readString();
            salarie = in.readString();
            start_date = in.readString();
            contact_mode = in.readString();
            status = in.readString();
            added_by = in.readString();
            user_id = in.readString();
            createdOn = in.readString();
            updatedOn = in.readString();
            job_title_id = in.readString();
        }

        public static final Creator<ActiveJobsBean> CREATOR = new Creator<ActiveJobsBean>() {
            @Override
            public ActiveJobsBean createFromParcel(Parcel in) {
                return new ActiveJobsBean(in);
            }

            @Override
            public ActiveJobsBean[] newArray(int size) {
                return new ActiveJobsBean[size];
            }
        };

        public String getJob_title_id() {
            return job_title_id;
        }

        public void setJob_title_id(String job_title_id) {
            this.job_title_id = job_title_id;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(job_id);
            dest.writeString(job_title);
            dest.writeString(job_description);
            dest.writeString(job_image);
            dest.writeString(job_location);
            dest.writeString(contract_type);
            dest.writeString(education_level);
            dest.writeString(experience);
            dest.writeString(lang);
            dest.writeString(num_of_hours);
            dest.writeString(salarie);
            dest.writeString(start_date);
            dest.writeString(contact_mode);
            dest.writeString(status);
            dest.writeString(added_by);
            dest.writeString(user_id);
            dest.writeString(createdOn);
            dest.writeString(updatedOn);
            dest.writeString(job_title_id);
        }
    }

    public static class ClosedJobsBean {
        /**
         * job_id : 14
         * job_title : Femme de chambre
         * job_description : facultatif
         * job_image : http://139.162.164.98/bonjob//public/uploads/job_image/29621051adc62369196660a16db32c30_image.png
         * job_location : Mare Palu, Méautis, France
         * contract_type : CDD
         * education_level : CAP
         * experience : Moins d'1 an
         * lang : Anglais
         * num_of_hours : 10h/semaine
         * salarie : 16 €/heure
         * start_date : 11-7-2017
         * contact_mode : Téléphone:0667215749
         * status : 2
         * added_by : 0
         * user_id : 71
         * createdOn : 2017-08-05 06:03:28
         * updatedOn : 2017-08-15 11:57:19
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
        private String job_title_id;
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
