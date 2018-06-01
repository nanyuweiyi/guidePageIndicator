package com.nanyuweiyi.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nanyuweiyi.view.FPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FPageIndicator.Callback{

    TextView tvOpen;
    private List<Pair<Integer, Integer>> dataMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOpen = (TextView) findViewById(R.id.tvOpen);
        tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "按钮被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new PicFragmentAdapter(getSupportFragmentManager()));

        FPageIndicator indicator = (FPageIndicator) findViewById(R.id.indicator);
        indicator.attachToViewPager(pager, this);
    }

    @Override
    public void isLastListener(boolean isLastPos) {
        if(isLastPos){
            tvOpen.setVisibility(View.VISIBLE);
        }else {
            tvOpen.setVisibility(View.GONE);
        }
    }

    private class PicFragmentAdapter extends FragmentStatePagerAdapter {

        PicFragmentAdapter(FragmentManager fm) {
            super(fm);
            dataMap = new ArrayList<>();
            dataMap.add(new Pair<>(R.color.bg_1, R.mipmap.img_1));
            dataMap.add(new Pair<>(R.color.bg_2, R.mipmap.img_2));
            dataMap.add(new Pair<>(R.color.bg_3, R.mipmap.img_3));
            dataMap.add(new Pair<>(R.color.bg_4, R.mipmap.img_4));
            dataMap.add(new Pair<>(R.color.bg_5, R.mipmap.img_5));
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
