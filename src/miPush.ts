import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-push-notifier' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const MiPush = NativeModules.MiPushNotifier
  ? NativeModules.MiPushNotifier
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

enum NotifyType {
  DEFAULT_SOUND = 1,
  DEFAULT_VIBRATE = 2,
  DEFAULT_LIGHTS = 4,
  DEFAULT_ALL = -1,
}
const MiPushNotifier = {
  /**
   * @description 获取初始数据
   * @returns
   */
  getInitialMessage: () => {
    return MiPush.getInitialMessage();
  },
  /**
   * @description 注册MiPush推送服务
   * @param appId 在开发者网站上注册时生成的，MiPush推送服务颁发给app的唯一认证标识
   * @param appToken 在开发者网站上注册时生成的，与appID相对应，用于验证appID是否合法
   */
  registerPush: (appId: string, appToken: string) => {
    MiPush.registerPush(appId, appToken);
  },

  /**
   * @description 取消注册MiPush推送服务
   */
  unregisterPush: () => {
    MiPush.unregisterPush();
  },

  /**
   * @description 启用MiPush推送服务
   */
  enablePush: () => {
    MiPush.enablePush();
  },

  /**
   * @description 禁用MiPush推送服务
   */
  disablePush: () => {
    MiPush.disablePush();
  },

  /**
   * @description 为指定用户设置alias
   * @param alias 为指定用户设置别名
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  setAlias(alias: string, category: any = null) {
    MiPush.setAlias(alias, category);
  },

  /**
   * @description 取消指定用户的alias
   * @param alias 为指定用户取消别名
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  unsetAlias(alias: string, category: any = null) {
    MiPush.unsetAlias(alias, category);
  },

  /**
   * @description 为指定用户设置userAccount
   * @param userAccount 为指定用户设置userAccount
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  setUserAccount(userAccount: string, category: any = null) {
    MiPush.setUserAccount(userAccount, category);
  },

  /**
   * @description 取消指定用户的某个userAccount，服务器就不会给这个userAccount推送消息了
   * @param userAccount 为指定用户取消userAccount
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  unsetUserAccount(userAccount: string, category: any = null) {
    MiPush.unsetUserAccount(userAccount, category);
  },

  /**
   * @description 为某个用户设置订阅主题（Topic）；根据用户订阅的不同主题，开发者可以根据订阅的主题实现分组群发
   * @param topic 某个用户设置订阅的主题
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  subscribe(topic: string, category: any = null) {
    MiPush.subscribe(topic, category);
  },

  /**
   * @description 为某个用户取消某个订阅主题
   * @param topic 某个用户设置订阅的主题
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  unsubscribe(topic: string, category: any = null) {
    MiPush.unsubscribe(topic, category);
  },

  /**
   * 暂停接收MiPush服务推送的消息
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  pausePush(category: any = null) {
    MiPush.pausePush(category);
  },

  /**
   * @description 恢复接收MiPush服务推送的消息，这时服务器会把暂停时期的推送消息重新推送过来。
   * @param category  扩展参数，暂时没有用途，直接填null
   */
  resumePush(category: any = null) {
    MiPush.resumePush(category);
  },

  /**
   * @description 设置接收MiPush服务推送的时段，不在该时段的推送消息会被缓存起来，到了合适的时段再向app推送原先被缓存的消息
   * @param startHour 接收时段开始时间的小时
   * @param startMin 接收时段开始时间的分钟
   * @param entHour 接收时段结束时间的小时
   * @param endMin 接收时段结束时间的分钟
   * @param category 扩展参数，暂时没有用途，直接填null
   */
  setAcceptTime(
    startHour: number,
    startMin: number,
    entHour: number,
    endMin: number,
    category: any
  ) {
    MiPush.setAcceptTime(startHour, startMin, entHour, endMin, category);
  },

  /**
   * @description 获取客户端所有设置的别名
   * @returns {Promise<any>}
   */
  getAllAlias() {
    return MiPush.getAllAlias();
  },

  /**
   * @description 获取客户端所有订阅的主题
   * @returns {Promise<any>}
   */
  getAllTopics() {
    return MiPush.getAllTopics();
  },

  /**
   * @description 上报点击的消息
   * @param msgId 调用server api推送消息后返回的消息ID
   */
  reportMessageClicked(msgId: string) {
    MiPush.reportMessageClicked(msgId);
  },

  /**
   * @description 清除小米推送弹出的某一个notifyId通知
   * @param notifyId 调用server api设置通知消息的notifyId
   */
  clearNotification(notifyId: string) {
    MiPush.clearNotification(notifyId);
  },

  /**
   * @description 清除小米推送弹出的所有通知
   */
  clearAllNotification() {
    MiPush.clearAllNotification();
  },

  /**
   * @description 客户端设置通知消息的提醒类型
   * @param notifyType
   */
  setLocalNotificationType(notifyType: NotifyType) {
    MiPush.setLocalNotificationType(notifyType);
  },

  /**
   * @description 清除客户端设置的通知消息提醒类型
   */
  clearLocalNotificationType() {
    MiPush.clearLocalNotificationType();
  },

  /**
   * @description 获取客户端的RegId
   * @returns Promise<string>
   */
  getRegId() {
    return MiPush.getRegId();
  },
};
export default MiPushNotifier;
