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

package berlin.volders.indicators;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

public class PageIndicatorsBindingAdapters {

    private PageIndicatorsBindingAdapters() {
        throw new AssertionError("No instances!");
    }

    @BindingAdapter("page")
    public static void setIndicatorsPage(@NonNull PageIndicators indicators, int page) {
        ViewPager viewPager = indicators.getViewPager();
        if (viewPager != null) {
            viewPager.setCurrentItem(page);
        }
    }

    @InverseBindingAdapter(attribute = "page", event = "pageAttrChanged")
    public static int getIndicatorsPage(@NonNull PageIndicators indicators) {
        ViewPager viewPager = indicators.getViewPager();
        if (viewPager == null) {
            return 0;
        }
        return viewPager.getCurrentItem();
    }

    @BindingAdapter("pageAttrChanged")
    public static void setupPageObserver(@NonNull PageIndicators indicators,
                                         final InverseBindingListener listener) {
        PageIndicators.PageObserver newObserver = null;
        if (listener != null) {
            newObserver = new PageIndicators.PageObserver() {
                @Override
                public void onPageChanged(@NonNull PageIndicators indicators, int page) {
                    listener.onChange();
                }
            };
        }
        PageIndicators.PageObserver oldObserver
                = ListenerUtil.trackListener(indicators, newObserver, R.id.indicators_page_observer);
        registerIndicatorsObserver(indicators, oldObserver, newObserver);
    }

    @BindingAdapter("pageObserver")
    public static void registerPageObserver(@NonNull PageIndicators indicators,
                                            PageIndicators.PageObserver oldObserver,
                                            PageIndicators.PageObserver newObserver) {
        registerIndicatorsObserver(indicators, oldObserver, newObserver);
    }

    @BindingAdapter("pageCountObserver")
    public static void registerPageCountObserver(@NonNull PageIndicators indicators,
                                                 PageIndicators.PageCountObserver oldObserver,
                                                 PageIndicators.PageCountObserver newObserver) {
        registerIndicatorsObserver(indicators, oldObserver, newObserver);
    }

    @BindingAdapter("pageStateObserver")
    public static void registerPageStateObserver(@NonNull PageIndicators indicators,
                                                 PageIndicators.PageStateObserver oldObserver,
                                                 PageIndicators.PageStateObserver newObserver) {
        registerIndicatorsObserver(indicators, oldObserver, newObserver);
    }

    @BindingAdapter("pageScrollObserver")
    public static void registerPageScrollObserver(@NonNull PageIndicators indicators,
                                                  PageIndicators.PageScrollObserver oldObserver,
                                                  PageIndicators.PageScrollObserver newObserver) {
        registerIndicatorsObserver(indicators, oldObserver, newObserver);
    }

    @BindingAdapter("indicatorsObserver")
    public static void registerIndicatorsObserver(@NonNull PageIndicators indicators,
                                                  PageIndicators.Observer oldObserver,
                                                  PageIndicators.Observer newObserver) {
        indicators.registerObserver(newObserver);
        indicators.unregisterObserver(oldObserver);
    }

    @BindingAdapter("pageIndicators")
    public static void bindPageIndicators(@NonNull PageIndicatorView view,
                                          PageIndicators oldIndicators,
                                          PageIndicators newIndicators) {
        bindPageIndicators((PageIndicators.Observer) view, oldIndicators, newIndicators);
    }

    @BindingAdapter("pageIndicators")
    public static void bindPageIndicators(@NonNull PageNumberView view,
                                          PageIndicators oldIndicators,
                                          PageIndicators newIndicators) {
        bindPageIndicators((PageIndicators.Observer) view, oldIndicators, newIndicators);
    }

    @BindingAdapter("pageIndicators")
    public static void bindPageIndicators(@NonNull PageIndicators.Observer observer,
                                          PageIndicators oldIndicators,
                                          PageIndicators newIndicators) {
        if (oldIndicators != null) {
            oldIndicators.unregisterObserver(observer);
        }
        if (newIndicators != null) {
            newIndicators.registerObserver(observer);
        }
    }
}
