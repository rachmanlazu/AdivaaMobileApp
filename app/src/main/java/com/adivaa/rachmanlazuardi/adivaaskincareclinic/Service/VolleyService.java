package com.adivaa.rachmanlazuardi.adivaaskincareclinic.Service;

import android.content.Context;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.IResult;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyService {
    IResult mResultCallback = null;
    Context mContext;

    public VolleyService(IResult resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }

    public void getDataVolley(final String requestType, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(mResultCallback != null) {
                            try {
                                mResultCallback.notifySuccess(requestType,response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mResultCallback != null)
                            mResultCallback.notifyError(requestType,error);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void patchDataVolley(final String requestType, String url, final Map<String, String> params, final HashMap headers){
        StringRequest postRequest  =  new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(mResultCallback != null) {
                            try {
                                JSONObject result = new JSONObject(response);
                                mResultCallback.notifySuccess(requestType,result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mResultCallback != null)
                            mResultCallback.notifyError(requestType,error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {

                return params;
            }


            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {

                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(postRequest);
    }

    public void postDataHeadersVolley(final String requestType, String url, final Map<String, String> params, final HashMap headers){
        StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(mResultCallback != null) {
                            try {
                                JSONObject result = new JSONObject(response);
                                mResultCallback.notifySuccess(requestType,result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mResultCallback != null)
                            mResultCallback.notifyError(requestType,error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {

                return params;
            }

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {

                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(postRequest);
    }

    public void getDataHeadersVolley(final String requestType, String url, final HashMap headers){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(mResultCallback != null) {
                    try {
                        mResultCallback.notifySuccess(requestType,response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(mResultCallback != null)
                    mResultCallback.notifyError(requestType,error);
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        requestQueue.add(req);
    }

}
