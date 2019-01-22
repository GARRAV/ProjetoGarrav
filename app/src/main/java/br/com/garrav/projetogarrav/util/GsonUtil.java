package br.com.garrav.projetogarrav.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

public class GsonUtil {

    public static JsonSerializer<Date> DATE_SERIALIZER = new JsonSerializer<Date>() {
        /**
         * Método responsável por Serializar a data para utilizar
         * no JSON a ser enviado ao servidor
         *
         * @param src Data a ser convertida
         * @param typeOfSrc Tipo de conversão
         * @param context Contexto da conversão JSON
         * @return Data serializada
         * @author Felipe Savaris
         * @since 21/12/2018
         */
        @Override
        public JsonElement serialize(Date src,
                                     Type typeOfSrc,
                                     JsonSerializationContext context) {

            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    };

    public static JsonDeserializer<Date> DATE_DESERIALIZAER = new JsonDeserializer<Date>() {
        /**
         * Método responsável por Deserializar a data recebida
         * no JSON enviado pelo servidor
         *
         * @param json Json recebido pelo servidor
         * @param typeOfT Tipo de conversão
         * @param context Contexto da conversão JSON
         * @return Data Deserializada
         * @throws JsonParseException Erro acontecido durante execução
         * @author Felipe Savaris
         * @since 24/12/2018
         */
        @Override
        public Date deserialize(JsonElement json,
                                Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {

            return json == null ? null : new Date(json.getAsLong());
        }
    };
}
