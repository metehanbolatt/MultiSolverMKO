package com.deu.multisolvermko.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;
import com.deu.multisolvermko.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    EditText signUpEmail,signUpPassword,signUpName,signUpSurname;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    String name,surname,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();

        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpName = findViewById(R.id.signUpName);
        signUpSurname = findViewById(R.id.signUpSurname);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void signUpButton(View view){

        if (signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("") && !signUpName.getText().toString().equals("") && !signUpSurname.getText().toString().equals("")) {

            showEmailToast();
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            onShakeEmail();

        }else if (!signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && !signUpName.getText().toString().equals("") && !signUpSurname.getText().toString().equals("")) {

            showPasswordToast();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            onShakePassword();

        }else if (!signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && !signUpSurname.getText().toString().equals("")) {

            showNameError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            onShakeName();

        }else if (!signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("") && !signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showSurnameError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakeSurname();

        } else if (signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && !signUpName.getText().toString().equals("") && !signUpSurname.getText().toString().equals("")) {

            showEmailPasswordToast();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            onShakePassword();
            onShakeEmail();

        } else if (signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && !signUpSurname.getText().toString().equals("")) {

            showEmailNameError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            onShakeName();
            onShakeEmail();

        } else if (signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("") && !signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showEmailSurnameError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakeSurname();
            onShakeEmail();

        } else if (!signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && !signUpSurname.getText().toString().equals("")) {

            showPasswordNameError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            onShakePassword();
            onShakeName();

        } else if (!signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && !signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showPasswordSurnameError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakePassword();
            onShakeSurname();

        } else if (!signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showNameSurnameError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakeName();
            onShakeSurname();

        }else if (signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showAllError();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakeName();
            onShakeSurname();
            onShakePassword();
            onShakeEmail();

        }else if (!signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showExceptEmail();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakeName();
            onShakeSurname();
            onShakePassword();

        }else if (signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showExceptPassword();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakeName();
            onShakeSurname();
            onShakeEmail();

        }else if (signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && !signUpName.getText().toString().equals("") && signUpSurname.getText().toString().equals("")) {

            showExceptName();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            onShakeSurname();
            onShakePassword();
            onShakeEmail();

        }else if (signUpEmail.getText().toString().equals("") && signUpPassword.getText().toString().equals("") && signUpName.getText().toString().equals("") && !signUpSurname.getText().toString().equals("")) {

            showExceptSurname();
            signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border_red, null));
            signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
            onShakeName();
            onShakePassword();
            onShakeEmail();

        }else{

            email = signUpEmail.getText().toString();
            password = signUpPassword.getText().toString();
            name = signUpName.getText().toString();
            surname = signUpSurname.getText().toString();

            final HashMap<String, Object> data = new HashMap<>();
            data.put("useremail",email);
            data.put("name",name);
            data.put("surname",surname);
            data.put("urlfoto", "null" );

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {

                firebaseFirestore.collection("Users").document(signUpEmail.getText().toString()).set(data);
                signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
                signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
                signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
                signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                finish();

            }).addOnFailureListener(e -> {
                if (Objects.equals(e.getLocalizedMessage(), "Geçerli formatta email adresi giriniz.")){

                    showFirebaseEmailError();
                    signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
                    signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
                    signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
                    signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
                    onShakeEmail();

                }else if(signUpPassword.getText().length() < 6){

                    showFirebasePasswordError();
                    signUpPassword.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border_red,null));
                    signUpEmail.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_border,null));
                    signUpName.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
                    signUpSurname.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_border, null));
                    onShakePassword();

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
    public void onShakeName() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        signUpName.startAnimation(shake);
    }
    public void onShakeSurname() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        signUpSurname.startAnimation(shake);
    }

    public void showEmailToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_email, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showPasswordToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_password, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showEmailPasswordToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_email_password, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showFirebaseEmailError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_email_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showFirebasePasswordError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_password_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showNameError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_name_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showSurnameError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_surname_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showEmailNameError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_email_name, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showAllError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_all_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showEmailSurnameError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_email_surname_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showNameSurnameError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_name_surname_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showPasswordNameError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_password_name_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showPasswordSurnameError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_password_surname_error, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showExceptEmail(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_email_except_empty, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showExceptPassword(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_password_except_empty, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showExceptName(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_name_except_empty, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showExceptSurname(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_signup_firebase_surname_except_empty, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}