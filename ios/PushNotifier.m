#import "PushNotifier.h"

@implementation PushNotifier

RCT_EXPORT_MODULE()



RCT_EXPORT_METHOD(registerMiPush)
{
    [MiPushSDK registerMiPush:self];
}
RCT_EXPORT_METHOD(registerMiPush)
{
    [MiPushSDK registerMiPush:self];
}

RCT_EXPORT_METHOD(registerMiPushWithType:(int)type)
{
    [MiPushSDK registerMiPush:self type:(UIRemoteNotificationType)type];
}

RCT_EXPORT_METHOD(registerMiPushAndConnect:(BOOL)isConnect type:(int)type)
{
    [MiPushSDK registerMiPush:self type:(UIRemoteNotificationType)type connect:isConnect];
}

RCT_EXPORT_METHOD(unregisterMiPush)
{
    [MiPushSDK unregisterMiPush];
}

RCT_EXPORT_METHOD(bindDeviceToken:(NSString *)hexDeviceToken)
{
    NSMutableData * deviceToken = [[NSMutableData alloc] init];
    char bytes[3] = {'\0', '\0', '\0'};
    for (int i=0; i<[hexDeviceToken length] / 2; i++) {
        bytes[0] = [hexDeviceToken characterAtIndex:i*2];
        bytes[1] = [hexDeviceToken characterAtIndex:i*2+1];
        unsigned char c = strtol(bytes, NULL, 16);
        [deviceToken appendBytes:&c length:1];
    }

    [MiPushSDK bindDeviceToken:deviceToken];
}

RCT_EXPORT_METHOD(setAlias:(NSString *)alias)
{
    [MiPushSDK setAlias:alias];
}

RCT_EXPORT_METHOD(unsetAlias:(NSString *)alias)
{
    [MiPushSDK unsetAlias:alias];
}

RCT_EXPORT_METHOD(setAccount:(NSString *)account)
{
    [MiPushSDK setAccount:account];
}

RCT_EXPORT_METHOD(unsetAccount:(NSString *)account)
{
    [MiPushSDK unsetAccount:account];
}

RCT_EXPORT_METHOD(subscribe:(NSString *)topic)
{
    [MiPushSDK subscribe:topic];
}

RCT_EXPORT_METHOD(unsubscribe:(NSString *)topic)
{
    [MiPushSDK unsubscribe:topic];
}

RCT_EXPORT_METHOD(openAppNotify:(NSString *)messageId)
{
    [MiPushSDK openAppNotify:messageId];
}

RCT_EXPORT_METHOD(getAllAliasAsync)
{
    [MiPushSDK getAllAliasAsync];
}

RCT_EXPORT_METHOD(getAllAccountAsync)
{
    [MiPushSDK getAllAccountAsync];
}

RCT_EXPORT_METHOD(getAllTopicAsync)
{
    [MiPushSDK getAllTopicAsync];
}

RCT_EXPORT_METHOD(getSDKVersion:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    NSString * sdkVersion = [MiPushSDK getSDKVersion];

    resolve(sdkVersion);
}

@end
