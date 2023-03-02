package com.shaktipumps.shakti_rms.aryabata.splash_demo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.github.amlcurran.showcaseview.ShowcaseDrawer;

public class CustomShowcaseView implements ShowcaseDrawer {

    private final float width;
    private final float height;
    private final Paint eraserPaint;
    private final Paint basicPaint;
    private final int eraseColour;
    private final RectF renderRect;

    public CustomShowcaseView(float _width, float _height, int _eraserPaintColor, int _eraserColor) {
        width = _width;
        height = _height;
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        eraserPaint = new Paint();
        eraserPaint.setColor(_eraserColor);
        eraserPaint.setAlpha(0);
        eraserPaint.setXfermode(xfermode);
        eraserPaint.setAntiAlias(true);
        eraseColour = _eraserColor;
        basicPaint = new Paint();
        renderRect = new RectF();
    }

    @Override
    public void setShowcaseColour(int color) {
        eraserPaint.setColor(color);
    }

    @Override
    public void drawShowcase(Bitmap buffer, float x, float y, float scaleMultiplier) {
        Canvas bufferCanvas = new Canvas(buffer);
        renderRect.left = x - width / 2f;
        renderRect.right = x + width / 2f;
        renderRect.top = y - height / 2f;
        renderRect.bottom = y + height / 2f;
        bufferCanvas.drawRoundRect(renderRect, 70f, 70f, eraserPaint);
        //bufferCanvas.drawRect(renderRect, eraserPaint);
    }

    @Override
    public int getShowcaseWidth() {
        return (int) width;
    }

    @Override
    public int getShowcaseHeight() {
        return (int) height;
    }

    @Override
    public float getBlockedRadius() {
        return width;
    }

    @Override
    public void setBackgroundColour(int backgroundColor) {
    }

    @Override
    public void erase(Bitmap bitmapBuffer) {
        bitmapBuffer.eraseColor(eraseColour);
    }

    @Override
    public void drawToCanvas(Canvas canvas, Bitmap bitmapBuffer) {
        canvas.drawBitmap(bitmapBuffer, 0, 0, basicPaint);
    }

}
