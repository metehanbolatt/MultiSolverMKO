package com.deu.multisolvermko.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.authentication.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
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
    TextView emailText, full_nameText,profile_cikis;
    ImageView userImage;

    FirebaseUser firebaseUser;

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
        full_nameText=viewGroup.findViewById(R.id.full_name);
        userImage=viewGroup.findViewById(R.id.imageProfile);
        profile_cikis = viewGroup.findViewById(R.id.profile_cikis);

        final TextView clickText=viewGroup.findViewById(R.id.profile_sifre_degistir);
        final ConstraintLayout layoutDialogContainer = viewGroup.findViewById(R.id.layoutDialogContainer);

        clickText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(requireContext(),R.style.AlertDialogTheme);
                final View viewView = LayoutInflater.from(getContext()).inflate(R.layout.change_password_dialog, layoutDialogContainer);
                builder.setView(viewView);

                final AlertDialog alertDialog = builder.create();

                viewView.findViewById(R.id.buttonChangeOkWithKC).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText oldPassword=viewView.findViewById(R.id.oldPassword);
                        EditText newPassword=viewView.findViewById(R.id.newPassword);

                        if (newPassword.getText().toString().isEmpty() && oldPassword.getText().toString().isEmpty()){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_profile_new_old_password, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
                            Toast toast = new Toast(getContext());
                            toast.setGravity(Gravity.BOTTOM,0,50);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                        }else if(newPassword.getText().toString().isEmpty()){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_profile_new_password, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
                            Toast toast = new Toast(getContext());
                            toast.setGravity(Gravity.BOTTOM,0,50);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                        }else if(oldPassword.getText().toString().isEmpty()){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_profile_old_password, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
                            Toast toast = new Toast(getContext());
                            toast.setGravity(Gravity.BOTTOM,0,50);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                        }else if (oldPassword.getText().length() < 6 || newPassword.getText().length() < 6){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_profile_password_length, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
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
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    firebaseUser.updatePassword(newPasswordAuth).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                LayoutInflater inflater = getLayoutInflater();
                                                View layout = inflater.inflate(R.layout.toast_profile_change_password_success, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
                                                Toast toast = new Toast(getContext());
                                                toast.setGravity(Gravity.BOTTOM, 0, 50);
                                                toast.setDuration(Toast.LENGTH_LONG);
                                                toast.setView(layout);
                                                toast.show();
                                                alertDialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (Objects.equals(e.getLocalizedMessage(), "The password is invalid or the user does not have a password.")){
                                        LayoutInflater inflater = getLayoutInflater();
                                        View layout = inflater.inflate(R.layout.toast_profile_password_change_failed, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
                                        Toast toast = new Toast(getContext());
                                        toast.setGravity(Gravity.BOTTOM,0,50);
                                        toast.setDuration(Toast.LENGTH_LONG);
                                        toast.setView(layout);
                                        toast.show();
                                    }else if (Objects.equals(e.getLocalizedMessage(), "We have blocked all requests from this device due to unusual activity. Try again later. [ Access to this account has been temporarily disabled due to many failed login attempts. You can immediately restore it by resetting your password or you can try again later. ]")){
                                        LayoutInflater inflater = getLayoutInflater();
                                        View layout = inflater.inflate(R.layout.toast_profile_change_password_block, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
                                        Toast toast = new Toast(getContext());
                                        toast.setGravity(Gravity.BOTTOM,0,50);
                                        toast.setDuration(Toast.LENGTH_LONG);
                                        toast.setView(layout);
                                        toast.show();
                                    }


                                }
                            });
                        }
                    }
                });

                viewView.findViewById(R.id.buttonChangeCancelWithKC).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_profile_password_change_canceled, (ViewGroup) viewGroup.findViewById(R.id.toast_root));
                        Toast toast = new Toast(getContext());
                        toast.setGravity(Gravity.BOTTOM,0,50);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                        alertDialog.dismiss();
                    }
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });

        storageReference=FirebaseStorage.getInstance().getReference();
        storageReference.child(email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                Picasso.with(getContext()).load(uri).into(userImage);
            }
        });

        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        collectionReference.whereEqualTo("useremail",email).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null){
                    for (DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String,Object> data = snapshot.getData();
                        assert data != null;
                        name = (String) data.get("name");
                        surname = (String) data.get("surname");
                        full_nameText.setText(name + " " + surname);
                    }
                }
            }
        });

        emailText.setText(email);

        profile_cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        return viewGroup;

    }

}