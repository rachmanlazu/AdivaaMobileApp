package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText eMail, password;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RequestQueue queue = singleton.getInstance(this.getApplicationContext()).getRequestQueue();

        eMail = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eMail.getText().toString();
                String pwd = password.getText().toString();
                loginUser(email, pwd);
            }
        });
    }

    private void loginUser(final String email, final String pwd) {
        String url = "http://10.0.2.2:8000/api/login";
        Log.d("login", "email & password" + email + pwd);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", "berhasil" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject sukses = obj.getJSONObject("success");
                            String token = sukses.getString("token");
                            Log.d("Response", "token" + token);
                            SharedPreferences sharedpref = getSharedPreferences("token", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpref.edit();
                            editor.putString("token", token);
                            editor.apply();
                            Intent moveIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(moveIntent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "error" + String.valueOf(error));
                        Toast.makeText(getApplicationContext(), "Email atau Password salah", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", pwd);

                return params;
            }
        };
        singleton.getInstance(this).addToRequestQueue(postRequest);
    }

}
