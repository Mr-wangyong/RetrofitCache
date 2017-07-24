# RetrofitCache
优雅的给 Retrofit 加上 Cache

只需要一行代码,就让你的应用,即可拥有 离线缓存的功能



####  集成

添加依赖库

```
project build.gradle加入jitpack

allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}

项目的build.gradle加入
compile 'com.github.Mr-wangyong:RetrofitCache:v1.0'
```



#### 开始使用

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



注意:Retrofit 为 RESTful API 而生,所以内部开启缓存只支持 `GET` 请求,对于部分项目后台接口混乱, `GET` `POST`乱飞的情况,暂时不支持`POST`缓存



### 附言 

```
HTTP协议中共定义了八种方法或者叫“动作”来表明对Request-URI指定的资源的不同操作方式，具体介绍如下： 

OPTIONS：返回服务器针对特定资源所支持的HTTP请求方法。也可以利用向Web服务器发送'*'的请求来测试服务器的功能性。 
HEAD：向服务器索要与GET请求相一致的响应，只不过响应体将不会被返回。这一方法        可以在不必传输整个响应内容的情况下，就可以获取包含在响应消息头中的元信息。 
GET   向特定的资源发出请求。 
POST：向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。数据被包含在请求体中。POST请求可能会导致新的资源的创建和/或已有资源的修改。 
PUT：向指定资源位置上传其最新内容。 
DELETE：请求服务器删除Request-URI所标识的资源。 
TRACE：回显服务器收到的请求，主要用于测试或诊断。 

CONNECT：HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器。

```

原理请看我之前的文章:

| [Retrofit 源码解读之离线缓存策略的实现](http://www.jianshu.com/p/3a8d910cce38)


