package cc.buddies.app.appdemo.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * 构建项目的时候，创建默认的GlideApp类，并按照GlideBuilder配置初始化Glide。
 * 会在AndroidManifest文件中application下添加一个meta-data.
 * <meta-data
 *      android:name="cc.buddies.app.appdemo.glide.CustomGlideModule"
 *      android:value="GlideModule"/>
 */
@GlideModule
public class CustomGlideModule extends AppGlideModule {

    /**
     * 如果实现AppGlideModule后，则将isManifestParsingEnabled()方法返回false，防止AndroidManifest初始化两遍。
     * 默认返回true
     * @return false
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false; // 可以提升初始化速度，避免一些潜在错误。
    }

    /**
     * 在这里配置Glide。
     * @param context 上下文
     * @param builder GlideBuilder
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);

        // setMemoryCacheScreens设置MemoryCache应该能够容纳的像素值的设备屏幕数，说白了就是缓存多少屏图片，默认值是2。
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));


//        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));

        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));


        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
