package com.infoicon.bonjob.retrofit;


import android.annotation.SuppressLint;


import com.infoicon.bonjob.R;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.logger.MyApplication;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ServiceCreator {

    public ServiceCreator() {
    }

    public static <T> void enqueueCall(final Call<T> call, final Callback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Response<T> response, Retrofit retrofit) {
                response.raw().request().url();
               retrofit.baseUrl().url();
                Logger.e("Response param " + response.raw().request().url().toString());
                if (response.body() == null)
                    try {
                        if (response.errorBody() != null)
                            Logger.e("Response message - Error:" + response.errorBody().string());
                        callback.onFailure(new Throwable(MyApplication.getAppContext().getResources().getString(R.string.error_something_went_wrong)));
                    } catch (IOException | NullPointerException e) {
                        Logger.e("catch Response message - Error:" + e.toString());
                        callback.onFailure(new Throwable(MyApplication.getAppContext().getResources().getString(R.string.error_something_went_wrong)));
                        e.printStackTrace();
                    }
                else {
                    callback.onResponse(response, retrofit);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Logger.e("Response param - onFailure " + t.toString());
                Logger.e("Response param - onFailure message " + t.getMessage());
                Logger.e("Response param - onFailure class " + t.getClass().getSimpleName());

                if (t.getClass().getSimpleName().equals("SocketTimeoutException")) {
                    callback.onFailure(new Throwable(MyApplication.getAppContext().getResources().getString(R.string.error_conn_timeout)));
                } else if (t.getClass().getSimpleName().equals("UnknownHostException")) {
                    callback.onFailure(new Throwable(MyApplication.getAppContext().getResources().getString(R.string.internet_connection)));
                } else {
                    callback.onFailure(new Throwable(MyApplication.getAppContext().getResources().getString(R.string.error_something_went_wrong)));
                }
            }
        });
    }
}
