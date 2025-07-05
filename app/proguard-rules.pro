# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.alipay.sdk.app.** { *; }
-keep class com.download.library.** { *; }
-keep class javax.lang.model.element.** { *; }

# 保留支付宝 SDK 类
-keep class com.alipay.sdk.app.** { *; }

# 保留下载库相关类
-keep class com.download.library.** { *; }

# 保留 ARouter 注解类
-keep class javax.lang.model.element.** { *; }

# 保留 AgentWeb 中的 WebView 相关类
-keepclassmembers class com.just.agentweb.** {
    <methods>;
}

# 防止 Glide、ARouter 等框架被混淆
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep class com.alibaba.android.arouter.** { *; }
