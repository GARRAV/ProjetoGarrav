package br.com.garrav.projetogarrav.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.garrav.projetogarrav.model.Challenge_User;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.Challenge_UserService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Challenge_UserServerService {

    /**
     * Método responsável por enviar uma requisição GET para o servidor
     * e retornar uma lista de desafios diários já checados pelo usuário
     *
     * @param context Contexto da atual activity em execução do android
     * @param id_user Id do usuário
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    public static void getCheckedChallengeFromServer(final Context context,
                                                     long id_user) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .addConverterFactory(RetrofitUtil.getConverterFactory())
                .build();

        //Resgata o link que fara a conexão com a API
        Challenge_UserService cus = retrofit.create(Challenge_UserService.class);

        //Faz a conexão com a API
        Call<JsonArray> callChallengeDoneList
                = cus.getChallengeDoneJson(id_user);

        //Métodos da requisição da API
        callChallengeDoneList.enqueue(new Callback<JsonArray>() {
            /**
             * Método responsável por retornar a resposta do servidor
             * e entregar uma lista de desafios diários já checados
             * pelo usuário
             *
             * @param call Chamada da API no servidor
             * @param response Resposta do servidor
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onResponse(Call<JsonArray> call,
                                   Response<JsonArray> response) {

                //JSON
                assert response.body() != null;
                String json = response.body().toString();

                //Type Converter
                Type listType = new TypeToken<List<Challenge_User>>(){}.getType();

                //Lista convertida
                List<Challenge_User> lstChecked = getCheckedChallengeFromJson(
                        json,
                        listType
                );

                //Set Static List
                Challenge_User.setUniqueListCheckChallenge(lstChecked);
            }

            /**
             * Método invocado se a conexão da requisição falhar e
             * retornando o erro ocorrido
             *
             * @param call Chamado da API no servidor
             * @param t Erro ocorrido
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onFailure(Call<JsonArray> call,
                                  Throwable t) {

                //Error Message
                MessageActionUtil.makeText(
                        context,
                        t.getMessage()
                );

            }
        });

    }

    /**
     * Método responsável por enviar uma requisição POST para o servidor
     * e inserir no banco de dados uma nova checagem do usuário
     *
     * @param context Contexto da atual activity em execução do android
     * @param cu Nova Instância de Challenge_User
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    public static void postCheckedChallengeToServer(final Context context,
                                                    Challenge_User cu) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .build();

        //GSON
        Gson gson = new Gson();

        //JSON
        String json = gson.toJson(cu);

        //Resgata o link que fará a conexão com a API
        Challenge_UserService cus = retrofit.create(Challenge_UserService.class);

        //Corpo do JSON que irá ao servidor
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        //Métodos da requisição da API
        cus.postCheckChallengeJson(requestBody).enqueue(new Callback<ResponseBody>() {
            /**
             * Método responsável por retornar a resposta do servidor
             * e informar se a requisição foi sucedida
             *
             * @param call Chamado da API no servidor
             * @param response Resposta do servidor
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                MessageActionUtil.makeText(
                        context,
                        "Desafio concluído com sucesso!"
                );

            }

            /**
             * Método invocado se a conexão da requisição falhar e
             * retornando o erro ocorrido
             *
             * @param call Chamado da API no servidor
             * @param t Erro ocorrido
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onFailure(Call<ResponseBody> call,
                                  Throwable t) {

                MessageActionUtil.makeText(
                        context,
                        t.getMessage()
                );

            }
        });

    }

    /**
     * Método responsável por enviar uma requisição DELETE para o servidor
     * e deletar do banco de dados a checagem do desafio diário caso assim
     * o usuário queira
     *
     * @param context Contexto da atual activity em execução do android
     * @param id_user Id do usuário
     * @param id_challenge Id do desafio selecionado
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    public static void deleteCheckedChallengeFromServer(final Context context,
                                                        long id_user,
                                                        long id_challenge) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .build();

        //Resgata o Link que fará a conexão com a API
        Challenge_UserService cus = retrofit.create(Challenge_UserService.class);

        //Faz a conexão com a API
        Call<ResponseBody> callDeleteCheckChallenge =
                cus.deleteCheckChallengeJson(
                        id_user,
                        id_challenge
                );

        //Métodos da requisição da API
        callDeleteCheckChallenge.enqueue(new Callback<ResponseBody>() {
            /**
             * Método responsável por retornar a resposta do servidor
             * e informar se a requisição foi bem sucedida
             *
             * @param call Chamado da API no servidor
             * @param response Resposta do servidor
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                //Sucessful Message
                MessageActionUtil.makeText(
                        context,
                        "Desafio Desmarcado!"
                );

            }

            /**
             * Método invocado se a conexão da requisição falhar e
             * retornando o erro ocorrido
             *
             * @param call Chamado da API no servidor
             * @param t Erro ocorrido
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onFailure(Call<ResponseBody> call,
                                  Throwable t) {

                //Error Message
                MessageActionUtil.makeText(
                        context,
                        t.getMessage()
                );

            }
        });
    }

    /**
     * Método responsável por converter um array advindo
     * do servidor e torna-lô uma lista
     *
     * @param jsonString Json para conversão
     * @param type Tipo de conversão
     * @param <T> Generic
     * @return Lista convertida
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    private static <T>List<T> getCheckedChallengeFromJson(String jsonString,
                                                          Type type) {

        //Gson
        Gson gson = new Gson();

        return gson.fromJson(jsonString, type);
    }

}
