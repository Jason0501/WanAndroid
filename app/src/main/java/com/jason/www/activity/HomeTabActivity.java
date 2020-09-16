package com.jason.www.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jason.www.R;
import com.jason.www.adapter.BasePagerAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.fragment.HomeFragment;
import com.jason.www.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class HomeTabActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private List<String> mTitleList;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void initView(View decorView) {
        super.initView(decorView);
        initViewPager();
    }

    private void initViewPager() {
        String[] titles = getResources().getStringArray(R.array.home_tab_title);
        mTitleList = Arrays.asList(titles);
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new HomeFragment());
        viewpager.setOffscreenPageLimit(mFragmentList.size() - 1);
        BasePagerAdapter adapter = new BasePagerAdapter(mActivity.getSupportFragmentManager(), mFragmentList, mTitleList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewpager);
        int size = mFragmentList.size();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_indicator_layout, null);
            TextView textview = view.findViewById(R.id.textview_tab_home);
            ImageView imageview = view.findViewById(R.id.imageview_tab_home);
            switch (i) {
                case 0:
                    imageview.setImageResource(R.drawable.selector_tab_image1);
                    break;
                case 1:
                    imageview.setImageResource(R.drawable.selector_tab_image2);
                    break;
                case 2:
                    imageview.setImageResource(R.drawable.selector_tab_image3);
                    break;
                case 3:
                    imageview.setImageResource(R.drawable.selector_tab_image4);
                    break;
            }
            textview.setText(mTitleList.get(i));
            tabLayout.getTabAt(i).setCustomView(view);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home_tab;
    }

    private long firstTimeMillis;

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - firstTimeMillis <= 1500) {
            super.onBackPressed();
            SystemUtils.killMySelfProcess();
        } else {
            showToast(getString(R.string.exist_press_again));
            firstTimeMillis = t;
        }
    }
}