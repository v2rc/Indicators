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

import androidx.viewpager.widget.ViewPager;

import org.junit.Before;
import org.junit.Test;

import berlin.volders.indicators.test.TestPageChangeObserverView;
import berlin.volders.indicators.test.TestPagerAdapter;
import berlin.volders.indicators.test.TestViewPager;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class PageIndicatorsTest {

    PageIndicators indicators;
    TestPageChangeObserverView pageObserverView;

    @Before
    public void setup() {
        indicators = new PageIndicators(getApplicationContext());
        pageObserverView = new TestPageChangeObserverView(getApplicationContext());
    }

    @Test
    public void onViewAdded() {
        indicators.onViewAdded(pageObserverView);

        assertThat(indicators.observers.pageObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageCountObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageStateObservers, hasItem(pageObserverView));
        assertThat(indicators.observers.pageScrollObservers, hasItem(pageObserverView));
    }

    @Test
    public void onViewRemoved() {
        indicators.registerObserver(pageObserverView);

        indicators.onViewRemoved(pageObserverView);

        assertThat(indicators.observers.pageObservers, empty());
        assertThat(indicators.observers.pageCountObservers, empty());
        assertThat(indicators.observers.pageStateObservers, empty());
        assertThat(indicators.observers.pageScrollObservers, empty());
    }

    @Test
    public void setViewPager() {
        indicators.registerObserver(pageObserverView);
        TestViewPager oldViewPager = new TestViewPager(getApplicationContext());
        TestViewPager newViewPager = new TestViewPager(getApplicationContext());

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
    public void bindViewPager() {
        TestViewPager viewPager = new TestViewPager(getApplicationContext());

        indicators.bindViewPager(viewPager);

        viewPager.assertHasListeners();
        viewPager.getAdapter().assertHasObservers(true);
    }

    @Test
    public void unbindViewPager() {
        TestViewPager viewPager = new TestViewPager(getApplicationContext());
        indicators.bindViewPager(viewPager);

        indicators.unbindViewPager(viewPager);

        viewPager.assertHasNoListeners();
        viewPager.getAdapter().assertHasObservers(false);
    }

    @Test
    public void bindPagerAdapter() {
        TestPagerAdapter adapter = new TestPagerAdapter();

        indicators.bindPagerAdapter(adapter);

        adapter.assertHasObservers(true);
    }

    @Test
    public void unbindPagerAdapter() {
        TestPagerAdapter adapter = new TestPagerAdapter();
        indicators.bindPagerAdapter(adapter);

        indicators.unbindPagerAdapter(adapter);

        adapter.assertHasObservers(false);
    }

    @Test
    public void setupPage() {
        TestViewPager viewPager = new TestViewPager(getApplicationContext());
        indicators.setViewPager(viewPager);

        indicators.setupPage(pageObserverView);

        pageObserverView.assertChange(indicators, viewPager.getCurrentItem());
    }

    @Test
    public void setupPageCount() {
        TestViewPager viewPager = new TestViewPager(getApplicationContext());
        indicators.setViewPager(viewPager);

        indicators.setupPageCount(pageObserverView);

        pageObserverView.assertChange(indicators, viewPager.getAdapter().getCount());
    }

    @Test
    public void registerObserver() {
        TestViewPager viewPager = new TestViewPager(getApplicationContext());
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
    public void unregisterObserver() {
        indicators.registerObserver(pageObserverView);

        indicators.unregisterObserver(pageObserverView);

        assertThat(indicators.observers.pageObservers, empty());
        assertThat(indicators.observers.pageCountObservers, empty());
        assertThat(indicators.observers.pageStateObservers, empty());
        assertThat(indicators.observers.pageScrollObservers, empty());
    }

    @Test
    public void unregisterAllPageObservers() {
        indicators.registerObserver(pageObserverView);
        indicators.registerObserver(new TestPageChangeObserverView(getApplicationContext()));

        indicators.unregisterAllObservers();

        assertThat(indicators.observers.pageObservers, empty());
        assertThat(indicators.observers.pageCountObservers, empty());
        assertThat(indicators.observers.pageStateObservers, empty());
        assertThat(indicators.observers.pageScrollObservers, empty());
    }
}
