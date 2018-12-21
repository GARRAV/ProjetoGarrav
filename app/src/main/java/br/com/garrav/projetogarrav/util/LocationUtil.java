package br.com.garrav.projetogarrav.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class LocationUtil {

    /**
     * Método responsável por retornar uma lista de endereços basados
     * na latitude e longitude informadas pelo google maps
     *
     * @param context Contexto da atual activity em execução do android
     * @param latitude Latidude pega pelo google maps
     * @param longitude Longitude pega pelo google maps
     * @return Lista de endereços adiquiridos pelas coordenadas
     * @author Felipe Savaris
     * @since 18/12/2018
     */
    public Address seekAddress(Context context,
                                double latitude,
                               double longitude) {

        //Variáveis
        Address address = null;
        List<Address> addresses;
        Geocoder geocoder = new Geocoder(context);

        try {

            /*
            Pega uma lista de endereços possiveis pela
            latitude e longitude informadas
             */
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
            );
            //Caso contenha endereço, é adicionado para a lista
            if(addresses.size() > 0) {
                address = addresses.get(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

}
