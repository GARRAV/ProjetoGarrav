package br.com.garrav.projetogarrav;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.model.User;
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

    //Variáveis Address Endereço
    private String latLng;
    private double latitude;
    private double longitude;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        //Set Name ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registro de Eventos");

        //Get Coordinates
        this.latLng = getIntent().getStringExtra("coordinates");

        //Adapter Coordinates
        EventTextValidator etv = new EventTextValidator();

        //Latitude Adapter
        this.latitude = etv.valLatitude(this.latLng);
        //Longitude Adapter
        this.longitude = etv.valLongitude(this.latLng);

        //Find Address
        LocationUtil lu = new LocationUtil();
        address = lu.seekAddress(
                this,
                this.latitude,
                this.longitude
        );

        //Init Address EditText
        this.etAddressEvent = findViewById(R.id.etAddressEvent);

        //Set EditText Address
        this.etAddressEvent.setText(
                this.address.getAddressLine(0)
        );

        //Init Time Date
        this.etTimeEvent = findViewById(R.id.etTimeEvent);

        /*
        Quando clicado no EditText etDateEvent, abrirá um calendário
        para a seleção de data do Evento
         */
        this.etDateEvent = findViewById(R.id.etDateEvent);
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
                //Linha que altera a cor de fundo do calendário
                /*dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));*/

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
        Quando clicado no EditText etTimeEvent, abrirá um TimePicker
        para a seleção de horários do Evento
         */
        this.etTimeEvent.setOnClickListener(new View.OnClickListener() {
            /**
             *
             *
             * @param view
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
             *
             * @param timePicker
             * @param hour
             * @param minute
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
            Instância dos dados do Evento
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
            cal.set(yearD,
                    monthD,
                    dayD,
                    hourD,
                    minuteD
            );
            Date date;
            date = cal.getTime();
            event.setDateEvent(date);

            /*
            Inicio do Envio para servidor
             */
            //Conversão para Json
            Gson gson = new Gson();
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
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Logger.getLogger(t.getMessage());
                }
            });
        }

    }
}
