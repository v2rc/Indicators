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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;

import androidx.core.content.ContextCompat;

class Images implements Html.ImageGetter {

    private final Context context;

    Images(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public Drawable getDrawable(String source) {
        if (source.equals("github.png")) {
            return getDrawable(context);
        }
        return null;
    }

    private static Drawable getDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.github);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        return drawable;
    }
}
