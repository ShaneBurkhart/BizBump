package com.bizbump.utils;

import android.graphics.Bitmap;

import com.google.zxing.common.BitMatrix;

public final class QRCodeUtiils {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private QRCodeUtiils() {}

    public static Bitmap toBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}