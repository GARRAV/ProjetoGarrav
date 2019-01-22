package br.com.garrav.projetogarrav.ws;

import com.google.gson.JsonArray;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Event_UserService {

    /**
     *
     * @param userEventBody
     * @return
     * @author Felipe Savaris
     * @since 28/12/2018
     */
    @POST("/GarravWS/webresources/event_user/register_presence")
    Call<ResponseBody> postEventPresence(
            @Body RequestBody userEventBody
    );

    /**
     *
     * @param id_user
     * @return
     * @author Felipe Savaris
     * @since 31/12/2018
     */
    @GET("/GarravWS/webresources/event_user/presence/{id_user}")
    Call<JsonArray> getJsonEventPresence(
            @Path("id_user") long id_user
    );

}
