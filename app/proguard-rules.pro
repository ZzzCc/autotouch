-keep class com.hrzk.ipfs.entity.** {*;}

#qmui
-keep class com.qmuiteam.qmui.** {*;}
-dontwarn com.qmuiteam.qmui.**

#umeng
-keep class com.umeng.** {*;}
-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class com.witfrog.frogx.R$*{
    public static final int *;
}
