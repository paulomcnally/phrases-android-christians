package chocoyolabs.phrase.christian.api;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class Api {

    private static OkHttpClient client = new OkHttpClient();

    private static final String BASE = "http://api.frase.club/api";

    public static String getResource(String resource) {
        return BASE + resource;
    }

    public static OkHttpClient getClient() {
        client.setConnectTimeout(10, TimeUnit.MINUTES); // connect timeout
        client.setReadTimeout(10, TimeUnit.MINUTES);    // socket timeout
        client.setWriteTimeout(10, TimeUnit.MINUTES);
        return client;
    }
}