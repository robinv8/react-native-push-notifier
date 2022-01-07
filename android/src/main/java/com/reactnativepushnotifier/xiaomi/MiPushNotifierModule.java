package com.reactnativepushnotifier.xiaomi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

@ReactModule(name = MiPushNotifierModule.NAME)
public class MiPushNotifierModule extends ReactContextBaseJavaModule {
  public static final String NAME = "MiPushNotifier";


  public MiPushNotifierModule(ReactApplicationContext context) {
    super(context);

    final ReactApplicationContext ctx = context;

    final BroadcastReceiver receiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        WritableMap params = Arguments.fromBundle(bundle);

        sendEvent(params);
      }
    };



    final LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(ctx);

    LifecycleEventListener listener = new LifecycleEventListener() {
      public void onHostResume() {
        mgr.registerReceiver(receiver, new IntentFilter("xiaomipush"));
      }

      public void onHostPause() {
        try {
          mgr.unregisterReceiver(receiver);
        } catch (java.lang.IllegalArgumentException e) {
          Log.e(MiPushNotifierModule.NAME, "receiver not registered", e);
        }
      }

      public void onHostDestroy() {
        try {
          mgr.unregisterReceiver(receiver);
        } catch (java.lang.IllegalArgumentException e) {
          Log.e(MiPushNotifierModule.NAME, "receiver not registered", e);
        }
      }
    };

    context.addLifecycleEventListener(listener);

  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  /**
   * 注册MiPush推送服务
   * @param appID 在开发者网站上注册时生成的，MiPush推送服务颁发给app的唯一认证标识
   * @param appToken 在开发者网站上注册时生成的，与appID相对应，用于验证appID是否合法
   */
  @ReactMethod
  public void registerPush(String appID, String appToken){
    MiPushClient.registerPush(getReactApplicationContext(), appID, appToken);

    Log.i("registerPush", );

  }

  /**
   * 关闭MiPush推送服务
   */
  @ReactMethod
  public void unregisterPush(){
    MiPushClient.unregisterPush(getReactApplicationContext());
  }

  /**
   * 启用MiPush推送服务
   */
  @ReactMethod
  public void enablePush(){
    MiPushClient.enablePush(getReactApplicationContext());
  }

  /**
   * 禁用MiPush推送服务
   */
  @ReactMethod
  public void disablePush(){
    MiPushClient.disablePush(getReactApplicationContext());
  }

  /**
   * 为指定用户设置alias
   * @param alias 为指定用户设置别名
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void setAlias(String alias, String category) {
    MiPushClient.setAlias(getReactApplicationContext(), alias, category);
  }

  /**
   * 取消指定用户的alias
   * @param alias 为指定用户取消别名
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void unsetAlias(String alias, String category) {
    MiPushClient.unsetAlias(getReactApplicationContext(), alias, category);
  }

  /**
   * 为指定用户设置userAccount
   * @param userAccount 为指定用户设置userAccount
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void setUserAccount(String userAccount, String category) {
    MiPushClient.setUserAccount(getReactApplicationContext(), userAccount, category);
  }

  /**
   * 取消指定用户的某个userAccount，服务器就不会给这个userAccount推送消息了
   * @param userAccount 为指定用户取消userAccount
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void unsetUserAccount(String userAccount, String category) {
    MiPushClient.unsetUserAccount(getReactApplicationContext(), userAccount, category);
  }

  /**
   * 为某个用户设置订阅主题（Topic）；根据用户订阅的不同主题，开发者可以根据订阅的主题实现分组群发
   * @param topic 某个用户设置订阅的主题
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void subscribe(String topic, String category) {
    MiPushClient.subscribe(getReactApplicationContext(), topic, category);
  }

  /**
   * 为某个用户取消某个订阅主题
   * @param topic 某个用户取消订阅的主题
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void unsubscribe(String topic, String category) {
    MiPushClient.unsubscribe(getReactApplicationContext(), topic, category);
  }

  /**
   * 暂停接收MiPush服务推送的消息
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void pausePush(String category) {
    MiPushClient.pausePush(getReactApplicationContext(), category);
  }

  /**
   * 恢复接收MiPush服务推送的消息，这时服务器会把暂停时期的推送消息重新推送过来。
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void resumePush(String category) {
    MiPushClient.resumePush(getReactApplicationContext(), category);
  }

  /**
   * 设置接收MiPush服务推送的时段，不在该时段的推送消息会被缓存起来，到了合适的时段再向app推送原先被缓存的消息
   * @param startHour 接收时段开始时间的小时
   * @param startMin 接收时段开始时间的分钟
   * @param endHour 接收时段结束时间的小时
   * @param endMin 接收时段结束时间的分钟
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  @ReactMethod
  public void setAcceptTime(int startHour, int startMin, int endHour, int endMin, String category) {
    MiPushClient.setAcceptTime(getReactApplicationContext(), startHour, startMin, endHour, endMin, category);
  }

  /**
   * 获取客户端所有设置的别名
   * @param promise
   */
  @ReactMethod
  public void getAllAlias(Promise promise) {
    List<String> allAlias = MiPushClient.getAllAlias(getReactApplicationContext());
    String[] allAliasArray = allAlias.toArray(new String[allAlias.size()]);
    promise.resolve(Arguments.fromArray(allAliasArray));
  }

  /**
   * 获取客户端所有订阅的主题
   * @param promise
   */
  @ReactMethod
  public void getAllTopics(Promise promise) {
    List<String> allTopics = MiPushClient.getAllAlias(getReactApplicationContext());
    String[] allTopicsArray = allTopics.toArray(new String[allTopics.size()]);
    promise.resolve(Arguments.fromArray(allTopicsArray));
  }

  /**
   * 上报点击的消息
   * @param msgId 调用server api推送消息后返回的消息ID
   */
  @ReactMethod
  public void reportMessageClicked(String msgId) {
    MiPushClient.reportMessageClicked(getReactApplicationContext(), msgId);
  }

  /**
   * 清除小米推送弹出的某一个notifyId通知
   * @param notifyId 调用server api设置通知消息的notifyId
   */
  @ReactMethod
  public void clearNotification(int notifyId) {
    MiPushClient.clearNotification(getReactApplicationContext(), notifyId);
  }

  /**
   * 清除小米推送弹出的所有通知
   */
  @ReactMethod
  public void clearAllNotification() {
    MiPushClient.clearNotification(getReactApplicationContext());
  }

  /**
   * 客户端设置通知消息的提醒类型
   * @param notifyType
   */
  @ReactMethod
  public void setLocalNotificationType(int notifyType) {
    MiPushClient.setLocalNotificationType(getReactApplicationContext(), notifyType);
  }

  /**
   * 清除客户端设置的通知消息提醒类型
   */
  @ReactMethod
  public void clearLocalNotificationType() {
    MiPushClient.clearLocalNotificationType(getReactApplicationContext());
  }

  /**
   * 获取客户端的RegId
   * @param promise
   */
  @ReactMethod
  public void getRegId(Promise promise) {
    String regId = MiPushClient.getRegId(getReactApplicationContext());
    promise.resolve(regId);
  }

  private void processIntent(Intent intent) {
    WritableMap params = MiPushHelper.getDataOfIntent(intent);
    if (params != null) {
      params.putString("type", "NOTIFICATION_MESSAGE_CLICKED");
    }
    sendEvent(params);
  }

  private void sendEvent(WritableMap params) {
    final ReactApplicationContext ctx = getReactApplicationContext();

    if (ctx.hasActiveCatalystInstance()) {
      ctx
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("xiaomipush", params);
    }
  }
}
