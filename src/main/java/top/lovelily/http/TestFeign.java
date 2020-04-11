package top.lovelily.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import feign.*;
import feign.codec.ErrorDecoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static feign.FeignException.errorStatus;

/**
 * Desc: TestFeign
 * Author: xuhe
 * Date: 2019/12/11 5:02 下午
 * Version: 1.0
 */

class PayInfo implements Serializable {


    private String pay_type;
    private String product_id;

    PayInfo(String pay_type, String product_id) {
        this.pay_type = pay_type;
        this.product_id = product_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}

class Result<T> implements Serializable{
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

interface OrderClient {
    @RequestLine("POST /api/orders")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Result create(@Param("pay_type") String payType, @Param("product_id") String productId, @Param("user_id") String userId);

    @RequestLine("POST /1/ecall/orders")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Result create(@HeaderMap Map<String, String> headerMap, @Param("pay_type") String payType, @Param("product_id") String productId);


    @RequestLine("POST /api/orders")
    @Headers("Content-Type: application/x-www-form-urlencoded")
   // @Headers("Content-Type: multipart/form-data") // todo: 有问题
    Result createByPOJO(@HeaderMap Map<String,String> headerMap, PayInfo payInfo);

    @RequestLine("POST /1/ecall/orders")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8") // todo:使用对象接收有问题
    Result createByMap(@HeaderMap Map headerMap, @QueryMap Map<String, Object> paramMap);

    @RequestLine("POST /user/xcx/bind") // auth 使用@RequestParam接收没问题
    Result<Map> xcxBinding(@HeaderMap Map headMap, @QueryMap Map<String, Object> value);

    // get not 200 code
    @RequestLine("GET /devices/sn/{sn}")
    Result getDevice(@Param("sn") String sn);

}
 class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = null;
        try {
            body = Util.toString(response.body().asReader());
        } catch (IOException e) {

        }
        if (response.status() >= 400 && response.status() <= 500) {
          //  throw Exceptions.badRequestParams(body);
            Gson gson = new Gson();
            Map<String, String> map = new HashMap();
            map = gson.fromJson(body, HashMap.class);
            throw new BusinessException(map.get("code"), map.get("msg"));
        }
        return errorStatus(methodKey, response);
    }
}

public class TestFeign {
    public static void main(String[] args) {
        SimpleModule customModule = new SimpleModule("ecallModule", new Version(1, 0, 0, "", "", ""));
        customModule.addSerializer(Long.class, new ToStringSerializer());
        // json读写
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(customModule)
                .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
                .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setTimeZone(TimeZone.getTimeZone("GMT+8"));


        String url = "http://localhost:9002";
        url = "https://gw.test.itransbit.info";
        url = "http://treasure.test.getqood.com";

        HashMap headerMap = Maps.newHashMap();
        headerMap.put("Access-Token", "1194288477575840157.e28c30b4512f4cdbad43692e3f2cbfc6");

        OrderClient client = Feign.builder()
                            .encoder(new FormEncoder())
                            .decoder(new JacksonDecoder(mapper))
                            .errorDecoder(new FeignClientErrorDecoder()) // 非2xx返回处理
                            .target(OrderClient.class, url);


        //Result result = client.create(headerMap, "alipay", "9631140071604226");
        try {
            Result result = client.getDevice("N000099");
        } catch (BusinessException e) {
            System.out.println(e.getCode() + ": " + e.getMsg());
        }



//        url = "http://auth.test.getqood.com";
//        OrderClient client = Feign.builder()
//                .encoder(new FormEncoder(new JacksonEncoder()))     // encode a request
//                .decoder(new JacksonDecoder(mapper))                // decode a response
//                .target(OrderClient.class, url);
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("nick_name", "张三丰");
//        params.put("avatar", "http://www.baidu.com");
//        params.put("union_id", "sdfsfsdfdsfsfs");
//        Result<Map> result = client.xcxBinding(headerMap, params);


//        OrderClient client = Feign.builder()
//                .encoder(new FormEncoder(new JacksonEncoder()))     // encode a request
//                .decoder(new JacksonDecoder(mapper))                // decode a response
//                .target(OrderClient.class, url);
//
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("pay_type", "alipay");
//        paramMap.put("product_id", "9631140071604226");
//        // header not worked?
//        // paramMap.put("user_id", "12345");
//        Result result = client.createByMap(headerMap, paramMap);



        // pojo not work?
      //  OrderInfo result = client.createByPOJO(new PayInfo("alipay", "9631140071604226", "123"));
        //OrderInfo orderInfo = client.createByPOJO(headerMap, new PayInfo("alipay", "9631140071604226"));
    }
}