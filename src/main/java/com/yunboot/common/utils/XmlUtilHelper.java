package com.yunboot.common.utils;

import java.io.Writer;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XmlUtilHelper
{

    /**
     * XML转对象
     * 
     * @param clazz
     *            对象类
     * @param str
     *            xml字符串
     * @param <T>
     *            T
     * @return
     */
    public static <T> T parseFromXml(Class<T> clazz, String xml)
    {
        // 创建解析XML对象
        XStream xStream = new XStream(new DomDriver());
        // 处理注解
        xStream.processAnnotations(clazz);
        @SuppressWarnings("unchecked")
        // 将XML字符串转为bean对象
        T t = (T) xStream.fromXML(xml);
        return t;
    }

    /**
     * 对象转xml
     * 
     * @param obj
     *            对象
     * @return
     */
    public static String toXml(Object obj)
    {
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.processAnnotations(obj.getClass());
        return xStream.toXML(obj);
    }

    /**
     * [简要描述]:对象转xml支持CDATA标签
     * [详细描述]:除了整型和浮点型其他的都加CDATA
     * 
     * @author yqzhang
     * @param obj
     * @return
     */
    public static String toXmlSupportCDATA(Object obj)
    {
        XStream xstream = new XStream(new XppDriver(new NoNameCoder())
        {
            public HierarchicalStreamWriter createWriter(Writer out)
            {

                return new PrettyPrintWriter(out)
                {
                    // 对那些xml节点的转换增加CDATA标记 true增加 false反之
                    boolean cdata = false;

                    @SuppressWarnings("rawtypes")
                    @Override
                    public void startNode(String name, Class clazz)
                    {
                        super.startNode(name, clazz);
                    }

                    @Override
                    public void setValue(String text)
                    {
                        if (text != null && !"".equals(text))
                        {
                            // 浮点型判断
                            Pattern patternInt = Pattern.compile("[0-9]*(\\.?)[0-9]*");
                            // 整型判断
                            Pattern patternFloat = Pattern.compile("[0-9]+");
                            // 如果是整数或浮点数 就不要加[CDATA[]了
                            if (patternInt.matcher(text).matches() || patternFloat.matcher(text).matches())
                            {
                                cdata = false;
                            }
                            else
                            {
                                cdata = true;
                            }
                        }
                        super.setValue(text);
                    }

                    // 重写节点name 使其可以支持下划线"_"
                    @Override
                    public String encodeNode(String name)
                    {
                        return name;
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text)
                    {

                        if (cdata)
                        {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        }
                        else
                        {
                            writer.write(text);
                        }
                    }
                };
            }
        });
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }
}
