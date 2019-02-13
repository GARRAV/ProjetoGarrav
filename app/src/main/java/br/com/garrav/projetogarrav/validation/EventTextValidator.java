package br.com.garrav.projetogarrav.validation;

import android.content.Context;

import java.util.Calendar;

import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class EventTextValidator {

    /**
     * Método responsável por verificar se a data informada pelo usuário é
     * válida ou não para o Evento. Os filtros de datas se referem ao passado,
     * ou seja, caso a data esteja em um dia anterior a data atual, o resultado
     * será false para a reprovação da data
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
     * Método Responsável por verificar a hora informada pelo usuário
     * para criação de eventos e garantir que passe pelos devidos filtros
     * para envio para servidor. Os filtros de horas se referem ao passado,
     * presente e futuro, ou seja, caso a hora informada seja anterior ou
     * igual a hora do dia atual, a hora será reprovada. Caso o Evento
     * ocorrra na data atual que foi cadastrada, a hora para o evento
     * deverá obrigatóriamente estar no minimo três horas adiantadas para
     * aprovação da hora, caso contrário será reprovada.
     * Os minutos não passam por avaliação.
     *
     * @param context Contexto da atual activity em execução do android
     * @param day Dia informado pelo usuário
     * @param month Mês informado pelo usuário
     * @param year Ano informado pelo usuário
     * @param hour Hora informado pelo usuário
     * @return Resultado da validação da Hora, se true a hora está valida
     * se false, a hora está inválida
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
                        "Evento de dia atual não pode ter seu horário marcado para " +
                                "a mesma hora ou antes!"
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
                                "até o evento começar!"
                );
                return false;
            }
        }

        return true;
    }

    /**
     * Método responsável por verificar os dados informados
     * informados pelo usuário para a criação de um novo evento
     * a ser enviada para o servidor. Os filtros se referem a
     * todos os campos estarem vazios, sete caracteres no minimo
     * para o nome do evento e cinco caracteres para o objetivo
     * do evento
     *
     * @param context Contexto da atual activity em execução do android
     * @param nameEvent Nome do evento
     * @param dateEvent Data do evento
     * @param timeEvent Hora do evento
     * @param objectiveEvent Objetivo do evento
     * @return Resultado da validação
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
