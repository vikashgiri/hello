package com.infoicon.bonjob.chat;

public class Responce {


    /**
     * success : true
     * error : false
     * data : {"client_secret":"pi_1FIC1eKVinoY1FjycSUUEtsq_secret_gzBK72jIolDTRCWdxxb5HUHZ0"}
     * msg : Payment intent client secret
     */

    private boolean success;
    private boolean error;
    private DataBean data;
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

    public static class DataBean {
        /**
         * client_secret : pi_1FIC1eKVinoY1FjycSUUEtsq_secret_gzBK72jIolDTRCWdxxb5HUHZ0
         */

        private String client_secret;

        public String getClient_secret() {
            return client_secret;
        }

        public void setClient_secret(String client_secret) {
            this.client_secret = client_secret;
        }
    }
}
