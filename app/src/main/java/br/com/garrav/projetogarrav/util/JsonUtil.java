package br.com.garrav.projetogarrav.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

public class JsonUtil {

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

}
