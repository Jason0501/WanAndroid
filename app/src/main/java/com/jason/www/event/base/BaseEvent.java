package com.jason.www.event.base;

import org.greenrobot.eventbus.EventBus;

/**
 * @author：Jason
 * @date：2020/9/21 16:54
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class BaseEvent {
    public void post() {
        EventBus.getDefault().post(this);
    }

    public void postSticky() {
        EventBus.getDefault().postSticky(this);
    }

    public void removeSticky() {
        EventBus.getDefault().removeStickyEvent(this);
    }
}