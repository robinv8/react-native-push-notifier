package com.segmentfault.push.xiaomi;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


class MIPushHelper {

  public static WritableMap getDataOfIntent(Intent intent) {
    if (intent == null) {
      return null;
    }

    final Set<String> categories = new HashSet<String>(Arrays.asList(Intent.CATEGORY_LAUNCHER));
    if (intent.getAction() != Intent.ACTION_MAIN || !categories.equals(intent.getCategories())) {
      return null;
    }

    MiPushMessage message = (MiPushMessage)intent.getSerializableExtra(PushMessageHelper.KEY_MESSAGE);
    if (message == null) {
      return null;
    }

    Bundle bundle = message.toBundle();
    HashMap<String, String> extra = (HashMap<String, String>)bundle.getSerializable("extra");
    if (extra != null) {
      Bundle extraBundle = new Bundle();
      for (String key: extra.keySet()) {
        String value = extra.get(key);
        extraBundle.putString(key, value);
      }
      bundle.putBundle("extra", extraBundle);
    }

    WritableMap params = Arguments.fromBundle(bundle);
    return params;
  }
}
