package br.com.garrav.projetogarrav;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.util.GsonUtil;
import br.com.garrav.projetogarrav.util.LocationUtil;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.validation.EventTextValidator;
import br.com.garrav.projetogarrav.ws.EventService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterEventActivity extends AppCompatActivity {

    //EditText's
    private EditText etNameEvent, etAddressEvent, etDateEvent, etTimeEvent, etObjectiveEvent;

    //Dialog's
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    //Variáveis Date e hora
    private int dayD;
    private int monthD;
    private int yearD;
    private int hourD;
    private int minuteD;
    private int secondD = 0;

    //Variáveis Coordenadas
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        //Set Name ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registro de Eventos");

        /*
        Inicio Tratamento Coordenadas
         */

        //Get Intent Coordinates
        //Variáveis Address Endereço
        String latLng = getIntent().getStringExtra("coordinates");
        //Adapter Coordinates
        EventTextValidator etv = new EventTextValidator();
        //Latitude Adapter
        this.latitude = etv.valLatitude(latLng);
        //Longitude Adapter
        this.longitude = etv.valLongitude(latLng);
        //Find Address by Coordinates
        LocationUtil lu = new LocationUtil();
        Address address = lu.seekAddress(
                this,
                this.latitude,
                this.longitude
        );
        //Init Address EditText
        this.etAddressEvent = findViewById(R.id.etAddressEvent);
        //Set EditText Address
        this.etAddressEvent.setText(
                address.getAddressLine(0)
        );
        /*
        Fim Tratamento Coordenadas
         */

        /*
        Inicio Tratamento Data
        */

        //Init DateEvent EditText
        this.etDateEvent = findViewById(R.id.etDateEvent);
        /*
        Quando clicado no EditText etDateEvent, abrirá um calendário
        para a seleção de data do Evento
        */
        this.etDateEvent.setOnClickListener(new View.OnClickListener() {
            /**
             * Método responsável por mostrar um calendário no dia atual
             * para o usuário escolher uma data para o evento que está
             * criando
             *
             * @param view Elemento utilizado para inicializar a ação
             * @author Felipe Savaris
             * @since 12/12/2018
             */
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                //Dia, Mês, Ano
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                //Calendário
                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterEventActivity.this,
                        mDateSetListener,
                        year, month, day
                );

                //Exibição do calendário
                dialog.show();
            }
        });
        /*
        Resultado da data após selecionada pelo usuário, separada por dia, mês e
        ano. Após resultado adiquirido, a data passará por filtros para se
        verificar se não é inválida
         */
        this.mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            /**
             * Método responsável de ser executado logo após o usuário ter
             * selecionado uma data para seu evento e fazer os devidos
             * filtros dessa data e aprova-lá ou não para seu uso e
             * exibição
             *
             * @param datePicker Variável que resgata a data do calendário gráfico
             * @param day Dia informado pelo usuário
             * @param month Mês informado pelo usuário
             * @param year Ano informado pelo usuário
             * @author Felipe Savaris
             * @since 12/12/2018
             */
            @Override
            public void onDateSet(DatePicker datePicker,
                                  int year,
                                  int month,
                                  int day) {

                //Filter Date
                EventTextValidator etv = new EventTextValidator();
                boolean boo = etv.valEventDate(
                        RegisterEventActivity.this,
                        day,
                        month,
                        year
                );

                //Resultado do filtro
                if(boo) {

                    //Apaga Textos presentes no campo Hora
                    if(!etTimeEvent.getText().toString().isEmpty()) {
                        etTimeEvent.setText("");
                    }

                    //Set Variáveis
                    dayD = day;
                    monthD = month;
                    yearD = year;

                    month = month + 1;

                    //Set EditText
                    String date = day + "/" + month + "/" + year;
                    etDateEvent.setText(date);
                }
            }
        };
        /*
        Fim Tratamento Data
         */

        /*
        Inicio Tratamento Hora
         */

        //Init Time Date
        this.etTimeEvent = findViewById(R.id.etTimeEvent);
        /*
        Quando clicado no EditText etTimeEvent, abrirá um TimePicker
        para a seleção de horários do Evento
         */
        this.etTimeEvent.setOnClickListener(new View.OnClickListener() {
            /**
             * Método responsável por exibir um relógio na hora atual
             * para o usuário escolher um horário para o evento que
             * está criando.
             * A data precisa estar obrigatóriamente preenchida para
             * usar o relógio
             *
             * @param view Elemento utilizado para inicializar a ação
             * @author Felipe Savaris
             * @since 17/12/2018
             */
            @Override
            public void onClick(View view) {

                //Verificador se a data já foi estabelecida
                if(etDateEvent.getText().toString().isEmpty()) {
                    MessageActionUtil.makeText(
                            RegisterEventActivity.this,
                            "Selecione um Data primeiro"
                    );
                    return;
                }

                Calendar cal = Calendar.getInstance();

                //Hora, Minuto
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                //TimePicker
                TimePickerDialog dialog = new TimePickerDialog(
                        RegisterEventActivity.this,
                        mTimeSetListener,
                        hour, minute,
                        true
                );

                //Exibição do TimePicker
                dialog.show();
            }
        });
        /*
        Resultado da hora após selecionada pelo usuário, separada por hora,
        e minuto. Após resultado adiquirido, a hora passará por filtros para se
        verificar se não é inválida
         */
        this.mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            /**
             * Método responsável de ser executado logo após o usuário ter
             * selecionado um horario para seu evento e fazer os devidos
             * filtros dessa data e aprova-lá ou não para seu uso e
             * exibição.
             *
             * @param timePicker Variável que resgata a data do relógio gráfico
             * @param hour Hora informado pelo usuário
             * @param minute Minuto informado pelo usuário
             * @author Felipe Savaris
             * @since 17/12/2018
             */
            @Override
            public void onTimeSet(TimePicker timePicker,
                                  int hour,
                                  int minute) {

                //Filter Time
                EventTextValidator etv = new EventTextValidator();
                boolean boo = etv.valEventTime(
                        RegisterEventActivity.this,
                        dayD,
                        monthD,
                        yearD,
                        hour
                );

                //Resultado do Filtro
                if(boo) {

                    //Set Variáveis
                    hourD = hour;
                    minuteD = minute;

                    String time = hour + ":" + minute;

                    etTimeEvent.setText(time);
                }
            }
        };
        /*
        Fim tratamento hora
         */
    }


    /**
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 12/12/2018
     */
    public void btRegisterEvent(View view) {

        //Init EditText's
        this.etNameEvent = findViewById(R.id.etNameEvent);
        this.etDateEvent = findViewById(R.id.etDateEvent);
        this.etTimeEvent = findViewById(R.id.etTimeEvent);
        this.etObjectiveEvent = findViewById(R.id.etObjectiveEvent);

        //Instância de novo evento e seu validador de texto
        EventTextValidator etv = new EventTextValidator();

        //Validador de dados do Evento
        boolean boo = etv.valEventData(
                this,
                this.etNameEvent.getText().toString(),
                this.etDateEvent.getText().toString(),
                this.etTimeEvent.getText().toString(),
                this.etObjectiveEvent.getText().toString()
        );

        //Resultado do filtro - True = Envio de dados ao servidor
        if(boo) {

            /*
            Inicio dos Set's dos dados do Evento na instância
             */
            Event event = new Event();
            //Set Id_user
            event.setId_user(User.getUniqueUser().getId());
            //Set Name Event
            event.setName(this.etNameEvent.getText().toString());
            //Set Objective Event
            event.setObjective(this.etObjectiveEvent.getText().toString());
            //Set Latitude & Longitude
            event.setLatitude(this.latitude);
            event.setLongitude(this.longitude);
            //Set Date & Time Event
            Calendar cal = Calendar.getInstance();
            cal.set(
                    this.yearD,
                    this.monthD,
                    this.dayD,
                    this.hourD,
                    this.minuteD,
                    this.secondD
            );
            Date date = cal.getTime();
            event.setDateEvent(date);
            /*
            Fim dos Set's dos dados do Evento na instância
             */

            /*
            Inicio do Envio para servidor
             */
            //Conversão para Json + Serializer para Datas
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, GsonUtil.DATE_SERIALIZER)
                    .create();
            //JSON
            String json = gson.toJson(event);

            //Ação Retrofit - Servidor
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            //Definições Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitUtil.getUrlServer())
                    .client(client)
                    .build();

            //Resgata o link que fará o service com a API
            EventService es = retrofit.create(EventService.class);

            //Corpo do JSON que irá ao servidor
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

            //Métodos da Requisição da API
            es.postJsonEvent(requestBody).enqueue(new Callback<ResponseBody>() {
                /**
                 *
                 * @param call
                 * @param response
                 */
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {

                    MessageActionUtil.makeText(
                            RegisterEventActivity.this,
                            "Evento salvo com sucesso!"
                    );

                    //Mudança de Activity -> MapsEventsActivity
                    Intent it = new Intent(
                            RegisterEventActivity.this,
                            MapsEventsActivity.class
                    );
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(it);
                }

                /**
                 *
                 * @param call
                 * @param t
                 */
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Logger.getLogger(t.getMessage());
                }
            });
        }

    }
}
