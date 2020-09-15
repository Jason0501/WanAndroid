package com.jason.www.base;

import android.app.Application;
import android.content.Context;

import com.jason.www.R;
import com.jason.www.config.AppData;
import com.jason.www.utils.DynamicTimeFormat;
import com.jason.www.utils.glide.GlideUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * @author：Jason
 * @date：2020/8/14 15:12
 * @email：1129847330@qq.com
 * @description:
 */
public class RunApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppData.init(getApplicationContext());
        GlideUtils.init(getApplicationContext());
    }

    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout refreshLayout) {
                //全局设置（优先级最低）
                refreshLayout.setEnableRefresh(true);//开启下拉刷新
                refreshLayout.setEnableLoadMore(true);//开启上拉加载更多
                refreshLayout.setEnableAutoLoadMore(true);//开启自动上拉加载更多
                refreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
                refreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(R.color.windows_background, R.color.textcolor_dark_grey);
                layout.setEnableHeaderTranslationContent(true);
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }
}