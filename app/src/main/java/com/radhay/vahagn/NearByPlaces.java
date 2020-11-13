package com.radhay.vahagn;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NearByPlaces extends AsyncTask<Object,String,String> {
    private String googleplaceData,url;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {
    mMap = (GoogleMap)objects[0];
    ///        String data = strings[0];
        url = (String) objects[1];
        System.out.println("enter into nearbyplace doinbackground map "+mMap+"  url is in doinbacjground : "+url);
    DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleplaceData = downloadUrl.ReadTheUrl(url);
            System.out.println("enter into nearbyplace doinbackground googleplacedata  "+googleplaceData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleplaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        DataParser dataParser = new DataParser();
        System.out.println("enter onpostexecute method of nearbyplace");
        List<HashMap<String, String>> nearByPlacesList = dataParser.parse(s);
        System.out.println("after parse ready enter into display");
        DisplayNearByPlaces(nearByPlacesList);
    }

    public void DisplayNearByPlaces(List<HashMap<String,String>> nearByPlacesList){
        System.out.println("enter display,ethosd method of nearbyplace");
        for (int i=0;i<nearByPlacesList.size();i++){
            System.out.println("enter for loop");
            MarkerOptions markerOptions = new MarkerOptions();
            System.out.println("enter markeroption method of nearbyplace");
            HashMap<String,String> googleNearbyPlace = nearByPlacesList.get(i);
            System.out.println("enter nearbyplace list method of nearbyplace");
             String nameOfPlace = googleNearbyPlace.get("place_name");
            System.out.println("enter displace method of nearbyplace "+nameOfPlace);
            String vicinity = googleNearbyPlace.get("vicinity");
            System.out.println("enter displace method of nearbyplace "+vicinity);
             double lat= Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlace.get("lng"));
                       System.out.println("enter displace method of nearbyplace "+lat+","+lng);
                    LatLng latLng = new LatLng(lat,lng);
                    markerOptions.position(latLng);
                    //set title
                    System.out.println("enter displaynearbyplace method of nearbyplace");
                    markerOptions.title(nameOfPlace+" : "+vicinity);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                }
            }
}
