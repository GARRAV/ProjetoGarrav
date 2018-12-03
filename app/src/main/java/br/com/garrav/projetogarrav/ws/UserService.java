package br.com.garrav.projetogarrav.ws;

import br.com.garrav.projetogarrav.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    /**
     * Requisição GET que será feita com o servidor com os parâmetros
     * informados do Usuário
     *
     * @param email E-mail recebido pelo Usuário
     * @return link da API que será feita no servidor com o parâmetro
     * @author Felipe Savaris
     * @since 27/11/2018
     */
    @GET("/GarravWS/webresources/user/login/{email}")
    Call<User> getJsonLogin(
            @Path("email") String email
    );
}
