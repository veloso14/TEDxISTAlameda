package com.fct.TEDxISTAlameda.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Veloso on 21/07/2017.
 */

public class fotoPerfilRequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://tedx.atspace.cc/imagens/foto.php";
    private Map<String,String> params;

    public fotoPerfilRequest(String username, Response.Listener<String> listener){
        super(Method.POST,REGISTER_LOGIN_URL,listener,null);
        params = new HashMap<>();

        params.put("username",username);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
