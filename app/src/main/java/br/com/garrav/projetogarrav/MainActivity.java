package br.com.garrav.projetogarrav;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.garrav.projetogarrav.api.UserServerService;
import br.com.garrav.projetogarrav.validation.LoginTextValidator;

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
        LoginTextValidator ltv = new LoginTextValidator();
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

            //Requisição Retrofit - Login
            UserServerService.getLoginUserFromServer(
                    this,
                    this.tvLoginEmail.getText().toString(),
                    this.tvLoginPassword.getText().toString(),
                    this.pbLoginLoading,
                    ltv
            );
        }
    }

    /**
     * Faz a interação do TextView tvRegisterUser da activity activity_main.xml
     * que abrirá a activity activity_register.xml para cadastrar um novo
     * usuário no sistema
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 08/01/2019
     */
    public void tvRegisterUser(View view) {

        //Mudança de Activity -> RegisterActivity
        Intent it = new Intent(
                this,
                RegisterActivity.class
        );
        startActivity(it);

    }
}
