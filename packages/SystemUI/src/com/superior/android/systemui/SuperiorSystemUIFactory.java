package com.superior.android.systemui;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;

import com.superior.android.systemui.dagger.SuperiorGlobalRootComponent;
import com.superior.android.systemui.dagger.SuperiorSysUIComponent;
import com.superior.android.systemui.dagger.DaggerSuperiorGlobalRootComponent;

import com.android.systemui.SystemUIFactory;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.navigationbar.gestural.BackGestureTfClassifierProvider;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;

import com.google.android.systemui.gesture.BackGestureTfClassifierProviderGoogle;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class SuperiorSystemUIFactory extends SystemUIFactory {
    @Override
    protected GlobalRootComponent buildGlobalRootComponent(Context context) {
        return DaggerSuperiorGlobalRootComponent.builder()
                .context(context)
                .build();
    }

    @Override
    public BackGestureTfClassifierProvider createBackGestureTfClassifierProvider(AssetManager am, String modelName) {
        return new BackGestureTfClassifierProviderGoogle(am, modelName);
    }

    @Override
    public void init(Context context, boolean fromTest) throws ExecutionException, InterruptedException {
        super.init(context, fromTest);
        if (shouldInitializeComponents()) {
            ((SuperiorSysUIComponent) getSysUIComponent()).createKeyguardSmartspaceController();
        }
    }
}
