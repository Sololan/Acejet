package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.entity.view.VBanner;
import com.wejoy.wejoycms.service.ArticleService;
import com.wejoy.wejoycms.service.BannerService;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RestController
@Api(tags = "轮播管理")
@RequestMapping("/banner")
public class BannerController {
    @Value("${picturepath.upload-url}")
    private String uploadUrl;

    @Value("${picturepath.loading-url}")
    private String loadingUrl;

    @Value("${picturepath.db-url}")
    private String dbUrl;

    @Value("${picturepath.watermark-url}")
    private String watermarkUrl;

    @Autowired
    BannerService bannerService;

    @Autowired
    ArticleService articleService;

    @ApiOperation("查询轮播列表")
    @GetMapping("/getAllBannerS")
    public RespEntity<List<TBanner>> getAllBannerS() {
        return CtrlUtil.exe(r -> r.setVal(bannerService.getAllBannerS()));
    }

    @ApiOperation("查询开启状态的轮播列表")
    @GetMapping("/getAllOpenBannerS")
    public RespEntity<List<TBanner>> getAllOpenBannerS() {
        return CtrlUtil.exe(r -> r.setVal(bannerService.getAllOpenBannerS()));
    }

    @ApiOperation("查询轮播列表视图")
    @GetMapping("/getAllBannerSView")
    public RespEntity<List<VBanner>> getAllBannerSView() {
        System.out.println(bannerService.getAllBannerSView());
        return CtrlUtil.exe(r -> r.setVal(bannerService.getAllBannerSView()));
    }

    @ApiOperation("修改轮播信息")
    @PostMapping("/saveBanner")
    public RespEntity<String> saveBanner(@RequestBody TBanner banner) {
        return CtrlUtil.exe(r -> r.setVal(bannerService.saveBanner(banner)));
    }


    @ApiOperation("根据轮播id查询轮播详细信息")
    @GetMapping("/getBannerById/{id}")
    public RespEntity<TBanner> getBannerById(@PathVariable("id") Long id) {
        return CtrlUtil.exe(r -> r.setVal(bannerService.getBannerById(id)));
    }

    @ApiOperation("根据轮播id查询轮播详细信息视图")
    @GetMapping("/getBannerByIdView/{id}")
    public RespEntity<VBanner> getBannerByIdView(@PathVariable("id") Long id) {
        return CtrlUtil.exe(r -> r.setVal(bannerService.getBannerByIdView(id)));
    }

    @ApiOperation("修改轮播启用状态")
    @PutMapping("/taggleBanner")
    public RespEntity<String> taggleBanner(@RequestBody Map<String, Object> map) {
        return CtrlUtil.exe(r -> r.setVal(bannerService.taggleBanner((Integer) map.get("id"), (Integer) map.get("status"))));
    }

    @ApiOperation("获取所有可以选择指向的图片")
    @GetMapping("/getAllSelectableArticle")
    public RespEntity<List<TArticle>> getAllSelectableArticle() {
        return CtrlUtil.exe(r -> r.setVal(articleService.getAllSelectableArticle()));
    }

    @ApiOperation("上传图片")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RespEntity<String> upload(@RequestParam("file") MultipartFile fileContent, @RequestParam(value = "watermark", required = false) Integer watermark,
                                     @RequestParam(value = "watermarkText", required = false) String watermarkText) {
        // 用于拼接文件名
        String uuid = UUID.randomUUID().toString();
        String suffix = fileContent.getOriginalFilename().substring(fileContent.getOriginalFilename().lastIndexOf("."));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String millis = String.valueOf(calendar.getTimeInMillis());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = null;
        String endPath = null;
        try {
            inputStream = fileContent.getInputStream();
            fileName = fileContent.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            //改地址
            String path = uploadUrl + year + "/" + month + "/" + day + "/";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            endPath = tempFile.getPath() + "/" + uuid + "-" + millis + suffix;
            outputStream = new FileOutputStream(endPath);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 0不做处理  1为水印图片  2为水印文字
        try {
            if (watermark == 0) {

            } else if (watermark == 1) {
                Image srcImg = ImageIO.read(new File(endPath));
                BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                        srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
                // 得到画笔对象
                // Graphics g= buffImg.getGraphics();
                Graphics2D g = buffImg.createGraphics();

                // 设置对线段的锯齿状边缘处理
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                        .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);


                // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
                ImageIcon imgIcon = new ImageIcon(watermarkUrl);
                System.out.println(imgIcon);
                // 得到Image对象。
                Image img = imgIcon.getImage();
                float alpha = 0.9f; // 透明度
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                        alpha));

                // 表示水印图片的位置
                double width = buffImg.getWidth();
                double logowidth = width * 0.1;
                double logoheigh = logowidth * 81 / 88;
                int logowi = (int) logowidth;
                int logohe = (int) logoheigh;
                g.drawImage(img, 15, 15, logowi, logohe, null);

                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

                g.dispose();
                OutputStream os1 = null;
                os1 = new FileOutputStream(endPath);

                // 生成图片
                ImageIO.write(buffImg, "JPG", os1);

                System.out.println("图片完成添加Icon印章。。。。。。");
                os1.close();
            } else {
                // 水印透明度
                float alpha = 0.6f;
                // 水印横向位置
                int positionWidth = 50;
                // 水印纵向位置
                int positionHeight = 100;
                // 水印文字字体
                Font font = new Font("宋体", Font.BOLD, 20);
                // 水印文字颜色
                Color color = Color.red;

                // 0、图片类型
                String type = endPath.substring(endPath.indexOf(".") + 1, endPath.length());

                // 1、源图片
                Image srcImg = ImageIO.read(new File(endPath));

                int imgWidth = srcImg.getWidth(null);
                int imgHeight = srcImg.getHeight(null);

                BufferedImage buffImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

                // 2、得到画笔对象
                Graphics2D g = buffImg.createGraphics();
                // 3、设置对线段的锯齿状边缘处理
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(srcImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), 0, 0, null);
                // 4、设置水印旋转
//                if (null != degree) {
//                    g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
//                }
                // 5、设置水印文字颜色
                g.setColor(color);
                // 6、设置水印文字Font
                g.setFont(font);
                // 7、设置水印文字透明度
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
                // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)

                g.drawString(watermarkText, positionWidth, positionHeight);
                // 9、释放资源
                g.dispose();
                // 10、生成图片
                OutputStream os2;
                os2 = new FileOutputStream(endPath);
                // ImageIO.write(buffImg, "JPG", os);
                ImageIO.write(buffImg, type.toUpperCase(), os2);
                os2.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CtrlUtil.exe(r -> r.setVal(dbUrl + year + "/" + month + "/" + day + "/" + uuid + "-" + millis + suffix));
    }

    @ApiOperation("上传附件")
    @PostMapping(value = "/enclosure/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RespEntity<String> upload(@RequestParam("file") MultipartFile fileContent) {
        // 用于拼接文件名
        String uuid = UUID.randomUUID().toString();
        String suffix = fileContent.getOriginalFilename().substring(fileContent.getOriginalFilename().lastIndexOf("."));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String millis = String.valueOf(calendar.getTimeInMillis());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = null;
        String endPath = null;
        try {
            inputStream = fileContent.getInputStream();
            fileName = fileContent.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            //改地址
            String path = uploadUrl + year + "/" + month + "/" + day + "/";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            endPath = tempFile.getPath() + "/" + uuid + "-" + millis + suffix;
            outputStream = new FileOutputStream(endPath);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CtrlUtil.exe(r -> r.setVal(dbUrl + year + "/" + month + "/" + day + "/" + uuid + "-" + millis + suffix));
    }


}
