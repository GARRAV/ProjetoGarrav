package br.com.garrav.projetogarrav;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import br.com.garrav.projetogarrav.model.Report;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.validation.ReportProblemTextValidator;

public class ReportProblemActivity extends AppCompatActivity {

    private EditText etReportProblemText;

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

        //Init EditText
        this.etReportProblemText = findViewById(R.id.etReportProblemText);

        //Init Report Instance
        Report report = new Report();
        //Set Bug Report String
        report.setBugReport(this.etReportProblemText.getText().toString());

        //Validation Report Filter Text
        ReportProblemTextValidator rptv = new ReportProblemTextValidator();
        boolean val = rptv.valProblemText(
                this,
                report
        );

        /*
        Caso true, é iniciado o processo de enviar o Bug ao servidor.
        Caso false, a mensagem de erro é exibida e a Instânce de Report
        é Destruida
         */
        if(val) {

            //Remaining Data
            //Actual Date
            Date date = new Date();
            report.setDateReport(date);

            //Id_User
            report.setId_user(User.getUniqueUser().getId());

            //Init Server Service


        } else {
            //Destroy Instance
            report = null;
        }

    }
}
