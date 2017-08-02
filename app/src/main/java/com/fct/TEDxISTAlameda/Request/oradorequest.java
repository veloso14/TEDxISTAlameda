package com.fct.TEDxISTAlameda.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Veloso on 12/07/2017.
 */

public class oradorequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://tedx.atspace.cc/oradores/orador.php";
    private Map<String,String> params;

    public oradorequest(Response.Listener<String> listener){
        super(Request.Method.POST,REGISTER_LOGIN_URL,listener,null);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
