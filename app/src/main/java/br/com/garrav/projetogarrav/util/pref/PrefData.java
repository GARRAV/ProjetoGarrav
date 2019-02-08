package br.com.garrav.projetogarrav.util.pref;

import android.content.Context;
import android.content.SharedPreferences;

class PrefData {

    /**
     * Método responsável por adicionar um {@link String} a um
     * {@link SharedPreferences} específico
     *
     * @param context Contém o getSharedPreferences
     * @param prefKey Chave do {@link SharedPreferences}
     * @param key Chave do Valor que será adicionado
     * @param value Valor a ser adicionado
     * @author Felipe Savaris
     * @since 08/02/2019
     */
    static void addSharedPreferences(Context context,
                                     String prefKey,
                                     String key,
                                     String value) {

        //Get SharedPreferences
        SharedPreferences pref = managerSharedPreferencesData(
                context,
                prefKey
        );

        //Faz o SharedPreferences selecionado Editavel
        SharedPreferences.Editor ed = pref.edit();

        //Put String value
        ed.putString(key, value);

        //Apply SharedPreferences
        ed.apply();
    }

    /**
     * Método responsável por adicionar um {@link Long} a um
     * {@link SharedPreferences} específico
     *
     * @param context Contém o getSharedPreferences
     * @param prefKey Chave do {@link SharedPreferences}
     * @param key Chave do Valor que será adicionado
     * @param value Valor a ser adicionado
     * @author Felipe Savaris
     * @since 08/02/2019
     */
    static void addSharedPreferences(Context context,
                                     String prefKey,
                                     String key,
                                     long value) {

        //Get SharedPreferences
        SharedPreferences pref = managerSharedPreferencesData(
                context,
                prefKey
        );

        //Faz o SharedPreferences selecionado Editavel
        SharedPreferences.Editor ed = pref.edit();

        //Put Long value
        ed.putLong(key, value);

        //Apply SharedPreferences
        ed.apply();
    }

    /**
     * Método responsável por resgatar valores {@link String} de
     * um {@link SharedPreferences} específico
     *
     * @param context Contém o getSharedPreferences
     * @param prefKey Chave do {@link SharedPreferences}
     * @param key Chave do Valor que será resgatado
     * @return Valor {@link String}
     * @author Felipe Savaris
     * @since 08/02/2019
     */
    static String getStringSharedPreferences(Context context,
                                             String prefKey,
                                             String key) {

        //Get SharedPreferences
        SharedPreferences pref = managerSharedPreferencesData(
                context,
                prefKey
        );

        //Get SharedPreferences String Value
        return pref.getString(key, null);
    }

    /**
     * Método responsável por resgatar valores {@link Long} de
     * um {@link SharedPreferences} específico
     *
     * @param context Contém o getSharedPreferences
     * @param prefKey Chave do {@link SharedPreferences}
     * @param key Chave do Valor que será resgatado
     * @return Valor {@link Long}
     * @author Felipe Savaris
     * @since 08/02/2019
     */
    static long getLongSharedPreferences(Context context,
                                         String prefKey,
                                         String key) {

        //Get SharedPreferences
        SharedPreferences pref = managerSharedPreferencesData(
                context,
                prefKey
        );

        //Get SharedPreferences Long Value
        return pref.getLong(key, 0);
    }

    /**
     * Método responsável por resgatar o {@link SharedPreferences} de
     * acordo com a chave informada
     *
     * @param context Contém o getSharedPreferences
     * @param prefKey Chave de um {@link SharedPreferences}
     * @return Conteúdo de um {@link SharedPreferences} específico
     * @author Felipe Savaris
     * @since 08/02/2019
     */
    private static SharedPreferences managerSharedPreferencesData(Context context,
                                                                  String prefKey) {

        //SharedPreferences
        return context.getSharedPreferences(
                prefKey,
                Context.MODE_PRIVATE
        );
    }

}
