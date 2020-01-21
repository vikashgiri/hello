package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by info on 22/3/18.
 */

public class GetAdvertisementResponce {

    /**
     * success : true
     * error : false
     * data : [{"app_advertisement_id":"1","title":"Google","advertisement_image":"http://172.104.8.51/bonjob/public/uploads/advertisements/391a8ee6ba0dbef717d8e2dacc0af7ee.png","url":"http://google.com","status":"1","createdOn":"2018-03-21 10:20:51","updatedOn":"2018-03-21 09:20:51"},{"app_advertisement_id":"2","title":"Google","advertisement_image":"http://172.104.8.51/bonjob/public/uploads/advertisements/754944db406373197948a2414782932c.png","url":"http://www.google.com","status":"1","createdOn":"2018-03-21 10:38:11","updatedOn":"2018-03-21 09:38:11"},{"app_advertisement_id":"3","title":"Google","advertisement_image":"http://172.104.8.51/bonjob/public/uploads/advertisements/391a8ee6ba0dbef717d8e2dacc0af7ee.png","url":"http://google.com","status":"1","createdOn":"2018-03-21 10:20:51","updatedOn":"2018-03-21 09:20:51"},{"app_advertisement_id":"4","title":"Google","advertisement_image":"http://172.104.8.51/bonjob/public/uploads/advertisements/754944db406373197948a2414782932c.png","url":"http://www.google.com","status":"1","createdOn":"2018-03-21 10:38:11","updatedOn":"2018-03-21 09:38:11"},{"app_advertisement_id":"5","title":"Google","advertisement_image":"http://172.104.8.51/bonjob/public/uploads/advertisements/754944db406373197948a2414782932c.png","url":"http://www.google.com","status":"1","createdOn":"2018-03-21 10:38:11","updatedOn":"2018-03-21 09:38:11"},{"app_advertisement_id":"6","title":"Google","advertisement_image":"http://172.104.8.51/bonjob/public/uploads/advertisements/391a8ee6ba0dbef717d8e2dacc0af7ee.png","url":"http://google.com","status":"1","createdOn":"2018-03-21 10:20:51","updatedOn":"2018-03-21 09:20:51"},{"app_advertisement_id":"8","title":"Yahoo","advertisement_image":"http://172.104.8.51/bonjob/public/uploads/advertisements/5d4da1b76947c591d5fb449957a3cf8f.png","url":"http://www.yahoo.com","status":"1","createdOn":"2018-03-21 12:09:15","updatedOn":"2018-03-21 11:09:15"}]
     * msg : Data found
     * active_user : 0
     */

    private boolean success;
    private boolean error;
    private String msg;
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

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * app_advertisement_id : 1
         * title : Google
         * advertisement_image : http://172.104.8.51/bonjob/public/uploads/advertisements/391a8ee6ba0dbef717d8e2dacc0af7ee.png
         * url : http://google.com
         * status : 1
         * createdOn : 2018-03-21 10:20:51
         * updatedOn : 2018-03-21 09:20:51
         */

        private String app_advertisement_id;
        private String title;
        private String advertisement_image;
        private String url;
        private String status;
        private String createdOn;
        private String updatedOn;
        private String advertisement_file;
        private String advertisement_type;
        private String     file_type;

        public String getAdvertisement_file() {
            return advertisement_file;
        }

        public void setAdvertisement_file(String advertisement_file) {
            this.advertisement_file = advertisement_file;
        }

        public String getAdvertisement_type() {
            return advertisement_type;
        }

        public void setAdvertisement_type(String advertisement_type) {
            this.advertisement_type = advertisement_type;
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getApp_advertisement_id() {
            return app_advertisement_id;
        }

        public void setApp_advertisement_id(String app_advertisement_id) {
            this.app_advertisement_id = app_advertisement_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdvertisement_image() {
            return advertisement_image;
        }

        public void setAdvertisement_image(String advertisement_image) {
            this.advertisement_image = advertisement_image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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
    }
}
