package com.example.newsviews.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newsviews.R;
import com.squareup.picasso.Picasso;

public class SliderAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public SliderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private int[] mSlideImages = {
            R.drawable.boy,
            R.drawable.news,
            R.drawable.computer
    };

    private String[] mSlideHeadings = {
            "Tusar",
            "NewsView",
            "Profession"
    };

    private String[] mSlideDescription = {
            "Hello, I am Tusar and I like to travel and taste different types of foods. I also like riding " +
                    "bike.",
            "This is the NewsViews app I am developing right now and you are seeing the nice introduction " +
                    "splash screen. But you will be seeing it only once.",
            "I am working as a Jr. Software Engineer where I develop android applications and I " +
                    "like my job."

    };

    @Override
    public int getCount() {
        return mSlideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.image_view_icon);
        TextView headingTextView = view.findViewById(R.id.text_view_heading);
        TextView descriptionTextView =view.findViewById(R.id.text_view_description);

        Picasso.get().load(mSlideImages[position]).into(slideImageView);
        headingTextView.setText(mSlideHeadings[position]);
        descriptionTextView.setText(mSlideDescription[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
