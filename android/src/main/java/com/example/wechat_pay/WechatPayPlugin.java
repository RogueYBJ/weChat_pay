package com.example.wechat_pay;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/**
 * WechatPayPlugin
 */
public class WechatPayPlugin implements FlutterPlugin, MethodCallHandler {

    private static Context context;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        context = flutterPluginBinding.getApplicationContext();
        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "wechat_pay");
        channel.setMethodCallHandler(new WechatPayPlugin());
    }


    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
//  public static void registerWith(PluginRegistry.Registrar registrar) {
//    final MethodChannel channel = new MethodChannel(registrar.messenger(), "wechat_pay");
//    channel.setMethodCallHandler(new WechatPayPlugin(registrar));
//  }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("pay")) {
            if (!Util.isInstalled(context, "com.tencent.mm")) {
                Toast.makeText(context, "请先安装微信App", Toast.LENGTH_SHORT).show();
                result.success("Android " + "请先安装微信App");
                return;
            }else{
                wxpay(call);
                result.success("Android " + "微信支付");
            }

        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }

    public void wxpay(MethodCall call) {
        WXPayService.getInstance().initWXPayService(context);
        WXPayService.getInstance().startWXPay(call);
    }
}
