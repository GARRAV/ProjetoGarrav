package br.com.garrav.projetogarrav.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class LocationUtil {

    /**
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     * @author Felipe Savaris
     * @since 18/12/2018
     */
    public Address seekAddress(Context context,
                                double latitude,
                               double longitude) {

        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(context);

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
            );
            if(addresses.size() > 0) {
                address = addresses.get(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

}
