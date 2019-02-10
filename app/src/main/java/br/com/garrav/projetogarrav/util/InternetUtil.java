package br.com.garrav.projetogarrav.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetUtil {

    /**
     * Método responsável por informar se há a disponibilidade de Internet
     * ou não
     *
     * @param context Contexto da atual activity em execução do android
     * @return True / False
     * @author Felipe Savaris
     * @since 08/02/2019
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null) {

            NetworkInfo ni = cm.getActiveNetworkInfo();

            if(ni == null) {

                //Message
                MessageActionUtil.makeText(
                        context,
                        "Sem conexão com a Internet"
                );

                return false;
            }

            if(!ni.isConnected()) {

                //Message
                MessageActionUtil.makeText(
                        context,
                        "Sem conexão com a Internet"
                );

                return ni.isConnected();
            } else return ni.isConnected();

        } else

        //Message
        MessageActionUtil.makeText(
                context,
                "Sem conexão com a Internet"
        );

        return false;
    }

}
