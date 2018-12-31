package br.com.garrav.projetogarrav.retrofitServerService;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.garrav.projetogarrav.MapsEventsActivity;
import br.com.garrav.projetogarrav.model.Event_User;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.Event_UserService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Event_UserServerService {

    /**
     *
     * @param context
     * @param eventUser
     * @author Felipe Savaris
     * @since 29/12/2018
     */
    public static void postEventUserPresenceToServer(final Context context,
                                                     Event_User eventUser) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .build();

        //Resgata o link que fará o service com a API
        Event_UserService eus = retrofit.create(Event_UserService.class);

        Gson gson = new Gson();
        String json = gson.toJson(eventUser);

        //Faz a conexão com a API
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        eus.postEventPresence(requestBody).enqueue(new Callback<ResponseBody>() {
            /**
             *
             * @param call
             * @param response
             * @author Felipe Savaris
             * @since 28/12/2018
             */
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                MessageActionUtil.makeText(
                        context,
                        "Presença confirmada no evento!"
                );

                MapsEventsActivity.getFragEventInteractor().getView().setVisibility(View.INVISIBLE);
            }

            /**
             *
             * @param call
             * @param t
             * @author Felipe Savaris
             * @since 28/12/2018
             */
            @Override
            public void onFailure(Call<ResponseBody> call,
                                  Throwable t) {

                MessageActionUtil.makeText(
                        context,
                        t.getMessage()
                );
                MapsEventsActivity.getFragEventInteractor().getView().setVisibility(View.INVISIBLE);
            }
        });

    }

    /**
     *
     * @param context
     * @param id_user
     * @author Felipe Savaris
     * @since 31/12/2018
     */
    public static void getEventUserPresenceFromServer(final Context context,
                                                      long id_user) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .addConverterFactory(RetrofitUtil.getConverterFactory())
                .build();

        //Resgata o link que fará o service com a API
        Event_UserService eus = retrofit.create(Event_UserService.class);

        //Faz a conexão com a API
        Call<JsonArray> callEventUserList
                = eus.getJsonEventPresence(id_user);

        //Métodos da requisição da API
        callEventUserList.enqueue(new Callback<JsonArray>() {
            /**
             *
             * @param call
             * @param response
             * @author Felipe Savaris
             * @since 31/12/2018
             */
            @Override
            public void onResponse(Call<JsonArray> call,
                                   Response<JsonArray> response) {

                String eventUsersString = response.body().toString();
                Type listType = new TypeToken<List<Event_User>>(){}.getType();

                List<Event_User> listTmp = getEventPresenceFromJson(
                        eventUsersString,
                        listType
                );

                Event_User.setUniqueListEvent_User(listTmp);

            }

            /**
             *
             * @param call
             * @param t
             * @author Felipe Savaris
             * @since 31/12/2018
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
     * @param jsonString
     * @param type
     * @param <T>
     * @return
     * @author Felipe Savaris
     * @since 31/12/2018
     */
    private static <T>List<T> getEventPresenceFromJson(String jsonString,
                                                       Type type) {

        //Gson
        Gson gson = new Gson();

        return gson.fromJson(jsonString, type);
    }

}
