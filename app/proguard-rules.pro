# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.*.** { *; }
#统计防混淆
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.xiaodangjia.R$*{
public static final int *;
}
-keep class com.xiaodangjia.moel.**{
*;
}
-keep class com.xiaodangjia.GetDataChannel.**{
*;
}
-keep class com.xiaodangjia.GetDataRecipe.**{
*;
}
-dontwarn javax.annotation.**
-keep class okhttp3.** { *; }
-ignorewarnings
-keep class * {
    public private *;
}
