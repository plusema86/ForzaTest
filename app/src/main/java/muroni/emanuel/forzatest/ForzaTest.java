package muroni.emanuel.forzatest;

import android.app.Application;

import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

public class ForzaTest extends Application {

    ForzaTest forzaTest;
    private Long cacheFile = 8L * 1024L * 1024L;


    @Override
    public void onCreate() {
        super.onCreate();
        forzaTest = this;
        SimpleCache cache = new SimpleCache(new File(getFilesDir().getPath() + "/toro_cache"),
                new LeastRecentlyUsedCacheEvictor(cacheFile));

    }
}
