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

import android.support.v4.view.ViewPager;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PageIndicatorsTest {

    PageIndicators indicators;
    TestPageChangeObserverView pageObserverView;

    @Before
    public void setup() throws Exception {
        indicators = new PageIndicators(getContext());
        pageObserverView = new TestPageChangeObserverView(getContext());
    }

    @Test
    public void onViewAdded() throws Exception {
        indicators.onViewAdded(pageObserverView);

        assertThat(indicators.observers.pageObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageCountObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageStateObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageScrollObservers, hasItem(pageObserverView));
    }

    @Test
    public void onViewRemoved() throws Exception {
        indicators.registerObserver(pageObserverView);

        indicators.onViewRemoved(pageObserverView);

        assertThat(indicators.observers.pageObservers, empty());
        assertThat(indicators.observers.pageCountObservers, empty());
        assertThat(indicators.observers.pageStateObservers, empty());
        assertThat(indicators.observers.pageScrollObservers, empty());
    }

    @Test
    public void setViewPager() throws Exception {
        indicators.registerObserver(pageObserverView);
        TestViewPager oldViewPager = new TestViewPager(getContext());
        TestViewPager newViewPager = new TestViewPager(getContext());

        indicators.setViewPager(oldViewPager);
        indicators.setViewPager(newViewPager);

        assertThat(indicators.getViewPager(), is((ViewPager) newViewPager));
        oldViewPager.assertHasNoListeners();
        oldViewPager.getAdapter().assertHasObservers(false);
        newViewPager.assertHasListeners();
        newViewPager.getAdapter().assertHasObservers(true);
        pageObserverView.assertChange(indicators, newViewPager.getCurrentItem());
        pageObserverView.assertChange(indicators, newViewPager.getAdapter().getCount());
    }

    @Test
    public void bindViewPager() throws Exception {
        TestViewPager viewPager = new TestViewPager(getContext());

        indicators.bindViewPager(viewPager);

        viewPager.assertHasListeners();
        viewPager.getAdapter().assertHasObservers(true);
    }

    @Test
    public void unbindViewPager() throws Exception {
        TestViewPager viewPager = new TestViewPager(getContext());
        indicators.bindViewPager(viewPager);

        indicators.unbindViewPager(viewPager);

        viewPager.assertHasNoListeners();
        viewPager.getAdapter().assertHasObservers(false);
    }

    @Test
    public void bindPagerAdapter() throws Exception {
        TestPagerAdapter adapter = new TestPagerAdapter();

        indicators.bindPagerAdapter(adapter);

        adapter.assertHasObservers(true);
    }

    @Test
    public void unbindPagerAdapter() throws Exception {
        TestPagerAdapter adapter = new TestPagerAdapter();
        indicators.bindPagerAdapter(adapter);

        indicators.unbindPagerAdapter(adapter);

        adapter.assertHasObservers(false);
    }

    @Test
    public void setupPage() throws Exception {
        TestViewPager viewPager = new TestViewPager(getContext());
        indicators.setViewPager(viewPager);

        indicators.setupPage(pageObserverView);

        pageObserverView.assertChange(indicators, viewPager.getCurrentItem());
    }

    @Test
    public void setupPageCount() throws Exception {
        TestViewPager viewPager = new TestViewPager(getContext());
        indicators.setViewPager(viewPager);

        indicators.setupPageCount(pageObserverView);

        pageObserverView.assertChange(indicators, viewPager.getAdapter().getCount());
    }

    @Test
    public void registerObserver() throws Exception {
        TestViewPager viewPager = new TestViewPager(getContext());
        indicators.setViewPager(viewPager);

        indicators.registerObserver(pageObserverView);

        assertThat(indicators.observers.pageObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageCountObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageStateObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageScrollObservers, hasItem(pageObserverView));
        pageObserverView.assertChange(indicators, viewPager.getCurrentItem());
        pageObserverView.assertChange(indicators, viewPager.getAdapter().getCount());
    }

    @Test
    public void unregisterObserver() throws Exception {
        indicators.registerObserver(pageObserverView);

        indicators.unregisterObserver(pageObserverView);

        assertThat(indicators.observers.pageObservers, empty());
        assertThat(indicators.observers.pageCountObservers, empty());
        assertThat(indicators.observers.pageStateObservers, empty());
        assertThat(indicators.observers.pageScrollObservers, empty());
    }

    @Test
    public void unregisterAllPageObservers() throws Exception {
        indicators.registerObserver(pageObserverView);
        indicators.registerObserver(new TestPageChangeObserverView(getContext()));

        indicators.unregisterAllObservers();

        assertThat(indicators.observers.pageObservers, empty());
        assertThat(indicators.observers.pageCountObservers, empty());
        assertThat(indicators.observers.pageStateObservers, empty());
        assertThat(indicators.observers.pageScrollObservers, empty());
    }
}
