package br.com.garrav.projetogarrav;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.ws.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText tvLoginEmail;
    private EditText tvLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retira a ActionBar
        ActionBar ab = getSupportActionBar();
        ab.hide();
    }

    public void btLogin(View view) {

        //Init EditText's
        this.tvLoginEmail = findViewById(R.id.tvLoginEmail);
        this.tvLoginPassword = findViewById(R.id.tvLoginPassword);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.191:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService us = retrofit.create(UserService.class);

        Call<User> callUserLogin =
                us.getJsonLogin();

        final Context ctx = this;

        callUserLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if(user == null) {
                    MessageActionUtil.makeText(
                            ctx,
                            "Erro"
                    );
                } else {
                    MessageActionUtil.makeText(
                            ctx,
                            "Usuário: " + user.getName() + " - Senha: " + user.getPassword()
                    );
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                MessageActionUtil.makeText(
                        ctx,
                        t.getMessage()
                );
            }
        });
    }
}
