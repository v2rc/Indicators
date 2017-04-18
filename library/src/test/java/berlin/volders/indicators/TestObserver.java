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

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.HashSet;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

class TestObserver implements PageIndicators.PageObserver, PageIndicators.PageCountObserver,
        PageIndicators.PageStateObserver, PageIndicators.PageScrollObserver {

    final HashSet<Pair<PageIndicators, Number>> changes = new HashSet<>();

    void onChanged(PageIndicators indicators, Number value) {
        changes.add(Pair.create(indicators, value));
    }

    void assertChange(PageIndicators indicators, Number value) {
        assertThat(changes, hasItem(Pair.create(indicators, value)));
    }

    @Override
    public void onPageChanged(PageIndicators indicators, int page) {
        onChanged(indicators, page);
    }

    @Override
    public void onPageCountChanged(PageIndicators indicators, int count) {
        onChanged(indicators, count);
    }

    @Override
    public void onPageStateChanged(PageIndicators indicators, int state) {
        onChanged(indicators, state);
    }

    @Override
    public void onPageScrolled(PageIndicators indicators, int position, float positionOffset) {
        onChanged(indicators, position + positionOffset);
    }

    PageIndicators.PageObserver pageObserver() {
        return new PageIndicators.PageObserver() {
            @Override
            public void onPageChanged(PageIndicators indicators, int page) {
                onChanged(indicators, page);
            }
        };
    }

    PageIndicators.PageCountObserver pageCountObserver() {
        return new PageIndicators.PageCountObserver() {
            @Override
            public void onPageCountChanged(PageIndicators indicators, int count) {
                onChanged(indicators, count);
            }
        };
    }

    PageIndicators.PageStateObserver pageStateObserver() {
        return new PageIndicators.PageStateObserver() {
            @Override
            public void onPageStateChanged(PageIndicators indicators, int state) {
                onChanged(indicators, state);
            }
        };
    }

    PageIndicators.PageScrollObserver pageScrollObserver() {
        return new PageIndicators.PageScrollObserver() {
            @Override
            public void onPageScrolled(@NonNull PageIndicators indicators, int position, float positionOffset) {
                onChanged(indicators, position + positionOffset);
            }
        };
    }
}
