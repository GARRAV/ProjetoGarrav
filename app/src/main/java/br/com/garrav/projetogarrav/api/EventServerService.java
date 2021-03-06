package br.com.garrav.projetogarrav.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import br.com.garrav.projetogarrav.EventPresenceListActivity;
import br.com.garrav.projetogarrav.MapsEventsActivity;
import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.model.Event_User;
import br.com.garrav.projetogarrav.util.GsonUtil;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.EventService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventServerService {

    /**
     * Método responsável por enviar uma requisição GET para o
     * servidor e retornar uma lista de Eventos a partir da
     * data atual
     *
     * @param context Contexto da atual activity em execução do android
     * @author Felipe Savaris
     * @since 24/12/2018
     */
    public static void getEventsFromServer(final Context context) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .addConverterFactory(RetrofitUtil.getConverterFactory())
                .build();

        //Resgata o link que fará o service com a API
        EventService es = retrofit.create(EventService.class);

        //Faz conexão com a API
        Call<JsonArray> callEventList =
                es.getJsonEvent();

        //Métodos da Requisição da API
         callEventList.enqueue(new Callback<JsonArray>() {
             /**
              * Método responsável por entregar a lista de eventos
              * advindas do servidor
              *
              * @param call Chamado da API do servidor
              * @param response Resposta do Servidor
              * @author Felipe Savaris
              * @since 24/12/2018
              */
            @Override
            public void onResponse(@NonNull Call<JsonArray> call,
                                   @NonNull Response<JsonArray> response) {

                assert response.body() != null;

                //JSON
                String eventsString = response.body().toString();
                //Type Converter
                Type listType = new TypeToken<List<Event>>(){}.getType();

                //Lista de Eventos
                List<Event> listTmp = getEventListFromJson(eventsString, listType);

                //Set Lista de Events
                Event.setUniqueListEvents(listTmp);
            }

             /**
              * Método invocado se a conexão com o servidor não for bem
              * sucedida, retornando uma mensagem de erro que mostra
              * o que aconteceu para a conexão não ter sido feita
              *
              * @param call Chamado da API do servidor
              * @param t Erro ocorrido durante o chamado
              * @author Felipe Savaris
              * @since 24/12/2018
              */
            @Override
            public void onFailure(Call<JsonArray> call,
                                  Throwable t) {
                MessageActionUtil.makeText(
                        context,
                        t.getMessage()
                );
            }
        });
    }

    /**
     * Método responsável por enviar uma requisição POST para o
     * servidor e cadastrar um novo evento
     *
     * @param context Contexto da atual activity em execução do android
     * @param event Instância do evento a ser cadastrada
     * @author Felipe Savaris
     * @since 29/12/2018
     */
    public static void postEventRegisterToServer(final Context context,
                                                 Event event) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .build();

        //GSON + Serializer
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, GsonUtil.DATE_SERIALIZER)
                .create();

        //JSON
        String json = gson.toJson(event);

        //Resgata o link que fará o service com a API
        EventService es = retrofit.create(EventService.class);

        //Corpo do JSON que irá ao servidor
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        //Métodos da Requisição da API
        es.postJsonEvent(requestBody).enqueue(new Callback<ResponseBody>() {
            /**
             * Método responsável por entregar a resposta do servidor
             * para saber se a requisição foi bem sucedida
             *
             * @param call Chamado da API do servidor
             * @param response Resposta do Servidor
             * @author Felipe Savaris
             * @since 13/12/2018
             */
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                MessageActionUtil.makeText(
                        context,
                        "Evento salvo com sucesso!"
                );

                //Mudança de Activity -> MapsEventsActivity
                Intent it = new Intent(
                        context,
                        MapsEventsActivity.class
                );
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(it);
            }

            /**
             * Método invocado se a conexão com o servidor não for bem
             * sucedida, retornando uma mensagem de erro que mostra
             * o que aconteceu para a conexão não ter sido feita
             *
             * @param call Chamado da API do servidor
             * @param t Erro ocorrido durante o chamado
             * @author Felipe Savaris
             * @since 13/12/2018
             */
            @Override
            public void onFailure(Call<ResponseBody> call,
                                  Throwable t) {

                MessageActionUtil.makeText(
                        context,
                        "Não foi possivel cadastrar o evento: "
                                + t.getMessage()
                );
            }
        });
    }

    /**
     * Método responsável por enviar uma requisição GET ao servidor
     * e retornar uma List de eventos que o usuário já tenha sido
     * registrado pelo usuário.
     *
     * @param context Contexto da atual activity em execução do android
     * @param lstPresenceUser Lista de Id's dos eventos do usuário
     * @param instance Intância de {@link EventPresenceListActivity}
     * @param dialog Dialog de Carregamento
     * @author Felipe Savaris
     * @since 24/01/2019
     */
    public static void getPresenceEventFromServer(final Context context,
                                                  List<Event_User> lstPresenceUser,
                                                  final EventPresenceListActivity instance,
                                                  final ProgressDialog dialog) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .addConverterFactory(RetrofitUtil.getConverterFactory())
                .build();

        //Gson
        Gson gson = new Gson();
        String json = gson.toJson(lstPresenceUser);

        //Restaga o link que fará o service com a API
        EventService es = retrofit.create(EventService.class);

        //Faz a conexão com a API
        Call<JsonArray> callPresenceEventList
                = es.getJsonPresenceEvent(json);

        //Métodos da requisição com a API
        callPresenceEventList.enqueue(new Callback<JsonArray>() {
            /**
             * Método responsável por entregar a lista de eventos
             * advindas do servidor e desligar o Dialog de carregamento
             *
             * @param call Chamado da API do servidor
             * @param response Resposta do Servidor
             * @author Felipe Savaris
             * @since 24/01/2019
             */
            @Override
            public void onResponse(Call<JsonArray> call,
                                   Response<JsonArray> response) {

                if(response.body() == null) {
                    //Dismiss ProgressDialog
                    if(dialog.isShowing()) {
                        dialog.dismiss();

                        //Load Adapter
                        instance.loadEventPresenceList();
                    }
                } else {

                    //Json
                    String json = response.body().toString();

                    //Type Converter
                    Type listType = new TypeToken<List<Event>>() {}.getType();

                    //Lista de Eventos
                    List<Event> listTmp = getEventListFromJson(json, listType);

                    //Set List
                    instance.setLstPrEventList(listTmp);

                    //Dismiss ProgressDialog
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    //Load Adapter
                    instance.loadEventPresenceList();
                }
            }

            /**
             * Método invocado se a conexão com o servidor não for bem
             * sucedida, retornando uma mensagem de erro que mostra
             * o que aconteceu para a conexão não ter sido feita
             *
             * @param call Chamado da API no servidor
             * @param t Erro Ocorrido no durante o chamada
             * @author Felipe Savaris
             * @since 24/01/2019
             */
            @Override
            public void onFailure(Call<JsonArray> call,
                                  Throwable t) {

                //Error Message
                MessageActionUtil.makeText(
                        context,
                        t.getMessage()
                );

                //Dismiss ProgressDialog
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });
    }

    /**
     * Método responsável por converter um array advindo
     * do servidor e torna-lô uma lista
     *
     * @param jsonString JSON para conversão
     * @param type Tipo de conversão
     * @param <T> Generic
     * @return Lista convertida
     * @author Felipe Savaris
     * @since 24/12/2018
     */
    private static <T>List<T> getEventListFromJson(String jsonString,
                                                   Type type) {

        //GSON Builder
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, GsonUtil.DATE_DESERIALIZAER)
                .create();

        return gson.fromJson(jsonString, type);
    }

}
