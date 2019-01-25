package br.com.garrav.projetogarrav;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.model.Event_User;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.retrofitServerService.EventServerService;
import br.com.garrav.projetogarrav.retrofitServerService.Event_UserServerService;

public class EventPresenceListActivity extends AppCompatActivity {

    private ListView lvEventList;
    private ProgressDialog progressDialog;
    private List<Event> lstPresenceEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_presence_list);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Lista de Presença em Eventos");

        //Show Progress Dialog
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Carregando...");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();

        //Get Static Presence List Event_User from Server - Reforce
        Event_UserServerService.getEventUserPresenceFromServer(
                this,
                User.getUniqueUser().getId()
        );

        /*
        Get Static Presence List Event from Server
            and
        Start loadEventPresenceList
            and
        Set List to lstPresenceEvent
         */
        EventServerService.getPresenceEventFromServer(
                this,
                Event_User.getUniqueListEvent_User(),
                this,
                progressDialog
        );

    }

    /**
     *
     * @author Felipe Savaris
     * @since 24/01/2019
     */
    public void loadEventPresenceList() {

    }

    /**
     *
     * @param lstPresenceEvent
     * @author Felipe Savaris
     * @since 24/01/2019
     */
    public void setLstPrEventList(List<Event> lstPresenceEvent) {
        this.lstPresenceEvent = lstPresenceEvent;
    }
}
