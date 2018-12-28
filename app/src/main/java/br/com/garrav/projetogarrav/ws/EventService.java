package br.com.garrav.projetogarrav.ws;

import com.google.gson.JsonArray;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {

    /**
     * Requisição POST que será feita com o servidor com os
     * parâmetros informados pelo Usuário
     *
     * @param eventBody Body do JSON que será enviado ao serivdor
     * @return link da API que será feita no servidor com o parâmetro
     * @author Felipe Savaris
     * @since 13/12/2018
     */
    @POST("/GarravWS/webresources/event/register")
    Call<ResponseBody> postJsonEvent(
            @Body RequestBody eventBody
    );

    /**
     *
     * @param userEventBody
     * @return
     * @author Felipe Savaris
     * @since 28/12/2018
     */
    @POST("/GarravWS/webresources/event/register_presence")
    Call<ResponseBody> postEventPresence(
            @Body RequestBody userEventBody
    );

    /**
     *
     * @return
     * @author Felipe Savaris
     * @since 24/12/2018
     */
    @GET("/GarravWS/webresources/event/select")
    Call<JsonArray> getJsonEvent();
}
