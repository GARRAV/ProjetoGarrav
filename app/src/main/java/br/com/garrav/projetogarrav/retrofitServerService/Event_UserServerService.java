package br.com.garrav.projetogarrav.retrofitServerService;

import android.content.Context;
import android.view.View;
import android.widget.Button;

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
     * Método responsável por enviar uma requisição POST para o
     * servidor e cadastrar uma nova presença
     *
     * @param context Contexto da atual activity em execução do android
     * @param eventUser Instância da presença
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
             * Método responsável por entregar a resposta do servidor
             * para saber se a requisição foi bem sucedida
             *
             * @param call Chamado da API do servidor
             * @param response Resposta do Servidor
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
             * Método invocado se a conexão com o servidor não for bem
             * sucedida, retornando uma mensagem de erro que mostra
             * o que aconteceu para a conexão não ter sido feita
             *
             * @param call Chamado da API do servidor
             * @param t Erro ocorrido durante o chamado
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
     * Método responsável por enviar uma requisição GET para o
     * servidor e retornar uma lista de Presença em eventos
     *
     * @param context Contexto da atual activity em execução do android
     * @param id_user ID do usuário a ser enviada como parâmetro
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
             * Método responável por retornar uma lista de presenças
             * advindas do servidor
             *
             * @param call Chamado da API do servidor
             * @param response Resposta do Servidor
             * @author Felipe Savaris
             * @since 31/12/2018
             */
            @Override
            public void onResponse(Call<JsonArray> call,
                                   Response<JsonArray> response) {

                assert response.body() != null;

                //JSON
                String eventUsersString = response.body().toString();
                //Type Converter
                Type listType = new TypeToken<List<Event_User>>(){}.getType();

                //Lista de Presenças
                List<Event_User> listTmp = getEventPresenceFromJson(
                        eventUsersString,
                        listType
                );

                //Set Lista de Presenças
                Event_User.setUniqueListEvent_User(listTmp);

            }

            /**
             * Método invocado se a conexão com o servidor não for bem
             * sucedida, retornando uma mensagem de erro que mostra
             * o que aconteceu para a conexão não ter sido feita
             *
             * @param call Chamado da API do servidor
             * @param t Erro ocorrido durante o chamado
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
     * @param CONTEXT
     * @param id_user
     * @param id_event
     * @param CANCEL_BUTTON
     * @author Felipe Savaris
     * @since 25/01/2019
     */
    public static void deleteUserEventPresenceFromServer(final Context CONTEXT,
                                                         long id_user,
                                                         long id_event,
                                                         final Button CANCEL_BUTTON) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .build();

        //Resgata o Link que fará a conexão com a API
        Event_UserService eus = retrofit.create(Event_UserService.class);

        //Faz a conexão com a API
        Call<ResponseBody> callDeleteEventPresence
                = eus.deleteJsonPresenceEvent(
                        id_user,
                        id_event
        );

        callDeleteEventPresence.enqueue(new Callback<ResponseBody>() {
            /**
             *
             * @param call
             * @param response
             * @author Felipe Savaris
             * @since 25/01/2019
             */
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                MessageActionUtil.makeText(
                        CONTEXT,
                        "Presença Apagada com Sucesso!"
                );

            }

            /**
             *
             * @param call
             * @param t
             * @author Felipe Savaris
             * @since 25/01/2019
             */
            @Override
            public void onFailure(Call<ResponseBody> call,
                                  Throwable t) {

                MessageActionUtil.makeText(
                        CONTEXT,
                        "Houve um erro ao deletar a presença: " + t.getMessage()
                );

                CANCEL_BUTTON.setText("Cancelar");
                CANCEL_BUTTON.setEnabled(true);

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
     * @since 31/12/2018
     */
    private static <T>List<T> getEventPresenceFromJson(String jsonString,
                                                       Type type) {

        //Gson
        Gson gson = new Gson();

        return gson.fromJson(jsonString, type);
    }

}
