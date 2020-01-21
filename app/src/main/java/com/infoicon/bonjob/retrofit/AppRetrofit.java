package com.infoicon.bonjob.retrofit;


import com.google.gson.GsonBuilder;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class AppRetrofit {
    //    private static Context mContext;
//    private static boolean mStatus;
    private ApiService apiServices;
    private static AppRetrofit appRetrofit;

    private AppRetrofit() {
        //code for retrofit 2.0

        Interceptor HEADER_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request request = chain.request().newBuilder()
                        // .addHeader(ServiceConstants.APP_VERSION,"")
                        //.addHeader("Content-Type", "application/json;charset=utf-8")
                        .addHeader(Keys.AUTH_KEY, Singleton.getUserInfoInstance().getAuthKey())
                        .addHeader(Keys.LANGUAGE_ID, UtilsMethods.getDefaultLanguage())
                        .addHeader(Keys.VERSION_ID, "1")
                        .addHeader(Keys.LOGIN_USER_ID, Singleton.getUserInfoInstance().getUser_id())
                        .addHeader(Keys.appType, "3")
                        .build();
                System.out.println("auths"+Singleton.getUserInfoInstance().getAuthKey());
                System.out.println("auths"+Singleton.getUserInfoInstance().getUser_id());
                return chain.proceed(request);
            }
        };

        //for logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //setting up client
        OkHttpClient unsafeClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
       // OkHttpClient unsafeClient = new OkHttpClient();
        unsafeClient.setReadTimeout(60, TimeUnit.SECONDS);
        unsafeClient.setConnectTimeout(60, TimeUnit.SECONDS);
        unsafeClient.networkInterceptors().add(HEADER_INTERCEPTOR);
        unsafeClient.interceptors().add(interceptor);
        //rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceUrls.BASE_URL)
               //.client(client)
                .client(unsafeClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        /*.serializeNulls()*/
               .create()))
               .build();


        apiServices = retrofit.create(ApiService.class);


    }

    public AppRetrofit(String baseUrl)
    {

        //code for retrofit 2.0
        Interceptor HEADER_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        // .addHeader(ServiceConstants.APP_VERSION,"")
                        .addHeader("Content-Type", "application/json;charset=utf-8")
                        .addHeader(Keys.AUTH_KEY, Singleton.getUserInfoInstance().getAuthKey())
                        .addHeader(Keys.LANGUAGE_ID, UtilsMethods.getDefaultLanguage())
                        .addHeader(Keys.LOGIN_USER_ID, Singleton.getUserInfoInstance().getUser_id())
                 .addHeader(Keys.VERSION_ID, "1")
                   .build();
                return chain.proceed(request);
            }
        };

        //for logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //setting up client
        OkHttpClient unsafeClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
      //  OkHttpClient unsafeClient = new OkHttpClient();
        unsafeClient.setReadTimeout(30, TimeUnit.SECONDS);
        unsafeClient.setConnectTimeout(30, TimeUnit.SECONDS);
        unsafeClient.networkInterceptors().add(HEADER_INTERCEPTOR);
        unsafeClient.interceptors().add(interceptor);
        //rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceUrls.BASE_URL)

                //.client(client)
                .client(unsafeClient)

                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        /*.serializeNulls()*/
                        .create()))
                .build();
        apiServices = retrofit.create(ApiService.class);
    }

    public ApiService getApiServices() {
        return apiServices;
    }

    // static method to get singleton object of AppRetrofit
    public static synchronized AppRetrofit getAppRetrofitInstance() {
        Logger.e("AUTH KEY : " + Singleton.getUserInfoInstance().getAuthKey());
        if (appRetrofit == null) {
            appRetrofit = new AppRetrofit();
            return appRetrofit;
        } else {
            return appRetrofit;
        }
    }

    // static method to get singleton object of AppRetrofit
    public static synchronized AppRetrofit getAppRetrofitInstance(String baseUrl) {
        if (appRetrofit == null) {
            appRetrofit = new AppRetrofit(baseUrl);
            return appRetrofit;
        } else {
            return appRetrofit;
        }
    }

}

