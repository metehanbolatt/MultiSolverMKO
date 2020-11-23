package com.deu.multisolvermko;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;
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

    ArrayList<String> latArrayList,lonArrayList;
    int a = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        sehirSayisi = intent.getIntExtra("sehir",1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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

                        Intent intent = new Intent(getApplicationContext(), FlyHesaplama.class);
                        intent.putStringArrayListExtra("latstr",latArrayList);
                        intent.putStringArrayListExtra("lonstr",lonArrayList);
                        intent.putExtra("deneme",5);
                        startActivity(intent);

                    }
                }.start();

            }

        }else{
            Toast.makeText(this, "Sehir sayisini aştınız.", Toast.LENGTH_SHORT).show();
        }

        a=a+1;

    }
}