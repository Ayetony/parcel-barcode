package com.logistic.parcel.utils;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.*;

public class BarcodeUtils {
    /**
     * 生成文件
     *
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     *
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     */
    public static void generate(String msg, OutputStream ous) {
        if (!StringUtils.hasLength(msg) || ous == null) {
            return;
        }

        Code128Bean bean = new Code128Bean();
        bean.doQuietZone(true);

        // 精细度
        final int dpi = 203;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(3.0f / dpi);
        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setBarHeight(20);
        bean.setMsgPosition(HumanReadablePlacement.HRP_TOP);
        String format = "image/png";
        try {
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, msg);
            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String msg = "503811196902765";
        String path = "src/main/resources/static/pages/images/barcode.png";
        generateFile(msg, path);
    }
}
