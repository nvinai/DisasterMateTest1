package com.example.calorietracker_v02;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFirstname;
    private EditText etSurname;
    private EditText etEmail;
    private EditText etHeight;
    private EditText etWeight;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioGroup radioGroup;
    private EditText etAddress;
    private EditText etPostCode;
    private EditText etSteps;
    private EditText etlevelOfActivity;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etDateOfBirth;
    private Button SignUp;
    private Calendar dobCalender;
    private String gender = null;
    private String result;

    String username,hashedPassword;
    int cidCount = 6;

    String firstName,surName,email,height,weight,steps,levelOfActivity,address,postcode,dateOfBirth;
    Date dateBirth;
    int id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dobCalender = Calendar.getInstance();

        etFirstname = (EditText) findViewById(R.id.firstName);
        etSurname = (EditText) findViewById(R.id.surname);
        etEmail = (EditText) findViewById(R.id.email);
        etHeight = (EditText) findViewById(R.id.height);
        SignUp = findViewById(R.id.signup_button);

        radioMale = findViewById(R.id.radio_male);
        radioFemale = findViewById(R.id.radio_female);
        radioGroup = findViewById(R.id.rgroup);





        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   gender = radioMale.getText().toString();
                }
            }
        });

        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  gender = radioFemale.getText().toString();
                }
            }
        });

        etAddress = (EditText) findViewById(R.id.address);
        etPostCode = (EditText) findViewById(R.id.postcode);
        etWeight = (EditText) findViewById(R.id.weight);
        etSteps = (EditText) findViewById(R.id.stepspermile);
        etUserName = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        etDateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        etlevelOfActivity = (EditText)  findViewById(R.id.levelOfActivity);




        final DatePickerDialog picker = new DatePickerDialog(RegisterActivity.this,android.R.style.Theme_Black,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, dobCalender
                .get(Calendar.YEAR), dobCalender.get(Calendar.MONTH),
                dobCalender.get(Calendar.DAY_OF_MONTH));

        picker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show();
            }
        });






        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SignUp();
            }
        });
    }





    @SuppressLint("ResourceType")
    private void SignUp() {


        firstName = etFirstname.getText().toString();
        surName = etSurname.getText().toString();
        email = etEmail.getText().toString();
        height = etHeight.getText().toString();
        weight = etWeight.getText().toString();
        steps = etSteps.getText().toString();
        levelOfActivity = etlevelOfActivity.getText().toString();
        address = etAddress.getText().toString();
        postcode = etPostCode.getText().toString();
        dateOfBirth = etDateOfBirth.getText().toString();
        id = -1;

//TODO possible errors here
        DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        {
            try {
                dateBirth = dateformat.parse(dateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        boolean cancel = false;
        View focusView = null;
        etFirstname.setError(null);
        etSurname.setError(null);
        etEmail.setError(null);
        etDateOfBirth.setError(null);
        etWeight.setError(null);
        etHeight.setError(null);
        etAddress.setError(null);
        etPostCode.setError(null);
        etSteps.setError(null);
        etUserName.setError(null);
        etPassword.setError(null);
        radioMale.setError(null);
        radioFemale.setError(null);



        if (etFirstname.getText().toString().isEmpty()) {
            etFirstname.setError("First Name is Required");
            focusView = etFirstname;
            cancel = true;
        } else if (etSurname.getText().toString().isEmpty()) {
            etSurname.setError("Surname is required");
            cancel = true;
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Valid Email Address is required");
            focusView = etEmail;
            cancel = true;
        } else if (etDateOfBirth.getText().toString().isEmpty()) {
            etDateOfBirth.setError("Date of Birth is required");
            focusView = etDateOfBirth;
            cancel = true;
        } else if (etHeight.getText().toString().isEmpty()) {
            etHeight.setError("Enter Height");
            focusView = etHeight;
            cancel = true;
        } else if (etWeight.getText().toString().isEmpty()) {
            etWeight.setError("Weight is required");
            focusView = etWeight;
            cancel = true;
        } else if (radioGroup.getCheckedRadioButtonId() <= 0) {
            radioFemale.setError("Select Item");
            focusView = radioGroup;
            cancel = true;
        } else if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("Address is required");
            focusView = etAddress;
            cancel = true;
        } else if (etPostCode.getText().toString().isEmpty()) {
            etPostCode.setError("PostCode is required");
            focusView = etPostCode;
            cancel = true;
        } else if (etSteps.getText().toString().isEmpty()) {
            etSteps.setError("Steps is required");
            focusView = etSteps;
            cancel = true;
        } else if (Integer.parseInt(etlevelOfActivity.getText().toString()) < 0 ||
                Integer.parseInt(etlevelOfActivity.getText().toString()) > 5) {
            etlevelOfActivity.setError("Enter Value between 0 and 5");
        } else if (etUserName.getText().toString().isEmpty()) {
            etUserName.setError("UserName is required");
            focusView = etUserName;
            cancel = true;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password is required");
            focusView = etPassword;
            cancel = true;
        }

        if (cancel) {
            Toast.makeText(this, "Errors found, retry", Toast.LENGTH_SHORT).show();
            focusView.requestFocus();
        } else {

            CredentialRetrieve credentialRetrieve = new CredentialRetrieve();
            credentialRetrieve.execute();
        }

        }


    public class CredentialRetrieve extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                result = CredentialClient.GetLoginCredsREST(etUserName.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("[]") ) {
                etUserName.setError("UserName Already Exists. Enter another.");
                etUserName.requestFocus();
            } else if (s.equals("")) {
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                finish();
            }
             else{
                try {
                    hashedPassword = SHA_1_Hashing.get_SHA_1_SecurePassword(etPassword.getText().toString(), SHA_1_Hashing.getSalt());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


                UserIdGetTask userIdGetTask = new UserIdGetTask();
                userIdGetTask.execute();



            }
        }

    }


private class UserIdGetTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {

            return PostToDB.generateUserId();
        }
        @Override
        protected void onPostExecute(Integer userid) {
            id = userid;
            if (id <= 0){
                Toast.makeText(RegisterActivity.this,"Something went wrong, are you offline?", Toast.LENGTH_LONG).show();
            }
            PostAppUser postAppUser =new PostAppUser();
            postAppUser.execute();
            Toast.makeText(RegisterActivity.this,"Successfully Registered", Toast.LENGTH_LONG).show();
        } }






    private class PostAppUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {

//                Appuser user = new Appuser(id,firstName,surName,dateBirth,Double.parseDouble(height),Double.parseDouble(weight),Integer.parseInt(postcode),Integer.parseInt(levelOfActivity),Integer.parseInt(steps),email,gender,address);

                Appuser user = new Appuser(id,firstName,surName,email,dateBirth,gender,Integer.parseInt(levelOfActivity),Integer.parseInt(steps),Integer.parseInt(height),Integer.parseInt(weight),address,Integer.parseInt(postcode));

                PostToDB.createUser(user);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Toast.makeText(RegisterActivity.this,"Added new user" + firstName + "to database", Toast.LENGTH_LONG).show();
            PostUserCredentials postUserCredentials = new PostUserCredentials();
            postUserCredentials.execute();
        }
    }


    private class PostUserCredentials extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Date signUpDate = new Date();

                Appuser user = new Appuser(id,firstName,surName,email,dateBirth,gender,Integer.parseInt(levelOfActivity),Integer.parseInt(steps),Integer.parseInt(height),Integer.parseInt(weight),address,Integer.parseInt(postcode));


                com.example.calorietracker_v02.Credential credential = new Credential(++cidCount,username,hashedPassword,signUpDate,user);


                PostToDB.createCredential(credential);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(RegisterActivity.this,"Credentials" + username + "was added to database", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegisterActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


}


