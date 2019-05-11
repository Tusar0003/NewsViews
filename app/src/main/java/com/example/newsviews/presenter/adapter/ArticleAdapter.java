package com.example.newsviews.presenter.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsviews.R;
import com.example.newsviews.model.pojo.Article;
import com.example.newsviews.view.fragment.ArticleFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> mArticleList;

    private OnItemClick mOnItemClick;
    public interface OnItemClick {
        void onItemClick(Article article);
    }

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.mContext = context;
        this.mArticleList = articleList;
        mOnItemClick = (OnItemClick) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_news_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        try {
            if (!TextUtils.isEmpty(mArticleList.get(position).getAuthor().toString())) {
                viewHolder.mAuthorTextView.setText(mArticleList.get(position).getAuthor().toString());
            }

            viewHolder.mTitleTextView.setText(mArticleList.get(position).getTitle());
            viewHolder.mDateTextView.setText(mArticleList.get(position).getPublishedAt());
            viewHolder.mDescriptionTextView.setText(mArticleList.get(position).getDescription());
            viewHolder.mUrlTextView.setText(mArticleList.get(position).getUrl());

            Picasso.get().load(mArticleList.get(position).getUrlToImage()).placeholder(R.drawable.news)
                    .into(viewHolder.mNewsImageView);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Confirmation")
                            .setMessage("Do you want to load the content on mobile browser?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    mOnItemClick.onItemClick(mArticleList.get(position));
                                }
                            })
                            .create().show();
                }
            });
        } catch (Exception e) {
//            AlertDialog alertDialog = new AlertDialog(mContext);
//            alertDialog.show(e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mAuthorTextView;
        private TextView mDateTextView;
        private TextView mDescriptionTextView;
        private TextView mUrlTextView;
        private ImageView mNewsImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

            mTitleTextView = itemView.findViewById(R.id.text_view_title);
            mAuthorTextView = itemView.findViewById(R.id.text_view_author);
            mDateTextView = itemView.findViewById(R.id.text_view_date);
            mDescriptionTextView = itemView.findViewById(R.id.text_view_description);
            mUrlTextView = itemView.findViewById(R.id.text_view_url);
            mNewsImageView = itemView.findViewById(R.id.image_view_news_image);
        }
    }
}
