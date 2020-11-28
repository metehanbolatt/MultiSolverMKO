package com.deu.multisolvermko.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        email = firebaseAuth.getCurrentUser().getEmail();

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
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                final View viewView = LayoutInflater.from(getContext()).inflate(R.layout.change_password_dialog, layoutDialogContainer);
                builder.setView(viewView);

                final AlertDialog alertDialog = builder.create();

                viewView.findViewById(R.id.buttonChangeOkWithKC).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TextView oldPassword=viewView.findViewById(R.id.oldPassword);
                        String oldPasswordAuth=oldPassword.getText().toString();
                        System.out.println(oldPassword);
                        TextView newPassword=viewView.findViewById(R.id.newPassword);

                        if (oldPassword.getText().length()<6 || newPassword.getText().length()<6){
                            Toast.makeText(getContext(), "Lütfen 6 haneden uzun şifre giriniz", Toast.LENGTH_SHORT).show();
                        }else {

                            final String newPasswordAuth = newPassword.getText().toString();
                            System.out.println(newPassword);
                            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPasswordAuth);
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.updatePassword(newPasswordAuth).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Şifreniz başarıyla değiştirildi.", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss();
                                                } else {
                                                    Toast.makeText(getContext(), "Şifre değiştirme başarısız", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getContext(), "Doğrulama Hatası", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                viewView.findViewById(R.id.buttonChangeCancelWithKC).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "ŞİFRE DEĞİŞTİRME İŞLEMİNİZ İPTAL EDİLMİŞTİR", Toast.LENGTH_SHORT).show();
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
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null){
                    for (DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String,Object> data = snapshot.getData();
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