package com.infoicon.bonjob.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by infoicona on 20/7/17.
 */

public class GetSelectedCandidateResponse {

    /**
     * success : true
     * error : false
     * selectedData : [{"aplied_id":"12","user_id":"12","job_id":"17","employer_id":"13","status":"1","createdOn":"2018-01-10 11:55:35","updatedOn":"2018-01-10 11:55:46","first_name":"Bonjob","last_name":"Seeker","user_pic":"https://bonjob.co//public/uploads/user_pic/e79fe4d4b59ecceda45d0b9172ca0dc3_image.png","job_title":"Attaché(e) commercial(e)","current_status":"Étudiant","experience":"0","selected_id":"","job_image":"https://bonjob.co/public/uploads/job_image/f30722c49e0376c191298386adf5f0a9_image.jpg","job_description":"new opening"}]
     * notSelectedData : [{"aplied_id":"12","user_id":"12","job_id":"17","employer_id":"13","status":"2","createdOn":"2018-01-10 11:55:35","updatedOn":"2018-01-29 07:20:16","first_name":"Bonjob","last_name":"Seeker","user_pic":"https://bonjob.co//public/uploads/user_pic/e79fe4d4b59ecceda45d0b9172ca0dc3_image.png","job_title":"Attaché(e) commercial(e)","current_status":"Étudiant","experience":"0","selected_id":"","job_image":"https://bonjob.co/public/uploads/job_image/f30722c49e0376c191298386adf5f0a9_image.jpg","job_description":"new opening"}]
     * msg : Données trouvées
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<SelectedDataBean> selectedData;
    private List<NotSelectedDataBean> notSelectedData;

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

    public List<SelectedDataBean> getSelectedData() {
        return selectedData;
    }

    public void setSelectedData(List<SelectedDataBean> selectedData) {
        this.selectedData = selectedData;
    }

    public List<NotSelectedDataBean> getNotSelectedData() {
        return notSelectedData;
    }

    public void setNotSelectedData(List<NotSelectedDataBean> notSelectedData) {
        this.notSelectedData = notSelectedData;
    }

    public static class SelectedDataBean {
        /**
         * aplied_id : 12
         * user_id : 12
         * job_id : 17
         * employer_id : 13
         * status : 1
         * createdOn : 2018-01-10 11:55:35
         * updatedOn : 2018-01-10 11:55:46
         * first_name : Bonjob
         * last_name : Seeker
         * user_pic : https://bonjob.co//public/uploads/user_pic/e79fe4d4b59ecceda45d0b9172ca0dc3_image.png
         * job_title : Attaché(e) commercial(e)
         * current_status : Étudiant
         * experience : 0
         * selected_id :
         * job_image : https://bonjob.co/public/uploads/job_image/f30722c49e0376c191298386adf5f0a9_image.jpg
         * job_description : new opening
         */
        private String aplied_id;
        private String user_id;
        private String job_id;
        private String employer_id;
        private String status;
        private String createdOn;
        private String updatedOn;
        private String first_name;
        private String last_name;
        private String user_pic;
        private String job_title;
        private String current_status;
        private String current_status_name;
        private String experience;
        private String selected_id;
        private String job_image;
        private String job_description;

        public String getCurrent_status_name() {
            return current_status_name;
        }

        public void setCurrent_status_name(String current_status_name) {
            this.current_status_name = current_status_name;
        }

        public String getAplied_id() {
            return aplied_id;
        }

        public void setAplied_id(String aplied_id) {
            this.aplied_id = aplied_id;
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

        public String getEmployer_id() {
            return employer_id;
        }

        public void setEmployer_id(String employer_id) {
            this.employer_id = employer_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getCurrent_status() {
            return current_status;
        }

        public void setCurrent_status(String current_status) {
            this.current_status = current_status;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getSelected_id() {
            return selected_id;
        }

        public void setSelected_id(String selected_id) {
            this.selected_id = selected_id;
        }

        public String getJob_image() {
            return job_image;
        }

        public void setJob_image(String job_image) {
            this.job_image = job_image;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
        }
    }

    public static class NotSelectedDataBean implements Parcelable{
        /**
         * aplied_id : 12
         * user_id : 12
         * job_id : 17
         * employer_id : 13
         * status : 2
         * createdOn : 2018-01-10 11:55:35
         * updatedOn : 2018-01-29 07:20:16
         * first_name : Bonjob
         * last_name : Seeker
         * user_pic : https://bonjob.co//public/uploads/user_pic/e79fe4d4b59ecceda45d0b9172ca0dc3_image.png
         * job_title : Attaché(e) commercial(e)
         * current_status : Étudiant
         * experience : 0
         * selected_id :
         * job_image : https://bonjob.co/public/uploads/job_image/f30722c49e0376c191298386adf5f0a9_image.jpg
         * job_description : new opening
         */

        private String aplied_id;
        private String user_id;
        private String job_id;
        private String employer_id;
        private String status;
        private String createdOn;
        private String updatedOn;
        private String first_name;
        private String last_name;
        private String user_pic;
        private String job_title;
        private String current_status;
        private String current_status_name;

        private String experience;
        private String selected_id;
        private String job_image;
        private String job_description;

        public String getCurrent_status_name() {
            return current_status_name;
        }

        public void setCurrent_status_name(String current_status_name) {
            this.current_status_name = current_status_name;
        }

        public NotSelectedDataBean(Parcel in) {
            aplied_id = in.readString();
            user_id = in.readString();
            job_id = in.readString();
            employer_id = in.readString();
            status = in.readString();
            createdOn = in.readString();
            updatedOn = in.readString();
            first_name = in.readString();
            last_name = in.readString();
            user_pic = in.readString();
            job_title = in.readString();
            current_status = in.readString();
            current_status_name = in.readString();
            experience = in.readString();
            selected_id = in.readString();
            job_image = in.readString();
            job_description = in.readString();
        }

        public static final Creator<NotSelectedDataBean> CREATOR = new Creator<NotSelectedDataBean>() {
            @Override
            public NotSelectedDataBean createFromParcel(Parcel in) {
                return new NotSelectedDataBean(in);
            }

            @Override
            public NotSelectedDataBean[] newArray(int size) {
                return new NotSelectedDataBean[size];
            }
        };

        public String getAplied_id() {
            return aplied_id;
        }

        public void setAplied_id(String aplied_id) {
            this.aplied_id = aplied_id;
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

        public String getEmployer_id() {
            return employer_id;
        }

        public void setEmployer_id(String employer_id) {
            this.employer_id = employer_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getCurrent_status() {
            return current_status;
        }

        public void setCurrent_status(String current_status) {
            this.current_status = current_status;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getSelected_id() {
            return selected_id;
        }

        public void setSelected_id(String selected_id) {
            this.selected_id = selected_id;
        }

        public String getJob_image() {
            return job_image;
        }

        public void setJob_image(String job_image) {
            this.job_image = job_image;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(aplied_id);
            dest.writeString(user_id);
            dest.writeString(job_id);
            dest.writeString(employer_id);
            dest.writeString(status);
            dest.writeString(createdOn);
            dest.writeString(updatedOn);
            dest.writeString(first_name);
            dest.writeString(last_name);
            dest.writeString(user_pic);
            dest.writeString(job_title);
            dest.writeString(current_status);
            dest.writeString(current_status_name);
            dest.writeString(experience);
            dest.writeString(selected_id);
            dest.writeString(job_image);
            dest.writeString(job_description);
        }
    }
}
