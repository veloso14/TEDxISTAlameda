package com.fct.TEDxISTAlameda.Request;

/**
 * Created by joaoveloso on 29/01/17.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class perguntarequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://tedx.atspace.cc/perguntas.php";
    private Map<String,String> params;

    public perguntarequest(String pergunta, Response.Listener<String> listener){
        super(Method.POST,REGISTER_LOGIN_URL,listener,null);
        params = new HashMap<>();

        params.put("pergunta",pergunta);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
