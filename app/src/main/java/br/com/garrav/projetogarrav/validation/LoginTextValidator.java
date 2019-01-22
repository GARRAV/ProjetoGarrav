package br.com.garrav.projetogarrav.validation;

import android.content.Context;

import org.mindrot.jbcrypt.BCrypt;

import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class LoginTextValidator {

    /**
     * Método responsável por validar os devidos filtros para
     * os textos nos parâmetros
     *
     * @param context Contexto da atual activity em execução do android
     * @param email E-mail vindo da MainActivity.java
     * @param password Senha vindo da MainActivity.java
     * @return Retorno do resultado da validação de textos, se true aprovado
     * se false reprovado
     * @author Felipe Savaris
     * @since 28/11/2018
     */
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

    /**
     * Método responsável por validar a senha do usuário com o hash
     * do E-mail cadastrado fazendo uma comparação de hash
     * e assim caso válido, o login será executado
     *
     * @param context Contexto da atual activity em execução do android
     * @param password Senha vindo da MainActivity.java
     * @param hashPassword Hash da senha cadastrada no banco de dados do servidor
     * @return Retorno do resultado da validação da senha, se true aprovado,
     * se false reprovado
     * @author Felipe Savaris
     * @since 28/11/2018
     */
    public boolean valHashPassword(Context context,
                                   String password,
                                   String hashPassword) {

        //Comparador de Hash
        if(!BCrypt.checkpw(password, hashPassword)) {
            MessageActionUtil.makeText(
                    context,
                    "E-mail ou Senha Incorretos!"
            );
            return false;
        } else return true;
    }
}
