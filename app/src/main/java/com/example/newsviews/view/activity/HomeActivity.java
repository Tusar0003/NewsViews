package com.example.newsviews.view.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsviews.R;
import com.example.newsviews.model.preferenecs.Preferences;
import com.example.newsviews.presenter.activityPresenter.HomePresenter;
import com.example.newsviews.view.fragment.AboutFragment;
import com.example.newsviews.view.fragment.AlertDialog;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements HomePresenter.View {

    private ActionBarDrawerToggle mToggle;

    private HomePresenter mPresenter;
    private Preferences mPreferences;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter(this);
        mPreferences = new Preferences(this);
        mAlertDialog = new AlertDialog(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(mToggle);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item);
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
    public void showAlertDialog(String message) {
        mAlertDialog.show(message);
    }
}
