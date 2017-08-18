package com.fct.TEDxISTAlameda.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Veloso on 18/08/2017.
 */

public class emailRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://tedemail.000webhostapp.com/demo.php";
    private Map<String,String> params;

    public emailRequest(String email, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("email",email);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
