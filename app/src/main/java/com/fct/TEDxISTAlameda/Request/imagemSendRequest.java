package com.fct.TEDxISTAlameda.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Veloso on 21/07/2017.
 */

public class imagemSendRequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://tedx.atspace.cc/imagens/change.php";
    private Map<String,String> params;

    public imagemSendRequest(Response.Listener<String> listener, String username, String foto){
        super(Request.Method.POST,REGISTER_LOGIN_URL,listener,null);
        params = new HashMap<>();

        params.put("username",username);
        params.put("foto",foto);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
