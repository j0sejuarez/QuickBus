package com.example.quickbus;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoutesMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText originInput;
    private EditText destinationInput;
    private Button btnGo;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rutas);

        originInput = findViewById(R.id.originInput);
        destinationInput = findViewById(R.id.destinationInput);
        btnGo = findViewById(R.id.btnGo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnGo.setOnClickListener(v -> {
            String origin = originInput.getText().toString();
            String destination = destinationInput.getText().toString();

            if (!TextUtils.isEmpty(origin) && !TextUtils.isEmpty(destination)) {
                getDirections(origin, destination);
            } else {
                Toast.makeText(this, "Please enter both origin and destination addresses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentLocationAndSetOrigin() {
        try {
            Task<android.location.Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<android.location.Location>() {
                @Override
                public void onComplete(@NonNull Task<android.location.Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        android.location.Location location = task.getResult();
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        // Agregar marcador de la ubicación actual
                        mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                        // Convertir la ubicación actual en dirección
                        getAddressFromLocation(location.getLatitude(), location.getLongitude());
                    } else {
                        Toast.makeText(RoutesMap.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressLine = address.getAddressLine(0);
                // Configurar la dirección obtenida como origen
                originInput.setText(addressLine);
            } else {
                Toast.makeText(this, "No address found for current location", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDirections(String origin, String destination) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBx2WbJ55LPgMwEGr0mD6kFhRaP_PmvV6s")
                .build();

        DirectionsApiRequest request = DirectionsApi.getDirections(context, origin, destination);
        request.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                if (result.routes != null && result.routes.length > 0) {
                    for (DirectionsRoute route : result.routes) {
                        List<com.google.maps.model.LatLng> path = route.overviewPolyline.decodePath();
                        List<LatLng> decodedPath = new ArrayList<>();
                        for (com.google.maps.model.LatLng latLng : path) {
                            decodedPath.add(new LatLng(latLng.lat, latLng.lng));
                        }
                        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocationAndSetOrigin(); // Obtener la ubicación actual y establecerla como origen
    }
}
