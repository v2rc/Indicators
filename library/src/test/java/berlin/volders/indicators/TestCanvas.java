/*
 * Copyright (C) 2016 Christian Schmitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package berlin.volders.indicators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestCanvas extends Canvas {

    boolean empty = true;

    @Override
    public void drawRGB(int r, int g, int b) {
        empty = false;
    }

    @Override
    public void drawARGB(int a, int r, int g, int b) {
        empty = false;
    }

    @Override
    public void drawColor(int color) {
        empty = false;
    }

    @Override
    public void drawColor(int color, PorterDuff.Mode mode) {
        empty = false;
    }

    @Override
    public void drawPaint(Paint paint) {
        empty = false;
    }

    @Override
    public void drawPoints(float[] pts, int offset, int count, Paint paint) {
        empty = false;
    }

    @Override
    public void drawPoints(float[] pts, Paint paint) {
        empty = false;
    }

    @Override
    public void drawPoint(float x, float y, Paint paint) {
        empty = false;
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        empty = false;
    }

    @Override
    public void drawLines(float[] pts, int offset, int count, Paint paint) {
        empty = false;
    }

    @Override
    public void drawLines(float[] pts, Paint paint) {
        empty = false;
    }

    @Override
    public void drawRect(RectF rect, Paint paint) {
        empty = false;
    }

    @Override
    public void drawRect(Rect r, Paint paint) {
        empty = false;
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom, Paint paint) {
        empty = false;
    }

    @Override
    public void drawOval(RectF oval, Paint paint) {
        empty = false;
    }

    @Override
    public void drawOval(float left, float top, float right, float bottom, Paint paint) {
        empty = false;
    }

    @Override
    public void drawCircle(float cx, float cy, float radius, Paint paint) {
        empty = false;
    }

    @Override
    public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
        empty = false;
    }

    @Override
    public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
        empty = false;
    }

    @Override
    public void drawRoundRect(RectF rect, float rx, float ry, Paint paint) {
        empty = false;
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint) {
        empty = false;
    }

    @Override
    public void drawPath(Path path, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(Bitmap bitmap, float left, float top, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(int[] colors, int offset, int stride, float x, float y, int width, int height, boolean hasAlpha, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(int[] colors, int offset, int stride, int x, int y, int width, int height, boolean hasAlpha, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmapMesh(Bitmap bitmap, int meshWidth, int meshHeight, float[] verts, int vertOffset, int[] colors, int colorOffset, Paint paint) {
        empty = false;
    }

    @Override
    public void drawVertices(VertexMode mode, int vertexCount, float[] verts, int vertOffset, float[] texs, int texOffset, int[] colors, int colorOffset, short[] indices, int indexOffset, int indexCount, Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(char[] text, int index, int count, float x, float y, Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(String text, float x, float y, Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(String text, int start, int end, float x, float y, Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(CharSequence text, int start, int end, float x, float y, Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextRun(char[] text, int index, int count, int contextIndex, int contextCount, float x, float y, boolean isRtl, Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextRun(CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint) {
        empty = false;
    }

    @Override
    public void drawPosText(char[] text, int index, int count, float[] pos, Paint paint) {
        empty = false;
    }

    @Override
    public void drawPosText(String text, float[] pos, Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextOnPath(char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint) {
        empty = false;
    }

    @Override
    public void drawPicture(Picture picture) {
        empty = false;
    }

    @Override
    public void drawPicture(Picture picture, RectF dst) {
        empty = false;
    }

    @Override
    public void drawPicture(Picture picture, Rect dst) {
        empty = false;
    }

    public void assertEmpty() {
        assertThat(empty, is(true));
    }
}
