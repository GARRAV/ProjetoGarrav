package br.com.garrav.projetogarrav;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import br.com.garrav.projetogarrav.adapter.challenge.ListChallengeAdapter;
import br.com.garrav.projetogarrav.model.Challenge;
import br.com.garrav.projetogarrav.retrofitServerService.ChallengeServerService;

public class ChallengeActivity extends AppCompatActivity {

    private ListView lvChallengeList;
    private ProgressDialog progressDialog;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Desafios");

        //Show ProgressDialog
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Carregando...");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();

        //Get List from Server
        ChallengeServerService.getDailyChallengeFromServer(
                this,
                this.progressDialog,
                this
        );
    }

    /**
     *
     * @author Felipe Savaris
     * @since 12/01/2019
     */
    public void loadChallenges() {

        //Adapter do ListView
        ListChallengeAdapter adapter =
                new ListChallengeAdapter(
                        this,
                        Challenge.getUniqueListChallenge()
                );

        //Init ListView
        this.lvChallengeList = findViewById(R.id.lvChallengeList);

        //Set Adapter no ListView
        this.lvChallengeList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

}
