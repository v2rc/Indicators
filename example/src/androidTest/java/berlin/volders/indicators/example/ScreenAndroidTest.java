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

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class ScreenAndroidTest {

    @Rule
    public final ActivityTestRule<Screen> activity = new ActivityTestRule<>(Screen.class, true, true);

    @Test
    public void swipeThroughAllPages() {
        PageCount pageCount = new PageCount();
        ViewAction waitFor1s = new WaitFor(1000);
        ViewInteraction onView = onView(withId(R.id.view_pager)).check(pageCount).perform(waitFor1s);
        pageCount.forEach(onView, waitFor1s).perform(waitFor1s);
        onView.perform(waitFor1s, new ScrollToStart(), waitFor1s);
    }
}
