package com.infoicon.bonjob.seeker.profile;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by infoicon on 5/11/16.
 */

public class DataLongOperationAsynchTask extends AsyncTask<String, Void, String[]> {

    public interface INetworkResponseLatLng {

        public void onSuccess(LatLng response);
        public void onError(String error);
    }

    String inputdata;
    INetworkResponseLatLng iNetworkResponse;
    public DataLongOperationAsynchTask(String inputdata, INetworkResponseLatLng iNetworkResponse){
        this.inputdata=inputdata;
        this.iNetworkResponse=iNetworkResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        String response;
        try {
            response = getLatLongByURL("https://maps.google.com/maps/api/geocode/json?key=AIzaSyDylH1-s9skrxYllDTD77fIpfbi_c_Ooo0&address="+ URLEncoder.encode(inputdata, "utf8"));
            Log.d("response",""+response);
            return new String[]{response};
        } catch (Exception e) {
            return new String[]{"error"};
        }
    }

    @Override
    protected void onPostExecute(String... result)
    {
        if (!result[0].equalsIgnoreCase("error")){
            try {
                JSONObject jsonObject = new JSONObject(result[0]);

                double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");

                LatLng latLng=new LatLng(lat,lng);

                iNetworkResponse.onSuccess(latLng);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            iNetworkResponse.onError("error");
        }


    }

    /** method for get lat long from url
     * @param requestURL is url */
    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
