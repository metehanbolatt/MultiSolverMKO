package com.deu.multisolvermko.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.authentication.SignInActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String email,name,surname;
    TextView emailText, fullNameText, profileLogout;
    ImageView userImage;

    FirebaseUser firebaseUser;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        email = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        emailText=viewGroup.findViewById(R.id.emailText);
        fullNameText =viewGroup.findViewById(R.id.fullNameText);
        userImage=viewGroup.findViewById(R.id.imageProfile);
        profileLogout = viewGroup.findViewById(R.id.profileLogoutText);

        final TextView clickText=viewGroup.findViewById(R.id.profile_change_password);
        final ConstraintLayout layoutDialogContainer = viewGroup.findViewById(R.id.layoutDialogContainer);

        clickText.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(requireContext(),R.style.AlertDialogTheme);
            final View viewView = LayoutInflater.from(getContext()).inflate(R.layout.change_password_dialog, layoutDialogContainer);
            builder.setView(viewView);

            final AlertDialog alertDialog = builder.create();

            viewView.findViewById(R.id.buttonChangeOk).setOnClickListener(view1 -> {

                EditText oldPassword=viewView.findViewById(R.id.oldPassword);
                EditText newPassword=viewView.findViewById(R.id.newPassword);

                if (newPassword.getText().toString().isEmpty() && oldPassword.getText().toString().isEmpty()){
                    LayoutInflater inflater1 = getLayoutInflater();
                    View layout = inflater1.inflate(R.layout.toast_profile_new_old_password, viewGroup.findViewById(R.id.toast_root));
                    Toast toast = new Toast(getContext());
                    toast.setGravity(Gravity.BOTTOM,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }else if(newPassword.getText().toString().isEmpty()){
                    LayoutInflater inflater1 = getLayoutInflater();
                    View layout = inflater1.inflate(R.layout.toast_profile_new_password, viewGroup.findViewById(R.id.toast_root));
                    Toast toast = new Toast(getContext());
                    toast.setGravity(Gravity.BOTTOM,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }else if(oldPassword.getText().toString().isEmpty()){
                    LayoutInflater inflater1 = getLayoutInflater();
                    View layout = inflater1.inflate(R.layout.toast_profile_old_password, viewGroup.findViewById(R.id.toast_root));
                    Toast toast = new Toast(getContext());
                    toast.setGravity(Gravity.BOTTOM,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }else if (oldPassword.getText().length() < 6 || newPassword.getText().length() < 6){
                    LayoutInflater inflater1 = getLayoutInflater();
                    View layout = inflater1.inflate(R.layout.toast_profile_password_length, viewGroup.findViewById(R.id.toast_root));
                    Toast toast = new Toast(getContext());
                    toast.setGravity(Gravity.BOTTOM,0,50);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }else {

                    final String newPasswordAuth = newPassword.getText().toString();
                    final String oldPasswordAuth = oldPassword.getText().toString();

                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPasswordAuth);
                    System.out.println(credential);
                    firebaseUser.reauthenticate(credential).addOnSuccessListener(aVoid -> firebaseUser.updatePassword(newPasswordAuth).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            LayoutInflater inflater1 = getLayoutInflater();
                            View layout = inflater1.inflate(R.layout.toast_profile_change_password_success, viewGroup.findViewById(R.id.toast_root));
                            Toast toast = new Toast(getContext());
                            toast.setGravity(Gravity.BOTTOM, 0, 50);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                            alertDialog.dismiss();
                        }
                    })).addOnFailureListener(e -> {
                        if (Objects.equals(e.getLocalizedMessage(), "The password is invalid or the user does not have a password.")){
                            LayoutInflater inflater1 = getLayoutInflater();
                            View layout = inflater1.inflate(R.layout.toast_profile_password_change_failed, viewGroup.findViewById(R.id.toast_root));
                            Toast toast = new Toast(getContext());
                            toast.setGravity(Gravity.BOTTOM,0,50);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }else if (Objects.equals(e.getLocalizedMessage(), "We have blocked all requests from this device due to unusual activity. Try again later. [ Access to this account has been temporarily disabled due to many failed login attempts. You can immediately restore it by resetting your password or you can try again later. ]")){
                            LayoutInflater inflater1 = getLayoutInflater();
                            View layout = inflater1.inflate(R.layout.toast_profile_change_password_block, viewGroup.findViewById(R.id.toast_root));
                            Toast toast = new Toast(getContext());
                            toast.setGravity(Gravity.BOTTOM,0,50);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    });
                }
            });

            viewView.findViewById(R.id.buttonChangeCancel).setOnClickListener(view12 -> {
                LayoutInflater inflater1 = getLayoutInflater();
                View layout = inflater1.inflate(R.layout.toast_profile_password_change_canceled, viewGroup.findViewById(R.id.toast_root));
                Toast toast = new Toast(getContext());
                toast.setGravity(Gravity.BOTTOM,0,50);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                alertDialog.dismiss();
            });

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
        });

        storageReference=FirebaseStorage.getInstance().getReference();
        storageReference.child(email).getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(userImage));

        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        collectionReference.whereEqualTo("useremail",email).addSnapshotListener((value, error) -> {
            if (error != null){
                Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            if (value != null){
                for (DocumentSnapshot snapshot: value.getDocuments()){
                    Map<String,Object> data = snapshot.getData();
                    assert data != null;
                    name = (String) data.get("name");
                    surname = (String) data.get("surname");
                    fullNameText.setText(name + " " + surname);
                }
            }
        });

        emailText.setText(email);

        profileLogout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
        return viewGroup;

    }
}