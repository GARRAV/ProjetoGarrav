package br.com.garrav.projetogarrav.util;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    /**
     * Método responsável por invocar o framework Retrofit e fazer
     * a operação desejada com a API
     *
     * @return Retorna uma instância Retrofit configurada para fazer
     * a conexão com a API do servidor
     * @author Felipe Savaris
     * @since 28/11/2018
     */
    public static Retrofit getUrlServer() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.191:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
