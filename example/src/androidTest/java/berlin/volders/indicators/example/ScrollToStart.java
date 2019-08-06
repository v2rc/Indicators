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

package berlin.volders.indicators.example;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.viewpager.widget.ViewPager;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

class ScrollToStart implements ViewAction {

    @Override
    public Matcher<View> getConstraints() {
        return new BaseMatcher<View>() {
            @Override
            public boolean matches(Object item) {
                return item instanceof ViewPager;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a ViewPager");
            }
        };
    }

    @Override
    public String getDescription() {
        return "Scroll ViewPager to first page";
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((ViewPager) view).setCurrentItem(0);
    }
}
