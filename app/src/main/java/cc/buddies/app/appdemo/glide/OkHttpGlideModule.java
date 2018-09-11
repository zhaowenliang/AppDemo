package cc.buddies.app.appdemo.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

@GlideModule
public class OkHttpGlideModule extends LibraryGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        // OkHttp


        // Volley
        // registry.replace(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(context));
    }
}
