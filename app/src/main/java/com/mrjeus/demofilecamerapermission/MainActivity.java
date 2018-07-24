package com.mrjeus.demofilecamerapermission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 100;
    public static final String PATH = Environment.getExternalStorageDirectory().getPath().toString()
            + "/DCIM/Camera";
    public static final int NUM_COLUMN = 2;
    public static final String DOTJPG = ".jpg";
    public static final String DOTPNG = ".png";
    public static final String DOTJPEG = ".jpeg";
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private List<File> mListImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                initData();
            } else {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}
                        , REQUEST_CODE);
            }
        }
    }

    private void initData() {
        getImages(new File(PATH));
        mImageAdapter.notifyDataSetChanged();
    }

    private void getImages(File dir) {
        File listImages[] = dir.listFiles();
        if (listImages != null && listImages.length > 0) {
            for (int i = 0; i < listImages.length; i++) {
                if (listImages[i].isDirectory()) {
                    getImages(listImages[i]);
                } else {
                    if (listImages[i].getName().endsWith(DOTPNG)
                            || listImages[i].getName().endsWith(DOTJPG)
                            || listImages[i].getName().endsWith(DOTJPEG)
                            ) {
                        mListImage.add(listImages[i]);
                    }
                }
            }
        }
    }

    private void initView() {
        mListImage = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);
        mImageAdapter = new ImageAdapter(MainActivity.this, mListImage);
        GridLayoutManager mManager = new GridLayoutManager(this, NUM_COLUMN);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mImageAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);
    }
}
