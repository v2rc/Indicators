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

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.v4.view.ViewPager;
import android.view.View;

import static android.support.test.espresso.action.ViewActions.swipeLeft;

class PageCount implements ViewAssertion {

    private int lastPageCount;

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (view instanceof ViewPager) {
            lastPageCount = ((ViewPager) view).getAdapter().getCount();
        }
    }

    ViewInteraction forEach(ViewInteraction onView, ViewAction viewAction) {
        int count = lastPageCount - 1;
        if (count >= 0) {
            onView.perform(viewAction);
            for (int i = 0; i < count; i++) {
                onView.perform(swipeLeft(), viewAction);
            }
        }
        return onView;
    }
}
