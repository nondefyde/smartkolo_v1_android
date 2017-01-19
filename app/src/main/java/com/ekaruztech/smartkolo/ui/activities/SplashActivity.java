package com.ekaruztech.smartkolo.ui.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import com.ekaruztech.smartkolo.R;
import com.ekaruztech.smartkolo.object.ModelUtils;
import com.ekaruztech.smartkolo.object.User;
import com.ekaruztech.smartkolo.ui.AppActivity;
import com.ekaruztech.smartkolo.ui.activities.signups.LoginActivity;
import com.ekaruztech.smartkolo.util.MyUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by yuweichen on 16/4/30.
 */
public class SplashActivity extends AppActivity {

    public static final String TAG = SplashActivity.class
            .getSimpleName();

    Context context;
    protected boolean _active = true;
    protected int _splashTime = 3000;

    Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SplashActivity sPlashScreen = this;

        context = this;
        final Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && waited < _splashTime) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (final InterruptedException e) {
                    // do nothing
                } finally {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            if (ModelUtils.getObject(User.class, context) != null) {
                                MyUtils.startActivity(context, DashbordActivity.class);
                                finish();
                            } else {
                                MyUtils.startActivity(sPlashScreen, LoginActivity.class);
                                finish();
                            }
                        }
                    });
                }
            }
        };
        splashTread.start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
