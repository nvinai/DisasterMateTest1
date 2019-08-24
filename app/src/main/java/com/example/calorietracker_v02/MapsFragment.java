package com.example.calorietracker_v02;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    View vMaps;
    GoogleMap gmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMaps = inflater.inflate(R.layout.activity_maps, container, false);
        MapFragment maps = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        ((MapFragment) maps).getMapAsync(this);


        return vMaps;
    }

    @Override
    public void onMapReady(GoogleMap    googleMap) {
        gmap = googleMap;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String address = sharedPreferences.getString("address",null);
        String postcode = sharedPreferences.getString("postCode",null);
        Geocoder geocoder = new Geocoder(vMaps.getContext());

        try {

            List<Address> addresses =  geocoder.getFromLocationName(address,1);
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();
            LatLng latLng = new LatLng(latitude ,longitude );


            gmap.addMarker(new MarkerOptions().position(latLng));
            //to zoom to the required location
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));

            Parks parks = new Parks();
            String park = "park";
            parks.execute(latitude + " " + longitude + " " + 5000 + " " + park);





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Parks extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String latitude = params[0].split(" ")[0];
                String longitude = params[0].split(" ")[1];
                String meters = params[0].split(" ")[2];
                String type = params[0].split(" ")[3];

                return GoogleMapsRESTClient.getParkJSON (latitude,longitude,meters,type);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            //null
           List<Park> parks = GoogleMapsRESTClient.getParks(result);
           for(Park park :parks){
            LatLng latLng = new LatLng(park.getLatitude(),park.getLongitude());
            String snippet = park.getName();
            gmap.addMarker(new MarkerOptions().position(latLng).title(snippet).snippet(park.getVicinity()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

           }




        }

    }



}
