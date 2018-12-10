package br.com.garrav.projetogarrav;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.PermissionUtil;

public class MapsFragment
        extends SupportMapFragment
        implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private final int REQUEST_PERMISSIONS_CODE = 128;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * @author Felipe Savaris
     * @since 06/12/2018
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            mMap = googleMap;

            mMap.setOnMapClickListener(this);

            // Adiciona um marcador na Unioeste de Toledo - PR
            LatLng unioesteLocation = new LatLng(-24.724407, -53.752796);

            MarkerOptions marker = new MarkerOptions();
            marker.position(unioesteLocation);
            marker.title("Localização da Unioeste - Toledo PR");

            mMap.addMarker(marker);

            //Verifica se as permissões necessárias estão ativadas
            int permission = ContextCompat.checkSelfPermission(
                    getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
            );

            //Pedido da permissão, caso não ativo
            if (permission == -1) {
                PermissionUtil.callAccessFine_CoarseLocation(
                        getActivity(),
                        REQUEST_PERMISSIONS_CODE
                );
            }

            mMap.setMyLocationEnabled(true);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unioesteLocation, 16.5f));
        } catch (SecurityException ex) {
            Log.d("MapsFragment", "Error", ex);
        }
    }

    /**
     *
     * @param latLng
     * @author Felipe Savaris
     * @since 07/12/2018
     */
    @Override
    public void onMapClick(LatLng latLng) {

        //Mensagem teste
        MessageActionUtil.makeText(
                getContext(),
                "Coordenadas(lat/lng): " + latLng.toString()
        );

    }
}
