package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 7/11/17.
 */

public class GetAcceptJobResponse {


    /**
     * success : true
     * error : false
     * data : []
     * seekerInfo : {"id":"3839","first_name":"Dddddd","last_name":"Xxxxx","email":"ses@ses.com"}
     * employerInfo : {"id":"3840","first_name":"Zx","last_name":"Xx","email":"rec@rec.com"}
     * msg : You have accepted this request.
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private SeekerInfoBean seekerInfo;
    private EmployerInfoBean employerInfo;
    private String msg;
    private String active_user;
    private List<?> data;

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

    public SeekerInfoBean getSeekerInfo() {
        return seekerInfo;
    }

    public void setSeekerInfo(SeekerInfoBean seekerInfo) {
        this.seekerInfo = seekerInfo;
    }

    public EmployerInfoBean getEmployerInfo() {
        return employerInfo;
    }

    public void setEmployerInfo(EmployerInfoBean employerInfo) {
        this.employerInfo = employerInfo;
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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class SeekerInfoBean {
        /**
         * id : 3839
         * first_name : Dddddd
         * last_name : Xxxxx
         * email : ses@ses.com
         */

        private String id;
        private String first_name;
        private String last_name;
        private String email;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class EmployerInfoBean {
        /**
         * id : 3840
         * first_name : Zx
         * last_name : Xx
         * email : rec@rec.com
         */

        private String id;
        private String first_name;
        private String last_name;
        private String email;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
