package com.example.newsviews.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsviews.R;
import com.example.newsviews.model.pojo.Article;
import com.example.newsviews.model.preferenecs.Preferences;
import com.example.newsviews.presenter.activityPresenter.HomePresenter;
import com.example.newsviews.presenter.adapter.ArticleAdapter;
import com.example.newsviews.view.fragment.AboutFragment;
import com.example.newsviews.view.fragment.AlertDialog;
import com.example.newsviews.view.fragment.ArticleFragment;
import com.example.newsviews.view.fragment.SearchFragment;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomePresenter.View, ArticleAdapter.OnItemClick {

    private static final String TAG = "HomeActivity";

    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;

    private RecyclerView mArticlesRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private HomePresenter mPresenter;
    private Preferences mPreferences;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter(this);
        mPreferences = new Preferences(this);
        mProgressDialog = new ProgressDialog(this);
        mAlertDialog = new AlertDialog(this);

        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);

        mArticlesRecyclerView = findViewById(R.id.recycler_view_articles);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.text_view_navigation_user_name);
        TextView emailTextView = headerView.findViewById(R.id.text_view_navigation_email);
        ImageView profileImageView = headerView.findViewById(R.id.image_view_navigation);

        userNameTextView.setText(mPreferences.getLastName());
        emailTextView.setText(mPreferences.getEmail());
        Picasso.get().load(mPreferences.getImageUrl()).placeholder(R.drawable.boy).into(profileImageView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mPresenter.onNavigationItemSelected(menuItem.getItemId());

                return true;
            }
        });

        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getArticles();
            }
        });

        mPresenter.getArticles();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.show(getSupportFragmentManager(), null);
        }

        return mToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setArticleList(List<Article> articleList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mArticlesRecyclerView.setLayoutManager(layoutManager);
        mArticlesRecyclerView.setHasFixedSize(true);

        ArticleAdapter adapter = new ArticleAdapter(this, articleList);
        mArticlesRecyclerView.setAdapter(adapter);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @Override
    public void setRefreshOff() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void closeNavigationDrawer() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onItemClick(Article article) {
        Bundle bundle = new Bundle();
        bundle.putString("url", article.getUrl());

        ArticleFragment articleFragment = new ArticleFragment();
        articleFragment.setArguments(bundle);
        articleFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showAboutDialog() {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showLogOutConfirmation() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Logout Confirmation")
                .setMessage("Do you want to log out from facebook?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginManager.getInstance().logOut();
                        mPreferences.setUserLoggedIn(false);
                        mPreferences.setLastName("User Name");
                        mPreferences.setEmail("example@email.com");
                        mPreferences.setImageUrl(String.valueOf(R.drawable.boy));
                        mPresenter.finishActivity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.finishActivity();
                    }
                })
                .create().show();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showAlertDialog(String message) {
        mAlertDialog.show(message);
    }
}
