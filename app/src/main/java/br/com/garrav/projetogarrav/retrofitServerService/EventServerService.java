package br.com.garrav.projetogarrav.retrofitServerService;

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

import br.com.garrav.projetogarrav.MapsEventsActivity;
import br.com.garrav.projetogarrav.model.Event;
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
     *
     * @param context
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
              *
              * @param call
              * @param response
              * @author Felipe Savaris
              * @since 24/12/2018
              */
            @Override
            public void onResponse(@NonNull Call<JsonArray> call,
                                   @NonNull Response<JsonArray> response) {

                assert response.body() != null;
                String eventsString = response.body().toString();
                Type listType = new TypeToken<List<Event>>(){}.getType();

                List listTmp = getEventListFromJson(eventsString, listType);

                //Set Lista de Events
                Event.setUniqueListEvents(listTmp);
            }

             /**
              *
              * @param call
              * @param t
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
     *
     * @param context
     * @param event
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
             *
             * @param call
             * @param response
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
             *
             * @param call
             * @param t
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
     *
     * @param jsonString
     * @param type
     * @param <T>
     * @return
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
