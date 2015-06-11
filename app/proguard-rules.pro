# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /System/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:


#-keep public class Hooptap{ *; }
#-keep public class HooptapApi{ *; }
#-keep public class com.hooptap.sdkbrandclub.Customs.HooptapGame{ *; }
#-keep public class com.hooptap.sdkbrandclub.Interfaces.*

#-dontwarn com.squareup.okhttp.**
#-keep class com.squareup.okhttp.** { *; }
#-dontwarn com.squareup.okhttp.**
#-keep interface com.squareup.okhttp.** { *; }
#-dontwarn com.squareup.okhttp.**
#-keep class retrofit.** { *; }
#-keepclasseswithmembers class * {
 #   @retrofit.http.* <methods>;
#}
#-dontwarn sun.misc.Unsafe.**
#-keep class sun.misc.Unsafe { *; }

#-keepattributes *Annotation*
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService
-dontwarn com.hooptap.c.**
-dontwarn com.hooptap.a.**
#-keep class com.hooptap.a.** { *; }

#-keepparameternames
#-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#-keep class Hooptap { *; }
#-keep class HooptapApi { *; }
-keep public class com.hooptap.sdkbrandclub.Api.* {
    public *;
}

-keep public class com.hooptap.sdkbrandclub.Api.HooptapApi {
    public *;
}
-keep public class com.hooptap.sdkbrandclub.Customs.* {
    public *;
}

-keep public class com.hooptap.sdkbrandclub.Interfaces.HooptapJSInterface {
    public *;
}
-keep public class com.hooptap.sdkbrandclub.Interfaces.HooptapCallback {
    public *;
}

#-keep public class * {
#    public *;
#}

#-keepclassmembernames class * {
  #  java.lang.Class class$(java.lang.String);
 #   java.lang.Class class$(java.lang.String, boolean);
#}

#-keepclasseswithmembernames,includedescriptorclasses class * {
 #   native <methods>;
#}

#-keepclassmembers,allowoptimization enum * {
 #   public static **[] values();
  #  public static ** valueOf(java.lang.String);
#}

#-keepclassmembers class * implements java.io.Serializable {
 #   static final long serialVersionUID;
  #  private static final java.io.ObjectStreamField[] serialPersistentFields;
   # private void writeObject(java.io.ObjectOutputStream);
    #private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
 #   java.lang.Object readResolve();
#}