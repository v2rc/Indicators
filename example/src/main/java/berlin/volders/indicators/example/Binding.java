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

import android.databinding.BindingAdapter;
import android.view.View;

public class Binding {

    @BindingAdapter({"page", "pages"})
    public static void setupPageVisibility(View view, int page, int[] pages) {
        if (pages.length == 0) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        page++;
        for (int i = 0; i < pages.length; i++) {
            if (pages[i] == page) {
                view.setVisibility(View.VISIBLE);
                return;
            }
        }
        view.setVisibility(View.INVISIBLE);
    }
}
