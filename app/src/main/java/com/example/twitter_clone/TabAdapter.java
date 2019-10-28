package com.example.twitter_clone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int Tabposition) {
        switch (Tabposition) {
            case 0:
                return new profileTab();
            case 1:
                return new UsersTab();
            case 2:
                return new SharePicture();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "profileTab";
            case 1:
                return "UsersTab";
            case 2:
                return "SharePicture";
            default:
                return null;
        }
    }

}