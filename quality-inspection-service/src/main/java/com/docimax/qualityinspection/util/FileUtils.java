package com.docimax.qualityinspection.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class FileUtils {

    /**
     * 方法：图片合并PDF
     * 描述：
     * 参数：
     * 返回值：
     * 作者：ZiHeng Wang<br>
     * 时间：2023/7/27 16:26<br>
     */
    public static ByteArrayOutputStream createPdfFromImages(List<byte[]> imageBytesList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            for (byte[] imageBytes : imageBytesList) {
                PDPage page = new PDPage();
                document.addPage(page);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    BufferedImage bufferedImage = readTiffImage(imageBytes);
                    PDImageXObject imageXObject = LosslessFactory.createFromImage(document, bufferedImage);
                    // 计算图片在A4页面上绘制的大小，保持原始宽高比
                    float imageWidth = imageXObject.getWidth();
                    float imageHeight = imageXObject.getHeight();
                    float a4Width = PDRectangle.A4.getWidth();
                    float a4Height = PDRectangle.A4.getHeight();
                    float scale = Math.min(a4Width / imageWidth, a4Height / imageHeight);
                    float scaledWidth = imageWidth * scale;
                    float scaledHeight = imageHeight * scale;
                    float startX = (a4Width - scaledWidth) / 2;
                    float startY = (a4Height - scaledHeight) / 2;
                    contentStream.drawImage(imageXObject, startX, startY, scaledWidth, scaledHeight);
                }
            }
            document.save(outputStream);
        }
        return outputStream;
    }

    /**
     * 方法：图片合并PDF---读取Tiff格式图片
     * 描述：
     * 参数：
     * 返回值：
     * 作者：ZiHeng Wang<br>
     * 时间：2023/7/28 10:48<br>
     */
    private static BufferedImage readTiffImage(byte[] imageBytes) throws IOException {
        // 使用之前提到的ImageReader代码，根据需要选择解码器
        ImageReader reader = null;
        try (ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (readers.hasNext()) {
                reader = readers.next();
                reader.setInput(input);
                return reader.read(0);
            }
        } finally {
            if (reader != null) {
                reader.dispose();
            }
        }
        throw new IOException("Cannot read TIFF image");
    }

    /**
     * 方法：拆分PDF
     * 描述：将PDF文件拆分成图片集合
     * 参数：
     * 返回值：
     * 作者：ZiHeng Wang<br>
     * 时间：2023/7/27 16:26<br>
     */
    public static List<byte[]> convertPdfToImages(InputStream inputStream) {
        List<byte[]> imageFiles = new ArrayList<>();

        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int numPages = document.getNumberOfPages();
            for (int pageIndex = 0; pageIndex < numPages; pageIndex++) {
                // 设置 DPI
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 200);
                // 将 BufferedImage 转换为字节数组
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
//                MultipartFile multipartFile = new MockMultipartFile(
//                        "split.png",
//                        "split.png",
//                        "image/png",
//                        imageBytes);
                imageFiles.add(imageBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageFiles;
    }

}
