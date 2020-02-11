import 'dart:async';

import 'package:flutter/services.dart';

class WechatPay {
  static const MethodChannel _channel =
      const MethodChannel('wechat_pay');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  static Future<String> pay(Map data) async {
    final String version = await _channel.invokeMethod('pay',data);
    return version;
  }
}
