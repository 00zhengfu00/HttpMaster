package kale.http.framework;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import kale.http.framework.impl.HttpRequest;
import kale.http.framework.impl.HttpResponse;
import kale.http.framework.util.HttpUtil;

/**
 * @author Jack Tony
 * @date 2015/8/4
 */
public class OldHttpFrameWork extends HttpRequest {

    @Override
    public <Model> void doPost(@NonNull final String url, @Nullable final HashMap<String, String> map, @Nullable Class<Model> modelClass,
            @NonNull final HttpResponse<Model> response) {
        doHttp(url, "post", map, modelClass, response); // 通过继承的方法来调用原先的网络框架
    }

    @Override
    public <Model> void doGet(@NonNull final String url, @Nullable Class<Model> modelClass, @NonNull final HttpResponse<Model> response) {
        doHttp(url, "get", null, modelClass, response); // 通过继承的方法来调用原先的网络框架
    }

    /**
     * 原先的网络框架，需要做一定的修改
     */
    private static <Model> void doHttp(String url, final String mode, HashMap<String, String> map, final Class<Model> modelClass,
            final HttpResponse<Model> response) {
        if (mode.equals("post")) {
            // map 遍历
            if (map != null) {
                // TODO: 2015/8/4 map不为空时遍历 
            } else {
                // TODO: 2015/8/4 map为null时的操作
            }
            HttpUtil.doPostAsyn(url, "params", new HttpUtil.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    if (modelClass != null) {
                        if (modelClass.equals(result.getClass())) {
                            response.onSuccess((Model) result);
                        } else {
                            response.onSuccess(MyApplication.getGson().fromJson(result, modelClass));
                        }
                    } else {
                        response.onSuccess(null);
                    }
                }

                @Override
                public void onError() {
                    response.onError(new TimeoutException("网络超时"));
                }
            });
        } else {
            HttpUtil.doGetAsyn(url, new HttpUtil.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    if (modelClass != null) {
                        if (modelClass.equals(result.getClass())) {
                            response.onSuccess((Model) result);
                        } else {
                            response.onSuccess(MyApplication.getGson().fromJson(result, modelClass));
                        }
                    } else {
                        response.onSuccess(null);
                    }
                }

                @Override
                public void onError() {
                    response.onError(new TimeoutException("网络超时"));
                }
            });
        }
    }


}
