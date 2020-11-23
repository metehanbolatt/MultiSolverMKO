package com.deu.multisolvermko;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.deu.multisolvermko.problemler.gsmaps.FlyHesaplama;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    Integer sehirSayisi;
    TextView mapsTextView;
    ArrayList<String> latArrayList,lonArrayList;
    int a = 0 ;
    int[] distances;
    Python py;
    PyObject pyobj;

    ArrayList<Double> latdouble,londouble;
    ArrayList<Float> goToPy;
    Location loc1,loc2;
    Integer size ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        py = Python.getInstance();
        pyobj = py.getModule("gezginfly");

        mapsTextView = findViewById(R.id.maps_textView);

        latdouble = new ArrayList<>();
        londouble = new ArrayList<>();

        goToPy = new ArrayList<>();

        size = latdouble.size();
        distances = new int[size*size];

        Intent intent = getIntent();
        sehirSayisi = intent.getIntExtra("sehir",1);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latArrayList = new ArrayList<>();
        lonArrayList = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        if (a < sehirSayisi ){
            mMap.addMarker(new MarkerOptions().position(latLng).title("AAA"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            Double lat = latLng.latitude;
            Double lon = latLng.longitude;

            String latstr = lat.toString();
            String lonstr = lon.toString();

            latArrayList.add(latstr);
            lonArrayList.add(lonstr);


            if (a == sehirSayisi-1){
                new CountDownTimer(2000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        Toast.makeText(MapsActivity.this, "Hesaplanıyor", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        hesapla();
                        arrayToDizi();

                        PyObject obj = pyobj.callAttr("main123", distances);
                        mapsTextView.setText(obj.toString());


                    }
                }.start();

            }

        }else{
            Toast.makeText(this, "Sehir sayisini aştınız.", Toast.LENGTH_SHORT).show();
        }

        a=a+1;

    }

    public void hesapla(){
        for (int c =1 ; c<=size; c++){
            for (int d =1; d<=size ; d++){

                loc1 = new Location("");
                loc1.setLatitude(latdouble.get(c-1));
                loc1.setLongitude(londouble.get(c-1));

                loc2 = new Location("");
                loc2.setLatitude(latdouble.get(d-1));
                loc2.setLongitude(londouble.get(d-1));

                float distance = loc1.distanceTo(loc2);

                goToPy.add(distance);

            }
        }
    }

    public void arrayToDizi(){
        for (int i =0; i<size*size;i++){
            distances[i]=Math.round(goToPy.get(i));
        }
    }
}