package com.infoicon.bonjob.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by infoicona on 10/7/17.
 */

public class GetSocialLoginResponse implements Parcelable {

    /**
     * success : true
     * error : false
     * data : [{"first_name":"Ashish","last_name":"Sharma","email":"ashish17apyrail@gmail.com","user_id":"147","authKey":"1516256918711335","email_notification":"1","mobile_notification":"1","mobile_number":"","password":"0c6216fad668ce1465aeeee39be6653e","chat_password":"0c6216fad668ce1465aeeee39be6653e","enterprise_name":""}]
     * msg : Connexion réussie
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String prevLogined;
    private String register;
    private List<DataBean> data;

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getPrevLogined() {
        return prevLogined;
    }

    public void setPrevLogined(String prevLogined) {
        this.prevLogined = prevLogined;
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

    public static class DataBean implements Parcelable {
        /**
         * first_name : Ashish
         * last_name : Sharma
         * email : ashish17apyrail@gmail.com
         * user_id : 147
         * authKey : 1516256918711335
         * email_notification : 1
         * mobile_notification : 1
         * mobile_number :
         * password : 0c6216fad668ce1465aeeee39be6653e
         * chat_password : 0c6216fad668ce1465aeeee39be6653e
         * enterprise_name :
         */


        private String full_name;
        private String email;
        private String user_id;
        private String authKey;
        private String email_notification;
        private String mobile_notification;
        private String mobile_number;
        private String password;
        private String chat_password;
        private String enterprise_name;
        private String prevLogined;
        private String seeker_exp_count;
        private String training;
        private String city;
        private String user_pic;
        private String first_name;
        private String last_name;

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

        public String getSeeker_exp_count() {
            return seeker_exp_count;
        }

        public void setSeeker_exp_count(String seeker_exp_count) {
            this.seeker_exp_count = seeker_exp_count;
        }

        public String getTraining() {
            return training;
        }

        public void setTraining(String training) {
            this.training = training;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getPrevLogined() {
            return prevLogined;
        }

        public void setPrevLogined(String prevLogined) {
            this.prevLogined = prevLogined;
        }
      public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAuthKey() {
            return authKey;
        }

        public void setAuthKey(String authKey) {
            this.authKey = authKey;
        }

        public String getEmail_notification() {
            return email_notification;
        }

        public void setEmail_notification(String email_notification) {
            this.email_notification = email_notification;
        }

        public String getMobile_notification() {
            return mobile_notification;
        }

        public void setMobile_notification(String mobile_notification) {
            this.mobile_notification = mobile_notification;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getChat_password() {
            return chat_password;
        }

        public void setChat_password(String chat_password) {
            this.chat_password = chat_password;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.full_name);
            dest.writeString(this.email);
            dest.writeString(this.user_id);
            dest.writeString(this.authKey);
            dest.writeString(this.email_notification);
            dest.writeString(this.mobile_notification);
            dest.writeString(this.mobile_number);
            dest.writeString(this.password);
            dest.writeString(this.chat_password);
            dest.writeString(this.enterprise_name);
            dest.writeString(this.prevLogined);
            dest.writeString(this.seeker_exp_count);
            dest.writeString(this.training);
            dest.writeString(this.city);
            dest.writeString(this.first_name);
            dest.writeString(this.last_name);
            dest.writeString(this.user_pic);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.full_name = in.readString();
            this.email = in.readString();
            this.user_id = in.readString();
            this.authKey = in.readString();
            this.email_notification = in.readString();
            this.mobile_notification = in.readString();
            this.mobile_number = in.readString();
            this.password = in.readString();
            this.chat_password = in.readString();
            this.enterprise_name = in.readString();
            this.prevLogined = in.readString();
            this.seeker_exp_count = in.readString();
            this.training = in.readString();
            this.city = in.readString();
            this.first_name = in.readString();
            this.last_name = in.readString();
            this.user_pic = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
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
        dest.writeString(this.prevLogined);
        dest.writeString(this.register);
        dest.writeList(this.data);
    }

    public GetSocialLoginResponse() {
    }

    protected GetSocialLoginResponse(Parcel in) {
        this.success = in.readByte() != 0;
        this.error = in.readByte() != 0;
        this.msg = in.readString();
        this.prevLogined = in.readString();
        this.register = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetSocialLoginResponse> CREATOR = new Parcelable.Creator<GetSocialLoginResponse>() {
        @Override
        public GetSocialLoginResponse createFromParcel(Parcel source) {
            return new GetSocialLoginResponse(source);
        }

        @Override
        public GetSocialLoginResponse[] newArray(int size) {
            return new GetSocialLoginResponse[size];
        }
    };
}
