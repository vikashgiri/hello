package com.infoicon.bonjob.fb_login;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.utils.Keys;
import org.json.JSONException;
import java.util.Arrays;

/**
 * Created by android1 on 2/12/16.
 * this class use for login with facebook.
 * register FacebookActivity class in manifest
 * set meta-data tag with ApplicationId in manifest
 */

public class FbLoginActivity extends AppCompatActivity
{

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginWithFacebook();
    }

    /**
     * method for login with facebook.
     */

    private void loginWithFacebook()
    {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    Log.e("LoginActivity : ", response.toString());
                                    // Application code
                                    try {
                                        String fb_id=object.getString("id");
                                        String name =object.getString("name");
                                        String email="";
                                        if (object.has("email"))
                                        {
                                            email = object.getString("email");
                                        }
                                        returnSuccess(fb_id,name,email);
                                     //   Toast.makeText(FbLoginActivity.this, "Name :"+name+"\n Email : "+email, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    LoginManager.getInstance().logOut();

                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e("LoginActivity :"," cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("LoginActivity : ", exception.toString());
                    }
                });
    }

    /**
     * set result to intent on response success
     */
    private void returnSuccess(String fb_id,String name, String email)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Keys.FB_ID, fb_id);
        returnIntent.putExtra(Keys.NAME, name);
        returnIntent.putExtra(Keys.EMAIL, email);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    /**
     * set result to intent on response cancel/failure
     */
    private void returnCancel()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
    /**
     * callback for social log in.
     *
     * @param requestCode return from the social.
     * @param resultCode  status for the social.
     * @param data        return data in form of intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //callback for facebook
        if (requestCode == Keys.FACEBOOK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }else {
                returnCancel();
            }
        }
    }
}
