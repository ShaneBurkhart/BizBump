package me.occucard.storage.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Shane on 8/5/13.
 */
public class MemoryCache{

    private static MemoryCache instance;

    public static MemoryCache getInstance(){
        if(instance == null)
            instance = new MemoryCache();
        return instance;
    }

    private static final int DIVISION_OF_MEMORY = 4;
    private int getMaxSize(){
        return (int) (Runtime.getRuntime().maxMemory() / 1024 / DIVISION_OF_MEMORY);
    }

    private MemoryCache(){
        this.cache = new LruCache<String, Bitmap>(getMaxSize()){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    private LruCache<String, Bitmap> cache;

    public void clear(){
        cache.evictAll();
    }

    public void add(String url, Bitmap bit){
        if(cache.get(url) == null)
            cache.put(url, bit);
    }

    public Bitmap get(String url){
        return cache.get(url);
    }
}
