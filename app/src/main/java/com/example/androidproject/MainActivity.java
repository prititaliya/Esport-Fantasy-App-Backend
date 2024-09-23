package com.example.androidproject;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.androidproject.ui.main.SectionsPagerAdapter;
import com.example.androidproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = ".mainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        Log.d(TAG, "onCreate: mainactivity");
    }

}