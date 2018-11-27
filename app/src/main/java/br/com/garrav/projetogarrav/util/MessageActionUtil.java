package br.com.garrav.projetogarrav.util;

import android.content.Context;
import android.widget.Toast;

public class MessageActionUtil {

    //Toast
    public static void makeText(Context context,
                                String message) {

        Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG)
                .show();

    }

}
