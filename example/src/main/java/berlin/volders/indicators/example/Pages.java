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
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static berlin.volders.indicators.example.R.string.page1;
import static berlin.volders.indicators.example.R.string.page2;
import static berlin.volders.indicators.example.R.string.page3;
import static berlin.volders.indicators.example.R.string.page4;
import static berlin.volders.indicators.example.R.string.page5;
import static berlin.volders.indicators.example.R.string.page6;
import static berlin.volders.indicators.example.R.string.page7;

class Pages extends PagerAdapter {

    @StringRes
    private int[] pages = {page1, page2, page3, page4, page5, page6, page7};

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
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
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            return Html.fromHtml(context.getString(html), images, null);
        }
        return Html.fromHtml(context.getString(html), 0, images, null);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
