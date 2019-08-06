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

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.util.HashSet;

import berlin.volders.indicators.PageIndicators;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class TestObserver implements PageIndicators.PageObserver, PageIndicators.PageCountObserver,
        PageIndicators.PageStateObserver, PageIndicators.PageScrollObserver {

    private final HashSet<Pair<PageIndicators, Number>> changes = new HashSet<>();

    private void onChanged(PageIndicators indicators, Number value) {
        changes.add(Pair.create(indicators, value));
    }

    public void assertChange(PageIndicators indicators, Number value) {
        assertThat(changes, hasItem(Pair.create(indicators, value)));
    }

    @Override
    public void onPageChanged(@NonNull PageIndicators indicators, int page) {
        onChanged(indicators, page);
    }

    @Override
    public void onPageCountChanged(@NonNull PageIndicators indicators, int count) {
        onChanged(indicators, count);
    }

    @Override
    public void onPageStateChanged(@NonNull PageIndicators indicators, int state) {
        onChanged(indicators, state);
    }

    @Override
    public void onPageScrolled(@NonNull PageIndicators indicators, int position, float positionOffset) {
        onChanged(indicators, position + positionOffset);
    }

    public PageIndicators.PageObserver pageObserver() {
        return new PageIndicators.PageObserver() {
            @Override
            public void onPageChanged(@NonNull PageIndicators indicators, int page) {
                onChanged(indicators, page);
            }
        };
    }

    public PageIndicators.PageCountObserver pageCountObserver() {
        return new PageIndicators.PageCountObserver() {
            @Override
            public void onPageCountChanged(@NonNull PageIndicators indicators, int count) {
                onChanged(indicators, count);
            }
        };
    }

    public PageIndicators.PageStateObserver pageStateObserver() {
        return new PageIndicators.PageStateObserver() {
            @Override
            public void onPageStateChanged(@NonNull PageIndicators indicators, int state) {
                onChanged(indicators, state);
            }
        };
    }

    public PageIndicators.PageScrollObserver pageScrollObserver() {
        return new PageIndicators.PageScrollObserver() {
            @Override
            public void onPageScrolled(@NonNull PageIndicators indicators, int position, float positionOffset) {
                onChanged(indicators, position + positionOffset);
            }
        };
    }
}
