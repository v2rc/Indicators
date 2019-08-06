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

import org.hamcrest.Matcher;

class WaitFor implements ViewAction {

    private final long millis;

    WaitFor(long millis) {
        this.millis = millis;
    }

    @Override
    public Matcher<View> getConstraints() {
        return AnyView.I;
    }

    @Override
    public String getDescription() {
        return "Waiting for at least " + millis + "ms";
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadForAtLeast(millis);
    }
}
