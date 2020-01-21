package com.infoicon.bonjob.servicesConnection;

/**
 * Created by infoicon on 25/3/16.
 */
public interface INetworkResponse {

    public void onSuccess(String response);
    public void onError(String error);
}
