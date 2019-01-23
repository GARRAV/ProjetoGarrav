package br.com.garrav.projetogarrav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.retrofitServerService.EventServerService;
import br.com.garrav.projetogarrav.retrofitServerService.Event_UserServerService;
import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class MapsEventsActivity extends AppCompatActivity
        implements EventIteractorFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private static Fragment FRAG_EVENT_INTERACTOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_events);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarMaps);
        setSupportActionBar(toolbar);

        //Resgate de Lista de Eventos via Servidor para Google Maps
        EventServerService.getEventsFromServer(this);
        //Resgate de Lista de Eventos já cadastrado como Presente
        Event_UserServerService.getEventUserPresenceFromServer(
                this,
                User.getUniqueUser().getId()
        );

        //Instância do Fragment Google Maps
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.add(R.id.flMapsContainer, new MapsFragment(), "MapsFragment");
        transaction.commitAllowingStateLoss();

        //Init Fragment Event
        FRAG_EVENT_INTERACTOR = this.fragmentManager.findFragmentById(R.id.fragEventInteractor);
        FRAG_EVENT_INTERACTOR.getView().setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {

        if(MapsFragment.EVENT_REGISTER) MapsFragment.EVENT_REGISTER = false;

        //Fecha a barra lateral se aberta pelo botão retornar
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            //Fecha o Fragment caso aberto
        } else if(FRAG_EVENT_INTERACTOR.getView().getVisibility() == View.VISIBLE) {
            FRAG_EVENT_INTERACTOR.getView().setVisibility(View.GONE);

        } else super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction() {
        Log.i("FragEvent", "Fragment Active");
    }

    /**
     * Método responsável por ativar o Floating Action Button
     *
     * @param view Elementos do XML
     */
    public void fabRegisterNewEvent(View view) {

        MapsFragment.EVENT_REGISTER = true;

        //Mensagem para criação de evento
        MessageActionUtil.makeText(
                this,
                "Selecione o local do evento"
        );
    }

    /**
     * Método responsável por retornar o controle do Fragment
     *
     * @return Fragment para interação
     */
    public static Fragment getFragEventInteractor() {
        return FRAG_EVENT_INTERACTOR;
    }
}
