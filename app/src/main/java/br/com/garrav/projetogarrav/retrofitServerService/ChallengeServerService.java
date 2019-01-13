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
     *
     * @param context
     * @param progressDialog
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
             *
             * @param call
             * @param response
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
             *
             * @param call
             * @param t
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
     *
     * @param jsonString
     * @param type
     * @param <T>
     * @return
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
