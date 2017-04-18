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

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.HashSet;

class Observers implements PageIndicators.PageObserver, PageIndicators.PageCountObserver,
        PageIndicators.PageStateObserver, PageIndicators.PageScrollObserver {

    @VisibleForTesting
    final HashSet<PageIndicators.PageObserver> pageObservers = new HashSet<>();
    @VisibleForTesting
    final HashSet<PageIndicators.PageCountObserver> pageCountObservers = new HashSet<>();
    @VisibleForTesting
    final HashSet<PageIndicators.PageStateObserver> pageStateObservers = new HashSet<>();
    @VisibleForTesting
    final HashSet<PageIndicators.PageScrollObserver> pageScrollObservers = new HashSet<>();

    void registerObserver(PageIndicators.Observer observer) {
        if (observer != null) {
            if (observer instanceof PageIndicators.PageObserver) {
                pageObservers.add((PageIndicators.PageObserver) observer);
            }
            if (observer instanceof PageIndicators.PageCountObserver) {
                pageCountObservers.add((PageIndicators.PageCountObserver) observer);
            }
            if (observer instanceof PageIndicators.PageStateObserver) {
                pageStateObservers.add((PageIndicators.PageStateObserver) observer);
            }
            if (observer instanceof PageIndicators.PageScrollObserver) {
                pageScrollObservers.add((PageIndicators.PageScrollObserver) observer);
            }
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    void unregisterObserver(PageIndicators.Observer observer) {
        if (observer != null) {
            pageObservers.remove(observer);
            pageCountObservers.remove(observer);
            pageStateObservers.remove(observer);
            pageScrollObservers.remove(observer);
        }
    }

    void unregisterAllObservers() {
        pageObservers.clear();
        pageCountObservers.clear();
        pageStateObservers.clear();
        pageScrollObservers.clear();
    }

    @Override
    public void onPageChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int page) {
        for (PageIndicators.PageObserver observer : pageObservers) {
            observer.onPageChanged(indicators, page);
        }
    }

    @Override
    public void onPageCountChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int count) {
        for (PageIndicators.PageCountObserver observer : pageCountObservers) {
            observer.onPageCountChanged(indicators, count);
        }
    }

    @Override
    public void onPageStateChanged(@NonNull PageIndicators indicators, @State int state) {
        for (PageIndicators.PageStateObserver observer : pageStateObservers) {
            observer.onPageStateChanged(indicators, state);
        }
    }

    @Override
    public void onPageScrolled(@NonNull PageIndicators indicators, @IntRange(from = 0) int page,
                               @FloatRange(from = 0, to = 1, toInclusive = false) float pageOffset) {
        for (PageIndicators.PageScrollObserver observer : pageScrollObservers) {
            observer.onPageScrolled(indicators, page, pageOffset);
        }
    }
}
