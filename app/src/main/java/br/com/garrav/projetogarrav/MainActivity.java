package br.com.garrav.projetogarrav;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.garrav.projetogarrav.api.UserServerService;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.util.InternetUtil;
import br.com.garrav.projetogarrav.util.pref.PrefUserUtil;
import br.com.garrav.projetogarrav.validation.LoginTextValidator;

public class MainActivity extends AppCompatActivity {

    private EditText tvLoginEmail;
    private EditText tvLoginPassword;
    private Button btLogin;
    private ProgressBar pbLoginLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retira a ActionBar
        ActionBar ab = getSupportActionBar();
        ab.hide();

        //Verifica Se há conexão com a Internet
        if(!InternetUtil.isConnected(this)) {
            /*
            Apaga a Chave de User no SharedPreferences caso
            não haja Internet Disponivel
             */
            PrefUserUtil.clearUserSharedPreferences(this);

            //Fecha o onCreate()
            return;
        }

        //Verifica se já existe User no SharedPreferences e o resgata
        User user = PrefUserUtil.getUserSharedPreferences(this);

        //Se não vazio -> Login Automático
        if(user.getEmail() != null) {

            //Set Unique User
            User.setUniqueUser(user);

            //Mudança de Activity -> AfterLoginActivity
            Intent it = new Intent(
                    this,
                    AfterLoginActivity.class
            );
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);
        } else {
            //Destroy Instance
            user = null;
        }
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

        //Init EditText's & Button
        this.tvLoginEmail = findViewById(R.id.tvLoginEmail);
        this.tvLoginPassword = findViewById(R.id.tvLoginPassword);
        this.btLogin = findViewById(R.id.btLogin);

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
            //Disable Button
            this.btLogin.setEnabled(false);
            this.btLogin.setClickable(false);

            //Requisição Retrofit - Login
            UserServerService.getLoginUserFromServer(
                    this,
                    this.tvLoginEmail,
                    this.tvLoginPassword,
                    this.pbLoginLoading,
                    this.btLogin,
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
