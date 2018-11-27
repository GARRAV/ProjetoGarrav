package br.com.garrav.projetogarrav.ws;

import br.com.garrav.projetogarrav.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("/GarravWS/webresources/user/login")
    Call<User> getJsonLogin();

}
