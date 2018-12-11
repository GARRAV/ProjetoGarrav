package br.com.garrav.projetogarrav;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.garrav.projetogarrav.data.EventTextValidator;
import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class RegisterEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registro de Eventos");

        //Código Teste
        String latLng;

        latLng = getIntent().getStringExtra("coordinates");

        Event event = new Event();
        EventTextValidator etv = new EventTextValidator();

        etv.valLatitudeLongitude(
                event,
                latLng
        );

        MessageActionUtil.makeText(
                this,
                event.getLatitude() + " " + event.getLongitude()
        );
    }
}
