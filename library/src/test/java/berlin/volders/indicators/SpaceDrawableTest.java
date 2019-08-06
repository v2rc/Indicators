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

import android.graphics.PixelFormat;

import org.junit.Before;
import org.junit.Test;

import berlin.volders.indicators.test.TestCanvas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("WeakerAccess")
public class SpaceDrawableTest {

    final int spacing = 101;

    SpaceDrawable drawable;

    @Before
    public void setup() {
        drawable = new SpaceDrawable(spacing);
    }

    @Test
    public void spacing() {
        assertThat(drawable.spacing, is(spacing));
        assertThat(drawable.getIntrinsicWidth(), is(spacing));
        assertThat(drawable.getIntrinsicHeight(), is(spacing));
    }

    @Test
    public void draw() {
        TestCanvas canvas = new TestCanvas();

        drawable.draw(canvas);

        canvas.assertEmpty();
    }

    @Test
    public void getOpacity() {
        assertThat(drawable.getOpacity(), is(PixelFormat.TRANSPARENT));
    }
}
