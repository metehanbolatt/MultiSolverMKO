package com.deu.multisolvermko.problems.travellingsalesmanmaps;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.deu.multisolvermko.animations.ProgressBarAnimation;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.createlibrary.CreateLibraryActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FlySalesmanMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    Integer numberOfCities;
    TextView mapsTextView;
    int a = 0 ;
    int b = 0;
    int[] distances;
    Python py;
    PyObject pyobj;
    ProgressBar progressBar,progressBar2;

    ArrayList<Double> latdouble,londouble;
    ArrayList<Float> goToPy;
    Location loc1,loc2;
    ImageView imageMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_salesman_maps);

        final Intent intent = getIntent();

        numberOfCities = intent.getIntExtra("city",1);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        py = Python.getInstance();
        pyobj = py.getModule("flySalesman");

        mapsTextView = findViewById(R.id.maps_textView);
        progressBar = findViewById(R.id.progress_bar_maps);
        progressBar2 = findViewById(R.id.progress_bar_maps2);

        imageMaps = findViewById(R.id.imageMaps);
        imageMaps.setVisibility(View.INVISIBLE);

        progressBar2.setVisibility(View.INVISIBLE);

        progressBar.setMax(100);
        progressBar.setScaleY(3.f);

        latdouble = new ArrayList<>();
        londouble = new ArrayList<>();

        goToPy = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        imageMaps.setOnClickListener(v -> {
                Intent intent1 = new Intent(getApplicationContext(), CreateLibraryActivity.class);
                startActivity(intent1);
        });
    }

    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLongClick(@NotNull LatLng latLng) {

        if (a < numberOfCities){
            String explanation = a+". Konum";
            Objects.requireNonNull(mMap.addMarker(new MarkerOptions().position(latLng).title(explanation))).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            Double lat = latLng.latitude;
            Double lon = latLng.longitude;

            latdouble.add(lat);
            londouble.add(lon);

            if (a == numberOfCities -1){

                progressBar.setVisibility(View.VISIBLE);

                mapsTextView.setText("0 %");
                progressAnimation2();

                ExampleThread2 thread2 = new ExampleThread2();
                thread2.start();

                mapsTextView.addTextChangedListener(new TextWatcher() {
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
                        imageMaps.setVisibility(View.VISIBLE);

                    }
                });
            }
        }else{
            afterCalculateValue();
        }
        a=a+1;
    }

    public void calculation(){
        for (int c = 1; c<= numberOfCities; c++){
            for (int d = 1; d<= numberOfCities; d++){

                loc1 = new Location("");
                loc1.setLatitude(latdouble.get(c-1));
                loc1.setLongitude(londouble.get(c-1));

                loc2 = new Location("");
                loc2.setLatitude(latdouble.get(d-1));
                loc2.setLongitude(londouble.get(d-1));

                float distance = (loc1.distanceTo(loc2)/1000);

                goToPy.add(distance);

            }
        }
    }

    public void arrayToArray(){
        distances = new int[numberOfCities * numberOfCities];
        for (int i = 0; i< numberOfCities * numberOfCities; i++){
            distances[i]=Math.round(goToPy.get(i));
        }
    }

    class ExampleThread2 extends Thread{
        @Override
        public void run() {
            calculation();
            arrayToArray();
            Python py = Python.getInstance();
            final PyObject pyobj = py.getModule("flySalesman");
            final PyObject obj = pyobj.callAttr("flyFunction", distances);
            System.out.println(Arrays.toString(distances));

            runOnUiThread(() -> mapsTextView.setText(obj.toString()));
        }
    }

    public void progressAnimation2(){
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar,progressBar2,mapsTextView,0,100f);
        anim.setDuration(2000);
        progressBar.setAnimation(anim);
    }

    public void afterCalculateValue() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_salesman_maps_fly_calculation_done, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}