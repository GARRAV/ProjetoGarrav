package br.com.garrav.projetogarrav.logic;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import br.com.garrav.projetogarrav.MapsFragment;
import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.util.GsonUtil;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.EventService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventData {

    /**
     *
     * @param context
     * @author Felipe Savaris
     * @since 24/12/2018
     */
    public void getEventsFromServer(Context context) {

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

        final Context ctx = context;

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
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {

                //Gambiarra
                assert response.body() != null;
                String eventsString = response.body().toString();
                Type listType = new TypeToken<List<Event>>(){}.getType();

                MapsFragment.listEvent = getEventListFromJson(eventsString, listType);
            }

             /**
              *
              * @param call
              * @param t
              * @author Felipe Savaris
              * @since 24/12/2018
              */
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                MessageActionUtil.makeText(
                        ctx,
                        t.getMessage()
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
    private static <T>List<T> getEventListFromJson(String jsonString, Type type) {

        //GSON Builder
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, GsonUtil.DATE_DESERIALIZAER)
                .create();

        return gson.fromJson(jsonString, type);
    }

}
