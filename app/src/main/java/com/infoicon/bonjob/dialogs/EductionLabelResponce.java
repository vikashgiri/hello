package com.infoicon.bonjob.dialogs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by info on 6/10/18.
 */

public class EductionLabelResponce implements Parcelable {

    /**
     * success : true
     * error : false
     * educationLevels : [{"education_content_id":"1","education_id":"1","education_title":"No diploma","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:44"},{"education_content_id":"3","education_id":"2","education_title":"Youth training","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:16:58"},{"education_content_id":"5","education_id":"3","education_title":"Vocational training","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:12"},{"education_content_id":"7","education_id":"4","education_title":"BP","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:21"},{"education_content_id":"9","education_id":"5","education_title":"High-School","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:17:37"},{"education_content_id":"11","education_id":"6","education_title":"High-School (professional training)","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:01"},{"education_content_id":"13","education_id":"7","education_title":"Higher Diploma / 12th Grade","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:16"},{"education_content_id":"15","education_id":"8","education_title":"Higher Education / Associates's Degree","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:33"},{"education_content_id":"17","education_id":"9","education_title":"Bachelor","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:18:45"},{"education_content_id":"19","education_id":"10","education_title":"Master and above","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:06"},{"education_content_id":"21","education_id":"11","education_title":"Other","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:19:22"}]
     * msg : Data found
     */

    private boolean success;
    private boolean error;
    private String msg;
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

    public List<EducationLevelsBean> getEducationLevels() {
        return educationLevels;
    }

    public void setEducationLevels(List<EducationLevelsBean> educationLevels) {
        this.educationLevels = educationLevels;
    }

    public static class EducationLevelsBean implements Parcelable {

        /**
         * education_content_id : 1
         * education_id : 1
         * education_title : No diploma
         * language_id : 1
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.education_content_id);
            dest.writeString(this.education_id);
            dest.writeString(this.education_title);
            dest.writeString(this.language_id);
            dest.writeString(this.status);
            dest.writeString(this.addedBy);
            dest.writeString(this.updatedOn);
        }

        public EducationLevelsBean() {
        }

        protected EducationLevelsBean(Parcel in) {
            this.education_content_id = in.readString();
            this.education_id = in.readString();
            this.education_title = in.readString();
            this.language_id = in.readString();
            this.status = in.readString();
            this.addedBy = in.readString();
            this.updatedOn = in.readString();
        }

        public static final Creator<EducationLevelsBean> CREATOR = new Creator<EducationLevelsBean>() {
            @Override
            public EducationLevelsBean createFromParcel(Parcel source) {
                return new EducationLevelsBean(source);
            }

            @Override
            public EducationLevelsBean[] newArray(int size) {
                return new EducationLevelsBean[size];
            }
        };
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
        dest.writeList(this.educationLevels);
    }

    public EductionLabelResponce() {
    }

    protected EductionLabelResponce(Parcel in) {
        this.success = in.readByte() != 0;
        this.error = in.readByte() != 0;
        this.msg = in.readString();
        this.educationLevels = new ArrayList<EducationLevelsBean>();
        in.readList(this.educationLevels, EducationLevelsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<EductionLabelResponce> CREATOR = new Parcelable.Creator<EductionLabelResponce>() {
        @Override
        public EductionLabelResponce createFromParcel(Parcel source) {
            return new EductionLabelResponce(source);
        }

        @Override
        public EductionLabelResponce[] newArray(int size) {
            return new EductionLabelResponce[size];
        }
    };
}
