package br.com.garrav.projetogarrav;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.com.garrav.projetogarrav.data.LoginTextValidator;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.ws.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        final LoginTextValidator ltv = new LoginTextValidator();
        boolean val = ltv.valLoginText(
                this,
                this.tvLoginEmail.getText().toString(),
                this.tvLoginPassword.getText().toString()
        );

        if(val) {

            Retrofit retrofit = RetrofitUtil.getUrlServer();

            UserService us = retrofit.create(UserService.class);

            Call<User> callUserLogin =
                    us.getJsonLogin(this.tvLoginEmail.getText().toString());

            final Context ctx = this;

            callUserLogin.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();

                    if (user != null) {
                        boolean val = ltv.valHashPassword(
                                ctx,
                                tvLoginPassword.getText().toString(),
                                user.getPassword()
                        );
                        if(!val) {
                            user = null;
                        } else {
                            User.setUniqueUser(user);
                            MessageActionUtil.makeText(
                                    ctx,
                                    "Login bem-sucedido"
                            );
                        }
                    } else {
                        MessageActionUtil.makeText(
                                ctx,
                                "E-mail ou Senha Incorretos!"
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
}
