package br.com.garrav.projetogarrav.ws;

import br.com.garrav.projetogarrav.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("/GarravWS/webresources/user/login/{email}")
    Call<User> getJsonLogin(
            @Path("email") String email
    );
}
