package com.josea.mymangalist.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.josea.mymangalist.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class LocationActivity extends AppCompatActivity {
    TextView latitud, longitud, direccion, latitud_title, longitud_title, direccion_title;
    LocationManager locationManager;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        latitud = (TextView) findViewById(R.id.txtLatitud);
        longitud = (TextView) findViewById(R.id.txtLongitud);
        direccion = (TextView) findViewById(R.id.txtDireccion);

        latitud_title = (TextView) findViewById(R.id.latitud_title);
        longitud_title = (TextView) findViewById(R.id.longitud_title);
        direccion_title = (TextView) findViewById(R.id.direccion_title);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();
            setLocation(location);
            latitud.setText(String.valueOf(location.getLatitude()));
            longitud.setText(String.valueOf(location.getLongitude()));
            progressBar.setVisibility(View.INVISIBLE);
            latitud_title.setVisibility(View.VISIBLE);
            longitud_title.setVisibility(View.VISIBLE);
            direccion_title.setVisibility(View.VISIBLE);
        }

        public void onStatusChanged(String provider, int status, Bundle extra) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


    private void locationStart() {
        if (ContextCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, // Proveedor localización
                    0, // Intervalo mínimo entre eventos (milisegundos)
                    0, // Distancia mínima entre eventos (metros)
                    locationListener);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}