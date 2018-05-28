package com.tqs.android.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nanyuweiyi.view.FPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvOpen;
    private List<Pair<Integer, Integer>> dataMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOpen = (TextView) findViewById(R.id.tvOpen);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new PicFragmentAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this, "第" + position + "张", Toast.LENGTH_SHORT).show();
                if (position == dataMap.size() - 1) {
                    tvOpen.setVisibility(View.VISIBLE);
                } else {
                    tvOpen.setVisibility(View.GONE);
                }
                Log.e("---", position+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        FPageIndicator indicator = (FPageIndicator) findViewById(R.id.indicator);
        indicator.attachToViewPager(pager);
    }

    private class PicFragmentAdapter extends FragmentStatePagerAdapter {

        PicFragmentAdapter(FragmentManager fm) {
            super(fm);
            dataMap = new ArrayList<>();
            dataMap.add(new Pair<>(R.color.bg_1, R.drawable.img_1));
            dataMap.add(new Pair<>(R.color.bg_2, R.drawable.img_2));
            dataMap.add(new Pair<>(R.color.bg_3, R.drawable.img_3));
            dataMap.add(new Pair<>(R.color.bg_4, R.drawable.img_4));
            dataMap.add(new Pair<>(R.color.bg_5, R.drawable.img_5));
        }

        @Override
        public Fragment getItem(int position) {
            return PicFragment.newInstance(dataMap.get(position).first, dataMap.get(position).second);
        }

        @Override
        public int getCount() {
            return dataMap.size();
        }
    }

}
