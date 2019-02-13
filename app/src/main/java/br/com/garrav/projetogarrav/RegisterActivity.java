package br.com.garrav.projetogarrav;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.api.UserServerService;
import br.com.garrav.projetogarrav.validation.RegisterTextValidator;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etName, etFicName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar ac = getSupportActionBar();
        ac.setTitle("Cadastro");
    }

    /**
     * Faz a interação do Button btRegisterUser da activity activity_register.xml
     * que valida os dados dos EditText's etEmail, etName, etFicName, etPassword e
     * a partir desses dados após validado, é enviada uma instância User para o
     * servidor guardar no banco de dados.
     * Caso os textos não estejam válidos, o método sera encerrado, caso valido,
     * será iniciado a requisição POST de cadastro do usuário para o servidor.
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 08/01/2019
     */
    public void btRegisterUser(View view) {

        //Init EditText's
        this.etEmail = findViewById(R.id.etEmail);
        this.etName = findViewById(R.id.etName);
        this.etFicName = findViewById(R.id.etFicName);
        this.etPassword = findViewById(R.id.etPassword);

        //User Instance
        User user = new User();
        user.setEmail(this.etEmail.getText().toString());
        user.setName(this.etName.getText().toString());
        user.setFic_name(this.etFicName.getText().toString());
        //Set Type User
        user.setType_user("COMMON");

        //Validator
        RegisterTextValidator rtv = new RegisterTextValidator();
        boolean val = rtv.valRegisterText(
                this,
                user,
                this.etPassword.getText().toString()
        );

        //Se válido
        if(val) {

            //Hash Password to Instance
            user.setPassword(rtv.hashPassword(this.etPassword.getText().toString()));

            //Get Actual Date to Instance
            user.setDate_account(new Date());

            //Send Data to Server
            UserServerService.postLoginUserToServer(this, user);

        } else {
            //Destroy Instance
            user = null;
        }
    }

}
