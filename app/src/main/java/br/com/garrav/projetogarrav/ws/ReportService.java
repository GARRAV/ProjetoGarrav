package br.com.garrav.projetogarrav.ws;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportService {

    /**
     * Requisição POST que será feita com o servidor com os paramêtros
     * informados pelo usuário transformados em JSON
     *
     * @param reportBody Instância de {@link br.com.garrav.projetogarrav.model.Report}
     *                   em JSON
     * @return Link da API que será feito com o servidor
     * @author Felipe Savaris
     * @since 28/01/2019
     */
    @POST("/GarravWS/webresources/report/bug_report")
    Call<ResponseBody> postReportJson(
            @Body RequestBody reportBody
    );

}
