package com.ekaruztech.smartkolo.ui.activities.signups;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.ekaruztech.smartkolo.util.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ekaruztech.smartkolo.R.id.login;

public class RegistrationActivity extends AppActivity {

    public static final String TAG = RegistrationActivity.class.getSimpleName();

    String usernameText, emailText, passwordText, deviceText;
    EditText username, email, password, confirm_password;

    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    public void initViews() {
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
    }

    public boolean isFormValid() {
        return !(isEmptyOrNull(email) | isEmptyOrNull(password)
                | !isEntryValid());
    }

    public boolean isEntryValid() {
        if (!isEmailValid(email.getText().toString()))
            return false;
        else return isPasswordValid(password.getText().toString());
    }

    public boolean isEmptyOrNull(EditText view) {
        String value = view.getText().toString().trim();
        String tag = view.getTag().toString();
        if (value == null || value.equals("")) {
            view.setError("Empty value is not allowed");
            return true;
        } else if (tag.equalsIgnoreCase("password") && value.length() < 5) {
            view.setError("Password cannot be less than 5 characters");
            return true;
        } else {
            switch (tag) {
                case "email":
                    break;
                case "username":
                    break;
                case "password":
                    break;
                case "confirm_password":
                    break;
            }
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    public void registerOnClick(View view) {
        if (!isFormValid())
            return;

        emailText = email.getText().toString().trim();
        usernameText = username.getText().toString().trim();
        passwordText = password.getText().toString().trim();
        deviceText = "1234567";

        user = new User();
        user.setEmail(emailText);
        user.setUsername(usernameText);
        user.setDevice_token(deviceText);
        user.setPassword(passwordText);
        onSignupSuccess(user);
    }

    public void onSignupSuccess(User user) {

        DialogUtils.displayProgress(this);

        Call<ResponseBody> response = MainApplication.createService(AuthApi.class).register(user);
        Logger.write(TAG, response.request().url().toString());
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
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(User.TAG, user);
                        Logger.write(TAG, "USER : " + user.toString());
                        ModelUtils.logIn(user, context, DashbordActivity.class);
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
}
