package br.com.garrav.projetogarrav.util;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    /**
     * Método responsável por retornar a url do servidor
     *
     * @return Retorna o URL do servidor
     * @author Felipe Savaris
     * @since 28/11/2018
     */
    public static String getUrlServer() {

        return "http://192.168.0.193:8080/";
    }

    /**
     * Método responsável por retornar o cliente interceptor a
     * ser usado na transação de client side e server side
     *
     * @return Retorna o cliente interceptador
     * @author Felipe Savaris
     * @since 24/12/2018
     */
    public static OkHttpClient getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    /**
     * Método responsável por retornar um conversor Json para
     * dados no client side e usado preferencialmente quando
     * a requisição for GET
     *
     * @return Conversor GSON
     * @author Felipe Savaris
     * @since 24/12/2018
     */
    public static GsonConverterFactory getConverterFactory() {

        return GsonConverterFactory.create();
    }
}
