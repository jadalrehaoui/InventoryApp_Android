package com.jadrehaoui.inventoryv3;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jadrehaoui.inventoryv3.repo.UserRepository;
import com.jadrehaoui.inventoryv3.utils.FormValidator;
import com.jadrehaoui.inventoryv3.utils.PermissionUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private final int REQUEST_SEND_SMS_CODE = 0;
    private final int REQUEST_READ_SMS_CODE = 1;
    private final int REQUEST_INTERNET_CODE = 2;

    private UserRepository userRepo;

    Button submitBtn;

    TextView forgotPasswordBtn;
    TextView switchFormBtn;
    TextView formTitle;

    EditText fullNameEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confimPasswordEditText;
    FormSubmittedTask submitTask;
    private String formState = "LOGIN";
    private FormValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        boolean readSMSGranted = PermissionUtil.hasPermission(this, READ_SMS, R.string.read_sms_rationale, REQUEST_READ_SMS_CODE);
        boolean sendSMSGranted = PermissionUtil.hasPermission(this, SEND_SMS, R.string.send_sms_rationale, REQUEST_SEND_SMS_CODE);
        boolean internetGranted = PermissionUtil.hasPermission(this, INTERNET, R.string.send_internet_rationale, REQUEST_INTERNET_CODE);
        // buttons
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtnId);
        forgotPasswordBtn.setOnClickListener(this);
        submitBtn = findViewById(R.id.submitLoginBtnId);
        submitBtn.setOnClickListener(this);
        switchFormBtn = findViewById(R.id.switchToOtherFormBtnId);
        switchFormBtn.setOnClickListener(this);
        // text
        formTitle = findViewById(R.id.formTitleId);
        // inputs
        fullNameEditText = findViewById(R.id.fullNameInputId);
        usernameEditText = findViewById(R.id.usernameInputId);
        passwordEditText = findViewById(R.id.passwordInputId);
        confimPasswordEditText = findViewById(R.id.confirmPasswordInputId);
        // repo in use
        userRepo = UserRepository.getInstance(getApplicationContext());
        validator = new FormValidator();
        // onCreate action
        updateForm();
        // tasks
        submitTask = new FormSubmittedTask(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitLoginBtnId:
                submitTask.execute(view);
                break;
            case R.id.switchToOtherFormBtnId:
                if(formState == "LOGIN") { formState = "REGISTER";} else {formState = "LOGIN";}
                updateForm();
                break;
            case R.id.forgotPasswordBtnId:
                formState = "FORGOT_PASSWORD";
                updateForm();
                break;
        }

    }

    private void cancelTask() {
        submitTask.cancel(true);
        submitTask = new FormSubmittedTask(this);
    }

    private class FormSubmittedTask extends AsyncTask<View, Integer, String> {
        private final WeakReference<LoginActivity> mActivity;
        private ArrayList<EditText> requiredFields;
        private ArrayList<EditText> matchingFields;
        public FormSubmittedTask(LoginActivity context) {
            mActivity = new WeakReference<>(context);
        }
        @Override
        protected void onPreExecute() {
            EditText [] requiredForRegister = {fullNameEditText, usernameEditText, passwordEditText, confimPasswordEditText};
            EditText [] requiredForLogin = {usernameEditText, passwordEditText};
            EditText [] matchingForRegister = {passwordEditText, confimPasswordEditText};
            EditText [] requiredForRecovery = {usernameEditText};
            switch (formState) {
                case "REGISTER":
                    if(! (validator.validateRequiredFields(requiredForRegister) && validator.validateConfirmationFields(matchingForRegister))) {
                        cancelTask();
                    } else {
                        formTitle.setText("Registering and logging in ...");
                        submitTask = new FormSubmittedTask(mActivity.get());
                    }
                    break;
                case "LOGIN":
                    if(!validator.validateRequiredFields(requiredForLogin)){
                        cancelTask();
                    } else {
                        formTitle.setText("Logging in ...");
                        submitTask = new FormSubmittedTask(mActivity.get());
                    }
                    break;
                case "FORGOT_PASSWORD":
                    if(!validator.validateRequiredFields(requiredForRecovery)){
                        cancelTask();
                    } else {
                        formTitle.setText("Sending you a code ...");
                        submitTask = new FormSubmittedTask(mActivity.get());
                    }
            }
        }

        @Override
        protected String doInBackground(View... views) {
            switch (formState) {
                case "REGISTER":
                    userRepo.register(fullNameEditText.getText().toString(),usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    if(userRepo.getCurrentUser() != null) {
                        Log.d("LoginAcitvity", "Go to home page");
                        return "SUCCESS";
                    } else {
                        Log.d("LoginAcitvity", "Go to home page");
                        return "USERNAME_TAKEN";
                    }
                case "LOGIN":
                    userRepo.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    if(userRepo.getCurrentUser() != null) {
                        Log.d("LoginActivity", "Go to home page");
                        return "SUCCESS";
                    } else {
                        return "LOGIN_FAILURE";
                    }
                case "FORGOT_PASSWORD":
                    // do action in repo
                    break;
            }
            return "INVALID_FORM";
        }

        @Override
        protected void onPostExecute(String result) {
            switch(result) {
                case "SUCCESS":
                    Intent intent = new Intent(mActivity.get(), ProductsActivity.class);
                    startActivity(intent);
                    mActivity.get().finish();
                    break;
                case "LOGIN_FAILURE":
                    Log.d("Failure", "credential error");
                    formTitle.setText(getString(R.string.en_login));
                    usernameEditText.setError(getString(R.string.en_mismatch));
                    passwordEditText.setError(getString(R.string.en_mismatch));
//                    submitTask = new FormSubmittedTask(mActivity.get());
                    break;
                case "INVALID_FORM":
                    Log.d("Failure", "Form invalid");
                    break;
                case "USERNAME_TAKEN":
                    Log.d("Failure", "Username taken");
                    usernameEditText.setError(getString(R.string.en_username_taken));
                    break;
            }
        }
    }

    private void updateForm() {
        switch(formState) {
            case "REGISTER":
                fullNameEditText.setVisibility(VISIBLE);
                usernameEditText.setVisibility(VISIBLE);
                passwordEditText.setVisibility(VISIBLE);
                confimPasswordEditText.setVisibility(VISIBLE);
                forgotPasswordBtn.setVisibility(GONE);
                formTitle.setText(getString(R.string.en_register));
                submitBtn.setText(getString(R.string.en_register));
                switchFormBtn.setText(getString(R.string.en_login));
                break;
            case "LOGIN":
                fullNameEditText.setVisibility(GONE);
                usernameEditText.setVisibility(VISIBLE);
                passwordEditText.setVisibility(VISIBLE);
                confimPasswordEditText.setVisibility(GONE);
                forgotPasswordBtn.setVisibility(VISIBLE);
                formTitle.setText(getString(R.string.en_login));
                submitBtn.setText(getString(R.string.en_login));
                switchFormBtn.setText(getString(R.string.en_register));
                break;
            case "FORGOT_PASSWORD":
                fullNameEditText.setVisibility(GONE);
                usernameEditText.setVisibility(VISIBLE);
                passwordEditText.setVisibility(GONE);
                confimPasswordEditText.setVisibility(GONE);
                forgotPasswordBtn.setVisibility(GONE);
                formTitle.setText(getString(R.string.en_forgot_your_password));
                submitBtn.setText(getString(R.string.en_send_code));
                switchFormBtn.setText(getString(R.string.en_login));
                break;
        }
    }
}
