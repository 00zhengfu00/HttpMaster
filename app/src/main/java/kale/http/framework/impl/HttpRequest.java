package kale.http.framework.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * @author Jack Tony
 * @date 2015/8/4
 */
public abstract class HttpRequest {

    ////////////////////////////////// POST //////////////////////////////////

    public <Model> void doPost(@NonNull String url) {
        doPost(url, null); // 强制赋空
    }

    public <Model> void doPost(@NonNull String url, @Nullable Class<Model> modelClass,
            @NonNull Action<Model> onSuccess) {

        doPost(url, null, modelClass, onSuccess, null); // 强制赋空
    }

    public <Model> void doPost(@NonNull String url, @Nullable Class<Model> modelClass,
            @NonNull final Action<Model> onSuccess,
            @NonNull final Action<Exception> onError) {
        doPost(url, null, modelClass, onSuccess, onError);
    }

    public <Model> void doPost(@NonNull String url, @Nullable HashMap<String, String> map) {
        doPost(url, map, null, null, null); // 强制赋空
    }

    public <Model> void doPost(@NonNull String url, @Nullable HashMap<String, String> map, @Nullable Class<Model> modelClass,
            @NonNull Action<Model> onSuccess) {

        doPost(url, map, modelClass, onSuccess, null); // 强制赋空
    }

    public <Model> void doPost(@NonNull String url, @Nullable HashMap<String, String> map, @Nullable Class<Model> modelClass,
            @NonNull final Action<Model> onSuccess,
            @NonNull final Action<Exception> onError) {

        doPost(url, map, modelClass, new HttpResponse<Model>() {
            @Override
            public void onSuccess(Model model) {
                if (onSuccess != null) { // 强制判断是否为空
                    onSuccess.call(model);
                }
            }

            @Override
            public void onError(Exception ex) {
                if (onError != null) { // 强制判断是否为空
                    onError.call(ex);
                }
            }
        });
    }

    abstract public <Model> void doPost(@NonNull final String url, @Nullable final HashMap<String, String> map, @Nullable Class<Model> modelClass,
            @NonNull final HttpResponse<Model> response);

    ////////////////////////////////// GET //////////////////////////////////

    public <Model> void doGet(@NonNull String url) {
        doGet(url, null, null, null); // 强制赋空
    }

    public <Model> void doGet(@NonNull String url, @Nullable Class<Model> modelClass, @NonNull Action<Model> onSuccess) {
        doGet(url, modelClass, onSuccess, null); // 强制赋空
    }

    public <Model> void doGet(@NonNull String url, @Nullable Class<Model> modelClass,
            @NonNull final Action<Model> onSuccess, @NonNull final Action<Exception> onError) {
        doGet(url, modelClass, new HttpResponse<Model>() {
            @Override
            public void onSuccess(Model model) {
                if (onSuccess != null) { // 强制判断是否为空
                    onSuccess.call(model);
                }
            }

            @Override
            public void onError(Exception ex) {
                if (onError != null) { // 强制判断是否为空
                    onError.call(ex);
                }
            }
        });
    }

    abstract public <Model> void doGet(@NonNull final String url, @Nullable Class<Model> modelClass, @NonNull final HttpResponse<Model> response);


}
