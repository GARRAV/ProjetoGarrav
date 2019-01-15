package br.com.garrav.projetogarrav.retrofitServerService;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.garrav.projetogarrav.ChallengeActivity;
import br.com.garrav.projetogarrav.model.Challenge;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.ChallengeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChallengeServerService {

    /**
     * Método responsável por enviar uma requisição GET para o servidor
     * e retornar uma lista de desafios diários. No fim da requisição,
     * é invocado um comando para ativar o adapter da ListView.
     *
     * @param context Contexto da atual activity em execução do android
     * @param progressDialog Controle de ProgressDialog
     * @param activity Instância de ChallengeActivity
     * @author Felipe Savaris
     * @since 13/01/2019
     */
    public static void getDailyChallengeFromServer(final Context context,
                                                   final ProgressDialog progressDialog,
                                                   final ChallengeActivity activity) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .addConverterFactory(RetrofitUtil.getConverterFactory())
                .build();

        //Resgata do Link que fará a conexão com a API
        ChallengeService cs = retrofit.create(ChallengeService.class);

        //Faz a conexão com a API
        Call<JsonArray> callDailyChallengeList
                = cs.getDailyChallengeJson();

        //Métodos da requisição da API
        callDailyChallengeList.enqueue(new Callback<JsonArray>() {
            /**
             * Método responsável por retornar a resposta do servidor
             * e entregar uma lista de desafios diários.
             *
             * @param call Chamado da API no servidor
             * @param response Resposta do Servidor
             * @author Felipe Savaris
             * @since 13/01/2019
             */
            @Override
            public void onResponse(Call<JsonArray> call,
                                   Response<JsonArray> response) {

                //JSON
                assert response.body() != null;
                String json = response.body().toString();

                //Type Converter
                Type listType = new TypeToken<List<Challenge>>(){}.getType();

                //List Convertida
                List<Challenge> lstChallenge = getDailyChallengeFromJson(
                        json,
                        listType
                );

                //Set static List
                Challenge.setUniqueListChallenge(lstChallenge);

                //Off ProgressDialog
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                //Carregar os Challenges
                activity.loadChallenges();
            }

            /**
             * Método invocado se a conexão da requisição falhar
             * e retornando o erro ocorrido
             *
             * @param call Chamado da API no servidor
             * @param t Erro duarante a execução
             * @author Felipe Savaris
             * @since 13/01/2019
             */
            @Override
            public void onFailure(Call<JsonArray> call,
                                  Throwable t) {

                //Off ProgressDialog
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

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
     * @since 13/01/2019
     */
    private static <T>List<T> getDailyChallengeFromJson(String jsonString,
                                                       Type type) {

        //Gson
        Gson gson = new Gson();

        return gson.fromJson(jsonString, type);
    }

}
