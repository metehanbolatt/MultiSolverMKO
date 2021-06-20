package com.deu.multisolvermko.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.deu.multisolvermko.createlibrary.CreateLibraryActivity;
import com.deu.multisolvermko.createlibrary.Library;
import com.deu.multisolvermko.fragments.adapters.LibraryAdapter;
import com.deu.multisolvermko.createlibrary.LibraryDatabase;
import com.deu.multisolvermko.fragments.listeners.LibraryListener;
import com.deu.multisolvermko.R;
import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment implements LibraryListener {

    public static final int REQUEST_CODE_ADD_LIBRARY = 1;
    public static final int REQUEST_CODE_UPDATE_LIBRARY = 2;
    public static final int REQUEST_CODE_SHOW_LIBRARY = 3;
    private static final int RESULT_OK = -1;

    private RecyclerView libraryRecyclerView;
    private List<Library> libraryList;
    private LibraryAdapter libraryAdapter;

    private int libraryClickedPosition = -1;

    ImageView imageAddLibraryMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_library,container,false);
        imageAddLibraryMain = viewGroup.findViewById(R.id.imageAddLibraryMain);
        imageAddLibraryMain.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), CreateLibraryActivity.class), REQUEST_CODE_ADD_LIBRARY));

        libraryRecyclerView = viewGroup.findViewById(R.id.libraryRecyclerView);
        libraryRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );

        libraryList = new ArrayList<>();
        libraryAdapter = new LibraryAdapter(libraryList,this);
        libraryRecyclerView.setAdapter(libraryAdapter);
        getLibrary(REQUEST_CODE_SHOW_LIBRARY,false);

        EditText inputSearch = viewGroup.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                libraryAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (libraryList.size() != 0){
                    libraryAdapter.searchLibrary(s.toString());
                }

            }
        });

        return viewGroup;
    }

    @Override
    public void onLibraryClicked(Library library, int position) {
        libraryClickedPosition = position;
        Intent intent = new Intent(getActivity(), CreateLibraryActivity.class);
        intent.putExtra("isViewOrUpdate",true);
        intent.putExtra("note", library);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_LIBRARY);
    }

    private void getLibrary(final int requestCode, final boolean isNoteDeleted){
        @SuppressLint("StaticFieldLeak")
        class GetLibraryTask extends AsyncTask<Void, Void, List<Library>>{

            @Override
            protected List<Library> doInBackground(Void... voids) {
                return LibraryDatabase.getDatabase(getContext()).libraryInterfaceDao().getAllLibrary();
            }

            @Override
            protected void onPostExecute(List<Library> library) {
                super.onPostExecute(library);

                if (requestCode == REQUEST_CODE_SHOW_LIBRARY){
                    libraryList.addAll(library);
                    libraryAdapter.notifyDataSetChanged();
                }else if (requestCode == REQUEST_CODE_ADD_LIBRARY){
                    libraryList.add(0,library.get(0));
                    libraryAdapter.notifyItemInserted(0);
                    libraryRecyclerView.smoothScrollToPosition(0);
                }else if (requestCode == REQUEST_CODE_UPDATE_LIBRARY){
                    libraryList.remove(libraryClickedPosition);

                    if (isNoteDeleted){
                        libraryAdapter.notifyItemRemoved(libraryClickedPosition);
                    }else{
                        libraryList.add(libraryClickedPosition, library.get(libraryClickedPosition));
                        libraryAdapter.notifyItemChanged(libraryClickedPosition);
                    }
                }
            }
        }

        new GetLibraryTask().execute();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_LIBRARY && resultCode == RESULT_OK){
            getLibrary(REQUEST_CODE_ADD_LIBRARY,false);
        }else if (requestCode == REQUEST_CODE_UPDATE_LIBRARY && resultCode == RESULT_OK){
            if (data != null){
                getLibrary(REQUEST_CODE_UPDATE_LIBRARY,data.getBooleanExtra("isNoteDeleted",false));
            }
        }
    }
}