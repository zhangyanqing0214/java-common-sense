package com.yunboot.common.converter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 *
 * @author yqzhang
 * @version 1.0, 2020/1/16 9:14
 * @since JDK 1.8
 */
@Component
public class ConverterService<T, V> implements Converter<T, V>
{

    @Override
    public V converter(T value, Class<V> beanClass)
    {
        return converter(value, beanClass, null, null);
    }

    @Override
    public V converter(T value, Class<V> beanClass, String... ignoreProperties)
    {
        return converter(value, beanClass, null, ignoreProperties);
    }

    @Override
    public V converter(T value, Class<V> beanClass, CopyOptions copyOptions)
    {
        return converter(value, beanClass, copyOptions, null);
    }

    @Override
    public List<V> converterToList(List<T> sources, Class<V> elementType)
    {
        return converterToList(sources, elementType, null, null);
    }

    @Override
    public List<V> converterToList(List<T> sources, Class<V> elementType, String... ignoreProperties)
    {
        return converterToList(sources, elementType, null, ignoreProperties);
    }

    @Override
    public List<V> converterToList(List<T> sources, Class<V> elementType, CopyOptions copyOptions)
    {

        return converterToList(sources, elementType, copyOptions, null);
    }

    public V converter(T value, Class<V> beanClass, CopyOptions copyOptions, String... ignoreProperties)
    {
        V v = null;
        try
        {
            //不支持非bean对象转换
            if (value == null || !BeanUtil.isBean(beanClass))
            {
                return null;
            }
            v = beanClass.newInstance();
            if (copyOptions != null)
            {
                BeanUtil.copyProperties(value, v, copyOptions);
            }
            else if (ignoreProperties != null && ignoreProperties.length > 0)
            {
                BeanUtil.copyProperties(value, v, ignoreProperties);
            }
            else
            {
                BeanUtil.copyProperties(value, v);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return v;
    }

    public List<V> converterToList(List<T> sources, Class<V> elementType, CopyOptions copyOptions, String... ignoreProperties)
    {
        List<V> list = new ArrayList<>();
        if (sources == null || sources.isEmpty())
        {
            return list;
        }
        for (T source : sources)
        {
            list.add(converter(source, elementType, copyOptions, ignoreProperties));
        }
        return list;
    }
}
