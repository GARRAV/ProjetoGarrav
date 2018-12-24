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
         *
         * @param src
         * @param typeOfSrc
         * @param context
         * @return
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
         *
         * @param json
         * @param typeOfT
         * @param context
         * @return
         * @throws JsonParseException
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
