package net.avenwu.yoyogithub.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.avenwu.yoyogithub.widget.CircleTransform;
import net.avenwu.yoyogithub.widget.URLSpanNoUnderline;

/**
 * Created by aven on 3/31/16.
 */
public class BindingUtils {

    @BindingAdapter({"app:imageUrl", "app:error", "app:roundAsCircle"})
    public static void loadImage(ImageView view, String url, Drawable error, boolean roundAsCircle) {
        RequestCreator creator = Picasso.with(view.getContext()).load(url).error(error).placeholder(error);
        if (roundAsCircle) {
            creator.transform(new CircleTransform());
        }
        creator.into(view);
    }

    @BindingAdapter({"app:url_text", "app:link_underline"})
    public static void setTextWithoutLinkUnderline(TextView textView, String text, boolean underline) {
        textView.setText(text);
        if (!underline) {
            Spannable textSpannable = (Spannable) textView.getText();
            URLSpan[] spans = textSpannable.getSpans(0, textSpannable.length(), URLSpan.class);
            for (URLSpan span : spans) {
                int start = textSpannable.getSpanStart(span);
                int end = textSpannable.getSpanEnd(span);
                textSpannable.removeSpan(span);
                span = new URLSpanNoUnderline(span.getURL());
                textSpannable.setSpan(span, start, end, 0);
            }
        }
    }
}
