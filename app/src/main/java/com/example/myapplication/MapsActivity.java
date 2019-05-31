
package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Model.CountryDataSource;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String receivedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent mainActivityIntent= this.getIntent();
        receivedCountry=mainActivityIntent.getStringExtra(CountryDataSource.COUNTRY_KEY);

        if(receivedCountry==null)
        {
            receivedCountry=CountryDataSource.DEFAULT_COUNTRY_NAME;
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double CountryLatitude=CountryDataSource.DEFAULT_COUNTRY_LATITUDE;
        double CountryLongitude=CountryDataSource.DEFAULT_COUNTRY_LONGITUDE;

        CountryDataSource countryDataSource= MainActivity.countryDataSource;
        String countryMessage=countryDataSource.getTheInfo(receivedCountry);

        Geocoder geocoder= new Geocoder(MapsActivity.this);

        try {

            String countryAddress= receivedCountry;
            List<Address> countryAddresses= geocoder.getFromLocationName(countryAddress,10);

            if(countryAddress!=null)
            {
                CountryLatitude=countryAddresses.get(0).getLatitude();
                CountryLongitude=countryAddresses.get(0).getLongitude();

            }
            else
            {
                //receivedCountry=CountryDataSource.DEFAULT_COUNTRY_NAME;
                Toast.makeText(this,"Not recognized",Toast.LENGTH_SHORT).show();

            }



        }catch(Exception ioe)
        {
            receivedCountry=CountryDataSource.DEFAULT_COUNTRY_NAME;

        }





























//        // Add a marker in Sydney and move the camera
//        LatLng India = new LatLng(22.884004, 79.455990);
//
//        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(India,10.0f);
//        mMap.moveCamera(cameraUpdate);
//       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in India"));
//        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        MarkerOptions markerOptions= new MarkerOptions();
//        markerOptions.position(India);
//        markerOptions.title("Welcome To INDIA");
//        markerOptions.snippet("INCREDIBLE");
//        mMap.addMarker(markerOptions);
//
//        CircleOptions circleOptions= new CircleOptions();
//        circleOptions.center(India);
//        circleOptions.radius(600);
//        circleOptions.strokeWidth(20.f);
//        circleOptions.strokeColor(Color.BLUE);
//        mMap.addCircle(circleOptions);
//

        LatLng myCLocation= new LatLng(CountryLatitude,CountryLongitude);

        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(myCLocation,17.0f);
        mMap.moveCamera(cameraUpdate);

        MarkerOptions markerOptions= new MarkerOptions();
        markerOptions.position(myCLocation);
        markerOptions.title(countryMessage);
        markerOptions.snippet(CountryDataSource.DEFAULT_MESSAGE);
        mMap.addMarker(markerOptions);

        CircleOptions circleOptions= new CircleOptions();
        circleOptions.center(myCLocation);
        circleOptions.radius(400);
        circleOptions.strokeWidth(15);
        circleOptions.strokeColor(Color.CYAN);
        mMap.addCircle(circleOptions);



    }


}
