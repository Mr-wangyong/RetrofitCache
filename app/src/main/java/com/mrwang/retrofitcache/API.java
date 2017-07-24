package com.mrwang.retrofitcache;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/7/24
 * Time: 下午5:33
 */
public interface API {
  @GET("article_detail.php?id=3873")
  Call<ResponseBody> getNew();
}
