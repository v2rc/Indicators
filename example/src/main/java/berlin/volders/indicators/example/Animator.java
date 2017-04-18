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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import berlin.volders.indicators.PageIndicators;

public class Animator extends View implements PageIndicators.PageScrollObserver {

    private final Paint paint;
    private final RectF rect = new RectF();
    private float radius = 0;

    public Animator(Context context) {
        this(context, null);
    }

    public Animator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Animator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        setupPaint(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Animator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
        setupPaint(context);
    }

    private void setupPaint(Context context) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{R.attr.colorAccent});
        paint.setAntiAlias(true);
        paint.setColor(a.getColor(0, 0));
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(rect, radius, radius, paint);
    }

    @Override
    public void onPageScrolled(@NonNull PageIndicators indicators, int page, float pageOffset) {
        if (pageOffset < 0.5f) {
            rect.left = 0;
            rect.right = 2 * pageOffset * getWidth();
        } else {
            int width = getWidth();
            rect.left = 2 * pageOffset * width - width;
            rect.right = width;
        }
        rect.bottom = getHeight();
        radius = rect.bottom / 2;
        invalidate();
    }
}
