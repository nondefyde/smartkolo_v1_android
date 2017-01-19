package com.ekaruztech.smartkolo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ekaruztech.smartkolo.R;
import com.ekaruztech.smartkolo.object.ModelUtils;
import com.ekaruztech.smartkolo.object.User;
import com.ekaruztech.smartkolo.ui.AppActivity;

import static com.ekaruztech.smartkolo.R.id.username;


public class DashbordActivity extends AppActivity implements View.OnClickListener {

    public static final String TAG = DashbordActivity.class
            .getSimpleName();


    private Button logout;
    private User user;
    private Bundle bundle;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        initViews();

    }

    public void initViews() {
        logout = (Button) findViewById(R.id.logout);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.logout:
                ModelUtils.logOut(this);
                break;
        }
    }
}
