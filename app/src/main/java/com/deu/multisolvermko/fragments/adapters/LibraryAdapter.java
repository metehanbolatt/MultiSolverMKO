package com.deu.multisolvermko.fragments.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.createlibrary.Library;
import com.deu.multisolvermko.fragments.listeners.LibraryListener;
import com.deu.multisolvermko.R;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>{

    private List<Library> libraries;
    private final LibraryListener libraryListener;
    private Timer timer;
    private final List<Library> librarySource;

    public LibraryAdapter(List<Library> libraries, LibraryListener libraryListener) {
        this.libraries = libraries;
        this.libraryListener = libraryListener;
        librarySource = libraries;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LibraryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_library,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, final int position) {
        holder.setLibrary(libraries.get(position));
        holder.layoutLibrary.setOnClickListener(v -> libraryListener.onLibraryClicked(libraries.get(position), position));
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class LibraryViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textSubtitle, textDateTime;
        LinearLayout layoutLibrary;
        RoundedImageView imageLibrary;

        LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubtitle = itemView.findViewById(R.id.textSubtitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            layoutLibrary = itemView.findViewById(R.id.layoutLibrary);
            imageLibrary = itemView.findViewById(R.id.imageLibrary);
        }

        void setLibrary(Library library){
            textTitle.setText(library.getTitle());
            if (library.getSubtitle().trim().isEmpty()){
                textSubtitle.setVisibility(View.GONE);
            }else{
                textSubtitle.setText(library.getSubtitle());
            }
            textDateTime.setText(library.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) layoutLibrary.getBackground();
            if (library.getColor() != null){
                gradientDrawable.setColor(Color.parseColor(library.getColor()));
            }else{
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }
            if (library.getImagePath() != null){
                imageLibrary.setImageBitmap(BitmapFactory.decodeFile(library.getImagePath()));
                imageLibrary.setVisibility(View.VISIBLE);
            }else{
                imageLibrary.setVisibility(View.GONE);
            }
        }
    }

    public void searchLibrary(final String searchKeyword){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()){
                    libraries = librarySource;
                }else{
                    ArrayList<Library> temp = new ArrayList<>();
                    for (Library library : librarySource){
                        if (library.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || library.getSubtitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || library.getNoteText().toLowerCase().contains(searchKeyword.toLowerCase())){
                            temp.add(library);
                        }
                    }
                    libraries = temp;
                }
                new Handler(Looper.getMainLooper()).post(() -> notifyDataSetChanged());
            }
        },500);
    }

    public void cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }
}
