package com.tabukgym.tabukgym;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    notifyLocation(latLng);
                }
            });
        }
    };
    private void notifyLocation(LatLng latLng)
    {
        Log.d("location", latLng.longitude + "");
        //Success
        SweetAlertDialog pDialogSuccess = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        pDialogSuccess.setTitleText("Select this location");
        pDialogSuccess.setConfirmText("yes");
        pDialogSuccess.setCancelText("No");
        pDialogSuccess.setConfirmClickListener(sweetAlertDialog -> {
            CommonData.latitude=String.valueOf(latLng.latitude);
            CommonData.longitude=String.valueOf(latLng.longitude);
            pDialogSuccess.dismiss();
            getActivity().getSupportFragmentManager().beginTransaction().remove(MapsFragment.this).commit();
        });
        pDialogSuccess.setCancelClickListener(sweetAlertDialog ->
        {
            pDialogSuccess.dismiss();
        });
        pDialogSuccess.setCancelable(false);
        pDialogSuccess.show();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}