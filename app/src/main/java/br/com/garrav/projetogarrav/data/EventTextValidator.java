package br.com.garrav.projetogarrav.data;

import android.content.Context;

import java.util.Calendar;

import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.util.MessageActionUtil;

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

    /**
     * Método responsável por verificar se a data informada pelo usuário é
     * válida ou não para o Evento. Os filtros de datas se referem ao passado,
     * ou seja, caso a data esteja em um dia anterior a data atual, o resultado
     * será false para a aprovação da data
     *
     * @param context Contexto da atual activity em execução do android
     * @param day Dia informado pelo usuário
     * @param month Mês informado pelo usuário
     * @param year Ano informado pelo usuário
     * @return Retorno do resultado da validação da data, se true a data está valida
     *         se false, a data está inválida
     * @author Felipe Savaris
     * @since 12/12/2018
     */
    public boolean valEventDate(Context context,
                                int day,
                                int month,
                                int year) {

        Calendar calendar = Calendar.getInstance();

        //Filter Date Event
        //Dia
        if(day < calendar.get(Calendar.DAY_OF_MONTH)) {
            MessageActionUtil.makeText(
                    context,
                    "O dia do evento não pode ser anterior à data atual!"
            );
            return false;
        }
        //Mês
        if(month < calendar.get(Calendar.MONTH)) {
            MessageActionUtil.makeText(
                    context,
                    "O dia do evento não pode ser anterior à data atual!"
            );
            return false;
        }
        //Ano
        if(year < calendar.get(Calendar.YEAR)) {
            MessageActionUtil.makeText(
                    context,
                    "O dia do evento não pode ser anterior à data atual!"
            );
            return false;
        }

        return true;
    }

    /**
     *
     * @param context
     * @param nameEvent
     * @param objectiveEvent
     * @return
     * @author Felipe Savaris
     * @since 12/12/2018
     */
    public boolean valEventData(Context context,
                                String nameEvent,
                                String objectiveEvent) {

        //Filter Event Name
        if(nameEvent.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "Insira um nome para o Evento!"
            );
            return false;
        }
        if(nameEvent.length() < 7) {
            MessageActionUtil.makeText(
                    context,
                    "O nome do evento deve conter mais de 7 caracteres!"
            );
            return false;
        }

        //Filter Event Objective
        if(objectiveEvent.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "Insira um objetivo para o evento!"
            );
            return false;
        }
        if(objectiveEvent.length() < 5) {
            MessageActionUtil.makeText(
                    context,
                    "O objetivo do evento deve conter mais de 5 caracteres"
            );
            return false;
        }

        return true;
    }

}
