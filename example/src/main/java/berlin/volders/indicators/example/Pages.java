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
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.PagerAdapter;

class Pages extends PagerAdapter {

    @StringRes
    private int[] pages = {
            R.string.page1,
            R.string.page2,
            R.string.page3,
            R.string.page4,
            R.string.page5,
            R.string.page6,
            R.string.page7
    };

    @Override
    public int getCount() {
        return pages.length;
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        Context context = container.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        TextView view = (TextView) inflater.inflate(R.layout.page, container, false);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(fromHtml(context, pages[position]));
        container.addView(view);
        return view;
    }

    private static Spanned fromHtml(Context context, int html) {
        Images images = new Images(context);
        return HtmlCompat.fromHtml(context.getString(html), 0, images, null);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
