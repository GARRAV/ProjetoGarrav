package br.com.garrav.projetogarrav;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.util.PermissionUtil;

public class MapsFragment
        extends SupportMapFragment
        implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private final int REQUEST_PERMISSIONS_CODE = 128;
    public static boolean EVENT_REGISTER = false;

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
            mMap.setOnMarkerClickListener(this);

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

            /*Ativa o GPS
                Opção de criação de evento por GPS não ativa no momento
             */
            if(!mMap.isMyLocationEnabled()) {
                mMap.setMyLocationEnabled(true);
            }

            //Localização da Unioeste Toledo - PR
            LatLng unioesteLocation = new LatLng(-24.724407, -53.752796);

            for(int i = 0; i < Event.getUniqueListEvents().size(); i++) {
                Event event;

                event = Event.getUniqueListEvents().get(i);

                LatLng markerEvent = new LatLng(event.getLatitude(), event.getLongitude());
                MarkerOptions marker = new MarkerOptions();
                marker.position(markerEvent);
                marker.title("" + event.getId());
                mMap.addMarker(marker);
            }

            //Movimentação do mapa para a Unioeste + Zoom
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unioesteLocation, 16.5f));

        } catch (SecurityException ex) {
            Log.d("MapsFragment", "Error", ex);
        }
    }

    /**
     * Método responsável por receber a latitude e longitude
     * e levar para o cadastro de novos eventos
     *
     * @param latLng Latitude e Longitude
     * @author Felipe Savaris
     * @since 07/12/2018
     */
    @Override
    public void onMapClick(LatLng latLng) {

        if(EVENT_REGISTER) {
            //Mudança de Activity -> RegisterEventActivity
            Intent it = new Intent(
                    getContext(),
                    RegisterEventActivity.class
            );
            it.putExtra("coordinates", latLng.toString());
            startActivity(it);
            EVENT_REGISTER = false;
        }
    }

    /**
     * Método responsável por interagir com o marcador do Maps
     * e invocar o fragment para cadastro de presença. O método
     * só será invocado quando clicado pelo usuário
     *
     * @param marker Marcador do Maps selecionado
     * @return Resultado do Click
     * @author Felipe Savaris
     * @since 26/12/2018
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        //Get Marker Title
        String title = marker.getTitle();

        //Pega o evento correspondente ao Marker
        Event event = null;
        for(int i = 0; i < Event.getUniqueListEvents().size(); i++) {
            if(Event.getUniqueListEvents().get(i).getId() == Long.parseLong(title)) {
                event = Event.getUniqueListEvents().get(i);
                break;
            }
        }

        //Pega o Fragment
        Fragment frag = MapsEventsActivity.getFragEventInteractor();

        //Envia os dados para o Fragment exibir
        EventIteractorFragment eif = new EventIteractorFragment();
        eif.setEventData(
                frag.getView(),
                event
        );

        //Manda o Fragment ser Exibido
        frag.getView().setVisibility(View.VISIBLE);

        return true;
    }
}
