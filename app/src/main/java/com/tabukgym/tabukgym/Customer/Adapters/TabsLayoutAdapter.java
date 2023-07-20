package com.tabukgym.tabukgym.Customer.Adapters;

import android.text.SpannableString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tabukgym.tabukgym.Customer.Club.ViewClubDevices;
import com.tabukgym.tabukgym.Customer.Club.ViewClubProfile;
import com.tabukgym.tabukgym.Customer.Club.ViewClubSport;
import com.tabukgym.tabukgym.Customer.Club.ViewClubTrainingSchedule;

public class TabsLayoutAdapter extends FragmentStatePagerAdapter {
    public TabsLayoutAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)
            return new ViewClubProfile();
        else if (position==1)
            return new ViewClubDevices();
        else if (position==2)
            return new ViewClubSport();
        else
            return new ViewClubTrainingSchedule();

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0)
            return new SpannableString("Profile");
        else if (position==1)
            return new SpannableString("Devices");
        else if (position==2)
            return new SpannableString("Sports");
        else
            return new SpannableString("Schedule");
    }
}
