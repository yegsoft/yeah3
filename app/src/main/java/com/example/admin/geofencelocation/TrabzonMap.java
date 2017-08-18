/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.admin.geofencelocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.value;

/**
 * This shows how to close the info window when the currently selected marker is re-tapped.
 */
public class TrabzonMap extends AppCompatActivity implements
        OnMarkerClickListener,
        OnMapClickListener,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    String track;

    private static final LatLng AKCAABAT = new LatLng(41.022099, 39.570160);
    private static final LatLng SURMENE = new LatLng(40.911715, 40.118717);
    private static final LatLng MACKA = new LatLng(40.814207, 39.610737);
    private static final LatLng TONYA = new LatLng(40.886064, 39.290843);
    private static final LatLng HAMSIKOY = new LatLng(40.687377, 39.480178);

    private GoogleMap mMap = null;


    private Marker mSelectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trabzonmaplay);
        // tamamlanacak getGeofenceTrasitionDetails(Geofence.GEOFENCE_TRANSITION_ENTER,);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        new OnMapAndViewReadyListener(mapFragment, this);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Hide the zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Set listener for marker click event.  See the bottom of this class for its behavior.
        mMap.setOnMarkerClickListener(this);

        // Set listener for map click event.  See the bottom of this class for its behavior.
        mMap.setOnMapClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localized.
        map.setContentDescription("Demo showing how to close the info window when the currently"
            + " selected marker is re-tapped.");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(HAMSIKOY)
                .include(MACKA)
                .include(TONYA)
                .include(AKCAABAT)
                .include(SURMENE)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
    }

    public void addMarkersToMap() {
       mMap.addMarker(new MarkerOptions()
                .position(AKCAABAT)
                .title("Akçaabat Horonu")
                .snippet("Akçaabat"));


        mMap.addMarker(new MarkerOptions()
                .position(MACKA)
                .title("Maçka Yolları Taşlı")
                .snippet("Maçka"));

        mMap.addMarker(new MarkerOptions()
                .position(SURMENE)
                .title("Oy Çalamadum Gitti")
                .snippet("Sürmene"));

        mMap.addMarker(new MarkerOptions()
                .position(HAMSIKOY)
                .title("Hamsiköy")
                .snippet("Hamsiköy"));


        mMap.addMarker(new MarkerOptions()
                .position(TONYA)
                .title("Tonya")
                .snippet("Tonya"));
    }

    @Override
    public void onMapClick(final LatLng point) {
        // Any showing info window closes when the map is clicked.
        // Clear the currently selected marker.
        mSelectedMarker = null;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // The user has re-tapped on the marker which was already showing an info window.
        if (marker.equals(mSelectedMarker)) {

            track=marker.getSnippet();


            Intent in = new Intent(this, ActionBarDemoActivity.class);
            String intToSend = track;
            in.putExtra("location", intToSend);
            startActivity(in);
            mSelectedMarker = null;
            return true;
        }

        mSelectedMarker = marker;

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur.
        return false;
    }
}
