package com.yunboot.common.converter;

import cn.hutool.core.bean.copier.CopyOptions;

import java.util.List;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 *
 * @author yqzhang
 * @version 1.0, 2020/1/15 17:48
 * @since JDK 1.8
 */
public interface Converter<T, V>
{
    public V converter(T value, Class<V> beanClass);

    public V converter(T value, Class<V> beanClass, String... ignoreProperties);

    public V converter(T value, Class<V> beanClass, CopyOptions copyOptions);

    public List<V> converterToList(List<T> sources, Class<V> elementType);

    public List<V> converterToList(List<T> sources, Class<V> elementType, String... ignoreProperties);

    public List<V> converterToList(List<T> sources, Class<V> elementType, CopyOptions copyOptions);
}
