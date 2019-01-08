package br.com.garrav.projetogarrav;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.garrav.projetogarrav.model.User;
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
     *
     * @param view
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
            user.setDate_account(rtv.getActualDateToAccount());

            /*
            Testes
             */

            //Mudança de Activity -> MainActivity
            Intent it = new Intent(
                    this,
                    MainActivity.class
            );
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);

        } else {
            //Destroy Instance
            user = null;
        }
    }

}
