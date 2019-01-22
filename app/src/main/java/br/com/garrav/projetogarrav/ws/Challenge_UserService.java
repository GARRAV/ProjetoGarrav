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
     * Requisição GET que será enviada ao servidor e retornar
     * uma List de desafios diários concluidos
     *
     * @param id_user Id do usuário
     * @return Link da API que será feito com o servidor
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    @GET("/GarravWS/webresources/challenge_user/challengeDone/{id_user}")
    Call<JsonArray> getChallengeDoneJson(
            @Path("id_user") long id_user
    );

    /**
     * Requisição POST que será enviada ao servidor e inserir
     * no banco de dados uma nova checagem de desafios diários
     *
     * @param challenge_userBody Instância de Challenge_User em JSON
     * @return Link da API que será feito com o servidor
     * @author Felipe Savaris
     * @since 14/01/2019
     */
    @POST("/GarravWS/webresources/challenge_user/insertCheckChallenge")
    Call<ResponseBody> postCheckChallengeJson(
            @Body RequestBody challenge_userBody
    );

    /**
     * Requisição DELETE que será enviada ao servidor e deletar
     * do banco de dados a checagem do desafio diário escolhido
     * caso assim o usuário queira
     *
     * @param id_user Id do Usuário
     * @param id_challenge Id do desafio
     * @return Link da API que será feito com o servidor
     * @since 14/01/2019
     */
    @DELETE("/GarravWS/webresources/challenge_user/deleteCheckChallenge/{id_user}/{id_challenge}")
    Call<ResponseBody> deleteCheckChallengeJson(
            @Path("id_user") long id_user,
            @Path("id_challenge") long id_challenge
    );
}
