package com.ekaruztech.smartkolo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ekaruztech.smartkolo.object.ModelUtils;
import com.ekaruztech.smartkolo.object.User;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AppActivity extends AppCompatActivity {


    protected Context context;
    protected User user;
    protected Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        bundle = new Bundle();
        user = (User) ModelUtils.getObject(User.class, context);
        bundle.putSerializable(User.TAG, user);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
