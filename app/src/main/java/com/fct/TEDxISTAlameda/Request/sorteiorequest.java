package com.fct.TEDxISTAlameda.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joaoveloso on 21/02/17.
 */

public class sorteiorequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://tedx.atspace.cc/sorteio.php";
    private Map<String,String> params;

    public sorteiorequest(String pergunta, Response.Listener<String> listener){
        super(Method.POST,REGISTER_LOGIN_URL,listener,null);
        params = new HashMap<>();

        params.put("username",pergunta);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
