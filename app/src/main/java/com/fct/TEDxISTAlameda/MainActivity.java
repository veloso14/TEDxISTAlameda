package com.fct.TEDxISTAlameda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.fct.TEDxISTAlameda.Request.RegisterClass;
import com.fct.TEDxISTAlameda.Request.emailRequest;
import com.linkedin.TEDxISTAlameda.APIHelper;
import com.linkedin.TEDxISTAlameda.LISessionManager;
import com.linkedin.TEDxISTAlameda.errors.LIApiError;
import com.linkedin.TEDxISTAlameda.errors.LIAuthError;
import com.linkedin.TEDxISTAlameda.listeners.ApiListener;
import com.linkedin.TEDxISTAlameda.listeners.ApiResponse;
import com.linkedin.TEDxISTAlameda.listeners.AuthListener;
import com.linkedin.TEDxISTAlameda.utils.Scope;
import com.fct.TEDxISTAlameda.Request.LoginRequest;
import com.fct.TEDxISTAlameda.activity.IconTabsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String PACKAGE = "com.numetriclabz.linkedin";
    private static final String host = "api.linkedin.com";
    private static final String topCardUrl = "https://" + host + "/v1/people/~:(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";


    Button login_linkedin_btn,hask_key;


    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //sem net
        if(  !isInternetAvailable() ){
            Intent intent = new Intent(MainActivity.this, semNet.class);

            MainActivity.this.startActivity(intent);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        int idName  = pref.getInt("intro", 0);


        if(idName == 0){


            Intent intent = new Intent(MainActivity.this, IntroActivity.class);

            MainActivity.this.startActivity(intent);

        }




        File f = getFileStreamPath("data");
        if (f.length() != 0) {
            // empty or doesn't exist
            Intent intent = new Intent(MainActivity.this, IconTabsActivity.class);

            MainActivity.this.startActivity(intent);
        }



        final EditText etUsername = (EditText) findViewById(R.id.v1);
        final EditText etPassword = (EditText) findViewById(R.id.v2);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.textView2);
        final TextView supporte = (TextView) findViewById(R.id.textView6);

        final Button bLogin = (Button) findViewById(R.id.tvregister);

        supporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "team@tedxistalameda.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "TEDxISTAlameda 2018");

                Intent mailer = Intent.createChooser(intent, null);
                startActivity(mailer);
            }
        });

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {


                                String FILENAME = "data";


                                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                try {
                                    SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPrefsFile", 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("homeScore", username);

// Apply the edits!
                                    editor.apply();
                                    fos.write(username.getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                Intent intent = new Intent(MainActivity.this, IconTabsActivity.class);

                                MainActivity.this.startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Login Falhou")
                                        .setNegativeButton("Tentar Novamente", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });



        login_linkedin_btn = (Button) findViewById(R.id.login_button);
        hask_key = (Button) findViewById(R.id.show_hash);
        login_linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_linkedin();
            }
        });

        //Compute application package and hash

        hask_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateHashkey();
            }
        });
    }

    // Authenticate with linkedin and intialize Session.

    public void login_linkedin(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

                // Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                login_linkedin_btn.setVisibility(View.GONE);

            }

            @Override
            public void onAuthError(LIAuthError error) {

                Toast.makeText(getApplicationContext(), "failed " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }, true);
    }

    // After complete authentication start new HomePage Activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(MainActivity.this, topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    JSONObject response = result.getResponseDataAsJson();
                    final String email = response.get("emailAddress").toString();
                    String name =  response.get("formattedName").toString();
                    String FILENAME = "data";


                    Response.Listener<String> responListerner = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonOResponse = new JSONObject(response);
                                boolean sucess = jsonOResponse.getBoolean("success");




                                    Response.Listener<String> respondidoListerner = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {



                                        }
                                    };


                                    emailRequest registerrequest = new emailRequest(email,respondidoListerner);
                                    RequestQueue queuuue = Volley.newRequestQueue(com.fct.TEDxISTAlameda.MainActivity.this);
                                    queuuue.add(registerrequest);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };



                    RegisterClass registerrequest = new RegisterClass(email, name,20, "linkdedin",responListerner);
                    RequestQueue queue = Volley.newRequestQueue(com.fct.TEDxISTAlameda.MainActivity.this);
                    queue.add(registerrequest);


                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    try {
                        SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPrefsFile", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("homeScore", email);

// Apply the edits!
                        editor.apply();
                        fos.write(email.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(MainActivity.this, IconTabsActivity.class);

                    MainActivity.this.startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onApiError(LIApiError error) {
                ((TextView) findViewById(R.id.error)).setText(error.toString());

            }
        });



        Intent intent = new Intent(MainActivity.this,UserProfile.class);
        startActivity(intent);
    }

    // This method is used to make permissions to retrieve data from linkedin

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    // This Method is used to generate "Android Package Name" hash key

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                ((TextView) findViewById(R.id.package_name)).setText(info.packageName);
                ((TextView) findViewById(R.id.hash_key)).setText(Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

}