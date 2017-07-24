# RetrofitCache
优雅的给 Retrofit 加上 Cache

只需要一行代码,就让你的应用,即可拥有 离线缓存的功能



####  使用

1. 创建一个CacheInterceptor

```
RetrofitCacheInterceptor retrofitCacheInterceptor =
new RetrofitCacheInterceptor(getApplicationContext());
```

2. 创建一个 cache 目录(一般配置`Retrofit` 的时候都会配置)

```
File cacheDir = Environment.getExternalStorageDirectory();
Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
```

3. 给 OkHttp 设置上 cacheInterceptor

   ```
   OkHttpClient okHttpClient = new OkHttpClient()
         .newBuilder()
         .addInterceptor(retrofitCacheInterceptor)
         .cache(cache)
         .build();
   ```

4. 最后,配置完 `Retrofit`

   ```
   Retrofit build = new Retrofit
         .Builder()
         .baseUrl("http://api.jcodecraeer.com/")
         //set client
         .client(okHttpClient)
         .addConverterFactory(ScalarsConverterFactory.create())
         .build();
   ```



其实,大部分项目都已经配置完成了 cache 和 OkHttpClint

这个时候,只需要在 OkHttpClint里面加上即可

```
addInterceptor(new RetrofitCacheInterceptor(context))
```



