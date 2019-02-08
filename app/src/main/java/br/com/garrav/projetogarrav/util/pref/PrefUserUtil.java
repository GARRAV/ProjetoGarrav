package br.com.garrav.projetogarrav.util.pref;

import android.content.Context;

import java.util.Date;

import br.com.garrav.projetogarrav.model.User;

/**
 * Get e Set de {@link User} no {@link android.content.SharedPreferences}
 */
public class PrefUserUtil {

    /**
     * Método responsável por armazenar as informações de
     * {@link User} no {@link android.content.SharedPreferences}
     *
     * @param context Contém o getSharedPreferences
     * @param user Instância de {@link User}
     * @author Felipe Savaris
     * @since 08/02/2019
     */
    public static void saveUserSharedPreferences(Context context,
                                                 User user) {

        //ID
        PrefData.addSharedPreferences(
                context,
                PrefKeysUtil.KEY_USER,
                PrefKeysUtil.KEY_USER_ID,
                user.getId()
        );

        //Name
        PrefData.addSharedPreferences(
                context,
                PrefKeysUtil.KEY_USER,
                PrefKeysUtil.KEY_USER_NAME,
                user.getName()
        );

        //Fictional Name
        PrefData.addSharedPreferences(
                context,
                PrefKeysUtil.KEY_USER,
                PrefKeysUtil.KEY_USER_FIC_NAME,
                user.getFic_name()
        );

        //Email
        PrefData.addSharedPreferences(
                context,
                PrefKeysUtil.KEY_USER,
                PrefKeysUtil.KEY_USER_EMAIL,
                user.getEmail()
        );

        //Date -> Long
        PrefData.addSharedPreferences(
                context,
                PrefKeysUtil.KEY_USER,
                PrefKeysUtil.KEY_USER_DATE,
                user.getDate_account().getTime()
        );
    }

    /**
     * Método responsável por resgatar a instânca de {@link User}
     * armazenada no {@link android.content.SharedPreferences}
     *
     * @param context Contém o getSharedPreferences
     * @return Instância de {@link User}
     */
    public static User getUserSharedPreferences(Context context) {

        //User Instance
        User user = new User();

        //ID
        user.setId(
                PrefData.getLongSharedPreferences(
                    context,
                    PrefKeysUtil.KEY_USER,
                    PrefKeysUtil.KEY_USER_ID
            )
        );

        //Name
        user.setName(
                PrefData.getStringSharedPreferences(
                        context,
                        PrefKeysUtil.KEY_USER,
                        PrefKeysUtil.KEY_USER_NAME
                )
        );

        //Fictional Name
        user.setFic_name(
                PrefData.getStringSharedPreferences(
                        context,
                        PrefKeysUtil.KEY_USER,
                        PrefKeysUtil.KEY_USER_FIC_NAME
                )
        );

        //Email
        user.setEmail(
                PrefData.getStringSharedPreferences(
                        context,
                        PrefKeysUtil.KEY_USER,
                        PrefKeysUtil.KEY_USER_EMAIL
                )
        );

        //Date -> Long to Date
        Date date = new Date();
        date.setTime(
                PrefData.getLongSharedPreferences(
                    context,
                    PrefKeysUtil.KEY_USER,
                    PrefKeysUtil.KEY_USER_DATE
                )
        );
        user.setDate_account(date);

        return user;
    }

}
