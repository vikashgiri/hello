package com.infoicon.bonjob.retrofit.response;

/**
 * Created by Pramod on 8/2/18.
 */

public class GetMakePaymentResponse {

    /**
     * success : true
     * error : false
     * data : {"subscription_id":"1","amount":"39.00","total_amount":"46.80","time_period":"1","subscription_title":"1 mois","description":"39.00 \u20ac HT par mois","current_server_date":"2018-02-08 07:53:36","expiredOn":"2018-03-10 07:53:36","search_candidate_count":null,"job_post_count":0}
     * msg : Merci pour votre abonnement,
     * votre paiement est valide.
     * active_user : 0
     */

    private boolean success;
    private boolean error;
    private DataBean data;
    private String msg;
    private String active_user;

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

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public static class DataBean {
        /**
         * subscription_id : 1
         * amount : 39.00
         * total_amount : 46.80
         * time_period : 1
         * subscription_title : 1 mois
         * description : 39.00 € HT par mois
         * current_server_date : 2018-02-08 07:53:36
         * expiredOn : 2018-03-10 07:53:36
         * search_candidate_count : null
         * job_post_count : 0
         */

        private String subscription_id;
        private String amount;
        private String total_amount;
        private String time_period;
        private String subscription_title;
        private String description;
        private String current_server_date;
        private String expiredOn;
        private String search_candidate_count;
        private int job_post_count;

        public String getSubscription_id() {
            return subscription_id;
        }

        public void setSubscription_id(String subscription_id) {
            this.subscription_id = subscription_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTime_period() {
            return time_period;
        }

        public void setTime_period(String time_period) {
            this.time_period = time_period;
        }

        public String getSubscription_title() {
            return subscription_title;
        }

        public void setSubscription_title(String subscription_title) {
            this.subscription_title = subscription_title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCurrent_server_date() {
            return current_server_date;
        }

        public void setCurrent_server_date(String current_server_date) {
            this.current_server_date = current_server_date;
        }

        public String getExpiredOn() {
            return expiredOn;
        }

        public void setExpiredOn(String expiredOn) {
            this.expiredOn = expiredOn;
        }

        public String getSearch_candidate_count() {
            if (search_candidate_count == null) {
                return "";
            } else
                return search_candidate_count;
        }

        public void setSearch_candidate_count(String search_candidate_count) {
            this.search_candidate_count = search_candidate_count;
        }

        public int getJob_post_count() {
            return job_post_count;
        }

        public void setJob_post_count(int job_post_count) {
            this.job_post_count = job_post_count;
        }
    }
}
