package com.yunboot.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.yunboot.common.vo.User;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 *
 * @author yqzhang
 * @version 1.0, 2020/1/15 14:25
 * @since JDK 1.8
 */
public class ListUtils
{
    public static boolean isEmpty(final List<?> list)
    {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(final List<?> list)
    {
        return !isEmpty(list);
    }

    public static <T> Map<String, T> listToMap(final List<T> list, final MapKeyEntity<T> mapKeyEntity)
    {
        if (isEmpty(list))
        {
            return new HashMap<>(16);
        }
        final Map<String, T> map = new HashMap<>((int) (list.size() / 0.75) + 1);
        for (final T t : list)
        {
            final String key = mapKeyEntity.getMapKey(t);
            map.put(key, t);
        }
        return map;
    }

    private static <E, V> List<V> listToList(final List<E> list, final Converter<E, V> converter, final boolean useParallel, final boolean distinct)
    {
        if (isEmpty(list))
        {
            return new ArrayList<>();
        }
        final Stream<E> stream = useParallel ? list.parallelStream() : list.stream();
        Stream<V> vStream = stream.map((Function<? super E, ? extends V>) converter::convert);
        if (distinct)
        {
            vStream = vStream.distinct();
        }
        return vStream.collect(Collectors.toList());
    }

    public static <E, V> List<V> listToList(final List<E> list, final Converter<E, V> converter)
    {
        return listToList(list, converter, false, false);
    }

    public static <E, V> List<V> listToList(final List<E> list, final Converter<E, V> converter, final boolean distinct)
    {
        return listToList(list, converter, false, distinct);
    }

    public static <E, V> List<V> parallelListToList(final List<E> list, final Converter<E, V> converter)
    {
        return listToList(list, converter, true, false);
    }

    private static <E, V> Map<V, List<E>> groupBy(final List<E> list, final Converter<E, V> converter, final boolean useParallel)
    {
        if (isEmpty(list))
        {
            return new HashMap<>(16);
        }
        final Stream<E> stream = useParallel ? list.parallelStream() : list.stream();
        return stream.collect(Collectors.groupingBy((Function<? super E, ? extends V>) converter::convert));
    }

    public static <E, V> Map<V, List<E>> groupBy(final List<E> list, final Converter<E, V> converter)
    {
        return groupBy(list, converter, false);
    }

    public static <E, V> Map<V, List<E>> parallelGroupBy(final List<E> list, final Converter<E, V> converter)
    {
        return groupBy(list, converter, true);
    }

    public static <E> List<E> filter(final List<E> list, final List<Predicate<E>> predicates)
    {
        if (isEmpty(list))
        {
            return list;
        }
        final Iterator<E> iterator = list.iterator();
        while (iterator.hasNext())
        {
            final E next = iterator.next();
            for (final Predicate<E> predicate : predicates)
            {
                if (predicate.test(next))
                {
                    iterator.remove();
                    break;
                }
            }
        }
        return list;
    }

    public static <E> List<E> filter(final List<E> list, final Predicate<E> predicate)
    {
        if (isEmpty(list))
        {
            return list;
        }
        list.removeIf(predicate);
        return list;
    }

    public interface Converter<E, V>
    {
        V convert(final E p0);
    }

    public interface MapKeyEntity<T>
    {
        String getMapKey(final T p0);
    }

    public static void main(String[] args)
    {
        List<User> users = CollectionUtil.newArrayList(new User("user1", "pwd1"), new User("user2", "pwd2"), new User("user3", "pwd3"));
        //testListToMap(users);
        //testListToList(users);
        testGroupBy(users);
    }

    private static void testGroupBy(List<User> users)
    {
        Map<String, List<User>> map = groupBy(users, new Converter<User, String>()
        {
            @Override
            public String convert(User p0)
            {
                return p0.getUsername();
            }
        }, true);
        map.forEach((k, v) ->
        {
            System.out.println("key=" + k + ",value=" + JSONUtil.toJsonStr(v));
        });
    }

    private static void testListToList(List<User> users)
    {
        List<Map<String, String>> list = listToList(users, new Converter<User, Map<String, String>>()
        {
            @Override
            public Map<String, String> convert(User user)
            {
                return MapUtil.of(user.getUsername(), user.getPassword());
            }
        }, true, true);
        list.forEach(System.out::println);
    }

    private static void testListToMap(List<User> users)
    {
        Map<String, User> map = listToMap(users, new MapKeyEntity<User>()
        {
            @Override
            public String getMapKey(User user)
            {
                return user.getUsername();
            }
        });
        map.forEach((k, v) ->
        {
            System.out.println("key=" + k + ",value=" + JSONUtil.toJsonStr(v));
        });
    }
}
