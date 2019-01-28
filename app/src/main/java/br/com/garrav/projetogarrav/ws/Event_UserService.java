package br.com.garrav.projetogarrav.ws;

import com.google.gson.JsonArray;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Event_UserService {

    /**
     * Requisição {@link POST} que será enviada ao servidor e inserir
     * no banco de dados uma nova presença
     *
     * @param userEventBody Instância de {@link br.com.garrav.projetogarrav.model.Event_User}
     *                      em JSON
     * @return Link da API que será feito com o servidor
     * @author Felipe Savaris
     * @since 28/12/2018
     */
    @POST("/GarravWS/webresources/event_user/register_presence")
    Call<ResponseBody> postEventPresence(
            @Body RequestBody userEventBody
    );

    /**
     * Requisição {@link GET} que será enviada ao servidor e retornar
     * uma {@link java.util.List} de {@link br.com.garrav.projetogarrav.model.Event_User}
     * que representa as presenças de {@link br.com.garrav.projetogarrav.model.User}
     *
     * @param id_user Id do {@link br.com.garrav.projetogarrav.model.User}
     * @return Link da API que será feita com o servidor
     * @author Felipe Savaris
     * @since 31/12/2018
     */
    @GET("/GarravWS/webresources/event_user/presence/{id_user}")
    Call<JsonArray> getJsonEventPresence(
            @Path("id_user") long id_user
    );

    /**
     * Requisição {@link DELETE} que será enviada ao servidor e deletar
     * do banco de dados a presença do {@link br.com.garrav.projetogarrav.model.User}
     * dos {@link br.com.garrav.projetogarrav.model.Event}
     *
     * @param id_user Id do {@link br.com.garrav.projetogarrav.model.User}
     * @param id_event Id do {@link br.com.garrav.projetogarrav.model.Event}
     * @return link da API que será feita com o servidor
     * @author Felipe Savaris
     * @since 25/01/2019
     */
    @DELETE("/GarravWS/webresources/event_user/delete/{id_user}/{id_event}")
    Call<ResponseBody> deleteJsonPresenceEvent(
            @Path("id_user") long id_user,
            @Path("id_event") long id_event
    );

}
