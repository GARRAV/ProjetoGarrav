package br.com.garrav.projetogarrav.retrofitServerService;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import br.com.garrav.projetogarrav.AfterLoginActivity;
import br.com.garrav.projetogarrav.model.Report;
import br.com.garrav.projetogarrav.util.GsonUtil;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.ReportService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportServerService {

    public static void postBugReportToServer(final Context CONTEXT,
                                             Report report,
                                             final Button BUTTON_REPORT) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .build();

        //GSON + Serializaer
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, GsonUtil.DATE_SERIALIZER)
                .create();

        //JSON
        String json = gson.toJson(report);

        //Resgata o Link que fará o service com o servidor
        ReportService rs = retrofit.create(ReportService.class);

        //Corpo do JSON que irá ao Servidor
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        //Métodos da requisição da API
        rs.postReportJson(requestBody).enqueue(new Callback<ResponseBody>() {
            /**
             *
             * @param call
             * @param response
             * @author Felipe Savaris
             * @since 28/01/2019
             */
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                //♥♥♥♥ Message
                MessageActionUtil.makeText(
                        CONTEXT,
                        "Report enviado com sucesso, muito obrigado por fazer " +
                                "nosso aplicativo melhor a cada dia!"
                );

                //Mudança de Activity -> AfterLoginActivity
                Intent it = new Intent(
                        CONTEXT,
                        AfterLoginActivity.class
                );
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CONTEXT.startActivity(it);

            }

            /**
             *
             * @param call
             * @param t
             * @author Felipe Savaris
             * @since 28/01/2019
             */
            @Override
            public void onFailure(Call<ResponseBody> call,
                                  Throwable t) {

                MessageActionUtil.makeText(
                        CONTEXT,
                        "A mensagem não foi enviada por problemas com o servidor " +
                                "tente novamente mais tarde! - " + t.getMessage()
                );

                //Enable Button
                if(!BUTTON_REPORT.isEnabled()) {
                    BUTTON_REPORT.setEnabled(true);
                }
            }
        });
    }

}
