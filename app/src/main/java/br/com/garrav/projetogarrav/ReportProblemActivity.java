package br.com.garrav.projetogarrav;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import br.com.garrav.projetogarrav.model.Report;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.retrofitServerService.ReportServerService;
import br.com.garrav.projetogarrav.validation.ReportProblemTextValidator;

public class ReportProblemActivity extends AppCompatActivity {

    private EditText etReportProblemText;
    private Button btSendProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Reportar Problemas");
    }

    /**
     * Método responsável por interagir com o Button e invocar
     * o método que avaliará se o texto escrito pelo usuário
     * está apto a ser enviado para o servidor. Caso o texto não
     * esteja apto a ser enviado o método é encerrado, caso
     * esteja, é adicionado um ID e uma data para o {@link Report}
     * e chama o método reponsável por enviar dados para o servidor
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 28/01/2019
     */
    public void btSendProblem(View view) {

        //Init Button & EditText
        this.btSendProblem = findViewById(R.id.btSendProblem);
        this.etReportProblemText = findViewById(R.id.etReportProblemText);

        //Set Button Disabled
        this.btSendProblem.setEnabled(false);

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
            ReportServerService.postBugReportToServer(
                    this,
                    report,
                    this.btSendProblem
            );

        } else {
            //Destroy Instance
            report = null;

            //Set Button Enabled
            if(!btSendProblem.isEnabled()) {
                btSendProblem.setEnabled(true);
            }
        }

    }
}
