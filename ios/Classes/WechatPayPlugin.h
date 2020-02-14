#import <Flutter/Flutter.h>
#import "WXApi.h"
@interface WechatPayPlugin : NSObject<FlutterPlugin,WXApiDelegate>

@property (nonatomic, copy) FlutterResult resultBlock;

@end
