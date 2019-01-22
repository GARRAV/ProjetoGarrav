package br.com.garrav.projetogarrav;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.retrofitServerService.EventServerService;
import br.com.garrav.projetogarrav.retrofitServerService.Event_UserServerService;
import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class MapsEventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EventIteractorFragment.OnFragmentInteractionListener{

    private FragmentManager fragmentManager;
    private static Fragment FRAG_EVENT_INTERACTOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_events);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Chamado barra lateral
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Referência aos itens da barra lateral
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    /**
     * Método responsável por invocar e responder a barra lateral
     *
     * @param item Item selecionado da barra lateral
     * @author Felipe Savaris
     * @since 05/12/2018
     * @return Boolean da barra lateral
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Id da opção selecionada
        int id = item.getItemId();

        //Opções de seleção da barra lateral
        switch (id) {
            //Lista de Presença em Eventos
            case R.id.nav_event_presence :
                System.out.println("Teste");
                break;

            case R.id.nav_options :
                System.out.println("Opções");
                break;

            case R.id.nav_report_problem :
                System.out.println("Reportar um Problema");
                break;

            case R.id.nav_about :
                System.out.println("Sobre");
                break;

            case R.id.nav_logout :
                System.out.println("Sair");
                break;
        }

        //Fecha a barra lateral após item selecionado
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
