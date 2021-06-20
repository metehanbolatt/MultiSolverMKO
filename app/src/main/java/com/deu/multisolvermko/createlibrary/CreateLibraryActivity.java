package com.deu.multisolvermko.createlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.deu.multisolvermko.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateLibraryActivity extends AppCompatActivity {

    private EditText noteTitle, noteSubtitle, note;
    private TextView textDateTime;
    private View viewSubtitleIndicator;
    private ImageView noteImage;
    private String selectedLibraryColor;
    private String selectedImagePath;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private AlertDialog dialogDeleteLibrary;
    private Library alreadyAvailableLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_library);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());

        noteTitle = findViewById(R.id.inputNoteTitle);
        noteSubtitle = findViewById(R.id.inputNoteSubtitle);
        note = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);
        noteImage = findViewById(R.id.imageLibrary);

        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(new Date())
        );

        ImageView imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(v -> saveNote());

        selectedLibraryColor = "#333333";
        selectedImagePath = "";

        if (getIntent().getBooleanExtra("isViewOrUpdate",false)){
            alreadyAvailableLibrary = (Library) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }

        findViewById(R.id.imageRemoveImage).setOnClickListener(v -> {
            noteImage.setImageBitmap(null);
            noteImage.setVisibility(View.GONE);
            findViewById(R.id.imageRemoveImage).setVisibility(View.GONE);
            selectedImagePath = "";
        });

        initMiscellaneous();
        setSubtitleIndicatorColor();
    }

    private void setViewOrUpdateNote(){
        noteTitle.setText(alreadyAvailableLibrary.getTitle());
        noteSubtitle.setText(alreadyAvailableLibrary.getSubtitle());
        note.setText(alreadyAvailableLibrary.getNoteText());
        textDateTime.setText(alreadyAvailableLibrary.getDateTime());

        if (alreadyAvailableLibrary.getImagePath() != null && !alreadyAvailableLibrary.getImagePath().trim().isEmpty()){
            noteImage.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableLibrary.getImagePath()));
            noteImage.setVisibility(View.VISIBLE);
            findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
            selectedImagePath = alreadyAvailableLibrary.getImagePath();
        }
    }

    private void saveNote(){
        if (noteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Başlığı boş bırakmayınız.", Toast.LENGTH_SHORT).show();
            return;

        }else if (noteSubtitle.getText().toString().trim().isEmpty() && note.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Boş bir not kaydedemezsin.", Toast.LENGTH_SHORT).show();
            return;
        }

        final Library library = new Library();
        library.setTitle(noteTitle.getText().toString());
        library.setSubtitle(noteSubtitle.getText().toString());
        library.setNoteText(note.getText().toString());
        library.setDateTime(textDateTime.getText().toString());
        library.setColor(selectedLibraryColor);
        library.setImagePath(selectedImagePath);

        if (alreadyAvailableLibrary != null){
            library.setId(alreadyAvailableLibrary.getId());
        }

        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                LibraryDatabase.getDatabase(getApplicationContext()).libraryInterfaceDao().insertLibrary(library);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new SaveNoteTask().execute();
    }

    private void initMiscellaneous() {
        final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);
        layoutMiscellaneous.findViewById(R.id.textMiscellaneous).setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        final ImageView imageColor1 = layoutMiscellaneous.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = layoutMiscellaneous.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = layoutMiscellaneous.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = layoutMiscellaneous.findViewById(R.id.imageColor4);
        final ImageView imageColor5 = layoutMiscellaneous.findViewById(R.id.imageColor5);

        layoutMiscellaneous.findViewById(R.id.viewColor1).setOnClickListener(v -> {
            selectedLibraryColor = "#333333";
            imageColor1.setImageResource(R.drawable.ic_done);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewColor2).setOnClickListener(v -> {
            selectedLibraryColor = "#FDBE3B";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_done);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewColor3).setOnClickListener(v -> {
            selectedLibraryColor = "#FF4842";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_done);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewColor4).setOnClickListener(v -> {
            selectedLibraryColor = "#3A52FC";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_done);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewColor5).setOnClickListener(v -> {
            selectedLibraryColor = "#000000";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(R.drawable.ic_done);
            setSubtitleIndicatorColor();
        });

        if (alreadyAvailableLibrary != null && alreadyAvailableLibrary.getColor() != null && !alreadyAvailableLibrary.getColor().trim().isEmpty()){
            switch (alreadyAvailableLibrary.getColor()){
                case "#FDBE3B":
                    layoutMiscellaneous.findViewById(R.id.viewColor2).performClick();
                    break;
                case "#FF4842":
                    layoutMiscellaneous.findViewById(R.id.viewColor3).performClick();
                    break;
                case "#3A52FC":
                    layoutMiscellaneous.findViewById(R.id.viewColor4).performClick();
                    break;
                case "#000000":
                    layoutMiscellaneous.findViewById(R.id.viewColor5).performClick();
                    break;
            }
        }

        layoutMiscellaneous.findViewById(R.id.layoutAddImage).setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(CreateLibraryActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE_PERMISSION
                );
            }else{
                selectImage();
            }
        });

        if (alreadyAvailableLibrary != null){
            layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setVisibility(View.VISIBLE);
            layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setOnClickListener(v -> {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                showDeleteNoteDialog();
            });
        }
    }

    private void showDeleteNoteDialog(){
        if (dialogDeleteLibrary == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateLibraryActivity.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_delete_note,(ViewGroup)findViewById(R.id.layoutDeleteNoteContainer));
            builder.setView(view);
            dialogDeleteLibrary = builder.create();
            if (dialogDeleteLibrary.getWindow() != null){
                dialogDeleteLibrary.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    @SuppressLint("StaticFieldLeak")
                    class DeleteNoteTask extends AsyncTask<Void,Void,Void>{

                        @Override
                        protected Void doInBackground(Void... voids) {
                            LibraryDatabase.getDatabase(getApplicationContext()).libraryInterfaceDao().deleteLibrary(alreadyAvailableLibrary);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted",true);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    }
                    new DeleteNoteTask().execute();
                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(v -> dialogDeleteLibrary.dismiss());
        }
        dialogDeleteLibrary.show();
    }

    private void setSubtitleIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedLibraryColor));
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                Toast.makeText(this, "Galeriye ulaşılamadı.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if (data != null){
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null){
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        noteImage.setImageBitmap(bitmap);
                        noteImage.setVisibility(View.VISIBLE);
                        findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFromUri(selectedImageUri);

                    }catch (Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri){
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri,null,null,null,null);
        if (cursor == null){
            filePath = contentUri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
}