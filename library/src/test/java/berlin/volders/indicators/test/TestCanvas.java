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

package berlin.volders.indicators.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("WeakerAccess")
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
    public void drawColor(int color, @NonNull PorterDuff.Mode mode) {
        empty = false;
    }

    @Override
    public void drawPaint(@NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawPoints(float[] pts, int offset, int count, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawPoints(@NonNull float[] pts, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawPoint(float x, float y, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawLines(@NonNull float[] pts, int offset, int count, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawLines(@NonNull float[] pts, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawRect(@NonNull RectF rect, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawRect(@NonNull Rect r, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawOval(@NonNull RectF oval, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawOval(float left, float top, float right, float bottom, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawCircle(float cx, float cy, float radius, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawPath(@NonNull Path path, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(@NonNull Bitmap bitmap, float left, float top, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(@NonNull Bitmap bitmap, Rect src, @NonNull RectF dst, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(@NonNull Bitmap bitmap, Rect src, @NonNull Rect dst, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(@NonNull int[] colors, int offset, int stride, float x, float y, int width, int height, boolean hasAlpha, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(@NonNull int[] colors, int offset, int stride, int x, int y, int width, int height, boolean hasAlpha, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, Paint paint) {
        empty = false;
    }

    @Override
    public void drawBitmapMesh(@NonNull Bitmap bitmap, int meshWidth, int meshHeight, @NonNull float[] verts, int vertOffset, int[] colors, int colorOffset, Paint paint) {
        empty = false;
    }

    @Override
    public void drawVertices(@NonNull VertexMode mode, int vertexCount, @NonNull float[] verts, int vertOffset, float[] texs, int texOffset, int[] colors, int colorOffset, short[] indices, int indexOffset, int indexCount, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(@NonNull char[] text, int index, int count, float x, float y, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(@NonNull String text, float x, float y, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(@NonNull String text, int start, int end, float x, float y, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawText(@NonNull CharSequence text, int start, int end, float x, float y, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextRun(@NonNull char[] text, int index, int count, int contextIndex, int contextCount, float x, float y, boolean isRtl, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextRun(@NonNull CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawPosText(@NonNull char[] text, int index, int count, @NonNull float[] pos, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawPosText(@NonNull String text, @NonNull float[] pos, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextOnPath(@NonNull char[] text, int index, int count, @NonNull Path path, float hOffset, float vOffset, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawTextOnPath(@NonNull String text, @NonNull Path path, float hOffset, float vOffset, @NonNull Paint paint) {
        empty = false;
    }

    @Override
    public void drawPicture(@NonNull Picture picture) {
        empty = false;
    }

    @Override
    public void drawPicture(@NonNull Picture picture, @NonNull RectF dst) {
        empty = false;
    }

    @Override
    public void drawPicture(@NonNull Picture picture, @NonNull Rect dst) {
        empty = false;
    }

    public void assertEmpty() {
        assertThat(empty, is(true));
    }
}
