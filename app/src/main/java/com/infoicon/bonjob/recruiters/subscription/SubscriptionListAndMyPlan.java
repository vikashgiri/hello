package com.infoicon.bonjob.recruiters.subscription;

import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse.CurrentPlanBean;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse.DataBean;

import java.util.List;

/**
 * Created by Pramod on 8/2/18.
 */

public class SubscriptionListAndMyPlan {

    private List<DataBean> subscriptionList;
    private CurrentPlanBean currentPlan;

    public List<DataBean> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<DataBean> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }


    public CurrentPlanBean getCurrentPlan() {
        if (currentPlan != null)
            return currentPlan;
        else return null;
    }

    public void setCurrentPlan(CurrentPlanBean currentPlan) {
        this.currentPlan = currentPlan;
    }
}
