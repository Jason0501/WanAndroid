package com.jason.www.activity;

import com.google.android.material.tabs.TabLayout;
import com.jason.www.R;
import com.jason.www.adapter.BasePagerAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.fragment.CollectionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class CollectionActivity extends BaseActivity {
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
        String[] titles = getResources().getStringArray(R.array.collection_tab_title);
        mTitleList = Arrays.asList(titles);
        int size = mTitleList.size();
        for (int i = 0; i < size; i++) {
            mFragmentList.add(CollectionFragment.getInstance());
        }
        viewpager.setOffscreenPageLimit(mFragmentList.size() - 1);
        BasePagerAdapter adapter = new BasePagerAdapter(mActivity.getSupportFragmentManager(), mFragmentList, mTitleList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collection;
    }
}