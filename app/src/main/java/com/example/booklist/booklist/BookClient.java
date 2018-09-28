package com.example.booklist.booklist;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.loopj.android.http.AsyncHttpClient.log;

public class BookClient {
    private static final String API_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "api_key=51fdb273b8389f5500f1f60a57f6afdb&language=en-US&page=1&include_adult=false";
    private AsyncHttpClient client;

    public BookClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_URL + relativeUrl  ;
    }

    // Method for accessing the search API
    public void getBooks(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("search/movie?query=");
            url = url + URLEncoder.encode(query, "utf-8") + "&" + API_KEY ;
            log.d("URL--------",url);
            client.get(url, handler);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void getExtraBookDetails(String id, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/");
        log.d("URL--------",url + id +  "?" + API_KEY );
        client.get(url + id +  "?" + API_KEY , handler) ;


    }
}