package br.com.garrav.projetogarrav.data;

import android.content.Context;

import org.mindrot.jbcrypt.BCrypt;

import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class LoginTextValidator {

    public boolean valLoginText(Context context,
                                String email,
                                String password) {

        //Field E-mail
        if(email.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "O campo E-mail não deve estar vazio!"
            );
            return false;
        }

        //Field Password
        if(password.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "O campo Senha não deve estar vazio!"
            );
            return false;
        }
        return true;
    }

    public boolean valHashPassword(Context context,
                                   String password,
                                   String hashPassword) {

        if(!BCrypt.checkpw(password, hashPassword)) {
            MessageActionUtil.makeText(
                    context,
                    "E-mail ou Senha Incorretos!"
            );
            return false;
        } else return true;
    }
}
