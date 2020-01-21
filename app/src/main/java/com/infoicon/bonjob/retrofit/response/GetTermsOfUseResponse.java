package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 21/8/17.
 */

public class GetTermsOfUseResponse {


    /**
     * success : true
     * error : false
     * data : [{"page_content_id":"3","page_id":"2","page_title":"Terms of Use","page_content":"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.","meta_title":null,"meta_keywords":null,"meta_description":null,"language_id":"1","createdOn":"2017-05-16 19:54:00","updatedOn":"2017-08-21 10:30:16"}]
     * msg : Data Found
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
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * page_content_id : 3
         * page_id : 2
         * page_title : Terms of Use
         * page_content : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
         * meta_title : null
         * meta_keywords : null
         * meta_description : null
         * language_id : 1
         * createdOn : 2017-05-16 19:54:00
         * updatedOn : 2017-08-21 10:30:16
         */

        private String page_content_id;
        private String page_id;
        private String page_title;
        private String page_content;
        private Object meta_title;
        private Object meta_keywords;
        private Object meta_description;
        private String language_id;
        private String createdOn;
        private String updatedOn;

        public String getPage_content_id() {
            return page_content_id;
        }

        public void setPage_content_id(String page_content_id) {
            this.page_content_id = page_content_id;
        }

        public String getPage_id() {
            return page_id;
        }

        public void setPage_id(String page_id) {
            this.page_id = page_id;
        }

        public String getPage_title() {
            return page_title;
        }

        public void setPage_title(String page_title) {
            this.page_title = page_title;
        }

        public String getPage_content() {
            return page_content;
        }

        public void setPage_content(String page_content) {
            this.page_content = page_content;
        }

        public Object getMeta_title() {
            return meta_title;
        }

        public void setMeta_title(Object meta_title) {
            this.meta_title = meta_title;
        }

        public Object getMeta_keywords() {
            return meta_keywords;
        }

        public void setMeta_keywords(Object meta_keywords) {
            this.meta_keywords = meta_keywords;
        }

        public Object getMeta_description() {
            return meta_description;
        }

        public void setMeta_description(Object meta_description) {
            this.meta_description = meta_description;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
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
