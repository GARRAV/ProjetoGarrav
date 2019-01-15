package br.com.garrav.projetogarrav.ws;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChallengeService {

    /**
     * Requisição GET que será enviada ao servidor e retornar
     * uma List de desafios diários
     *
     * @return Link da API que será feito com o servidor
     * @author Felipe Savaris
     * @since 13/01/2019
     */
    @GET("/GarravWS/webresources/challenge/dailyChallenge")
    Call<JsonArray> getDailyChallengeJson();
}
