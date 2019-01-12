package br.com.garrav.projetogarrav;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ChallengeActivity extends AppCompatActivity {

    private ListView lvChallengeList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Desafios");

        loadChallenges();
    }

    /**
     *
     * @author Felipe Savaris
     * @since 12/01/2019
     */
    public void loadChallenges() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Carregando...");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

}
