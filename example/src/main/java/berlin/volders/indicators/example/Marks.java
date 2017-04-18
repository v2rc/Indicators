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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.StateSet;

public class Marks extends Drawable {

    private final ConstantState state = new MarkState();

    private final String mark;
    private final Paint paint;
    private final Rect bounds;

    private Marks(String mark, Paint paint) {
        this.mark = mark;
        this.paint = new Paint(paint);
        this.bounds = new Rect();
        setup();
    }

    private Marks(char mark, Context context) {
        this.mark = String.valueOf(mark);
        this.paint = new Paint();
        this.bounds = new Rect();
        setup(context);
    }

    private Marks(char mark, int alpha, Context context) {
        this(mark, context);
        setAlpha(alpha);
    }

    private void setup(Context context) {
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(context.getResources().getDimension(R.dimen.indicator_mark_size));
    }

    private void setup() {
        paint.getTextBounds(mark, 0, 1, bounds);
    }

    @Override
    public int getIntrinsicWidth() {
        return bounds.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return bounds.height();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = canvas.getClipBounds();
        canvas.drawText(mark, bounds.centerX(), bounds.bottom, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Nullable
    @Override
    public ConstantState getConstantState() {
        return state;
    }

    public static Drawable create(Context context) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected}, new Marks('✓', context));
        drawable.addState(StateSet.WILD_CARD, new Marks('✗', 56, context));
        return drawable;
    }

    private class MarkState extends ConstantState {

        @NonNull
        @Override
        public Marks newDrawable() {
            return new Marks(mark, paint);
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }
}
