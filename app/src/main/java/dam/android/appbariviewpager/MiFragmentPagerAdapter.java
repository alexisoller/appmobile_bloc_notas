package dam.android.appbariviewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MiFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private Context con;
    private String tabTitles[] = new String[] { "NOTAS", "RATING", "CAMARA","JASPER"};
    private savebitmap sb=new savebitmap();

    public MiFragmentPagerAdapter(FragmentManager fm,Context ct) {
        super(fm);
        this.con=ct;
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
                f = fragment1.newInstance();
                break;
            case 1:
                f = fragment2.newInstance();
                break;
            case 2:
                f = fragment3.newInstance(sb,con);
                break;
            case 3:
                f = fragment4.newInstance();
                break;

        }

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}