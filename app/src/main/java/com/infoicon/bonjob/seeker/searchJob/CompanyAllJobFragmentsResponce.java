package com.infoicon.bonjob.seeker.searchJob;

import java.util.List;

public class CompanyAllJobFragmentsResponce {

    /**
     * success : true
     * error : false
     * jobs : [{"job_id":"4583","job_title":"Cuisinier dans la fonction publique","createdOn":"2019-05-07 13:28:05","job_image":""},{"job_id":"4576","job_title":"Agent de réservation","createdOn":"2019-04-29 14:23:58","job_image":"http://172.104.8.51/bonjob/public/uploads/job_image//68b9dced903df6b8fac9e4675d2c7ebf_image.png"},{"job_id":"4575","job_title":"Agent de réservation","createdOn":"2019-04-29 14:21:25","job_image":"http://172.104.8.51/bonjob/public/uploads/job_image//1d5209f7a3c714492aa02682e2953d7e_image.png"},{"job_id":"4574","job_title":"Boulanger","createdOn":"2019-04-29 14:20:24","job_image":"http://172.104.8.51/bonjob/public/uploads/job_image//6cb52dfb58c10c49949b36fe7fcbf3df_image.png"},{"job_id":"4540","job_title":"testing","createdOn":"2019-03-13 13:54:44","job_image":"http://172.104.8.51/bonjob/public/uploads/job_image//5cd62b7025581bd0d8b40442ebb168ba_image.jpeg"},{"job_id":"4539","job_title":"testing","createdOn":"2019-03-13 13:54:04","job_image":""},{"job_id":"4538","job_title":"testing 3","createdOn":"2019-03-13 08:21:43","job_image":""},{"job_id":"4537","job_title":"testing2","createdOn":"2019-03-13 08:20:31","job_image":""},{"job_id":"4536","job_title":"Testing 1","createdOn":"2019-03-13 08:07:49","job_image":""},{"job_id":"4535","job_title":"Testing","createdOn":"2019-03-13 08:06:53","job_image":""},{"job_id":"4534","job_title":"test","createdOn":"2019-03-12 12:19:02","job_image":""},{"job_id":"4533","job_title":"Hmare Job","createdOn":"2019-03-12 10:22:48","job_image":""},{"job_id":"2536","job_title":"Boulanger","createdOn":"2018-07-27 13:43:54","job_image":""},{"job_id":"2478","job_title":"commis","createdOn":"2018-07-23 08:18:10","job_image":""},{"job_id":"1984","job_title":"Area Director","createdOn":"2018-06-25 10:50:53","job_image":""}]
     * msg : Data found
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<JobsBean> jobs;

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

    public List<JobsBean> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobsBean> jobs) {
        this.jobs = jobs;
    }

    public static class JobsBean {
        /**
         * job_id : 4583
         * job_title : Cuisinier dans la fonction publique
         * createdOn : 2019-05-07 13:28:05
         * job_image :
         */

        private String job_id;
        private String job_title;
        private String createdOn;
        private String job_image;

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

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getJob_image() {
            return job_image;
        }

        public void setJob_image(String job_image) {
            this.job_image = job_image;
        }
    }
}
