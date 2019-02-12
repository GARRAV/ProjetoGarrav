package br.com.garrav.projetogarrav.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

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
    public static Address seekAddress(Context context,
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

    /**
     * Método responsável por calcular a distância em Metros
     * entre dois pontos medidos por {@link LatLng}
     * Atualmente possuí uma precisão de 100 metros em relação
     * ao Google Maps
     *
     * Método de calculo base -> Fórmula de Haversine
     *
     * @param startP Ponto Inicial
     * @param endP Ponto Final
     * @return Distância em Metros entre os dois pontos
     * @author Felipe Savaris
     * @since 12/02/2019
     */
    public static double getDistance(LatLng startP,
                                     LatLng endP) {

        //Raio da Terra - KM
        final int EARTH_RADIUS = 6371;

        //Ponto Inicial
        double lat1 = startP.latitude;
        double lng1 = startP.longitude;

        //Ponto Final
        double lat2 = endP.latitude;
        double lng2 = endP.longitude;

        //Distâcia entre Latitude e Longitude
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        //Trigonometric Cosine of an Angle
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = EARTH_RADIUS * c;

        return dist * 1000;
    }
}
