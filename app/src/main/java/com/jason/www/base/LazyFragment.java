package com.jason.www.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class LazyFragment extends CacheFragment {

    private boolean mIsFirstVisible = true;
    private boolean mUserVisible = false;

    protected void onVisible(boolean isFirstVisible) {
        if (isFirstVisible) {
            initMvp();
            initView(mRootView);
            initEvent();
            initData();
        }
    }

    protected void initMvp() {
    }

    protected void initView(View view) {
    }

    protected void initData() {
    }

    protected void initEvent() {
    }

    protected void onInvisible() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d("LazyFragment", "onAttach()");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LazyFragment", "onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("LazyFragment", "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("LazyFragment", "setuserVisibleHint()-->isVisibleToUser=" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (mViewCreated) {
            if (isVisibleToUser && !mUserVisible) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && mUserVisible) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("LazyFragment", "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d("LazyFragment", "onHiddenChanged()");
        super.onHiddenChanged(hidden);
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onResume() {
        Log.d("LazyFragment", "onResume()");
        super.onResume();
        if (!mIsFirstVisible) {
            if (!isHidden() && !mUserVisible && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }
    }

    @Override
    public void onPause() {
        Log.d("LazyFragment", "onPause()");
        super.onPause();
        if (mUserVisible && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        Log.d("LazyFragment", "onDestroyView()");
        super.onDestroyView();
        mIsFirstVisible = true;
    }

    private void dispatchUserVisibleHint(boolean visible) {
        if (visible && !isParentVisible()) {
            return;
        }
        if (mUserVisible == visible) {
            return;
        }
        mUserVisible = visible;
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onVisible(true);
            } else {
                onVisible(false);
            }
            dispatchChildVisibleState(true);
        } else {
            dispatchChildVisibleState(false);
            onInvisible();
        }
    }

    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return true;
        }
        if (fragment instanceof LazyFragment) {
            LazyFragment lazyFragment = (LazyFragment) fragment;
            return lazyFragment.isSupportUserVisible();
        }
        return fragment.isVisible();
    }

    private boolean isSupportUserVisible() {
        return mUserVisible;
    }

    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment child : fragments) {
                if (child instanceof LazyFragment && !child.isHidden() && child.getUserVisibleHint()) {
                    ((LazyFragment) child).dispatchUserVisibleHint(visible);
                }
            }
        }
    }
}
