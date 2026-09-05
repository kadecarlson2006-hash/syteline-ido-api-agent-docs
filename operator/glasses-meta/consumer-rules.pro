# The app looks this factory up by name (reflection) so :app has no compile-time dependency on the SDK.
-keep class com.operator.glasses.meta.MetaGlassesProviderFactory { *; }
