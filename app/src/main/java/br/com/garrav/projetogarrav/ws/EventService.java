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
     * Requisição GET que será feita com o servidor
     *
     * @return JsonArray que será retornado ao dispositivo
     * @author Felipe Savaris
     * @since 24/12/2018
     */
    @GET("/GarravWS/webresources/event/select")
    Call<JsonArray> getJsonEvent();

    /**
     * Requisição GET que será feita com o servidor com os
     * paramêtros informados pelo usuário e transformado em
     * JSON
     *
     * @param json Json que será enviado para o servidor
     * @return Link da API que será enviado ao servidor
     * @author Felipe Savaris
     * @since 24/01/2019
     */
    @GET("/GarravWS/webresources/event/selectPresenceUser/{id_event}")
    Call<JsonArray> getJsonPresenceEvent(
            @Path("id_event") String json
    );
}
