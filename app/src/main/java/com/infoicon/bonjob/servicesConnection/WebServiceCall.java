package com.infoicon.bonjob.servicesConnection;

import android.content.Context;
import android.os.AsyncTask;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
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

import dmax.dialog.SpotsDialog;

public class WebServiceCall{

    private Context context;
    private String errorMsg;
    private INetworkResponse iNetworkResponse;
    private SpotsDialog pd;
    private boolean noprgress = false;
    private boolean isForchat = false;
    private boolean running = true;

    public WebServiceCall(Context context, INetworkResponse iNetworkResponse) {
        this.context = context;
        this.iNetworkResponse = iNetworkResponse;
    }


    public WebServiceCall(Context context, INetworkResponse iNetworkResponse, boolean noprgress, boolean isForchat) {
        this.context = context;
        this.iNetworkResponse = iNetworkResponse;
        this.noprgress = noprgress;
        this.isForchat = isForchat;
    }

    public void execute(JSONObject jsonObject, String url) {

        Logger.e("REQUEST URL :" + url);
        Logger.e("REQUEST JSON :" + jsonObject.toString());
        Call call = new Call(jsonObject);
        call.execute(url);
    }

    public void cancel() {
        Call call = new Call();
        if (call.getStatus() != AsyncTask.Status.FINISHED)
            call.cancel(true);

        //  call.cancel(true);
    }

    private class Call extends AsyncTask<String, Void, Boolean> {

        private static final String USER_AGENT = "Mozilla/5.0";
        JSONObject jsonObject;
        private String responseString;

        public Call(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public Call() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(context, R.style.Custom);
            pd.setCancelable(false);
            if (!noprgress) {
                pd.show();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                // Create the SSL https
//                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();


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
                // Create the SSL https

                //  urlConnection.setSSLSocketFactory(sc.getSocketFactory());

                /*-----*/

                urlConnection.setConnectTimeout(40000);
                urlConnection.setReadTimeout(40000);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Users-Agent", USER_AGENT);
                urlConnection.setRequestProperty("Accept-Encoding", "identity");
                urlConnection.setRequestProperty("content-type", "application/json");
                if (isForchat) {
                    urlConnection.setRequestProperty("Authorization", "tSTYI6C4GPKKJZdVOKSOxT2YycHDYt001MgEs3Dz");
                    urlConnection.setRequestProperty("Project-Key", "bonjob");
                    urlConnection.setRequestProperty(Keys.AUTH_KEY, Singleton.getUserInfoInstance().getAuthKey());
                    urlConnection.setRequestProperty(Keys.LANGUAGE_ID, UtilsMethods.getDefaultLanguage());
                    urlConnection.addRequestProperty(Keys.LOGIN_USER_ID, Singleton.getUserInfoInstance().getUser_id());
                    urlConnection .addRequestProperty(Keys.VERSION_ID, "1");
                } else {
                    urlConnection.setRequestProperty(Keys.AUTH_KEY, Singleton.getUserInfoInstance().getAuthKey());
                    urlConnection.setRequestProperty(Keys.LANGUAGE_ID, UtilsMethods.getDefaultLanguage());
                    urlConnection.addRequestProperty(Keys.LOGIN_USER_ID, Singleton.getUserInfoInstance().getUser_id());
                    urlConnection  .addRequestProperty(Keys.VERSION_ID, "1");
                }
                if (!running) {
                    return false;
                }

              /*  DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                dataOutputStream.writeByte(jsonObject.toString());
                dataOutputStream.flush();
                dataOutputStream.close();*/

                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dataOutputStream, "UTF-8"));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
               /* byte[] buffer = new byte[1024];


                while (inputStream.read(buffer) != -1) {
                    String s = new String(buffer);
                    stringBuilder.append(s);
                }*/

                String s;
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
            } catch (MalformedURLException e) {
                errorMsg = context.getResources().getString(R.string.error_something_went_wrong);
                e.printStackTrace();
            } catch (ConnectException e) {
                errorMsg = context.getResources().getString(R.string.error_network_unavailable);
                e.printStackTrace();
            } catch (IOException e) {
                errorMsg = context.getResources().getString(R.string.error_something_went_wrong);
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg = context.getResources().getString(R.string.error_something_went_wrong);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (pd.isShowing())
                pd.dismiss();

            if (result) {
                Logger.e("RESPONSE SUCCESS :" + responseString);
                iNetworkResponse.onSuccess(responseString);
            } else {
                iNetworkResponse.onError(errorMsg);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            running = false;
        }
    }
}
