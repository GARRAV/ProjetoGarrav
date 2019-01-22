package br.com.garrav.projetogarrav.retrofitServerService;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import br.com.garrav.projetogarrav.AfterLoginActivity;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.util.MessageActionUtil;
import br.com.garrav.projetogarrav.util.RetrofitUtil;
import br.com.garrav.projetogarrav.validation.LoginTextValidator;
import br.com.garrav.projetogarrav.ws.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserServerService {

    /**
     * Método responsável por fazer a comunicação com o servidor
     * para uma requisição GET do login do usuário e validando
     * o mesmo. Resgata os dados necessários para a manipulação
     * do servidor e do sistema e faz a requisição.
     *
     * Método produzido por reestruturação do código,
     * fazendo com que a data deste método seja mais nova que
     * os métodos internos
     *
     * @param context Contexto da activity atual
     * @param email E-mail enviado pelo Usuário
     * @param password Senha enviada pelo Usuário
     * @param pbLoginLoading Progress Bar da Activity
     * @param ltv Validador de texto - Usuário
     * @author Felipe Savaris
     * @since 29/12/2018
     */
    public static void getLoginUserFromServer(final Context context,
                                              final String email,
                                              final String password,
                                              final ProgressBar pbLoginLoading,
                                              final LoginTextValidator ltv) {

        //Definições Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUtil.getUrlServer())
                .client(RetrofitUtil.getClient())
                .addConverterFactory(RetrofitUtil.getConverterFactory())
                .build();

        //Resgata o link que fará o service com a API
        UserService us = retrofit.create(UserService.class);

        //Faz a conexão com a API
        Call<User> callUserLogin =
                us.getJsonLogin(
                        email
                );

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
            public void onResponse(Call<User> call,
                                   Response<User> response) {

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
                            context,
                            password,
                            user.getPassword()
                    );

                    /*
                        Se val == true, o login será feito.
                        Se val == false, é mostrado uma mensagem Toast indicando
                        um login mal-sucedido e o método será encerrado.
                    */
                    if(!val) {

                        MessageActionUtil.makeText(
                                context,
                                "E-mail ou Senha Incorretos!"
                        );
                        //Torna a ProgressBar Invisivel
                        pbLoginLoading.setVisibility(View.INVISIBLE);

                        return;

                    } else {

                        //Set User servidor para User estático
                        User.setUniqueUser(user);
                        MessageActionUtil.makeText(
                                context,
                                "Login bem-sucedido"
                        );

                        //Torna a ProgressBar Invisivel
                        pbLoginLoading.setVisibility(View.INVISIBLE);

                        //Mudança de Activity -> AfterLoginActivity
                        Intent it = new Intent(
                                context,
                                AfterLoginActivity.class
                        );
                        context.startActivity(it);

                    }
                } else {
                    MessageActionUtil.makeText(
                            context,
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
            public void onFailure(Call<User> call,
                                  Throwable t) {

                MessageActionUtil.makeText(
                        context,
                        t.getMessage()
                );
                //Torna a ProgressBar Invisivel
                pbLoginLoading.setVisibility(View.INVISIBLE);
            }
        });
    }

}
