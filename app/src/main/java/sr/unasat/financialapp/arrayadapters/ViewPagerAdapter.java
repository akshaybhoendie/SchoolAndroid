package sr.unasat.financialapp.arrayadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Jair on 2/28/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String>tabTitles=new ArrayList<>();

    public void addfragments(Fragment fragment,String tabTitle){
        this.fragments.add(fragment);
        this.tabTitles.add(tabTitle);
    }

    public ViewPagerAdapter(FragmentManager fragmentManager){

        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
