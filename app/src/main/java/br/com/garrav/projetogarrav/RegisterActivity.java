package br.com.garrav.projetogarrav;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        
    }

}
