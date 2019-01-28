package br.com.garrav.projetogarrav;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ReportProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Reportar Problemas");
    }

    /**
     *
     * @param view
     * @author Felipe Savaris
     * @since 28/01/2019
     */
    public void btSendProblem(View view) {

    }
}
