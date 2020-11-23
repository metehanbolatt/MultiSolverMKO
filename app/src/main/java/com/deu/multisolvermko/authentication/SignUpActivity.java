package com.deu.multisolvermko.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.deu.multisolvermko.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    EditText signUpEmail,signUpPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);
    }

    public void signUpButton(View view){

        if (signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("")){
            showEmailPasswordTost();
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
            onShakeBoth();
        }else if (signUpPassword.getText().toString().equals("")){
            showPasswordTost();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
            onShakePassword();
        }else if (signUpEmail.getText().toString().equals("")){
            showEmailTost();
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
            onShakeEmail();
        }else{
            String email = signUpEmail.getText().toString();
            String password = signUpPassword.getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
                    signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
                    Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e.getLocalizedMessage().equals("The email address is badly formatted.")){
                        showFirebaseEmailError();
                    }else{
                        showFirebasePasswordError();
                    }
                    signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
                    signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
                    onShakeBoth();
                }
            });
        }
    }

    public void onShakeEmail() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        signUpEmail.startAnimation(shake);
    }
    public void onShakePassword() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        signUpPassword.startAnimation(shake);
    }
    public void onShakeBoth() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        signUpEmail.startAnimation(shake);
        signUpPassword.startAnimation(shake);
    }
    public void showEmailTost(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_email, (ViewGroup)findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showPasswordTost(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_password, (ViewGroup)findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showEmailPasswordTost(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_email_password, (ViewGroup)findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showFirebaseEmailError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_email_error, (ViewGroup)findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showFirebasePasswordError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_password_error, (ViewGroup)findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}