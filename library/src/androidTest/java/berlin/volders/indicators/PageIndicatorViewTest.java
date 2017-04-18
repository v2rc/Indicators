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

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.filters.SdkSuppress;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PageIndicatorViewTest {

    PageIndicatorView pageIndicatorView;

    @Before
    public void setup() {
        pageIndicatorView = new PageIndicatorView(getContext());
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.JELLY_BEAN)
    public void getDividerDrawable_ignore_SpaceDrawable() throws Exception {
        pageIndicatorView.setDividerDrawable(new SpaceDrawable(5));

        assertThat(pageIndicatorView.getDividerDrawable(), nullValue());
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.JELLY_BEAN)
    public void getDividerDrawable_delegate_non_SpaceDrawable() throws Exception {
        Drawable drawable = new ShapeDrawable();
        pageIndicatorView.setDividerDrawable(drawable);

        assertThat(pageIndicatorView.getDividerDrawable(), is(drawable));
    }

    @Test
    public void onPageChanged() throws Exception {
        PageIndicators indicators = new PageIndicators(getContext());
        pageIndicatorView.onPageCountChanged(indicators, 3);

        pageIndicatorView.onPageChanged(indicators, 1);
        pageIndicatorView.onPageChanged(indicators, 2);

        assertThat(pageIndicatorView.indicators[0].isSelected(), is(false));
        assertThat(pageIndicatorView.indicators[1].isSelected(), is(false));
        assertThat(pageIndicatorView.indicators[2].isSelected(), is(true));
    }

    @Test
    public void onPageCountChanged() throws Exception {
        PageIndicators indicators = new PageIndicators(getContext());
        pageIndicatorView.onPageChanged(indicators, 1);

        pageIndicatorView.onPageCountChanged(indicators, 2);
        pageIndicatorView.onPageCountChanged(indicators, 3);

        assertThat(pageIndicatorView.indicators.length, is(3));
        assertThat(pageIndicatorView.indicators[1].isSelected(), is(true));
    }

    @Test
    public void setIndicatorDrawable_to_null() throws Exception {
        View view = new View(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        PageIndicatorView.setIndicatorDrawable(view, null, 1, 2);

        assertThat(view.getBackground(), nullValue());
        assertThat(view.getLayoutParams().width, is(0));
        assertThat(view.getLayoutParams().height, is(0));
    }

    @Test
    public void setIndicatorDrawable_to_drawable() throws Exception {
        View view = new View(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        Drawable drawable = new SpaceDrawable(2);

        PageIndicatorView.setIndicatorDrawable(view, drawable, -1, 1);

        assertThat(view.getBackground(), is(drawable));
        assertThat(view.getLayoutParams().width, is(2));
        assertThat(view.getLayoutParams().height, is(1));
    }

    @Test
    public void setIndicatorDrawable_to_new_drawable() throws Exception {
        View view = new View(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        TestDrawable drawable = new TestDrawable(new SpaceDrawable(2));

        PageIndicatorView.setIndicatorDrawable(view, drawable, -1, 1);

        assertThat(view.getBackground(), is(drawable.drawable));
        assertThat(view.getLayoutParams().width, is(2));
        assertThat(view.getLayoutParams().height, is(1));
    }
}
