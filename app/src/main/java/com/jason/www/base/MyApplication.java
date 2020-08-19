package com.jason.www.base;

import android.app.Application;
import android.content.Context;

import com.jason.www.R;
import com.jason.www.utils.DynamicTimeFormat;
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
public class MyApplication extends Application {
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
                refreshLayout.setEnableOverScrollDrag(false);//是否启用越界拖动（仿苹果效果）1.0.4
                refreshLayout.setEnablePureScrollMode(false);//是否启用纯滚动模式
                refreshLayout.setEnableNestedScroll(false);//是否启用嵌套滚动
                refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
                refreshLayout.setPrimaryColorsId(R.color.window_background, R.color.textcolor_dark_grey);
//                refreshLayout.setHeaderMaxDragRate(4);//最大显示下拉高度/Header标准高度
//                refreshLayout.setFooterMaxDragRate(4);//最大显示下拉高度/Footer标准高度
//                refreshLayout.setHeaderTriggerRate(1);//触发刷新距离 与 HeaderHeight 的比率1.0.4
//                refreshLayout.setFooterTriggerRate(1);//触发加载距离 与 FooterHeight 的比率1.0.4
//                refreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
//                refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
//                refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
//                refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
//                refreshLayout.setEnableFooterFollowWhenNoMoreData(false);//是否在全部加载结束之后Footer跟随内容1.0.4
//                refreshLayout.setEnableScrollContentWhenRefreshed(true);//是否在刷新完成时滚动列表显示新的内容 1.0.5
//                refreshLayout.setEnableClipHeaderWhenFixedBehind(true);//是否剪裁Header当时样式为FixedBehind时1.0.5
//                refreshLayout.setEnableClipFooterWhenFixedBehind(true);//是否剪裁Footer当时样式为FixedBehind时1.0.5
                refreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
                refreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
//                refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
//                refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
//                refreshLayout.setHeaderHeight(100);//Header标准高度（显示下拉高度>=标准高度 触发刷新）
//                refreshLayout.setHeaderHeightPx(100);//同上-像素为单位 （V1.1.0删除）
//                refreshLayout.setFooterHeight(100);//Footer标准高度（显示上拉高度>=标准高度 触发加载）
//                refreshLayout.setFooterHeightPx(100);//同上-像素为单位 （V1.1.0删除）
//                refreshLayout.setHeaderInsetStart(0);//设置 Header 起始位置偏移量 1.0.5
//                refreshLayout.setHeaderInsetStartPx(0);//同上-像素为单位 1.0.5 （V1.1.0删除）
//                refreshLayout.setFooterInsetStart(0);//设置 Footer 起始位置偏移量 1.0.5
//                refreshLayout.setFooterInsetStartPx(0);//同上-像素为单位 1.0.5 （V1.1.0删除）

            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
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

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }
}