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

import android.database.DataSetObserver;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("WeakerAccess")
public class TestPagerAdapter extends PagerAdapter {

    int observers;

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @Override
    public void registerDataSetObserver(@NonNull DataSetObserver observer) {
        observers++;
    }

    @Override
    public void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
        observers--;
    }

    public void assertHasObservers(boolean b) {
        assertThat(observers, b ? greaterThan(0) : is(0));
    }
}
