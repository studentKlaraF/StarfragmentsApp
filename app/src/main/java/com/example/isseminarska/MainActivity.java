package com.example.isseminarska;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView artikli;

    private String url = "https://starfragments-is-dev.azurewebsites.net/api/ArtikelApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        artikli = (TextView) findViewById(R.id.artiklov);
    }
    public  void prikaziArtikle(View view){
        if (view != null){
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
            requestQueue.add(request);
        }
    }
    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject object = response.getJSONObject(i);
                    String ime = object.getString("naziv");
                    String cena = object.getString("cena");
                    String trgovina = object.getString("trgovina");

                    data.add(ime + " " + cena + " " + trgovina);

                } catch (JSONException e){
                    e.printStackTrace();
                    return;                }
            }
            artikli.setText("");
            for (String row: data){
                String currentText = artikli.getText().toString();
                artikli.setText(currentText + "\n\n" + row);
            }
        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };
}