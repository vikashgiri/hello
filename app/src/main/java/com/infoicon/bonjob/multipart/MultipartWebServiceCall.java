package com.infoicon.bonjob.multipart;

import android.content.Context;
import android.os.AsyncTask;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by infoicon on 12/10/15.
 */
public class MultipartWebServiceCall {

    private final String TAG = this.getClass().getSimpleName();
    Context context;
    private String errorMsg;
    INetworkResponse iNetworkResponse;

    public MultipartWebServiceCall(Context context, INetworkResponse iNetworkResponse) {
        this.context = context;
        this.iNetworkResponse = iNetworkResponse;
    }

    public void execute(MultipartEntityBuilder multipartEntityBuilder, String url) {
        Call call = new Call(multipartEntityBuilder);
        call.execute(url);
    }

    public void cancel() {
        Call call = new Call();
        call.cancel(true);
    }

    private class Call extends AsyncTask<String, Void, Boolean> {

        private static final String USER_AGENT = "Mozilla/5.0";
        private String responseString;
        private MultipartEntityBuilder multipartEntityBuilder;

        public Call(MultipartEntityBuilder multipartEntityBuilder) {
            this.multipartEntityBuilder = multipartEntityBuilder;
        }

        public Call() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... params) {
            {
                String boundary = "Infoicon" + System.currentTimeMillis();
                HttpEntity entity = multipartEntityBuilder.build();

                URL url = null;

                try {
                    url = new URL(params[0]);
                 //  HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                   HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                     /*------------*/

                    // Create the SSL connection

                    final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    }};


                    SSLContext sc;
                    sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                //    urlConnection.setSSLSocketFactory(sc.getSocketFactory());

                /*-----*/

                    urlConnection.setConnectTimeout(20000);
                    urlConnection.setReadTimeout(20000);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.addRequestProperty("Content-length", entity.getContentLength() + "");
                    urlConnection.addRequestProperty(Keys.AUTH_KEY, Singleton.getUserInfoInstance().getAuthKey());
                    urlConnection.addRequestProperty(Keys.LANGUAGE_ID, UtilsMethods.getDefaultLanguage());
                    urlConnection.addRequestProperty(Keys.LOGIN_USER_ID, Singleton.getUserInfoInstance().getUser_id());
                    urlConnection .addRequestProperty(Keys.VERSION_ID, "1");

                    urlConnection.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getValue());
                    Logger.e("AUTH KEY : " + Singleton.getUserInfoInstance().getAuthKey());
                    OutputStream os = urlConnection.getOutputStream();


                    entity.writeTo(urlConnection.getOutputStream());
                    os.close();
                    urlConnection.connect();
                    InputStream inputStream = urlConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String s = "";
                    StringBuilder stringBuilder = new StringBuilder("");
                    while ((s = bufferedReader.readLine()) != null) {
                        stringBuilder.append(s);
                    }
                    responseString = stringBuilder.toString();
                    return true;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    errorMsg = context.getResources().getString(R.string.internet_connection);
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    errorMsg = context.getResources().getString(R.string.error_conn_timeout);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMsg = context.getResources().getString(R.string.error_something_went_wrong);
                }
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Logger.e(TAG + " : " + responseString);
                iNetworkResponse.onSuccess(responseString);
            } else {
                iNetworkResponse.onError(errorMsg);
            }
        }
    }
}
