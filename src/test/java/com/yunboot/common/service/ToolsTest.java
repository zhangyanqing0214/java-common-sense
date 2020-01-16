package com.yunboot.common.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 *
 * @author yqzhang
 * @version 1.0, 2020/1/13 14:11
 * @since JDK 1.8
 */
public class ToolsTest
{
    public static void main(String[] args)
    {
        //图片缩放
        //ImgUtil.scale(FileUtil.file("H:\\1002281.jpg"), FileUtil.file("H:\\1002281-scale.jpg"), 0.5f);
        //ImgUtil.cut(FileUtil.file("H:\\1002281.jpg"), FileUtil.file("H:\\1002281-cut.jpg"), new Rectangle(200, 200, 100, 100));
        //ImgUtil.slice(FileUtil.file("H:/1002281.jpg"), FileUtil.file("H:/dest/"), 200, 200);
        //        ImgUtil.pressText(//
        //                FileUtil.file("H:/1002281.jpg"), //
        //                FileUtil.file("H:\\1002281-pressText.jpg"), //
        //                "版权所有", Color.WHITE, //文字
        //                new Font("黑体", Font.BOLD, 100), //字体
        //                0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
        //                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
        //                0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        //        );

        //        ImgUtil.pressImage(FileUtil.file("H:/1002266.jpg"), FileUtil.file("H:\\1002266-pressImage.jpg"), ImgUtil.read(FileUtil.file("G:\\wx_mini\\wetchat-mall-mini\\images\\pt_image.png")), //水印图片
        //                0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
        //                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
        //                0.8f);
        //        ImgUtil.gray(FileUtil.file("H:/1002266.jpg"), FileUtil.file("H:\\1002266-gray.jpg"));
        //        String dirs = "F:/360downloads/wpcache/srvsetwp/";
        //        String dest = "H:/dest/";
        //        long start = System.currentTimeMillis();
        //        zipImg(dirs, dest, 0.8f, "H:/dest.zip");
        //        System.out.println(System.currentTimeMillis() - start);
        System.out.println(IdUtil.randomUUID());
        System.out.println(IdUtil.simpleUUID());
        System.out.println(IdUtil.objectId());
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        for (int i = 0; i < 10; i++)
        {
            System.out.println(snowflake.nextIdStr());
        }
    }

    public static void zipImg(String dirs, String dest, float scale, String destZipPath)
    {
        if (StringUtils.isEmpty(dest))
        {
            File file = FileUtil.mkdir(new File(FileUtil.getTmpDirPath() + "img_" + System.currentTimeMillis()));
            dest = file.getPath() + File.separator;
        }
        if (!FileUtil.exist(dest) || !FileUtil.isDirectory(dest))
        {
            FileUtil.mkdir(dest);
        }
        List<String> fileNames = FileUtil.listFileNames(dirs);
        System.out.println("获取目录文件：" + dirs);
        System.out.println("文件个数：" + fileNames.size());
        for (int i = 0; i < fileNames.size(); i++)
        {
            String fileName = fileNames.get(i);
            ImgUtil.scale(FileUtil.file(dirs + fileName), FileUtil.file(dest + fileName), scale);
            System.out.println("压缩第：" + (i + 1) + "张图片");
        }
        if (destZipPath != null)
        {
            ZipUtil.zip(dest, destZipPath);
            System.out.println("压缩后的文件路径：" + destZipPath);
        }
        else
        {
            ZipUtil.zip(dest);
            System.out.println("压缩后的文件路径：同目录下" + FileUtil.getName(dest) + ".zip");
        }
    }
}
