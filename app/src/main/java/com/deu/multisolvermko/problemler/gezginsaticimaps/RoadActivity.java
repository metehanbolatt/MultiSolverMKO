package com.deu.multisolvermko.problemler.gezginsaticimaps;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.animations.ProgressBarAnimation;
import com.deu.multisolvermko.createnote.CreateNoteActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoadActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    TextView textView;
    Button button;
    Integer sehirSayisi;
    String origin = "";
    ArrayList<String> sehirListe;
    Geocoder geocoder;
    String url;
    List<Address> addressList;
    private RequestQueue mQueue;
    JSONArray text;
    JSONObject distance;
    ArrayList<Integer> distanceMatrix;
    int[] distances;
    int b = 0;
    ImageView imageMapsRoad;

    ProgressBar progressBar,progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road);

        textView = findViewById(R.id.textViewG);
        button = findViewById(R.id.buttonG);
        button.setVisibility(View.INVISIBLE);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        imageMapsRoad = findViewById(R.id.imageMapsRoad);
        imageMapsRoad.setVisibility(View.INVISIBLE);

        progressBar = findViewById(R.id.progress_bar_maps3);
        progressBar2 = findViewById(R.id.progress_bar_maps4);

        progressBar2.setVisibility(View.INVISIBLE);

        progressBar.setMax(100);
        progressBar.setScaleY(3.f);

        Intent intent = getIntent();
        sehirSayisi = intent.getIntExtra("sehir",1);

        distanceMatrix = new ArrayList<>();
        sehirListe = new ArrayList<>();
        addressList = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
                imageMapsRoad.setVisibility(View.VISIBLE);


                textView.setText("0 %");
                progressAnimation2();

                RoadActivity.ExampleThread4 thread4 = new ExampleThread4();
                thread4.start();

                textView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);

                    }
                });

            }
        });

        imageMapsRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CreateNoteActivity.class);
                startActivity(intent1);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void jsonParse(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");

                    for (int i = 0 ; i < jsonArray.length(); i++){
                        JSONObject rows = jsonArray.getJSONObject(i);
                        text = rows.getJSONArray("elements");
                        for (int y = 0; y<text.length();y++){
                            JSONObject elements = text.getJSONObject(y);
                            distance = elements.getJSONObject("distance");
                            int value = distance.getInt("value");
                            distanceMatrix.add(value);
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(RoadActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoadActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        mQueue.add(request);

    }

    public void arrayToDizi(){
        distances = new int[sehirSayisi*sehirSayisi];
        for (int i = 0; i<sehirSayisi*sehirSayisi;i++){
            distances[i]=distanceMatrix.get(i);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        if (b < sehirSayisi){

            origin += latLng.latitude + "," + latLng.longitude+ "|";
            mMap.addMarker(new MarkerOptions().position(latLng).title(b + ".Konum"));
            b = b+1;

            if (b == sehirSayisi){
                url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+origin+"&departure_time=now&key=AIzaSyDS63q3MP2Kh2B4J0cjzRiU2Hrr-HlrNps";
                jsonParse();
                Toast.makeText(this, "Veriler Alındı", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
    }

    class ExampleThread4 extends Thread{
        @Override
        public void run() {

            arrayToDizi();
            Python py = Python.getInstance();
            final PyObject pyobj = py.getModule("gezgin1");
            final PyObject obj = pyobj.callAttr("main1", distances);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(obj.toString());
                }
            });

        }
    }

    public void progressAnimation2(){
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar,progressBar2,textView,0,100f);
        anim.setDuration(2000);
        progressBar.setAnimation(anim);
    }
}