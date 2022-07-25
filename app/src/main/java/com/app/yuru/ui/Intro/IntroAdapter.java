package com.app.yuru.ui.Intro;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IntroAdapter extends FragmentPagerAdapter {
    public IntroAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new FirstScreen();

            case 1 : return new SecondScreen();

            case 2 : return new ThirdScreen();

            case 3 : return new ForthScreen();

            case 4 : return new FifthScreen();

            case 5 : return new SixthScreen();


            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
