package com.jason.www.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BasePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list_fragment;
    private List<String> list_Title;

    public BasePagerAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
        //第二个参数取BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT可用于做懒加载
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }


    public void setList_fragment(List<Fragment> list_fragment) {
        this.list_fragment = list_fragment;
        notifyDataSetChanged();
    }

    public void updateData(List<Fragment> list_fragment, List<String> list_Title) {
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
        notifyDataSetChanged();
    }

    public void setList_Title(List<String> list_Title) {
        this.list_Title = list_Title;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position);
    }
}
