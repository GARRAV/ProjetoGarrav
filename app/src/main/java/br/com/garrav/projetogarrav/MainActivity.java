package br.com.garrav.projetogarrav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.garrav.projetogarrav.validation.LoginTextValidator;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
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
    private ProgressBar pbLoginLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retira a ActionBar
        ActionBar ab = getSupportActionBar();
        ab.hide();
    }

    /**
     *  Faz a interação do botão btLogin da activity activity_main.xml
     *  que valida os dados de EditText tvLoginEmail, tvLoginPassword
     *  e a partir desses dados, resgata os dados da instância da classe
     *  modelo User, utilizando servidor via Retrofit.
     *  Caso os textos não estejam validados, o método será encerrado, caso
     *  validados, será iniciado o login do sistema com dados vindos de um
     *  servidor, se os dados conferem com os textos apresentados, o login
     *  será feito, caso não esteja válido, o método será encerrado.
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 27/11/2018
     */
    public void btLogin(View view) {

        //Init EditText's
        this.tvLoginEmail = findViewById(R.id.tvLoginEmail);
        this.tvLoginPassword = findViewById(R.id.tvLoginPassword);

        //Text Validator
        final LoginTextValidator ltv = new LoginTextValidator();
        boolean val = ltv.valLoginText(
                this,
                this.tvLoginEmail.getText().toString(),
                this.tvLoginPassword.getText().toString()
        );

        /*
        Se val = true, é feita a tentativa de Login
        caso false, o método é encerrado
         */
        if(val) {

            //Init ProgressBar, e a torna Visivel
            this.pbLoginLoading = findViewById(R.id.pbLoginLoading);
            this.pbLoginLoading.setVisibility(View.VISIBLE);


            //Ação Retrofit - Servidor
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            //Definições Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitUtil.getUrlServer())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //Resgata o link que fará o service com a API
            UserService us = retrofit.create(UserService.class);

            //Faz a conexão com a API
            Call<User> callUserLogin =
                    us.getJsonLogin(
                            this.tvLoginEmail.getText().toString()
                    );

            //Context
            final Context ctx = this;

            //Métodos da Requisição da API
            callUserLogin.enqueue(new Callback<User>() {

                /**
                 * Demonstra a resposta do servidor baseado nos parâmetros informados
                 * via JSON e a convertendo para uma Instância User.
                 * Após a conversão para instância feita, se a mesma for nula, o método
                 * será encerrado, caso retorne uma instância, o hash senha da
                 * instância será comparada com os dados inseridos no método invocador.
                 * Se a senha for validada, o Login será feito, fazendo set de User
                 * estático, caso não validada, o método será encerrado
                 *
                 * @param call Chamado da API do servidor
                 * @param response Resposta do Servidor
                 * @author Felipe Savaris
                 * @since 27/11/2018
                 */
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    //Intância de User vindo do servidor
                    User user = response.body();

                    /*
                    Se user != null, é feita a validação da senha e o Login
                    caso validado.
                    Se user == null, é mostrado uma mensagem Toast indicando
                    um login mal-sucedido e o método será encerrado.
                     */
                    if (user != null) {

                        //Hash Password Validator
                        boolean val = ltv.valHashPassword(
                                ctx,
                                tvLoginPassword.getText().toString(),
                                user.getPassword()
                        );

                        /*
                        Se val == true, o login será feito.
                        Se val == false, é mostrado uma mensagem Toast indicando
                        um login mal-sucedido e o método será encerrado.
                         */
                        if(!val) {

                            MessageActionUtil.makeText(
                                    ctx,
                                    "E-mail ou Senha Incorretos!"
                            );
                            //Torna a ProgressBar Invisivel
                            pbLoginLoading.setVisibility(View.INVISIBLE);

                            return;

                        } else {

                            //Set User servidor para User estático
                            User.setUniqueUser(user);
                            MessageActionUtil.makeText(
                                    ctx,
                                    "Login bem-sucedido"
                            );

                            //Torna a ProgressBar Invisivel
                            pbLoginLoading.setVisibility(View.INVISIBLE);

                            //Mudança de Activity -> AfterLoginActivity
                            Intent it = new Intent(
                                    ctx,
                                    AfterLoginActivity.class
                            );
                            startActivity(it);

                        }
                    } else {
                        MessageActionUtil.makeText(
                                ctx,
                                "E-mail ou Senha Incorretos!"
                        );
                        //Torna a ProgressBar Invisivel
                        pbLoginLoading.setVisibility(View.INVISIBLE);
                    }
                }

                /**
                 * Método invocado se a conexão com o servidor não for bem
                 * sucedida, retornando uma mensagem de erro que mostra
                 * o que aconteceu para a conexão não ter sido feita
                 *
                 * @param call Chamado da API do servidor
                 * @param t Erro ocorrido durante o chamado
                 * @author Felipe Savaris
                 * @since 27/11/2018
                 */
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    MessageActionUtil.makeText(
                            ctx,
                            t.getMessage()
                    );
                    //Torna a ProgressBar Invisivel
                    pbLoginLoading.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
