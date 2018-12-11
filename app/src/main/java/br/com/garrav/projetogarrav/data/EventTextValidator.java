package br.com.garrav.projetogarrav.data;

import br.com.garrav.projetogarrav.model.Event;

public class EventTextValidator {

    /**
     * Método responsável por receber uma String contendo a latitude
     * e longitude do local selecionado no Maps e formatá-lo para separar
     * seu conteúdo para os inserir em uma instância de Event em suas
     * devidas variáveis Double. O texto recebido é separado em duas
     * partes e colocados em duas variáveis Double da instância Event
     *
     * @param event Instância de Event que receberá Latitude e Longitude
     *              em suas variáveis
     * @param latLng Latitude e Longitude em forma de texto
     * @author Felipe Savaris
     * @since 11/12/2018
     */
    public void valLatitudeLongitude(Event event,
                                     String latLng) {

        //Processo de Formatação de texto
        latLng = latLng.replace("lat/lng: (", "");
        latLng = latLng.replace(",", " ");
        latLng = latLng.replace(")", "");

        //Get Double Latitude from String
        event.setLatitude(
                Double.parseDouble(
                        latLng.substring(
                                0,
                                latLng.indexOf(" ")
                        )
                )
        );
        //Get Double Longitude from String
        event.setLongitude(
                Double.parseDouble(
                        latLng.substring(
                                latLng.lastIndexOf(" ") + 1,
                                latLng.length()
                        )
                )
        );
    }

}
