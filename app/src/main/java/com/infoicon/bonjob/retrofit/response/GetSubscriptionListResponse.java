package com.infoicon.bonjob.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Pramod on 8/2/18.
 */

public class GetSubscriptionListResponse {

    /**
     * success : true
     * error : false
     * data : [{"subscription_id":"1","status":"1","amount":"39.00","total_amount":"46.80","time_period":"1","createdOn":"2018-02-05 10:24:26","updatedOn":"2018-02-05 09:24:26","subscription_title":"1 mois","description":"39.00 \u20ac HT par mois"},{"subscription_id":"2","status":"1","amount":"348.00","total_amount":"417.60","time_period":"12","createdOn":"2018-02-05 10:31:26","updatedOn":"2018-02-05 09:31:26","subscription_title":"1 An","description":"29.00 \u20ac HT par mois"}]
     * currentPlan : {"subscription_id":"","amount":"","total_amount":"","time_period":"","subscription_title":"","description":"","current_server_date":"2018-02-08 06:00:06","expiredOn":"","search_candidate_count":"0","job_post_count":3}
     * msg : Données trouvées
     * active_user : 0
     */

    private boolean success;
    private boolean error;
    private CurrentPlanBean currentPlan;
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

    public CurrentPlanBean getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(CurrentPlanBean currentPlan) {
        this.currentPlan = currentPlan;
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

    public static class CurrentPlanBean {
        /**
         * subscription_id :
         * amount :
         * total_amount :
         * time_period :
         * subscription_title :
         * description :
         * current_server_date : 2018-02-08 06:00:06
         * expiredOn :
         * search_candidate_count : 0
         * job_post_count : 3
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
        private String  select_candidate_count;

        public String getSelect_candidate_count() {
            return select_candidate_count;
        }

        public void setSelect_candidate_count(String select_candidate_count)
        {
            this.select_candidate_count = select_candidate_count;
        }

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
            if (search_candidate_count == null)
                return "0";
            else if (search_candidate_count.equals("")) {
                return "0";
            } else return search_candidate_count;
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

    public static class DataBean implements Parcelable {
        /**
         * subscription_id : 1
         * status : 1
         * amount : 39.00
         * total_amount : 46.80
         * time_period : 1
         * createdOn : 2018-02-05 10:24:26
         * updatedOn : 2018-02-05 09:24:26
         * subscription_title : 1 mois
         * description : 39.00 € HT par mois
         */

        private String subscription_id;
        private String status;
        private String amount;
        private String total_amount;
        private String time_period;
        private String createdOn;
        private String updatedOn;
        private String subscription_title;
        private String description;

        protected DataBean(Parcel in) {
            subscription_id = in.readString();
            status = in.readString();
            amount = in.readString();
            total_amount = in.readString();
            time_period = in.readString();
            createdOn = in.readString();
            updatedOn = in.readString();
            subscription_title = in.readString();
            description = in.readString();
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

        public String getSubscription_id() {
            return subscription_id;
        }

        public void setSubscription_id(String subscription_id) {
            this.subscription_id = subscription_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(subscription_id);
            dest.writeString(status);
            dest.writeString(amount);
            dest.writeString(total_amount);
            dest.writeString(time_period);
            dest.writeString(createdOn);
            dest.writeString(updatedOn);
            dest.writeString(subscription_title);
            dest.writeString(description);
        }
    }
}
