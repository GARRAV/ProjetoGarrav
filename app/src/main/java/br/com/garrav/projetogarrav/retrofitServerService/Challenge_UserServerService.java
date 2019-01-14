package br.com.garrav.projetogarrav.retrofitServerService;

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
     *
     * @param context
     * @param id_user
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
             *
             * @param call
             * @param response
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
             *
             * @param call
             * @param t
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
     *
     * @param context
     * @param cu
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
             *
             * @param call
             * @param response
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
             *
             * @param call
             * @param t
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
     *
     * @param context
     * @param id_user
     * @param id_challenge
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
             *
             * @param call
             * @param response
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
             *
             * @param call
             * @param t
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
     *
     * @param jsonString
     * @param type
     * @param <T>
     * @return
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
