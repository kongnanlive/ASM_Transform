package github.leavesczy.asm.okHttp;

import java.util.ArrayList;
import java.util.List;

import github.leavesczy.asm.utils.ReflectUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class OkHttpHook {

    private static final String TAG = "OkHttpHook";
    private volatile static OkHttpHook sInstance;

    private OkHttpHook() {

    }

    public static OkHttpHook getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpHook.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpHook();
                }
            }
        }
        return sInstance;
    }

    public void addInterceptor(OkHttpClient client) {
        dealInterceptor(client);
    }

    public OkHttpClient addInterceptorWithReturn(OkHttpClient client) {
        dealInterceptor(client);
        return client;
    }

    private void dealInterceptor(OkHttpClient client) {
//        LogUtil.d(TAG, "OkHttpHook addInterceptor.");
        List<Interceptor> interceptors = new ArrayList<>(client.interceptors());
        List<Interceptor> networkInterceptors = new ArrayList<>(client.networkInterceptors());
        noDuplicateAdd(interceptors, new OkHttp3Interceptor());
        ReflectUtils.reflect(client).field("interceptors", interceptors);
        ReflectUtils.reflect(client).field("networkInterceptors", networkInterceptors);

//        LogUtil.d(TAG, "addInterceptor, success!");
    }

    private void noDuplicateAdd(List<Interceptor> interceptors, OkHttp3Interceptor interceptor) {
//        LogUtil.d(TAG, "noDuplicateAdd.");
        boolean hasInterceptor = false;
        for (Interceptor i : interceptors) {
            if (i instanceof OkHttp3Interceptor) {
                hasInterceptor = true;
                break;
            }
        }

        if (!hasInterceptor) {
            interceptors.add(interceptor);
//            LogUtil.d(TAG, "noDuplicateAdd, add:" + interceptor.TAG_INTERCEPTOR);
        }
    }
}