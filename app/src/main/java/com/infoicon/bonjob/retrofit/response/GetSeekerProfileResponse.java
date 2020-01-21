package com.infoicon.bonjob.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by infoicona on 29/6/17.
 */

public class GetSeekerProfileResponse {

    /**
     * success : true
     * error : false
     * data : {"first_name":"Pramod1","last_name":"Kumar","city":"Noida Link Road, Mayur Vihar Phase 1, New Delhi, Delhi, Indi","username":"pramod@infoicon.co.in","email":"pramod@infoicon.co.in","patch_video":"http://139.162.164.98/bonjob//public/uploads/user_pic/5cde1d61c53d333bce183e50f331c83d_video.","user_pic":"http://139.162.164.98/bonjob//public/uploads/user_pic/e93cf4f92b0543047390d7b6a96eadd8_image.jpg","training":"android development traing","about":"myselt developer","mobility":null,"current_status":"Active","user_id":"43","experience":[{"experience_id":"7","user_id":"43","position_held":"Team Leader (fast rest)","company_name":"infoic","description":"desccccc","experience":"3","createdOn":"2017-06-30 09:32:49","updatedOn":"2017-06-30 09:32:49"}],"languages":[{"seeker_lang_id":"2","user_id":"43","seeker_lang_name":"English","lang_proficiency":"Current","createdOn":"2017-06-30 12:32:16","updatedOn":"2017-06-30 12:32:16"},{"seeker_lang_id":"3","user_id":"43","seeker_lang_name":"Italian","lang_proficiency":"Intermediate","createdOn":"2017-06-30 12:32:16","updatedOn":"2017-06-30 12:32:16"}]}
     * msg : Data found.
     */

    private boolean success;
    private boolean error;
    private DataBean data;
    private String msg;
    private String active_user;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
        private String candidate_seek_id;
        private String candidate_seek_name;


        private List<SkillsBean> userSkills;
        private String  skills_name;
        private String mobility_name;

        private String username;
        private String email;
        private String patch_video;
        private String user_pic;
        private String training;
        private String about;
        private String mobility;
        private String current_status;
        private String user_id;
        private String lattitude;
        private String longitude;
        private String education_level_name;
        private String  current_status_name;
        private String patch_video_thumbnail;
        private List<ExperienceBean> experience;
        private List<LanguagesBean> languages;
        private List<GalleryBean> gallery;


        protected DataBean(Parcel in) {
            first_name = in.readString();
            last_name = in.readString();
            city = in.readString();
            dob = in.readString();
            education_level = in.readString();
            candidate_seek_id = in.readString();
            candidate_seek_name = in.readString();
            //skills = in.readString();
            skills_name= in.readString();
            mobility_name = in.readString();
            current_status_name = in.readString();
            username = in.readString();
            email = in.readString();
            patch_video = in.readString();
            user_pic = in.readString();
            training = in.readString();
            about = in.readString();
            mobility = in.readString();
            current_status = in.readString();
            user_id = in.readString();
            lattitude = in.readString();
            longitude = in.readString();
            education_level_name=in.readString();
            patch_video_thumbnail = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
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

        public List<SkillsBean> getSkills() {
            return userSkills;
        }

        public void setSkills(List<SkillsBean> skills) {
            this.userSkills = skills;
        }

        public String getSkills_name() {
            return skills_name;
        }

        public void setSkills_name(String skills_name) {
            this.skills_name = skills_name;
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

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getCandidate_seek_id() {
            return candidate_seek_id;
        }

        public void setCandidate_seek_id(String candidate_seek_id) {
            this.candidate_seek_id = candidate_seek_id;
        }

        public String getCandidate_seek_name() {
            return candidate_seek_name;
        }

        public void setCandidate_seek_name(String candidate_seek_name) {
            this.candidate_seek_name = candidate_seek_name;
        }

        public String getEducation_level() {
            return education_level;
        }

        public void setEducation_level(String education_level) {
            this.education_level = education_level;
        }

        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }

        public String getEducation_level_name() {
            return education_level_name;
        }

        public void setEducation_level_name(String education_level_name) {
            this.education_level_name = education_level_name;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
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

        public String getPatch_video_thumbnail() {
            return patch_video_thumbnail;
        }

        public void setPatch_video_thumbnail(String patch_video_thumbnail) {
            this.patch_video_thumbnail = patch_video_thumbnail;
        }


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<ExperienceBean> getExperience() {
            return experience;
        }

        public void setExperience(List<ExperienceBean> experience) {
            this.experience = experience;
        }

        public List<LanguagesBean> getLanguages() {
            return languages;
        }

        public void setLanguages(List<LanguagesBean> languages) {
            this.languages = languages;
        }

        public List<GalleryBean> getGallery() {
            return gallery;
        }

        public void setGallery(List<GalleryBean> gallery) {
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
            dest.writeString(candidate_seek_id);
            dest.writeString(candidate_seek_name);
          //  dest.writeString(skills);
            dest.writeString(skills_name);
            dest.writeString(mobility_name);
            dest.writeString(current_status_name);
            dest.writeString(username);
            dest.writeString(email);
            dest.writeString(patch_video);
            dest.writeString(user_pic);
            dest.writeString(training);
            dest.writeString(about);
            dest.writeString(mobility);
            dest.writeString(current_status);
            dest.writeString(user_id);
            dest.writeString(lattitude);
            dest.writeString(longitude);
            dest.writeString(education_level_name);
            dest.writeString(patch_video_thumbnail);
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
            private String industry_type;
            private String industry_type_name;
            private String company_name;
            private String description;
            private String experience;
            private String createdOn;
            private String updatedOn;
            private String position_held_name;
public ExperienceBean()
{

}
            protected ExperienceBean(Parcel in) {
                experience_id = in.readString();
                user_id = in.readString();
                position_held = in.readString();
                industry_type = in.readString();
                industry_type_name = in.readString();
                company_name = in.readString();
                description = in.readString();
                experience = in.readString();
                createdOn = in.readString();
                updatedOn = in.readString();
                position_held_name=in.readString();
            }

            public static final Creator<ExperienceBean> CREATOR = new Creator<ExperienceBean>() {
                @Override
                public ExperienceBean createFromParcel(Parcel in) {
                    return new ExperienceBean(in);
                }

                @Override
                public ExperienceBean[] newArray(int size) {
                    return new ExperienceBean[size];
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

            public String getPosition_held_name() {
                return position_held_name;
            }

            public void setPosition_held_name(String position_held_name) {
                this.position_held_name = position_held_name;
            }

            public void setPosition_held(String position_held) {
                this.position_held = position_held;
            }

            public String getIndustry_type_name() {
                return industry_type_name;
            }

            public void setIndustry_type_name(String industry_type_name) {
                this.industry_type_name = industry_type_name;
            }

            public String getIndustry_type() {
                return industry_type;
            }

            public void setIndustry_type(String industry_type) {
                this.industry_type = industry_type;
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
                dest.writeString(industry_type);
                dest.writeString(industry_type_name);
                dest.writeString(company_name);
                dest.writeString(description);
                dest.writeString(experience);
                dest.writeString(createdOn);
                dest.writeString(updatedOn);
                dest.writeString(position_held_name);
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
            private String lang_proficiency_name;
            private String       seeker_lang;

            public String getLang_proficiency_name() {
                return lang_proficiency_name;
            }

            public void setLang_proficiency_name(String lang_proficiency_name) {
                this.lang_proficiency_name = lang_proficiency_name;
            }

            public String getSeeker_lang() {
                return seeker_lang;
            }

            public void setSeeker_lang(String seeker_lang) {
                this.seeker_lang = seeker_lang;
            }

            protected LanguagesBean(Parcel in) {
                seeker_lang_id = in.readString();
                user_id = in.readString();
                seeker_lang_name = in.readString();
                lang_proficiency = in.readString();
                createdOn = in.readString();
                updatedOn = in.readString();
                lang_proficiency_name = in.readString();
                seeker_lang = in.readString();
            }

            public static final Creator<LanguagesBean> CREATOR = new Creator<LanguagesBean>() {
                @Override
                public LanguagesBean createFromParcel(Parcel in) {
                    return new LanguagesBean(in);
                }

                @Override
                public LanguagesBean[] newArray(int size) {
                    return new LanguagesBean[size];
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
                dest.writeString(seeker_lang);
                dest.writeString(lang_proficiency_name);
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

            public static final Creator<GalleryBean> CREATOR = new Creator<GalleryBean>() {
                @Override
                public GalleryBean createFromParcel(Parcel in) {
                    return new GalleryBean(in);
                }

                @Override
                public GalleryBean[] newArray(int size) {
                    return new GalleryBean[size];
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

        public static class SkillsBean implements Parcelable {
            /**
             * seeker_lang_id : 2
             * user_id : 43
             * seeker_lang_name : English
             * lang_proficiency : Current
             * createdOn : 2017-06-30 12:32:16
             * updatedOn : 2017-06-30 12:32:16
             */

            private String skills;
            private String skills_name;

            public String getSkills() {
                return skills;
            }

            public void setSkills(String skills) {
                this.skills = skills;
            }

            public String getSkills_name() {
                return skills_name;
            }

            public void setSkills_name(String skills_name) {
                this.skills_name = skills_name;
            }

            protected SkillsBean(Parcel in) {
                skills = in.readString();
                skills_name = in.readString();

            }

            public static final Creator<SkillsBean> CREATOR = new Creator<SkillsBean>() {
                @Override
                public SkillsBean createFromParcel(Parcel in) {
                    return new SkillsBean(in);
                }

                @Override
                public SkillsBean[] newArray(int size) {
                    return new SkillsBean[size];
                }
            };




            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(skills_name);
                dest.writeString(skills_name);
            }
        }

    }
}
