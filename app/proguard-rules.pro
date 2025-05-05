# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.ryanharter.auto.value.gson.GsonTypeAdapterFactory

 # Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response
 -keep,allowobfuscation,allowshrinking class retrofit2.Callback

 -keep class androidx.compose.foundation.text.HorizontalScrollLayoutModifier
 -keep class androidx.compose.foundation.text.modifiers.TextStringSimpleNode

 -keep class androidx.lifecycle.** { *; }
 -keep class androidx.compose.** { *; }

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 -keep class hilt_aggregated_deps.** { *; }

 -keep class io.socket.client.** {*;}

 -dontwarn io.netty.internal.tcnative.Buffer
 -dontwarn io.netty.internal.tcnative.CertificateCallback
 -dontwarn io.netty.internal.tcnative.CertificateVerifier
 -dontwarn io.netty.internal.tcnative.Library
 -dontwarn io.netty.internal.tcnative.SSL
 -dontwarn io.netty.internal.tcnative.SSLContext
 -dontwarn io.netty.internal.tcnative.SniHostNameMatcher
 -dontwarn org.apache.log4j.Level
 -dontwarn org.apache.log4j.Logger
 -dontwarn org.apache.log4j.Priority
 -dontwarn org.apache.logging.log4j.LogManager
 -dontwarn org.apache.logging.log4j.Logger
 -dontwarn org.apache.logging.log4j.message.MessageFactory
 -dontwarn org.apache.logging.log4j.spi.ExtendedLogger
 -dontwarn org.apache.logging.log4j.spi.ExtendedLoggerWrapper
 -dontwarn org.slf4j.ILoggerFactory
 -dontwarn org.slf4j.Logger
 -dontwarn org.slf4j.LoggerFactory
 -dontwarn org.slf4j.Marker
 -dontwarn org.slf4j.helpers.FormattingTuple
 -dontwarn org.slf4j.helpers.MessageFormatter
 -dontwarn org.slf4j.helpers.NOPLoggerFactory
 -dontwarn org.slf4j.spi.LocationAwareLogger
 -dontwarn sun.security.util.ObjectIdentifier
 -dontwarn sun.security.x509.AlgorithmId
 -dontwarn sun.security.x509.CertificateAlgorithmId
 -dontwarn sun.security.x509.CertificateIssuerName
 -dontwarn sun.security.x509.CertificateSerialNumber
 -dontwarn sun.security.x509.CertificateSubjectName
 -dontwarn sun.security.x509.CertificateValidity
 -dontwarn sun.security.x509.CertificateVersion
 -dontwarn sun.security.x509.CertificateX509Key
 -dontwarn sun.security.x509.X500Name
 -dontwarn sun.security.x509.X509CertImpl
 -dontwarn sun.security.x509.X509CertInfo
