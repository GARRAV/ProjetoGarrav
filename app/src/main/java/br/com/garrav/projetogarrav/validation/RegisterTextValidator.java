package br.com.garrav.projetogarrav.validation;

import android.content.Context;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class RegisterTextValidator {

    /**
     *
     * @param context
     * @param user
     * @return
     * @author Felipe Savaris
     * @since 08/01/2019
     */
    public boolean valRegisterText(Context context,
                                   User user,
                                   String password) {

        //Field Email
        if(user.getEmail().isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "O campo E-mail não deve estar vazio!"
            );
            return false;
        }
        if(!user.getEmail().contains("@")) {
            MessageActionUtil.makeText(
                    context,
                    "O E-mail deve conter um '@'!"
            );
            return false;
        }
        if(user.getEmail().length() > 100) {
            MessageActionUtil.makeText(
                    context,
                    "O campo E-mail deve conter no máximo 100 caracteres!"
            );
            return false;
        }

        //Field Name
        if(user.getName().isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "O campo Nome não deve estar vazio!"
            );
            return false;
        }
        if(user.getName().length() < 3) {
            MessageActionUtil.makeText(
                    context,
                    "O campo Nome deve conter no mínimo 3 caracteres!"
            );
            return false;
        }
        if(user.getName().length() > 30) {
            MessageActionUtil.makeText(
                    context,
                    "O campo Nome deve conter no máximo 30 caracteres!"
            );
            return false;
        }

        //Field Fictional Name
        if(!user.getFic_name().isEmpty()) {
            if(user.getFic_name().length() < 3) {
                MessageActionUtil.makeText(
                        context,
                        "O campo Nome Fícticio deve conter no mímimo 3 caracteres!"
                );
                return false;
            }
            if(user.getFic_name().length() > 30) {
                MessageActionUtil.makeText(
                        context,
                        "O campo Nome Fícticio deve conter no máximo 30 caracteres!"
                );
                return false;
            }
        }

        //Field Password
        if(password.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "O campo Senha não deve estar vazio!"
            );
            return false;
        }
        if(password.length() < 6) {
            MessageActionUtil.makeText(
                    context,
                    "A senha deve conter no mínimo 6 caracteres!"
            );
            return false;
        }
        if(password.length() > 30) {
            MessageActionUtil.makeText(
                    context,
                    "A senha deve conter no máximo 30 caracteres!"
            );
            return false;
        }

        return true;
    }

    /**
     *
     * @param password
     * @return
     * @author Felipe Savaris
     * @since 08/01/2019
     */
    public String hashPassword(String password) {

        //Hash Password
        password = BCrypt.hashpw(password, BCrypt.gensalt(5));

        return password;
    }

    /**
     *
     * @return
     * @author Felipe Savaris
     * @since 08/01/2019
     */
    public Date getActualDateToAccount() {

        //Actual Date
        Date date = new Date();

        return date;
    }

}
