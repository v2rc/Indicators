/*
 * Copyright (C) 2016 volders GmbH with <3 in Berlin
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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A page indicator implementation to display stateful drawables.
 */
public class PageIndicatorView extends LinearLayout
        implements PageIndicators.PageObserver, PageIndicators.PageCountObserver {

    /**
     * Indicates to use the intrinsic size of the {@link Drawable}.
     *
     * @see #setIndicatorWidth(int)
     * @see #setIndicatorHeight(int)
     */
    public static final int INTRINSIC_SIZE = -1;

    @VisibleForTesting
    View[] indicators = new View[0];

    private Drawable drawable;
    @Dimension
    private int spacing;
    @IndicatorSize
    private int width;
    @IndicatorSize
    private int height;

    private int weight;

    private int page;

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public PageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PageIndicatorView, defStyleAttr, defStyleRes);
        setIndicator(a.getDrawable(
                R.styleable.PageIndicatorView_indicator));
        setIndicatorSpacing(a.getDimensionPixelSize(
                R.styleable.PageIndicatorView_indicatorSpacing, 0));
        setIndicatorWidth(a.getLayoutDimension(
                R.styleable.PageIndicatorView_indicatorWidth, INTRINSIC_SIZE));
        setIndicatorHeight(a.getLayoutDimension(
                R.styleable.PageIndicatorView_indicatorHeight, INTRINSIC_SIZE));
        setFillViewport(a.getBoolean(
                R.styleable.PageIndicatorView_android_fillViewport, false));
        a.recycle();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Drawable getDividerDrawable() {
        Drawable divider = super.getDividerDrawable();
        if (divider instanceof SpaceDrawable) {
            return null;
        }
        return divider;
    }

    /**
     * @param fillViewport if {@code true} all indicators stretch to fill the viewport
     */
    public void setFillViewport(boolean fillViewport) {
        int weight = fillViewport ? 1 : 0;
        if (this.weight != weight) {
            this.weight = weight;
            updateIndicators();
        }
    }

    /**
     * @return Whether or not to fill the viewport
     */
    public boolean getFillViewport() {
        return weight == 1;
    }

    /**
     * @param resId of indicator drawable to display to the user
     * @see #setIndicator(Drawable)
     */
    public void setIndicator(@DrawableRes int resId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setIndicator(getResources().getDrawable(resId));
        } else {
            setIndicator(getContext().getDrawable(resId));
        }
    }

    /**
     * @param drawable indicator to display to the user
     */
    public void setIndicator(@Nullable Drawable drawable) {
        if (this.drawable != drawable) {
            this.drawable = drawable.mutate();
            updateIndicators();
        }
    }

    /**
     * @return the indicator {@link Drawable}
     */
    public Drawable getIndicator() {
        return drawable;
    }

    private SpaceDrawable getSpaceDrawable(int spacing) {
        if (spacing == 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable divider = super.getDividerDrawable();
            if (divider instanceof SpaceDrawable) {
                SpaceDrawable space = (SpaceDrawable) divider;
                space.spacing = spacing;
                return space;
            }
        }
        return new SpaceDrawable(spacing);
    }

    /**
     * @param spacing between the single indicators
     */
    public void setIndicatorSpacing(@Dimension int spacing) {
        if (this.spacing != spacing) {
            this.spacing = spacing;
            setDividerDrawable(getSpaceDrawable(spacing));
            setShowDividers(getShowDividers() | SHOW_DIVIDER_MIDDLE);
        }
    }

    /**
     * @return the spacing between the single indicators
     * @see #setIndicatorSpacing(int)
     */
    @Dimension
    public int getIndicatorSpacing() {
        return spacing;
    }

    /**
     * @param width of the drawable or {@link #INTRINSIC_SIZE} to use the intrinsic width
     *              returned by {@link Drawable#getIntrinsicWidth()}
     */
    public void setIndicatorWidth(@IndicatorSize int width) {
        if (this.width != width) {
            this.width = width;
            updateIndicators();
        }
    }

    /**
     * @return the indicator width
     * @see #setIndicatorWidth(int)
     */
    @IndicatorSize
    public int getIndicatorWidth() {
        return width;
    }

    /**
     * @param height of the drawable or {@link #INTRINSIC_SIZE} to use the intrinsic height
     *               returned by {@link Drawable#getIntrinsicHeight()}
     */
    public void setIndicatorHeight(@IndicatorSize int height) {
        if (this.height != height) {
            this.height = height;
            updateIndicators();
        }
    }

    /**
     * @return the indicator height
     * @see #setIndicatorHeight(int)
     */
    @IndicatorSize
    public int getIndicatorHeight() {
        return height;
    }

    private void updateIndicators() {
        for (View view : indicators) {
            setIndicatorDrawable(view, drawable, width, height);
        }
        requestLayout();
    }

    @CallSuper
    @Override
    public void onPageChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int page) {
        if (this.page != page) {
            updatePageSelection(this.page, false);
            this.page = page;
            updatePageSelection(page, true);
        }
    }

    @CallSuper
    @Override
    public void onPageCountChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int count) {
        if (this.indicators.length != count) {
            removeAllViews();
            View[] indicatorViews = new View[count];
            for (int i = 0; i < count; i++) {
                indicatorViews[i] = new View(getContext());
                addView(indicatorViews[i]);
                setIndicatorDrawable(indicatorViews[i], drawable, width, height);
            }
            this.indicators = indicatorViews;
            updatePageSelection(this.page, true);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = super.generateDefaultLayoutParams();
        params.weight = weight;
        return params;
    }

    private void updatePageSelection(int page, boolean selected) {
        if (page < indicators.length) {
            indicators[page].setSelected(selected);
        }
    }

    /**
     * @return the current page
     */
    public int getPage() {
        return page;
    }

    /**
     * @return the page count
     */
    public int getPageCount() {
        return indicators.length;
    }

    @VisibleForTesting
    static void setIndicatorDrawable(View view, Drawable drawable, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (drawable != null) {
            Drawable.ConstantState state = drawable.getConstantState();
            if (state != null) {
                drawable = state.newDrawable();
            }
            params.width = getIndicatorSize(width, drawable.getIntrinsicWidth());
            params.height = getIndicatorSize(height, drawable.getIntrinsicHeight());
        } else {
            params.width = params.height = 0;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    private static int getIndicatorSize(int size, int intrinsicSize) {
        if (size >= 0) {
            return size;
        }
        if (intrinsicSize >= 0) {
            return intrinsicSize;
        }
        return 0;
    }

    @Documented
    @Retention(SOURCE)
    @Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE, ANNOTATION_TYPE})
    @IntDef(INTRINSIC_SIZE)
    @IntRange(from = 0)
    @Dimension
    public @interface IndicatorSize {
    }
}
