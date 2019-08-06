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
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
 * The Indicators framework base UI element. It handles all connections to the {@link ViewPager} and
 * notifies all observers of changes to the data, so that all indicators display the right values.
 */
@ViewPager.DecorView
public class PageIndicators extends FrameLayout {

    private final ViewPager.OnAdapterChangeListener adapterChangeListener
            = new OnAdapterChangeListener();
    private final ViewPager.OnPageChangeListener pageChangeListener
            = new OnPageChangeListener();
    private final DataSetObserver dataSetObserver = new DataSetObserverImpl();

    final Observers observers = new Observers();

    private ViewPager viewPager;
    private boolean addedAsDecor;

    public PageIndicators(Context context) {
        super(context);
    }

    public PageIndicators(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PageIndicators(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PageIndicators(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    @CallSuper
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (viewPager == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                setViewPager((ViewPager) parent);
                addedAsDecor = true;
            }
        }
    }

    @Override
    @CallSuper
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (addedAsDecor) {
            setViewPager(null);
        }
    }

    @Override
    @CallSuper
    public void onViewAdded(View child) {
        if (child instanceof Observer) {
            observers.registerObserver((Observer) child);
        }
    }

    @Override
    @CallSuper
    public void onViewRemoved(View child) {
        if (child instanceof Observer) {
            observers.unregisterObserver((Observer) child);
        }
    }

    /**
     * @param viewPager the {@link ViewPager} to bind this Indicators to
     */
    @CallSuper
    public void setViewPager(@Nullable ViewPager viewPager) {
        addedAsDecor = false;
        if (this.viewPager != viewPager) {
            if (this.viewPager != null) {
                unbindViewPager(this.viewPager);
            }
            this.viewPager = viewPager;
            setupPageCount(observers);
            if (viewPager != null) {
                bindViewPager(viewPager);
            }
            setupPage(observers);
        }
    }

    /**
     * @return the {@link ViewPager} this Indicators are bound to
     * @see #setViewPager(ViewPager)
     */
    @CallSuper
    public ViewPager getViewPager() {
        return viewPager;
    }

    void bindViewPager(@NonNull ViewPager viewPager) {
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.addOnAdapterChangeListener(adapterChangeListener);
        bindPagerAdapter(viewPager.getAdapter());
    }

    void unbindViewPager(@NonNull ViewPager viewPager) {
        unbindPagerAdapter(viewPager.getAdapter());
        viewPager.removeOnAdapterChangeListener(adapterChangeListener);
        viewPager.removeOnPageChangeListener(pageChangeListener);
    }

    void bindPagerAdapter(@Nullable PagerAdapter adapter) {
        if (adapter != null) {
            adapter.registerDataSetObserver(dataSetObserver);
        }
    }

    void unbindPagerAdapter(@Nullable PagerAdapter adapter) {
        if (adapter != null) {
            adapter.unregisterDataSetObserver(dataSetObserver);
        }
    }

    void setupPage(PageObserver observer) {
        int page = 0;
        if (viewPager != null) {
            page = viewPager.getCurrentItem();
        }
        observer.onPageChanged(this, page);
    }

    void setupPageCount(PageCountObserver observer) {
        int pageCount = 0;
        if (viewPager != null) {
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                pageCount = adapter.getCount();
            }
        }
        observer.onPageCountChanged(this, pageCount);
    }

    /**
     * @param observer to register to all callbacks being implemented
     * @see #unregisterObserver(Observer)
     * @see #unregisterAllObservers()
     * @see PageObserver
     * @see PageCountObserver
     * @see PageStateObserver
     * @see PageScrollObserver
     */
    @CallSuper
    public void registerObserver(@NonNull Observer observer) {
        observers.registerObserver(observer);
        if (observer instanceof PageObserver) {
            setupPage((PageObserver) observer);
        }
        if (observer instanceof PageCountObserver) {
            setupPageCount((PageCountObserver) observer);
        }
    }

    /**
     * @param observer to unregister from all callbacks
     * @see #registerObserver(Observer)
     * @see #unregisterAllObservers()
     */
    @CallSuper
    public void unregisterObserver(@NonNull Observer observer) {
        observers.unregisterObserver(observer);
    }

    /**
     * Unregister all observers from all callbacks.
     *
     * @see #registerObserver(Observer)
     * @see #unregisterObserver(Observer)
     */
    @CallSuper
    public void unregisterAllObservers() {
        observers.unregisterAllObservers();
    }

    /**
     * Base interface for all observer interfaces.
     * <p>
     * This class is mainly used to handle the registration of multi-observer implementations
     * with an universal implementation.
     *
     * @see #registerObserver(Observer)
     * @see #unregisterObserver(Observer)
     */
    interface Observer {
    }

    /**
     * Observer for the page selection.
     */
    public interface PageObserver extends Observer {

        /**
         * @param indicators sending this event
         * @param page       selected
         */
        void onPageChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int page);
    }

    /**
     * Observer for the page count.
     */
    public interface PageCountObserver extends Observer {

        /**
         * @param indicators sending this event
         * @param count      of pages
         */
        void onPageCountChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int count);
    }

    /**
     * Observer for the page scroll state.
     */
    public interface PageStateObserver extends Observer {

        /**
         * @param indicators sending this event
         * @param state      of {@link ViewPager#SCROLL_STATE_IDLE}, {@link ViewPager#SCROLL_STATE_DRAGGING} or {@link ViewPager#SCROLL_STATE_SETTLING}
         */
        void onPageStateChanged(@NonNull PageIndicators indicators, @State int state);

        @Documented
        @Retention(SOURCE)
        @Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE, ANNOTATION_TYPE})
        @IntDef({ViewPager.SCROLL_STATE_IDLE,
                ViewPager.SCROLL_STATE_DRAGGING,
                ViewPager.SCROLL_STATE_SETTLING})
        @interface State {
        }
    }

    /**
     * Observer for the page scroll offset.
     */
    public interface PageScrollObserver extends Observer {

        /**
         * @param indicators     sending this event
         * @param page           the offset is relative to
         * @param positionOffset in [0,1) raltive to the page
         */
        void onPageScrolled(@NonNull PageIndicators indicators, @IntRange(from = 0) int page,
                            @FloatRange(from = 0, to = 1, toInclusive = false) float positionOffset);
    }

    private class OnAdapterChangeListener implements ViewPager.OnAdapterChangeListener {

        OnAdapterChangeListener() {
        }

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager,
                                     @Nullable PagerAdapter oldAdapter,
                                     @Nullable PagerAdapter newAdapter) {
            unbindPagerAdapter(oldAdapter);
            bindPagerAdapter(newAdapter);
            setupPageCount(observers);
        }
    }

    private class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        OnPageChangeListener() {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            observers.onPageScrolled(PageIndicators.this, position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            setupPage(observers);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            observers.onPageStateChanged(PageIndicators.this, state);
        }
    }

    private class DataSetObserverImpl extends DataSetObserver {

        DataSetObserverImpl() {
        }

        @Override
        public void onChanged() {
            setupPageCount(observers);
        }
    }
}
