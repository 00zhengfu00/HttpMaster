package kale.http.framework.impl;

/**
 * @author Jack Tony
 * @date 2015/8/4
 */
public interface HttpResponse<T> {

    public void onSuccess(T t);
    
    public void onError(Exception ex);
    
}
