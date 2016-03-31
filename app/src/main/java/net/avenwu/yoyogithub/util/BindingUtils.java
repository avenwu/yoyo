package net.avenwu.yoyogithub.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.avenwu.yoyogithub.widget.CircleTransform;

/**
 * Created by aven on 3/31/16.
 */
public class BindingUtils {

    @BindingAdapter({"app:imageUrl", "app:error", "app:roundAsCircle"})
    public static void loadImage(ImageView view, String url, Drawable error, boolean roundAsCircle) {
        RequestCreator creator = Picasso.with(view.getContext()).load(url).error(error);
        if (roundAsCircle) {
            creator.transform(new CircleTransform());
        }
        creator.into(view);
    }

}
