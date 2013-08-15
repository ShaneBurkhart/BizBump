package me.occucard.storage.cache;

/**
 * Created by Shane on 8/15/13.
 */
public class OccucardTokenCache {

    private static OccucardTokenCache instance;

    private String occucardToken;

    public String getToken(){
        return occucardToken;
    }

    public void setToken(String token){
        this.occucardToken = token;
    }

    public boolean hasToken(){
        if(occucardToken != null)
            return true;
        else
            return false;
    }

    public void claarToken(){
        this.occucardToken = null;
    }

    private OccucardTokenCache(){}

    public static OccucardTokenCache getInstance(){
        if(instance == null)
            instance = new OccucardTokenCache();
        return instance;
    }
}
