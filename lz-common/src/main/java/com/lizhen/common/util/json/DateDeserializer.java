package com.lizhen.common.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * 有些特定需求, 比如说搜索引擎,
 * 很多人都要求时间必须是时间戳. 所以,
 * 我们把时间转成最原始的Long型.
 * Gson默认的是不支持的, 需要手动处理一下.
 *
 * @author Created by Administrator on 2017/6/16.
 */
public class DateDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new Date(json.getAsJsonPrimitive().getAsLong());
    }
}
