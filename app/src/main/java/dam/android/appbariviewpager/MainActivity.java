package dam.android.appbariviewpager;


import android.content.Intent;
import android.content.SharedPreferences;


import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private TabLayout tabLayout;
    private AutoCompleteTextView auto;
    private RatingBar rating;
    private int autocompleteposicion = 0;
    private SharedPreferences MyPreferences;
    private final String MYPREFS = "MyPrefs";
    private repromusica rp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mysettings();
        rp = new repromusica(getApplicationContext());
        rp.start();
        setContentView(R.layout.fragment2);
        setUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rp.stopmusic();
    }

    private void setUI() {
        setContentView(R.layout.fragment2);
        auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autocompleteposicion = position;
            }
        });
        rating = (RatingBar) findViewById(R.id.ratingBar);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MiFragmentPagerAdapter(getSupportFragmentManager(),getApplicationContext()));
        tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == 1) {
            navView = (NavigationView) findViewById(R.id.navview);
            navView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {

                            boolean fragmentTransaction = false;
                            Fragment fragment = null;

                            switch (menuItem.getItemId()) {
                                case R.id.menu_seccion_1:
                                    tabLayout.getTabAt(0).select();
                                    break;
                                case R.id.menu_seccion_2:
                                    tabLayout.getTabAt(1).select();
                                    break;
                                case R.id.menu_seccion_3:
                                    tabLayout.getTabAt(2).select();
                                    break;
                                case R.id.menu_seccion_4:
                                    tabLayout.getTabAt(3).select();
                                    break;
                            }

                            if (fragmentTransaction) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();

                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle(menuItem.getTitle());
                            }

                            drawerLayout.closeDrawers();

                            return true;
                        }
                    });


        }


    }


    private void mysettings() {
        MyPreferences = getSharedPreferences(MYPREFS, MODE_PRIVATE);


        if (MyPreferences.getBoolean("rbnegro", false)) {
            super.setTheme(R.style.thnegro);
        } else if (MyPreferences.getBoolean("rbblanco", false)) {
            super.setTheme(R.style.thblanco);
        } else if (MyPreferences.getBoolean("rbdefecto", false)) {
            super.setTheme(R.style.AppTheme);
        }
        if (MyPreferences.getBoolean("rbnegrita", false)) {
            super.setTheme(R.style.negrita);
        } else if (MyPreferences.getBoolean("rbcursiva", false)) {
            super.setTheme(R.style.cursiva);
        } else if (MyPreferences.getBoolean("rbnormal", false)) {
            super.setTheme(R.style.normal);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_buscar:
                Toast.makeText(getApplicationContext(),
                        "Lupa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, settings.class);
                startActivity(intent);
                finish();
                break;

            case android.R.id.home:
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == 1) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("auto", autocompleteposicion);
        savedInstanceState.putFloat("rating", rating.getRating());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        auto.setSelection(savedInstanceState.getInt("auto"));
        rating.setRating(savedInstanceState.getFloat("rating"));
    }
}
