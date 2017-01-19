package com.ekaruztech.smartkolo.ui.activities.signups;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ekaruztech.smartkolo.MainApplication;
import com.ekaruztech.smartkolo.R;
import com.ekaruztech.smartkolo.api.services.AuthApi;
import com.ekaruztech.smartkolo.customs.Constants;
import com.ekaruztech.smartkolo.object.ModelUtils;
import com.ekaruztech.smartkolo.object.User;
import com.ekaruztech.smartkolo.object._Meta;
import com.ekaruztech.smartkolo.object.responses.GeneralResponse;
import com.ekaruztech.smartkolo.ui.AppActivity;
import com.ekaruztech.smartkolo.ui.activities.DashbordActivity;
import com.ekaruztech.smartkolo.util.DialogUtils;
import com.ekaruztech.smartkolo.util.LauncherUtil;
import com.ekaruztech.smartkolo.util.Logger;
import com.ekaruztech.smartkolo.util.MyUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ekaruztech.smartkolo.R.id.register;
import static com.ekaruztech.smartkolo.R.id.username;


public class LoginActivity extends AppActivity implements View.OnClickListener{

    public static final String TAG = LoginActivity.class.getSimpleName();


    private String usernameText;
    private String passwordText;

    EditText username;
    EditText password;

    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    public void signInOnClick(View view) {
        boolean result = true;
        if (isEmptyOrNull(username)) {
            result = false;
        }
        if (isEmptyOrNull(password)) {
            result = false;
        }
        if (result == false)
            return;
        continue_action();
    }


    public void initViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.register);
        loginButton = (Button) findViewById(R.id.login);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean isEmptyOrNull(EditText view) {
        String value = view.getText().toString().trim();
        String tag = view.getTag().toString();
        if (value == null || value.equals("")) {
            view.setError(tag + " cannot be empty");
            return true;
        } else {
            switch (tag) {
                case "email":
                    usernameText = (value).toLowerCase();
                    break;
                case "password":
                    passwordText = (value);
                    break;
            }
            return false;
        }
    }


    public void continue_action() {
        usernameText = username.getText().toString();
        passwordText = password.getText().toString();

        Logger.write(TAG, "info " + usernameText + " " + passwordText);

        DialogUtils.displayProgress(this);

        Call<ResponseBody> response = MainApplication.createService(AuthApi.class).login(usernameText, passwordText, "123456");
        Logger.write(TAG +" ==== ", response.request().url().toString());
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> ResponseBodyResponse) {
                if (context == null) return;
                try {
                    GeneralResponse generalResponse = new GeneralResponse(ResponseBodyResponse);
                    _Meta meta = generalResponse.getMeta();
                    Logger.write(TAG + " meta--", meta.toString());
                    if (meta.isSuccess() && meta.getStatus_code() == 200) {
                        DialogUtils.closeProgress();
                        User user = generalResponse.getData(Constants.DATA, User.class);
                        user.setToken(meta.getToken());
                        ModelUtils.saveObject(user, context);
                        Logger.write(TAG, "USER : "+user.toString());
                        ModelUtils.logIn(user, context,  DashbordActivity.class);
                    } else {
                        MyUtils.showToast(meta.getError().getMessage());
                        DialogUtils.closeProgress();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    DialogUtils.closeProgress();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> checkEmailCall, Throwable t) {
                MyUtils.parseError(t);
                DialogUtils.closeProgress();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                LauncherUtil.LaunchClass(context, RegistrationActivity.class, null, false);
                break;
        }
    }
}
