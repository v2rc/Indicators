/*
 * Copyright (C) 2017 volders GmbH with <3 in Berlin
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

import org.junit.Before;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PageNumberViewTest {

    PageNumberView pageNumberView;

    @Before
    public void setup() {
        pageNumberView = new PageNumberView(getContext());
    }

    @Test
    public void onPageChanged() throws Exception {
        PageIndicators indicators = new PageIndicators(getContext());
        pageNumberView.setTemplate("%d-%d");
        pageNumberView.onPageCountChanged(indicators, 3);

        pageNumberView.onPageChanged(indicators, 1);
        pageNumberView.onPageChanged(indicators, 2);

        assertThat(pageNumberView.getText().toString(), is("3-3"));
    }

    @Test
    public void onPageCountChanged() throws Exception {
        PageIndicators indicators = new PageIndicators(getContext());
        pageNumberView.setTemplate("%d-%d");
        pageNumberView.onPageChanged(indicators, 1);

        pageNumberView.onPageCountChanged(indicators, 2);
        pageNumberView.onPageCountChanged(indicators, 3);

        assertThat(pageNumberView.getText().toString(), is("2-3"));
    }
}
