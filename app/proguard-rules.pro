# Add project specific ProGuard rules here.
# Kids Drive Adventure uses Jetpack Compose only; default AGP/Compose rules
# bundled by the Android Gradle Plugin handle Compose runtime classes.

-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# Keep data model classes used for local save (reflection-free, but kept for safety)
-keep class com.kidsdrive.adventure.data.** { *; }
