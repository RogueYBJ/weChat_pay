#import "WechatPayPlugin.h"

@implementation WechatPayPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    [WXApi registerApp:@"wx5c685a6653728677" universalLink:@"www.baidu.com"];
    
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"wechat_pay"
            binaryMessenger:[registrar messenger]];
  WechatPayPlugin* instance = [[WechatPayPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    self.resultBlock = result;
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if ([@"pay" isEqualToString:call.method]) {
      if (![[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"wechat://"]] ) {
          result([@"iOS " stringByAppendingString:@"请先安装微信App"]);
      }else{
          [self wxpay:call.arguments];
      }
      return;
  } else {
      
    result(FlutterMethodNotImplemented);
  }
}

- (void) wxpay:(NSDictionary*)dic{
    
        // 调起微信支付
        PayReq *request = [[PayReq alloc] init];
        /** 微信分配的公众账号ID -> APPID */
        request.partnerId = [dic objectForKey:@"partnerId"];;
        /** 预支付订单 从服务器获取 */
        request.prepayId = [dic objectForKey:@"prepayId"];
        /** 商家根据财付通文档填写的数据和签名 <暂填写固定值Sign=WXPay>*/
        request.package = [dic objectForKey:@"package"];
        /** 随机串，防重发 */
        request.nonceStr= [dic objectForKey:@"nonceStr"];
        /** 时间戳，防重发 */
        /** 商家根据微信开放平台文档对数据做的签名, 可从服务器获取，也可本地生成*/
        request.sign= [dic objectForKey:@"sign"];
        UInt32 u ;
        sscanf([[dic objectForKey:@"timeStamp"] UTF8String], "%u", &u);
        request.timeStamp= u;
            request= [dic objectForKey:@"x"];
        /* 调起支付 */
        [WXApi sendReq:request completion:nil];
    
}

- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options
{
   if ([url.host isEqualToString:@"pay"]){ //微信支付的回调
        return [WXApi handleOpenURL:url delegate:self];
    }
    return YES;
}

#pragma 微信支付协议
- (void)onReq:(BaseReq*)req{
    NSLog(@"%@",req);
}

- (void)onResp:(BaseResp *)resp{
    self.resultBlock(@"支付完成");
}
@end
