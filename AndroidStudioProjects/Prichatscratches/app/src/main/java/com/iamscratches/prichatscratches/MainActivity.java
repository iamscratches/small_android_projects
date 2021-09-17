package com.iamscratches.prichatscratches;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.iamscratches.prichatscratches.databinding.ActivityMainBinding;
import com.iamscratches.prichatscratches.menu.CallsFragment;
import com.iamscratches.prichatscratches.menu.ChatFragment;
import com.iamscratches.prichatscratches.menu.StatusFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpWithViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        setSupportActionBar(binding.toolbar);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeFabColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_search:
                Toast.makeText(MainActivity.this, "Action Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_more:
                Toast.makeText(MainActivity.this, "Action more", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpWithViewPager(ViewPager viewPager){
        MainActivity.SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatFragment(), "Chats");
        adapter.addFragment(new StatusFragment(), "Status");
        adapter.addFragment(new CallsFragment(), "Calls");

        viewPager.setAdapter(adapter);
    }

    private static class SectionPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public SectionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void changeFabColor(int index){
        binding.fabAction.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch(index){
                    case 0:
                        binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_baseline_chat_bubble_outline_24));
                        break;
                    case 1:
                        binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_baseline_linked_camera_24));
                        break;
                    case 2:
                        binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_baseline_call_24));
                        break;
                }
                binding.fabAction.show();
            }
        }, 400);
    }
}