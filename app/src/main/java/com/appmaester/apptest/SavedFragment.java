package com.appmaester.apptest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.appmaester.apptest.Adapters.PostAdapter;
import com.appmaester.apptest.Model.Item;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Nik on 4/23/2018.
 */

public class SavedFragment extends Fragment {
    List<Item> postList;
    PostAdapter pa;
    Context mContext;

    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_saved, container, false);
        mContext = container.getContext();

        ListView savedPostLists = (ListView) v.findViewById(R.id.savedPostslist);
        savedPostLists.setDivider(null);
        postList = new ArrayList<Item>();

        pa = new PostAdapter(container.getContext(),postList,false);

        savedPostLists.setAdapter(pa);
        getAllPosts();
        return v;
    }


    private void getAllPosts(){
        SQLiteDatabase ocoDB = mContext.openOrCreateDatabase("apptestdb", MODE_PRIVATE, null);
        Cursor c = ocoDB.rawQuery("SELECT * FROM apptestposts WHERE favorite=1 ORDER BY published DESC", null);
        int itemIndex = c.getColumnIndex("item");
        Gson gson = new Gson();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String json = c.getString(itemIndex);
            Item item = gson.fromJson(json, Item.class);
            postList.add(item);
            c.moveToNext();
        }
        c.close();
        ocoDB.close();
        pa.notifyDataSetChanged();
    }
}
