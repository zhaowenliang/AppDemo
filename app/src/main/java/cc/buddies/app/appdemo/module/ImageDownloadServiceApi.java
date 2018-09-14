package cc.buddies.app.appdemo.module;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ImageDownloadServiceApi {

    @GET
    Call<ResponseBody> downloadImageFromNet(@Url String imageUrl);

}
