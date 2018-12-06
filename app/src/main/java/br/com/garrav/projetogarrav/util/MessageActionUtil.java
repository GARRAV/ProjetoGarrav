package br.com.garrav.projetogarrav.util;

import android.content.Context;
import android.widget.Toast;

public class MessageActionUtil {

    /**
     * Método estático responsável de mostrar uma mensagem Toast rapida
     * para o Usuário, sendo universal para todas as classes do projeto
     *
     * @param context Contexto da atual activity em execução do android
     * @param message Mensagem que será transmitida para o Usuário
     * @author Felipe Savaris
     * @since 27/11/2018
     */
    public static void makeText(Context context,
                                String message) {

        Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG)
                .show();

    }

}
