package com.radhay.vahagn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MapDemo extends AppCompatActivity {
    Spinner sp_Type;
    Button bt_Find;
    protected  static SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat=0,currentLng=0;
    Object[] objects = new Object[2];
    String Nul_demo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        sp_Type = findViewById(R.id.sp_type);
        bt_Find = findViewById(R.id.bt_find);
        supportMapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        //final String[] placeType = {"atm","bank","hospital","movie_theater","restaurant"};
        String[] placeName  = {"HOSPITAL"};
        System.out.println("null is printed or not "+Nul_demo);
        sp_Type.setAdapter(new ArrayAdapter<>(MapDemo.this,
                android.R.layout.simple_spinner_dropdown_item,placeName));
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        System.out.println("enter into maps");
                  getCurrentLocation();

             bt_Find.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     System.out.println("enter into location button find click");
                     //int i = sp_Type.getSelectedItemPosition();
                     if (isLocationEnabled()) {
                         final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                                 "location=" + currentLat + "," + currentLng +
                                 "&radius=100000" + "&types=hospital" +
                                 "&sensor=true" +
                                 "&key=" + getResources().getString(R.string.google_map_key);
                         map.clear();
                         System.out.println("map value is " + map);
                         objects[0] = map;
                         objects[1] = url;
                         new NearByPlaces().execute(objects);
                     }else {
                         Toast.makeText(MapDemo.this, "Please turn on location", Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                         startActivity(intent);
                     }
                 }
                 });
    }

    private void getCurrentLocation() {
        System.out.println("enter into location getCurrentLocation()");
       if (ActivityCompat.checkSelfPermission(MapDemo.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapDemo.this,Manifest.permission
        .ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
          fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
              @Override
              public void onComplete(@NonNull Task<Location> task) {
                  Location location = task.getResult();
                  if (location!=null){
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                      supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                          @Override
                          public void onMapReady(GoogleMap googleMap) {
                              map = googleMap;

                          //    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLng,currentLng),10));
 MarkerOptions markerOptions = new MarkerOptions();
                      LatLng latLng = new LatLng(currentLat,currentLng);
                      markerOptions.position(latLng);
                      markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                      map.addMarker(markerOptions);
                              System.out.println("enter into location");
                      map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                      map.animateCamera(CameraUpdateFactory.zoomTo(10));
                          }
                      });
                  }
              }
          });
    }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapDemo.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private  boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}