package br.com.garrav.projetogarrav;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import br.com.garrav.projetogarrav.data.EventTextValidator;
import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
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

    //Variáveis do Evento
    private String latLng;
    private Date date;

    private RadioButton rbSupportEvent, rbSocialEvent;
    private EditText etNameEvent, etAddressEvent, etDateEvent, etObjectiveEvent;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registro de Eventos");

        /*Set das Coordenadas como endereço: TEMPORÁRIO ATÉ DESCOBRIR COMO
        converter as coordenadas em endereço real*/
        this.latLng = getIntent().getStringExtra("coordinates");
        this.etAddressEvent = findViewById(R.id.etAddressEvent);
        this.etAddressEvent.setText(latLng);

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


                    //Set Date Variable
                    Calendar cal = Calendar.getInstance();
                    cal.set(
                            year,
                            month,
                            day
                    );
                    date = cal.getTime();

                    //Set Calendar
                    month = month + 1;
                    cal.set(
                            year,
                            month,
                            day
                    );

                    //Set EditText
                    String date = day + "/" + month + "/" + year;
                    etDateEvent.setText(date);
                }
            }
        };
    }

    /**
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since  12/12/2018
     */
    public void btRegisterEvent(View view) {

        //Init Radio Button's
        this.rbSupportEvent = findViewById(R.id.rbSupportEvent);
        this.rbSocialEvent = findViewById(R.id.rbSocialEvent);

        //Verificador de RadioButton Caso os dois estejam desmarcados
        if(!this.rbSupportEvent.isChecked() && !this.rbSocialEvent.isChecked()) {
            MessageActionUtil.makeText(
                    this,
                    "Selecione um tipo de evento!"
            );
            return;
        }

        //Init EditText's
        this.etNameEvent = findViewById(R.id.etNameEvent);
        this.etDateEvent = findViewById(R.id.etDateEvent);
        this.etObjectiveEvent = findViewById(R.id.etObjectiveEvent);

        //Instância de novo evento e seu validador de texto
        Event event = new Event();
        EventTextValidator etv = new EventTextValidator();

        //Validador das coordenadas como endereço
        etv.valLatitudeLongitude(
                event,
                this.latLng
        );

        //Validador de dados do Evento
        boolean boo = etv.valEventData(
                this,
                this.etNameEvent.getText().toString(),
                this.etObjectiveEvent.getText().toString()
        );

        //Resultado do filtro - True = Envio de dados ao servidor
        if(boo) {

            //Instância do Evento
            event.setName(this.etNameEvent.getText().toString());
            event.setObjective(this.etObjectiveEvent.getText().toString());

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
