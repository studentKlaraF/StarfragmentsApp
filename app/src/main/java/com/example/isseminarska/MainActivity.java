package com.example.isseminarska;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
    private String url = "https://starfragments-is-dev.azurewebsites.net/api/v1/ArtikelApi";

    public static final String EXTRA_MESSAGE = "com.example.isSeminarska.MESSAGE";

    public void addArtikelActivity (View view) {
        Intent intent = new Intent(this,AddArtikelActivity.class);
        String message = "Dodaj artikel v seznam.";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        artikli = (TextView) findViewById(R.id.artikelID);
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
                    JSONObject object =response.getJSONObject(i);
                    String ime = object.getString("naziv");
                    String cena = object.getString("cena");
                    //String trgovina = object.getString("trgovina");

                    data.add(ime + " " + cena + " €");

                } catch (JSONException e){
                    e.printStackTrace();
                    return;

                }
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
