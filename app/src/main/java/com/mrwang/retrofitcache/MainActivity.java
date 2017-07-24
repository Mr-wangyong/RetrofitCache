package com.mrwang.retrofitcache;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mrwang.retrofitcacheinterceptor.RetrofitCacheInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

  private WebView webView;
  private API api;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    webView = (WebView) findViewById(R.id.webView);
    initRetrofit();
    initWebView();
    requestAPI();
  }

  /**
   * set cacheInterceptor to Retrofit
   */
  private void initRetrofit() {

    //create a cache
    File cacheDir = Environment.getExternalStorageDirectory();
    Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);

    //create

    RetrofitCacheInterceptor retrofitCacheInterceptor =
      new RetrofitCacheInterceptor(getApplicationContext());
        //this is not needful


    //set httpClint cache and
    OkHttpClient okHttpClient = new OkHttpClient()
      .newBuilder()
      .addInterceptor(retrofitCacheInterceptor)
      .cache(cache)
      .build();


    Retrofit build = new Retrofit
      .Builder()
      .baseUrl("http://api.jcodecraeer.com/")
      //set client
      .client(okHttpClient)
      .addConverterFactory(ScalarsConverterFactory.create())
      .build();
    api = build.create(API.class);
  }

  private void initWebView() {
    webView.setWebViewClient(new WebViewClient());
    webView.getSettings().setJavaScriptEnabled(true);
  }


  private void requestAPI() {
    api.getNew().enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
        if (response.isSuccessful())
          try {
            webView.loadData(response.body().string(), "text/html; charset=utf-8", "UTF-8");
          } catch (Exception e) {
            e.printStackTrace();
          }
      }

      @Override
      public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
        t.printStackTrace();
      }
    });
  }

}
