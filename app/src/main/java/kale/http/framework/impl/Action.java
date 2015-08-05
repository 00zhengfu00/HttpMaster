package kale.http.framework.impl;

/**
 * @author Jack Tony
 * @date 2015/8/4
 */
public interface Action<T> {

    void call(T t);
}
