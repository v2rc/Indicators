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

import berlin.volders.indicators.test.TestObserver;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

@SuppressWarnings("WeakerAccess")
public class ObserversTest {

    Observers observers;
    TestObserver observer;

    @Before
    public void setup() {
        observers = new Observers();
        observer = new TestObserver();
    }

    @Test
    public void registerObserver() {
        observers.registerObserver(observer);

        assertThat(observers.pageObservers, hasItem(observer));
        assertThat(observers.pageCountObservers, hasItem(observer));
        assertThat(observers.pageStateObservers, hasItem(observer));
        assertThat(observers.pageScrollObservers, hasItem(observer));
    }

    @Test
    public void unregisterObserver() {
        observers.registerObserver(observer);

        observers.unregisterObserver(observer);

        assertThat(observers.pageObservers, empty());
        assertThat(observers.pageCountObservers, empty());
        assertThat(observers.pageStateObservers, empty());
        assertThat(observers.pageScrollObservers, empty());
    }

    @Test
    public void unregisterAllObservers() {
        observers.registerObserver(observer);
        observers.registerObserver(new TestObserver());

        observers.unregisterAllObservers();

        assertThat(observers.pageObservers, empty());
        assertThat(observers.pageCountObservers, empty());
        assertThat(observers.pageStateObservers, empty());
        assertThat(observers.pageScrollObservers, empty());
    }

    @Test
    public void onPageChanged() {
        TestObserver observer = new TestObserver();
        observers.registerObserver(observer.pageObserver());
        observers.registerObserver(this.observer);
        PageIndicators indicators = new PageIndicators(null);

        observers.onPageChanged(indicators, 42);
        observers.onPageChanged(indicators, 99);

        observer.assertChange(indicators, 42);
        observer.assertChange(indicators, 99);
        this.observer.assertChange(indicators, 42);
        this.observer.assertChange(indicators, 99);
    }

    @Test
    public void onPageCountChanged() {
        TestObserver observer = new TestObserver();
        observers.registerObserver(observer.pageCountObserver());
        observers.registerObserver(this.observer);
        PageIndicators indicators = new PageIndicators(null);

        observers.onPageCountChanged(indicators, 42);
        observers.onPageCountChanged(indicators, 99);

        observer.assertChange(indicators, 42);
        observer.assertChange(indicators, 99);
        this.observer.assertChange(indicators, 42);
        this.observer.assertChange(indicators, 99);
    }

    @Test
    public void onPageStateChanged() {
        TestObserver observer = new TestObserver();
        observers.registerObserver(observer.pageStateObserver());
        observers.registerObserver(this.observer);
        PageIndicators indicators = new PageIndicators(null);

        observers.onPageStateChanged(indicators, ViewPager.SCROLL_STATE_IDLE);
        observers.onPageStateChanged(indicators, ViewPager.SCROLL_STATE_DRAGGING);
        observers.onPageStateChanged(indicators, ViewPager.SCROLL_STATE_SETTLING);

        observer.assertChange(indicators, ViewPager.SCROLL_STATE_IDLE);
        observer.assertChange(indicators, ViewPager.SCROLL_STATE_DRAGGING);
        observer.assertChange(indicators, ViewPager.SCROLL_STATE_SETTLING);
        this.observer.assertChange(indicators, ViewPager.SCROLL_STATE_IDLE);
        this.observer.assertChange(indicators, ViewPager.SCROLL_STATE_DRAGGING);
        this.observer.assertChange(indicators, ViewPager.SCROLL_STATE_SETTLING);
    }

    @Test
    public void onPageScrollChanged() {
        TestObserver observer = new TestObserver();
        observers.registerObserver(observer.pageScrollObserver());
        observers.registerObserver(this.observer);
        PageIndicators indicators = new PageIndicators(null);

        observers.onPageScrolled(indicators, 0, 0.5f);
        observers.onPageScrolled(indicators, 2, 0.7f);

        observer.assertChange(indicators, 0.5f);
        observer.assertChange(indicators, 2.7f);
        this.observer.assertChange(indicators, 0.5f);
        this.observer.assertChange(indicators, 2.7f);
    }
}
