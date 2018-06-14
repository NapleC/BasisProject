package com.dxs.stc.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.mvp.api.BookApi;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.http.ApiMethods;
import com.dxs.stc.utils.http.MyObserver;
import com.dxs.stc.utils.http.ObserverOnNextListener;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.utils.http.RetrofitUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends CompatStatusBarActivity {

    private static final int REQUEST_READ_CONTACTS = 0;

    // UI references.
    @BindView(R.id.et_account)
    EditText mAccountView;
    @BindView(R.id.et_password)
    EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setStatus(false, true, Color.parseColor("#FF161616"));
        initViewData();
    }

    private void initViewData() {

        populateAutoComplete();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.btn_login, R.id.tv_to_register, R.id.tv_to_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
//                attemptLogin();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.tv_to_register:
                startActivity(new Intent(LoginActivity.this, PhoneRegisterActivity.class));
                break;
            case R.id.tv_to_forget:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mAccountView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    private void attemptLogin() {

        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);

        String email = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mAccountView.setError(getString(R.string.error_invalid_email));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showLoading();
            // do login
            initObserver();
        }
    }


    private void initObserver() {
        ObserverOnNextListener<Movie> listener = new ObserverOnNextListener<Movie>() {
            @Override
            public void onNext(Movie movie) {
                Loger.debug("onNext:" + movie.getTitle());
                List<Movie.SubjectsBean> list = movie.getSubjects();
                for (Movie.SubjectsBean sub : list) {
                    Loger.debug("onNext SubjectsBean :" + "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                }
                hideLoading();
                ToastUtils.showShort("登陆成功");
                toMainActivity();
            }

            @Override
            public void onFailed(ParseErrorMsgUtil.ErrorMessage errorMessage) {
                hideLoading();
                ToastUtils.showShort(errorMessage.toString());
                mAccountView.setError(getString(R.string.error_incorrect_password));
                mAccountView.requestFocus();
            }
        };

        ApiMethods.ApiSubscribe(RetrofitUtil.create(BookApi.class).getTopMovie(0, 1),
                new MyObserver<Movie>(listener));
    }

    private void toMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return email.length() >= 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

}

