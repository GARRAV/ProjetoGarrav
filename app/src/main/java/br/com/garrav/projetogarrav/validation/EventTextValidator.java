package br.com.garrav.projetogarrav.validation;

import android.content.Context;

import java.util.Calendar;

import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class EventTextValidator {

    /**
     * Método responsável por receber uma String contendo a latitude
     * e longitude do local selecionado no Maps e formatá-lo para separar
     * seu conteúdo para os inserir em suas devidas variáveis Double.
     * O texto recebido é separado em duas partes e colocado a latitude em sua
     * variável Double
     *
     * @param latLng Latitude e Longitude em forma de texto
     * @return Valor da latitude em Double
     * @author Felipe Savaris
     * @since 18/12/2018
     */
    public double valLatitude(String latLng) {

        //Processo de Formatação de texto
        latLng = latLng.replace("lat/lng: (", "");
        latLng = latLng.replace(",", " ");
        latLng = latLng.replace(")", "");

        //Get Double Latitude from String
        return Double.parseDouble(
                latLng.substring(
                        0,
                        latLng.indexOf(" ")
                )
        );
    }

    /**
     * Método responsável por receber uma String contendo a latitude
     * e longitude do local selecionado no Maps e formatá-lo para separar
     * seu conteúdo para os inserir em suas devidas variáveis Double.
     * O texto recebido é separado em duas partes e colocado a longitude em sua
     * variável Double
     *
     * @param latLng Latitude e Longitude em forma de texto
     * @return Valor da Longitude em Double
     * @author Felipe Savaris
     * @since 18/12/2018
     */
    public double valLongitude(String latLng) {

        //Processo de Formatação de texto
        latLng = latLng.replace("lat/lng: (", "");
        latLng = latLng.replace(",", " ");
        latLng = latLng.replace(")", "");

        //Get Double Longitude from String
        return Double.parseDouble(
                latLng.substring(
                        latLng.lastIndexOf(" ") + 1,
                        latLng.length()
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
            if(month == calendar.get(Calendar.MONTH)
                    &&
                    year == calendar.get(Calendar.YEAR)) {
                MessageActionUtil.makeText(
                        context,
                        "O dia do evento não pode ser anterior à data atual!"
                );
                return false;
            }
        }
        //Mês
        if(month < calendar.get(Calendar.MONTH)) {
            if(year == calendar.get(Calendar.YEAR)) {
                MessageActionUtil.makeText(
                        context,
                        "O dia do evento não pode ser anterior à data atual!"
                );
                return false;
            }
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
     * @param day
     * @param month
     * @param year
     * @param hour
     * @return
     * @author Felipe Savaris
     * @since 17/12/2018
     */
    public boolean valEventTime(Context context,
                                int day,
                                int month,
                                int year,
                                int hour) {

        Calendar cal = Calendar.getInstance();

        month = month + 1;

        //Filter Time Event
        //Hour
        //Dia atual
        if(cal.get(Calendar.DAY_OF_MONTH) == day
                &&
                cal.get(Calendar.MONTH) == month - 1
                &&
                cal.get(Calendar.YEAR) == year) {

            //Hora anterior
            if(hour <= cal.get(Calendar.HOUR_OF_DAY)) {
                MessageActionUtil.makeText(
                        context,
                        "Evento de dia atual, não pode ser marcado para " +
                                "a mesma hora"
                );
                return false;
            }
            //Duas horas
            if(hour == cal.get(Calendar.HOUR_OF_DAY) + 1
                    ||
                    hour == cal.get(Calendar.HOUR_OF_DAY) + 2) {
                MessageActionUtil.makeText(
                        context,
                        "Evento de dia atual, deve ter pelo menos 3 horas " +
                                "até o evento começar"
                );
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param context
     * @param nameEvent
     * @param dateEvent
     * @param timeEvent
     * @param objectiveEvent
     * @return
     * @author Felipe Savaris
     * @since 12/12/2018
     */
    public boolean valEventData(Context context,
                                String nameEvent,
                                String dateEvent,
                                String timeEvent,
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

        //Filter Event Date
        if(dateEvent.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "Insira uma data!"
            );
            return false;
        }

        //Filter Event Time
        if(timeEvent.isEmpty()) {
            MessageActionUtil.makeText(
                    context,
                    "Insira um horário"
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
