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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple page number indicator showing the current page and the page count as formatted text.
 */
@SuppressLint("AppCompatCustomView")
public class PageNumberView extends TextView implements PageIndicators.PageObserver,
        PageIndicators.PageCountObserver {

    private static final String DEFAULT_TEMPLATE = "%1$d/%2$d";

    private String template = DEFAULT_TEMPLATE;
    private boolean formatLocalized;
    private int page;
    private int pageCount;

    private String text;

    public PageNumberView(Context context) {
        this(context, null);
    }

    public PageNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public PageNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PageNumberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PageNumberView, defStyleAttr, defStyleRes);
        formatLocalized = a.getBoolean(R.styleable.PageNumberView_indicatorFormatLocalized, false);
        setTemplate(a.getString(R.styleable.PageNumberView_indicatorTemplate));
        a.recycle();
    }

    /**
     * Set whether or not to use {@link Locale#getDefault()} for formatting the text.
     * The default {@code Locale} used for formatting is {@link Locale#US} otherwise.
     *
     * @param formatLocalized {@code true} to use {@link Locale#getDefault()}
     */
    public void setFormatLocalized(boolean formatLocalized) {
        if (this.formatLocalized != formatLocalized) {
            this.formatLocalized = formatLocalized;
            updateText();
        }
    }

    /**
     * @return whether or not to use {@link Locale#getDefault()}
     * @see #setFormatLocalized(boolean)
     */
    public boolean getFormatLocalized() {
        return formatLocalized;
    }

    /**
     * @param template to format the page numbers with
     */
    public void setTemplate(@Nullable String template) {
        if (template == null) {
            template = DEFAULT_TEMPLATE;
        }
        if (!this.template.equals(template)) {
            this.template = template;
            updateText();
        }
    }

    /**
     * @return the template to format the page numbers with
     * @see #setTemplate(String)
     */
    public String getTemplate() {
        return template;
    }

    private void updateText() {
        Locale locale = formatLocalized ? Locale.getDefault() : Locale.US;
        text = String.format(locale, template, page + 1, pageCount);
        setText(text);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (this.text != null && !this.text.equals(text.toString())) {
            setText(this.text);
        }
    }

    @Override
    public void onPageChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int page) {
        if (this.page != page) {
            this.page = page;
            updateText();
        }
    }

    @Override
    public void onPageCountChanged(@NonNull PageIndicators indicators, @IntRange(from = 0) int count) {
        if (this.pageCount != count) {
            this.pageCount = count;
            updateText();
        }
    }
}
