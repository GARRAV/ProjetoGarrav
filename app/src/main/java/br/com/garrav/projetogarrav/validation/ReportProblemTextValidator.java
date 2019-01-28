package br.com.garrav.projetogarrav.validation;

import android.content.Context;

import br.com.garrav.projetogarrav.model.Report;
import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class ReportProblemTextValidator {

    /**
     *
     * @param context
     * @param report
     * @return
     * @author Felipe Savaris
     * @since 28/01/2019
     */
    public boolean valProblemText(Context context,
                                  Report report) {

        String bug = report.getBugReport();

        //Report Bug Field
        if(bug.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "A mensagem não deve estar vazia!"
            );
            return false;
        }
        if(bug.length() < 15) {
            MessageActionUtil.makeText(
                    context,
                    "A mensagem deve conter mais de 15 caracteres!"
            );
            return false;
        }
        if(bug.length() > 2000) {
            MessageActionUtil.makeText(
                    context,
                    "A mensagem deve conter entre 15 a 2000 caracteres! " +
                            "Caracteres = " + bug.length()
            );
            return false;
        }

        return true;
    }

}
