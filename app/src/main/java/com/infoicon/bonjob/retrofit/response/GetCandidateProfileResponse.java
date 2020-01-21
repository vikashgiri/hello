package com.infoicon.bonjob.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by infoicona on 20/7/17.
 */

public class GetCandidateProfileResponse {
    /**
     * success : true
     * error : false
     * data : {"first_name":"Pramod1","last_name":"Kumar","city":"Noida Link Road, Mayur Vihar Phase 1, New Delhi, Delhi, Indi","username":"pramod@infoicon.co.in","email":"pramod@infoicon.co.in","patch_video":"http://139.162.164.98/bonjob//public/uploads/user_pic/5cde1d61c53d333bce183e50f331c83d_video.","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/e93cf4f92b0543047390d7b6a96eadd8_image.jpg","training":"android development traing","about":"myselt developer","mobility":null,"current_status":"Active","user_id":"43","experience":[{"experience_id":"7","user_id":"43","position_held":"Team Leader (fast rest)","company_name":"infoic","description":"desccccc","experience":"3","createdOn":"2017-06-30 09:32:49","updatedOn":"2017-06-30 09:32:49"}],"languages":[{"seeker_lang_id":"2","user_id":"43","seeker_lang_name":"English","lang_proficiency":"Current","createdOn":"2017-06-30 12:32:16","updatedOn":"2017-06-30 12:32:16"},{"seeker_lang_id":"3","user_id":"43","seeker_lang_name":"Italian","lang_proficiency":"Intermediate","createdOn":"2017-06-30 12:32:16","updatedOn":"2017-06-30 12:32:16"}]}
     * msg : Data found.
     */

    private boolean success;
    private boolean error;
    private GetSeekerProfileResponse.DataBean data;
    private String msg;

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

    public GetSeekerProfileResponse.DataBean getData() {
        return data;
    }

    public void setData(GetSeekerProfileResponse.DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean implements Parcelable {
        /**
         * first_name : Pramod1
         * last_name : Kumar
         * city : Noida Link Road, Mayur Vihar Phase 1, New Delhi, Delhi, Indi
         * "dob": "0000-00-00"
         * "education_level": "Higher Education / Associates's Degree"
         * username : pramod@infoicon.co.in
         * email : pramod@infoicon.co.in
         * patch_video : http://139.162.164.98/bonjob//public/uploads/user_pic/5cde1d61c53d333bce183e50f331c83d_video.
         * user_pic : http://139.162.164.98/bonjob//public/uploads/user_pic/e93cf4f92b0543047390d7b6a96eadd8_image.jpg
         * training : android development traing
         * about : myselt developer
         * mobility : null
         * current_status : Active
         * user_id : 43
         * experience : [{"experience_id":"7","user_id":"43","position_held":"Team Leader (fast rest)","company_name":"infoic","description":"desccccc","experience":"3","createdOn":"2017-06-30 09:32:49","updatedOn":"2017-06-30 09:32:49"}]
         * languages : [{"seeker_lang_id":"2","user_id":"43","seeker_lang_name":"English","lang_proficiency":"Current","createdOn":"2017-06-30 12:32:16","updatedOn":"2017-06-30 12:32:16"},{"seeker_lang_id":"3","user_id":"43","seeker_lang_name":"Italian","lang_proficiency":"Intermediate","createdOn":"2017-06-30 12:32:16","updatedOn":"2017-06-30 12:32:16"}]
         */

        private String first_name;
        private String last_name;
        private String city;
        private String dob;

        private String education_level;

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skills) {
            this.skills = skills;
        }

        private String skills;
        private String username;
        private String email;
        private String patch_video;
        private String user_pic;
        private String training;
        private String about;
        private String mobility;
        private String current_status;
        private String user_id;
        private List<GetSeekerProfileResponse.DataBean.ExperienceBean> experience;
        private List<GetSeekerProfileResponse.DataBean.LanguagesBean> languages;
        private List<GetSeekerProfileResponse.DataBean.GalleryBean> gallery;


        protected DataBean(Parcel in) {
            first_name = in.readString();
            last_name = in.readString();
            city = in.readString();
            dob = in.readString();
            education_level = in.readString();
            skills = in.readString();
            username = in.readString();
            email = in.readString();
            patch_video = in.readString();
            user_pic = in.readString();
            training = in.readString();
            about = in.readString();
            mobility = in.readString();
            current_status = in.readString();
            user_id = in.readString();
        }

        public static final Creator<GetSeekerProfileResponse.DataBean> CREATOR = new Creator<GetSeekerProfileResponse.DataBean>() {
            @Override
            public GetSeekerProfileResponse.DataBean createFromParcel(Parcel in) {
                return new GetSeekerProfileResponse.DataBean(in);
            }

            @Override
            public GetSeekerProfileResponse.DataBean[] newArray(int size) {
                return new GetSeekerProfileResponse.DataBean[size];
            }
        };

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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }


        public String getEducation_level() {
            return education_level;
        }

        public void setEducation_level(String education_level) {
            this.education_level = education_level;
        }


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPatch_video() {
            return patch_video;
        }

        public void setPatch_video(String patch_video) {
            this.patch_video = patch_video;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getTraining() {
            return training;
        }

        public void setTraining(String training) {
            this.training = training;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<GetSeekerProfileResponse.DataBean.ExperienceBean> getExperience() {
            return experience;
        }

        public void setExperience(List<GetSeekerProfileResponse.DataBean.ExperienceBean> experience) {
            this.experience = experience;
        }

        public List<GetSeekerProfileResponse.DataBean.LanguagesBean> getLanguages() {
            return languages;
        }

        public void setLanguages(List<GetSeekerProfileResponse.DataBean.LanguagesBean> languages) {
            this.languages = languages;
        }

        public List<GetSeekerProfileResponse.DataBean.GalleryBean> getGallery() {
            return gallery;
        }

        public void setGallery(List<GetSeekerProfileResponse.DataBean.GalleryBean> gallery) {
            this.gallery = gallery;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(first_name);
            dest.writeString(last_name);
            dest.writeString(city);
            dest.writeString(dob);
            dest.writeString(education_level);
            dest.writeString(skills);
            dest.writeString(username);
            dest.writeString(email);
            dest.writeString(patch_video);
            dest.writeString(user_pic);
            dest.writeString(training);
            dest.writeString(about);
            dest.writeString(mobility);
            dest.writeString(current_status);
            dest.writeString(user_id);
        }


        public static class ExperienceBean implements Parcelable {
            /**
             * experience_id : 7
             * user_id : 43
             * position_held : Team Leader (fast rest)
             * company_name : infoic
             * description : desccccc
             * experience : 3
             * createdOn : 2017-06-30 09:32:49
             * updatedOn : 2017-06-30 09:32:49
             */

            private String experience_id;
            private String user_id;
            private String position_held;
            private String company_name;
            private String description;
            private String experience;
            private String createdOn;
            private String updatedOn;


            protected ExperienceBean(Parcel in) {
                experience_id = in.readString();
                user_id = in.readString();
                position_held = in.readString();
                company_name = in.readString();
                description = in.readString();
                experience = in.readString();
                createdOn = in.readString();
                updatedOn = in.readString();
            }

            public static final Creator<GetSeekerProfileResponse.DataBean.ExperienceBean> CREATOR = new Creator<GetSeekerProfileResponse.DataBean.ExperienceBean>() {
                @Override
                public GetSeekerProfileResponse.DataBean.ExperienceBean createFromParcel(Parcel in) {
                    return new GetSeekerProfileResponse.DataBean.ExperienceBean(in);
                }

                @Override
                public GetSeekerProfileResponse.DataBean.ExperienceBean[] newArray(int size) {
                    return new GetSeekerProfileResponse.DataBean.ExperienceBean[size];
                }
            };

            public String getExperience_id() {
                return experience_id;
            }

            public void setExperience_id(String experience_id) {
                this.experience_id = experience_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getPosition_held() {
                return position_held;
            }

            public void setPosition_held(String position_held) {
                this.position_held = position_held;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
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
                dest.writeString(experience_id);
                dest.writeString(user_id);
                dest.writeString(position_held);
                dest.writeString(company_name);
                dest.writeString(description);
                dest.writeString(experience);
                dest.writeString(createdOn);
                dest.writeString(updatedOn);
            }
        }

        public static class LanguagesBean implements Parcelable {
            /**
             * seeker_lang_id : 2
             * user_id : 43
             * seeker_lang_name : English
             * lang_proficiency : Current
             * createdOn : 2017-06-30 12:32:16
             * updatedOn : 2017-06-30 12:32:16
             */

            private String seeker_lang_id;
            private String user_id;
            private String seeker_lang_name;
            private String lang_proficiency;
            private String createdOn;
            private String updatedOn;


            protected LanguagesBean(Parcel in) {
                seeker_lang_id = in.readString();
                user_id = in.readString();
                seeker_lang_name = in.readString();
                lang_proficiency = in.readString();
                createdOn = in.readString();
                updatedOn = in.readString();
            }

            public static final Creator<GetSeekerProfileResponse.DataBean.LanguagesBean> CREATOR = new Creator<GetSeekerProfileResponse.DataBean.LanguagesBean>() {
                @Override
                public GetSeekerProfileResponse.DataBean.LanguagesBean createFromParcel(Parcel in) {
                    return new GetSeekerProfileResponse.DataBean.LanguagesBean(in);
                }

                @Override
                public GetSeekerProfileResponse.DataBean.LanguagesBean[] newArray(int size) {
                    return new GetSeekerProfileResponse.DataBean.LanguagesBean[size];
                }
            };

            public String getSeeker_lang_id() {
                return seeker_lang_id;
            }

            public void setSeeker_lang_id(String seeker_lang_id) {
                this.seeker_lang_id = seeker_lang_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
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
                dest.writeString(seeker_lang_id);
                dest.writeString(user_id);
                dest.writeString(seeker_lang_name);
                dest.writeString(lang_proficiency);
                dest.writeString(createdOn);
                dest.writeString(updatedOn);
            }
        }

        public static class GalleryBean implements Parcelable {
            /**
             * gallery_id : 3
             * user_id : 43
             * image : http://139.162.164.98/bonjob/public/uploads/seeker_gallery/2b3eb8a8f6463a1f0bc69fcd9841bc0c_image.jpg
             * description :
             * createdOn : 2017-07-04 12:00:33
             * updatedOn : 2017-07-04 12:00:33
             */

            private String gallery_id;
            private String user_id;
            private String image;
            private String description;
            private String createdOn;
            private String updatedOn;

            public GalleryBean(Parcel in) {
                gallery_id = in.readString();
                user_id = in.readString();
                image = in.readString();
                description = in.readString();
                createdOn = in.readString();
                updatedOn = in.readString();
            }

            public static final Creator<GetSeekerProfileResponse.DataBean.GalleryBean> CREATOR = new Creator<GetSeekerProfileResponse.DataBean.GalleryBean>() {
                @Override
                public GetSeekerProfileResponse.DataBean.GalleryBean createFromParcel(Parcel in) {
                    return new GetSeekerProfileResponse.DataBean.GalleryBean(in);
                }

                @Override
                public GetSeekerProfileResponse.DataBean.GalleryBean[] newArray(int size) {
                    return new GetSeekerProfileResponse.DataBean.GalleryBean[size];
                }
            };

            public String getGallery_id() {
                return gallery_id;
            }

            public void setGallery_id(String gallery_id) {
                this.gallery_id = gallery_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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
                dest.writeString(gallery_id);
                dest.writeString(user_id);
                dest.writeString(image);
                dest.writeString(description);
                dest.writeString(createdOn);
                dest.writeString(updatedOn);
            }
        }
    }
}
