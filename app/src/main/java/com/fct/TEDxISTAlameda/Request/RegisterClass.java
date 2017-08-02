package com.fct.TEDxISTAlameda.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joaoveloso on 29/01/17.
 */

public class RegisterClass extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://tedx.atspace.cc/Register.php";
    private Map<String,String> params;

    public RegisterClass(String username, String name, int age, String password, Response.Listener<String> listener){
            super(Method.POST,REGISTER_REQUEST_URL,listener,null);
            params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("age",age + "");
        params.put("password",password);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
