package com.example.calorietracker_v02;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.calorietracker_v02.SHA_1_Hashing.getSalt;
import static com.example.calorietracker_v02.SHA_1_Hashing.get_SHA_1_SecurePassword;

public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
//     * A dummy authentication store containing known user names and passwords.
//     * TODO: remove after connecting to a real authentication system.
//     */
////    private static final String[] DUMMY_CREDENTIALS = new String[]{
////            "foo@example.com:hello", "bar@example.com:world"
////    };


//    /**
//     * Keep track of the login task to ensure we can cancel it if requested.
//     */
//    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText etUserName;
    private EditText etPassword;
    private View mProgressView;
    private View mLoginFormView;
    private UserLoginTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //Does the masking
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        etUserName.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) ) {
////
//            etPassword.setError(getString(R.string.error_invalid_password));
//            focusView = etPassword;
//            cancel = true;
//        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etUserName.setError(getString(R.string.error_field_required));
            focusView = etUserName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
//    /**
//     * Shows the progress UI and hides the login form.
//     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
//         On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//         for very easy animations. If available, use these APIs to fade-in
//         the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserName;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mUserName = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                //Applying SHA_1 Hashing to the password to match the secure password in the database
                byte[] salt = getSalt();
                String hashedPassword = get_SHA_1_SecurePassword(mPassword,salt);

                //querying the database for the user login details.
                String credentials = CredentialClient.GetLoginCredsREST(mUserName);

                JSONArray jsonArray = new JSONArray(credentials);

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String firstName = jsonObject.getJSONObject("userid").getString("name");
                String userId = jsonObject.getJSONObject("userid").getString("userid");
                String address = jsonObject.getJSONObject("userid").getString("address");
                String postCode = jsonObject.getJSONObject("userid").getString("postcode");
                String userName = jsonObject.getString("username");
                String password = jsonObject.getString("password");

                //storing the values of the user to Shared Preference so that we can call user by First Name in the Home Screen
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("userName",userName);
                editor.putString("name",firstName);
                editor.putString("id",userId);
                editor.putString("password",password);
                editor.putString("address",address);
                editor.putString("postcode",postCode);

                editor.apply();

                if (userName.equals(mUserName) && password.equals(hashedPassword)) {
                    // Account exists, return true if the password matches.
                    return true;

                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            mAuthTask = null;
            showProgress(false);

            if (result==true) {
                Intent newActivity = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(newActivity);
                finish();
            } else {
                etPassword.setError(getString(R.string.error_incorrect_password));
                etPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}

