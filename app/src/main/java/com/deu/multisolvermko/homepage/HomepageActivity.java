package com.deu.multisolvermko.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.authentication.SignInActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HomepageActivity extends AppCompatActivity {

    Bitmap selectedImage;
    public FirebaseAuth firebaseAuth;
    ImageView imageView;
    private StorageReference storageReference;
    Uri imageData;
    String currentUser;
    String downloadUrl;
    FirebaseFirestore firebaseFirestore;
    String email,name,surName;
    TextView userName;
    int control=0;
    String control2;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        getData();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.userName);

        imageView = headerView.findViewById(R.id.imageProfile);
        imageView.setOnLongClickListener(v -> {
            if (ContextCompat.checkSelfPermission(HomepageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(HomepageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else{
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
            return false;
        });

        NavController navController = Navigation.findNavController(this, R.id.navHosFragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        final TextView textTitle = findViewById(R.id.textTitle);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            textTitle.setText(destination.getLabel());

            if (destination.getId() == R.id.menuLogout){
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        currentUser = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
        StorageReference newReference = FirebaseStorage.getInstance().getReference(currentUser);
        newReference.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get().load(uri).into(imageView);
            control2 = String.valueOf(Picasso.get().load(uri));
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                if(control==0 && control2==null) {
                    Animation care;
                    care= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    imageView.startAnimation(care);
                    Toast.makeText(HomepageActivity.this, "Profil fotoğrafı yükleyiniz...", Toast.LENGTH_SHORT).show();
                    control=1;
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageData = data.getData();
            try {
                if (Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    upload();
                }else{
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    upload();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void upload(){
        currentUser = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
        if (imageData != null){
            assert currentUser != null;
            storageReference.child(currentUser).putFile(imageData).addOnSuccessListener(taskSnapshot -> {
                final StorageReference newReference = FirebaseStorage.getInstance().getReference(currentUser);
                newReference.getDownloadUrl().addOnSuccessListener(uri -> {

                    downloadUrl = uri.toString();
                    documentReference = firebaseFirestore.collection("Users").document(currentUser);
                    documentReference.update("urlfoto",downloadUrl).addOnSuccessListener(aVoid -> {

                    }).addOnFailureListener(e -> {

                    });
                    Picasso.get().load(uri).into(imageView);
                });
            });
        }
    }

    @SuppressLint("SetTextI18n")
    public void getData(){

        if (firebaseAuth.getCurrentUser() != null){
            email = firebaseAuth.getCurrentUser().getEmail();
        }

        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        collectionReference.whereEqualTo("useremail",email).addSnapshotListener((value, error) -> {
            if (error != null){
                Toast.makeText(HomepageActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            if (value != null){
                for (DocumentSnapshot snapshot: value.getDocuments()){
                    Map<String,Object> data = snapshot.getData();
                    assert data != null;
                    name = (String) data.get("name");
                    surName = (String) data.get("surname");
                    userName.setText(name+" "+surName);
                }
            }
        });
    }
}