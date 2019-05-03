package br.com.garrav.projetogarrav.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.garrav.projetogarrav.model.User_Amissol;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.User_AmissolService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class User_AmissolServerService {

	public static void getUserAmissolFromServer(final Context context) {

		// Definições Retrofit
		Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(RetrofitUtil.getUrlServer())
						.client(RetrofitUtil.getClient())
						.addConverterFactory(RetrofitUtil.getConverterFactory())
						.build();

		// Resgata o link que fará o service com a API
		User_AmissolService ua = retrofit.create(User_AmissolService.class);

		// Faz a conexão com a API
		Call<JsonArray> callUserAmissol
						= ua.getListUserAmissol();

		callUserAmissol.enqueue(new Callback<JsonArray>() {
			@Override
			public void onResponse(Call<JsonArray> call,
														 Response<JsonArray> response) {

				assert response.body() != null;

				//JSON
				String json = response.body().toString();
				//Type Converter
				Type listType = new TypeToken<List<User_Amissol>>(){}.getType();

				//Lista de Presenças
				List<User_Amissol> listTmp = getUserAmissolFromJson(
								json,
								listType
				);

				//Set Lista de Presenças
				User_Amissol.setUniqueUserAmissolList(listTmp);

			}

			@Override
			public void onFailure(Call<JsonArray> call,
														Throwable t) {

			}
		});
	}

	/**
	 * Método responsável por converter um array advindo
	 * do servidor e torna-lô uma lista
	 *
	 * @param jsonString JSON para conversão
	 * @param type Tipo de conversão
	 * @param <T> Generic
	 * @return Lista convertida
	 * @author Felipe Savaris
	 * @since 04/04/2019
	 */
	private static <T> List<T> getUserAmissolFromJson(String jsonString,
																											Type type) {
		//Gson
		Gson gson = new Gson();

		return gson.fromJson(jsonString, type);
	}
}
