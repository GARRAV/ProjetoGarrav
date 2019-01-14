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

public interface Challenge_UserService {

    /**
     *
     * @param id_user
     * @return
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    @GET("/GarravWS/webresources/challenge_user/challengeDone/{id_user}")
    Call<JsonArray> getChallengeDoneJson(
            @Path("id_user") long id_user
    );

    /**
     *
     * @param challenge_userBody
     * @return
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    @POST("/GarravWS/webresources/challenge_user/insertCheckChallenge")
    Call<ResponseBody> postCheckChallengeJson(
            @Body RequestBody challenge_userBody
    );

    /**
     *
     * @param id_user
     * @param id_challenge
     * @return
     * @since 14/01/2019
     */
    @DELETE("/GarravWS/webresources/challenge_user/deleteCheckChallenge/{id_user}/{id_challenge}")
    Call<ResponseBody> deleteCheckChallengeJson(
            @Path("id_user") long id_user,
            @Path("id_challenge") long id_challenge
    );
}
