package com.infoicon.bonjob.seeker.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.GPSLocation;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickLocationActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {


    private MarshmallowPermission marshmallowPermission;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    @BindView(R.id.textViewBack) CustomsTextView textViewBack;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.edLocationSearch) AutoCompleteTextView edLocationSearch;
    private double currentLatitude;
    private double currentLongitude;
    @BindView(R.id.rel1)
    RelativeLayout rel1;


    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    //private static final String BROWSER_API_KEY = "AIzaSyDylH1-s9skrxYllDTD77fIpfbi_c_Ooo0";
    private static final String BROWSER_API_KEY = "AIzaSyBtmaqxCbscAcxUBgvvMXXQ3RN01bgLZpg";
    private double searchLat;
    private double searchLong;
    private String search_address;
    private String search_city;
    private String postal_code;
    private boolean gettingLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);
        ButterKnife.bind(this);
        initialize();
        setTypeFace();
        showMap();
    }

    private void initialize() {
        //  textViewBack.setText(getResources().getString(R.string.profile_my));
        tvTitle.setText(getResources().getString(R.string.location));
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        marshmallowPermission = new MarshmallowPermission(this);

        edLocationSearch.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.places_text_view));
        //  edLocationSearch.setOnClickListener(this);
        edLocationSearch.setOnItemClickListener(this);
        edLocationSearch.setSingleLine(true);
        edLocationSearch.setFocusable(true);
        edLocationSearch.requestFocus();
        edLocationSearch.addTextChangedListener(textWatcher);
        edLocationSearch.setOnTouchListener(onTouchListener);

    }

    /** set typeface to editText */
    private void setTypeFace() {
        Typeface font = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTCom-LtEx.ttf");
        edLocationSearch.setTypeface(font);

    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {

                if (edLocationSearch.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    if (event.getRawX() >= (edLocationSearch.getRight() - edLocationSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        edLocationSearch.setText("");
                        edLocationSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.footer_search_deactive, 0, 0, 0);
                        return true;
                    }
                }
            }

            return false;
        }
    };


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() > 0) {
                edLocationSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.footer_search_deactive,
                        0,
                        R.drawable.close_btn_pink,
                        0);
            } else {
                edLocationSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.footer_search_deactive,
                        0, 0, 0);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * show map if location is on and permission for location is granted.
     */
    private void showMap() {
        if (UtilsMethods.isLocationEnabled(this)) {
            String[] permissions = {PermissionKeys.PERMISSION_ACCESS_FINE_LOCATION, PermissionKeys.PERMISSION_ACCESS_COARSE_LOCATION};
            if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL, permissions)) {
                mapFragment.getMapAsync(this);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.LOCATION_ENABLE_CODE) {
            gettingLocation();
        }
    }

    /** get result back is permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gettingLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    UtilsMethods.openAlert(getResources().getString(R.string.message_location_permission_request), this);
                }
                break;
            }
        }
    }

    /** loader to wait for getting location */
    private void gettingLocation() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.getting_location));
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            progressDialog.dismiss();
            showMap();
        }, 2000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 9/2/17  if permission is not available
            return;
        }

        // set location enable true
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        //GPSLocation class for get current latitude and longitude
        GPSLocation gpsLocation = new GPSLocation(this);
        currentLatitude = gpsLocation.getLatitude();
        currentLongitude = gpsLocation.getLongitude();

        search_address = gpsLocation.getAddress(currentLatitude, currentLongitude);
        UtilsMethods.City_name=search_address;
        search_city = search_address;
        searchLat = currentLatitude;
        searchLong = currentLongitude;
      /*  edLocationSearch.setText(search_address);
        edLocationSearch.setSelection(edLocationSearch.getText().length());
*/
        postal_code = UtilsMethods.postalCode;
        //search_address = search_city+", "+postal_code;
        if (postal_code != null && !postal_code.equals("")) {
            //search_address = place+", "+postal_code;
            search_address = search_city+", "+postal_code;
        }

        else
        {
            //  search_address = place;
            search_address = search_city;
        }
        edLocationSearch.setText(search_address);
        edLocationSearch.setSelection(edLocationSearch.getText().length());
        edLocationSearch.setFocusableInTouchMode(true);
        //set camera position in  map
        //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLatitude, currentLongitude)).zoom(20).build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 15));
        //  MarkerOptions markerOptions = new MarkerOptions();
        //current location marker
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("My Location");
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.category)).title("My Location");
        //  markerOptions.position(new LatLng(currentLatitude, currentLongitude));
        //  googleMap.addMarker(markerOptions);
    }


    @OnClick(R.id.tvSave)
    void save() {
        if (search_address != null && !search_address.equals("")) {
           UtilsMethods.postalCode = postal_code;
           Intent returnIntent = new Intent();
            returnIntent.putExtra(Keys.LOCATION_NAME, search_address);
            returnIntent.putExtra(Keys.LOCATION_CITY, search_city);
            returnIntent.putExtra(Keys.LOCATION_LAT, searchLat);
            returnIntent.putExtra(Keys.LOCATION_LONG, searchLong);
            returnIntent.putExtra(Keys.CURRENT_LAT, currentLatitude);
            returnIntent.putExtra(Keys.CURRENT_LONG, currentLongitude);



            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            CommonUtils.showToast(this, getResources().getString(R.string.location_empty));
        }
    }

    /** go back event */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        finish();
    }

   /* @OnClick(R.id.imgViewMyLocation)
    void myLocation() {
        GPSLocation gpsLocation = new GPSLocation(this);
        currentLatitude = gpsLocation.getLatitude();
        currentLongitude = gpsLocation.getLongitude();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 20));
    }*/


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        edLocationSearch.setText("");
        UtilsMethods.hideSoftKeyboard(this);
        getLatLongFromPlace((String) parent.getItemAtPosition(position));
    }

    /**
     * get lat long from places
     * @param place name of place
     */
    public void getLatLongFromPlace(final String place) {
        try {
           // int i=5/0;
            Geocoder selected_place_geocoder = new Geocoder(this);
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(place, 5);
                Address address1 = address.get(0);
                searchLat = address1.getLatitude();
                searchLong = address1.getLongitude();
                edLocationSearch.setFocusable(false);

            GPSLocation gpsLocation = new GPSLocation(PickLocationActivity.this);
            search_city = gpsLocation.getAddress(searchLat, searchLong);
            postal_code = UtilsMethods.postalCode;
            //search_address = search_city+", "+postal_code;
            if (postal_code != null && !postal_code.equals("")) {

                //search_address = place+", "+postal_code;
                search_address = search_city+", "+postal_code;
            }

            else
            {
                //  search_address = place;
                search_address = search_city;
            }
            Log.e("Google_Lat", ":: " + searchLat + " Google_Lng" + ":: " + searchLong);
            navigateMapToSearchLocation();
        } catch (Exception e) {
            e.printStackTrace();

            new DataLongOperationAsynchTask(place, new DataLongOperationAsynchTask.INetworkResponseLatLng() {
                @Override
                public void onSuccess(LatLng response) {
                    Log.e("Google_Response", "" + response);
                  //  search_address = place;
                    edLocationSearch.setFocusable(false);

                    searchLat = response.latitude;
                    searchLong = response.longitude;

                    GPSLocation gpsLocation = new GPSLocation(PickLocationActivity.this);
                    search_city = gpsLocation.getAddress(searchLat, searchLong);
                    postal_code = UtilsMethods.postalCode;
                    //search_address = search_city+", "+postal_code;
                    if (postal_code != null && !postal_code.equals("")) {

                        //search_address = place+", "+postal_code;
                        search_address = search_city+", "+postal_code;
                    }

                    else
                    {
                        //  search_address = place;
                        search_address = search_city;
                    }
                   // edLocationSearch.setText(search_address);
                   // edLocationSearch.setSelection(edLocationSearch.getText().length());
                   // edLocationSearch.addTextChangedListener(textWatcher);

                    Log.e("Google_Lat", ":: " + searchLat + " Google_Lng" + ":: " + searchLong);
                    navigateMapToSearchLocation();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(PickLocationActivity.this, getResources().getString(R.string.error_something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        }
    }

    /** navigate marker to search location */
    private void navigateMapToSearchLocation() {
        if (googleMap == null) {
            return;
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchLat, searchLong), 15));
        MarkerOptions markerOptions = new MarkerOptions();
        //current location marker
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(search_address);
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.category)).title("My Location");
        markerOptions.position(new LatLng(searchLat, searchLong));
        googleMap.addMarker(markerOptions);
        edLocationSearch.setText(search_address);
        edLocationSearch.setSelection(edLocationSearch.getText().length());
        edLocationSearch.setFocusable(true);
        edLocationSearch.setFocusableInTouchMode(true);

    }


    /** adapter class for location list */
    private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    /**  */
    private ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + BROWSER_API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            sb.append("&sensor=true");
            //sb.append(("&components=country:ind"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            return resultList;
        } catch (IOException e) {
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
        }
        return resultList;
    }

}
