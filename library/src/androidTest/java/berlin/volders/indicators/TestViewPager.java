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

import android.content.Context;
import android.support.v4.view.ViewPager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

class TestViewPager extends ViewPager {

    TestPagerAdapter pagerAdapter = new TestPagerAdapter();

    boolean adapterChangeListener;
    boolean pageChangeListener;

    TestViewPager(Context context) {
        super(context);
    }

    @Override
    public TestPagerAdapter getAdapter() {
        return pagerAdapter;
    }

    @Override
    public int getCurrentItem() {
        return 3;
    }

    @Override
    public void addOnAdapterChangeListener(OnAdapterChangeListener listener) {
        adapterChangeListener = true;
    }

    @Override
    public void removeOnAdapterChangeListener(OnAdapterChangeListener listener) {
        adapterChangeListener = false;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        pageChangeListener = true;
    }

    @Override
    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        pageChangeListener = false;
    }

    public void assertHasListeners() {
        assertThat(adapterChangeListener && pageChangeListener, is(true));
    }

    public void assertHasNoListeners() {
        assertThat(adapterChangeListener || pageChangeListener, is(false));
    }
}
