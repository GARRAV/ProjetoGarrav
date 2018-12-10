package br.com.garrav.projetogarrav.util;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {

    /**
     * Método responsável por requisitar ao usuário a permissão para o
     * uso de sua localização para uso no Fragment do Google Maps
     *
     * @param activity Activity sendo usada em tempo real
     * @param REQUEST_PERMISSIONS_CODE Código para liberar a permissão
     * @author Felipe Savaris
     * @since 10/12/2018
     */
    public static void callAccessFine_CoarseLocation(Activity activity,
                                                     final int REQUEST_PERMISSIONS_CODE) {

        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                REQUEST_PERMISSIONS_CODE
        );
    }

}
