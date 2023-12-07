package com.docimax.qualityinspection.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class RotatedMultipartFile implements MultipartFile {
    private final byte[] rotatedImageData;
    private final String originalFilename;
    private final String contentType;

    public RotatedMultipartFile(byte[] rotatedImageData, String originalFilename, String contentType) {
        this.rotatedImageData = rotatedImageData;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return originalFilename;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return rotatedImageData == null || rotatedImageData.length == 0;
    }

    @Override
    public long getSize() {
        return rotatedImageData.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return rotatedImageData;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayResource(rotatedImageData).getInputStream();
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("Not supported");
    }
}
