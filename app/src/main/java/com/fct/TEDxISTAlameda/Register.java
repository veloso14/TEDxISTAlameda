package com.fct.TEDxISTAlameda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.fct.TEDxISTAlameda.Request.RegisterClass;
import com.fct.TEDxISTAlameda.Request.emailRequest;

import org.json.JSONException;
import org.json.JSONObject;



public class Register extends AppCompatActivity {

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName= (EditText) findViewById(R.id.pass2);
        final EditText etPass = (EditText) findViewById(R.id.etname);
        final EditText etmail = (EditText) findViewById(R.id.v2);
        final EditText pass2 = (EditText) findViewById(R.id.v1);

        final Button bRegister = (Button) findViewById(R.id.register);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                final String name = etName.getText().toString();
                final String username = etmail.getText().toString();
                final String password = etPass.getText().toString();
                final String password2 = pass2.getText().toString();
                final int pontos = 20;

                if(password.length() <= 5){
                    AlertDialog.Builder  burlder =    new    AlertDialog.Builder(Register.this);

                    burlder.setMessage("A Password tem de ter no minimo 6 letras")
                            .setNegativeButton("Tenta novamente",null)
                            .create()
                            .show();


                }

                else{

                    if(name.isEmpty()){

                        AlertDialog.Builder  burrlder =    new    AlertDialog.Builder(Register.this);

                        burrlder.setMessage("O Nome esta vazio")
                                .setNegativeButton("Tenta novamente",null)
                                .create()
                                .show();

                    }
                    else{

                    if(!password.matches(password2)){
                        AlertDialog.Builder  burrlder =    new    AlertDialog.Builder(Register.this);

                        burrlder.setMessage("Password não são iguais")
                                .setNegativeButton("Tenta novamente",null)
                                .create()
                                .show();

                    }



                    else{
                Response.Listener<String> responListerner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonOResponse = new JSONObject(response);
                            boolean sucess = jsonOResponse.getBoolean("success");

                            if (sucess){


                                Response.Listener<String> respondidoListerner = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("resposta",response);

                                        Intent intent  = new Intent(Register.this ,MainActivity.class);
                                        Register.this.startActivity(intent);

                                    }
                                };


                                emailRequest registerrequest = new emailRequest(username,respondidoListerner);
                                RequestQueue queuuue = Volley.newRequestQueue(Register.this);
                                queuuue.add(registerrequest);


                                Toast.makeText(getApplicationContext(),"Registado com Sucesso", Toast.LENGTH_SHORT).show();
                            }
                            else  {
                                AlertDialog.Builder  bulder =    new    AlertDialog.Builder(Register.this);

                                bulder.setMessage("Falha no Registo,Email em uso")
                                        .setNegativeButton("Tenta novamente",null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };



                RegisterClass registerrequest = new RegisterClass(username, name,20, password,responListerner);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerrequest);

            }} }
            }
        });


    }
}
