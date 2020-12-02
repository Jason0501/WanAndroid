package com.jason.www.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.jason.www.R;
import com.jason.www.adapter.BasePagerAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.fragment.FrequentWebSiteFragment;
import com.jason.www.fragment.HomeFragment;
import com.jason.www.fragment.MineFragment;
import com.jason.www.fragment.QuestionFragment;
import com.jason.www.utils.FastClickUtils;
import com.jason.www.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @author：Jason
 * @date: 2020/12/2 13:37
 * @email：1129847330@qq.com
 * @description:
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private List<String> mTitleList;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        initViewPager();
    }

    private void initViewPager() {
        String[] titles = getResources().getStringArray(R.array.home_tab_title);
        mTitleList = Arrays.asList(titles);
        mFragmentList.add(HomeFragment.getInstance());
        mFragmentList.add(QuestionFragment.getInstance());
        mFragmentList.add(FrequentWebSiteFragment.getInstance());
        mFragmentList.add(MineFragment.getInstance());
        viewpager.setOffscreenPageLimit(mFragmentList.size() - 1);
        BasePagerAdapter adapter = new BasePagerAdapter(mActivity.getSupportFragmentManager(),
                mFragmentList, mTitleList);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        int size = mFragmentList.size();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_indicator_layout,
                    null);
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
    protected void initEvent() {
        super.initEvent();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).statusBarColor(R.color.transparent).init();
                        break;
                    case 1:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).statusBarColor(R.color.orange).init();
                        break;
                    case 2:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).statusBarColor(R.color.blue).init();
                        break;
                    case 3:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(true).statusBarColor(R.color.yellow).init();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (FastClickUtils.isFastClicked(2000)) {
            super.onBackPressed();
            SystemUtils.killMySelfProcess();
        } else {
            showToast(getString(R.string.exist_press_again));
        }
    }
}