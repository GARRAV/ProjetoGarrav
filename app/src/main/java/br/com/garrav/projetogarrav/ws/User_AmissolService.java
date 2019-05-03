package br.com.garrav.projetogarrav.ws;

import com.google.gson.JsonArray;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface User_AmissolService {

	/**
	 * Requisição {@link GET} que será enviada ao servidor e retornar uma
	 * {@link java.util.List} de {@link br.com.garrav.projetogarrav.model.User_Amissol}
	 *
	 * @return Link da API que será feito com o servidor
	 * @author Felipe Savaris
	 * @since 04/04/2019
	 */
	@GET("/GarravWS/webresources/user_amissol/getListUser")
	Call<JsonArray> getListUserAmissol();

}
