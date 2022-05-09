package com.segmentfault.push;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.segmentfault.push.xiaomi.MiPushNotifierModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PushNotifierPackage implements ReactPackage {
  private MiPushNotifierModule miPushNotifierModule;
  private Intent mIntent;

  public void onIntent(Intent intent) {
    if (miPushNotifierModule != null) {
      miPushNotifierModule.onIntent(intent);
    } else {
      mIntent = intent;
    }
  }

  @NonNull
  @Override
  public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
    miPushNotifierModule = new MiPushNotifierModule(reactContext);
    if (mIntent != null) {
      miPushNotifierModule.onIntent(mIntent);
      mIntent = null;
    }

    List<NativeModule> modules = new ArrayList<>();
    modules.add(miPushNotifierModule);
    return modules;
  }

  @Override
  public List<Class<? extends JavaScriptModule>> createJSModules() {
    return new ArrayList<>();
  }

  @NonNull
  @Override
  public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }
}
