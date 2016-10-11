package com.openteam.ot.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.openteam.ot.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.fabric.sdk.android.Fabric;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignUpFragment extends AbstractFragment{

    private static final String TWITTER_KEY = "G40mgFszXVdMsl695qJX7RFmP";
    private static final String TWITTER_SECRET = "h0jAB6117vBQC5NVuSJawi14fnU91IEnQQWcFRy19xa07NFx6x";

    private TextView confirmBtn;
    private ImageView facebookBtn;
    private ImageView twitterBtn;
    private CallbackManager facebookCallbackManager;
    private TwitterAuthClient mTwitterAuthClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFacebook();
        initTwitter();
        View view =  inflater.inflate(R.layout.sign_up,container,false);
        confirmBtn = (TextView) view.findViewById(R.id.sign_up_confirm);
        facebookBtn = (ImageView) view.findViewById(R.id.facebook);
        twitterBtn = (ImageView) view.findViewById(R.id.twitter);
        facebookBtn = (ImageView) view.findViewById(R.id.facebook);
        return view;
    }

    @Override
    public void initListeners() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpValidation();
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager loginManager =LoginManager.getInstance();
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken != null) {
                    // Log out
                    loginManager.logOut();
                } else {
                    //Log in
                    loginManager.logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
                    loginManager.registerCallback(facebookCallbackManager, createFacebookCallback());
                }
            }
        });
        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTwitterAuthClient.authorize(getActivity(), createTwitterCallback());
            }
        });
    }

    private void signUpValidation(){
        getAbsActivity().replaceFragment(new SignUpValidationFragment(), R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private void initFacebook(){
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();
    }
    private void initTwitter(){
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getActivity(), new Twitter(authConfig));
        mTwitterAuthClient= new TwitterAuthClient();
    }

    private FacebookCallback<LoginResult> createFacebookCallback(){
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                    String name = object.getString("name");

                                    Toast.makeText(getActivity(), "FACEBOOK :  "+name+ " "+email, Toast.LENGTH_LONG).show();
                                    signUpValidation();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
            }
        };
    }

    private Callback<TwitterSession> createTwitterCallback(){
        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                String msg = "TWITTER : " + session.getUserName() + " logged in! (#" + session.getAuthToken() + ")";
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                signUpValidation();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }

        };
    }



    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }
}
