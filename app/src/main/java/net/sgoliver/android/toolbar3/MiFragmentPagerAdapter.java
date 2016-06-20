package net.sgoliver.android.toolbar3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MiFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    public MiFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        switch(position) {
            case 0:
                f = Fragment1.newInstance();
                break;
            case 1:
                f = Fragment2.newInstance();
                break;
        }

        return f;
    }
    @Override
    public CharSequence getPageTitle(int position) {

        String tabTitles[] =
                new String[] {"ENTRADAS/SALIDAS", "HISTORIAL"};

        return tabTitles[position];
    }
}
