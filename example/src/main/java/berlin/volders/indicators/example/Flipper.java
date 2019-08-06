/*
 * Copyright (C) 2017 volders GmbH with <3 in Berlin
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

package berlin.volders.indicators.example;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import berlin.volders.indicators.PageIndicators;

public class Flipper extends View implements PageIndicators.PageCountObserver, PageIndicators.PageScrollObserver {

    private final Paint paint;
    private final float cornerRadius;
    private final RectF rect = new RectF();

    private int page;
    private int pageCount;
    private float pageOffset;
    private float spacing;
    private double rotationMod;

    public Flipper(Context context) {
        this(context, null);
    }

    public Flipper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Flipper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cornerRadius = context.getResources().getDimension(R.dimen.indicator_radius);
        paint = new Paint();
        setupPaint(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Flipper(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        cornerRadius = context.getResources().getDimension(R.dimen.indicator_radius);
        paint = new Paint();
        setupPaint(context);
    }

    private void setupPaint(Context context) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{R.attr.colorAccent});
        paint.setColor(a.getColor(0, 0));
        paint.setAntiAlias(true);
        paint.setAlpha(123);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (pageCount == 0) {
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        } else {
            float width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            float diameter = 2 * cornerRadius;
            spacing = (width - diameter) / pageCount - diameter;
            float minHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            float height = Math.min(minHeight, 2 * diameter + spacing);
            rotationMod = Math.asin((height - diameter) / (spacing + diameter)) / Math.PI;
            setMeasuredDimension((int) width, (int) height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (pageCount != 0) {
            float diameter = 2 * cornerRadius;
            int height = getHeight();
            double rotation = Math.sin(Math.PI * ((1 - 2 * rotationMod) * pageOffset + rotationMod));
            double diagonal = Math.min(diameter + spacing, (height - diameter) / rotation);
            rect.left = (pageCount - page - 1) * (diameter + spacing);
            rect.right = rect.left + diameter + (float) diagonal;
            rect.bottom = height;
            rect.top = rect.bottom - diameter;
            canvas.rotate(-180 * pageOffset, rect.left + cornerRadius, rect.bottom - cornerRadius);
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
        }
    }

    @Override
    public void onPageCountChanged(@NonNull PageIndicators indicators, int pageCount) {
        if (this.pageCount != pageCount) {
            this.pageCount = pageCount;
            requestLayout();
            invalidate();
        }
    }

    @Override
    public void onPageScrolled(@NonNull PageIndicators indicators, int page, float pageOffset) {
        if (this.page != page || this.pageOffset != pageOffset) {
            this.page = page;
            this.pageOffset = pageOffset;
            invalidate();
        }
    }
}
