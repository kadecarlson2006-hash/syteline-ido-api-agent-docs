package com.operator.app.glasses

import android.content.Context
import android.util.Log
import com.operator.app.BuildConfig
import com.operator.core.glasses.GlassesProvider
import com.operator.core.glasses.NoGlassesProvider
import kotlinx.coroutines.CoroutineScope

/**
 * Picks the glasses provider at runtime. `:glasses-meta` is an optional module (it needs a GitHub
 * Packages token to build), so it is reached through its factory class by name rather than a
 * compile-time reference. Falls back to [NoGlassesProvider], which reports itself honestly.
 */
object GlassesProviderLoader {
    private const val FACTORY = "com.operator.glasses.meta.MetaGlassesProviderFactory"

    fun load(context: Context, scope: CoroutineScope): GlassesProvider {
        if (!BuildConfig.META_SDK_ENABLED) {
            return NoGlassesProvider("Built without the Meta Wearables toolkit (no GitHub Packages token at build time)")
        }
        return try {
            val factory = Class.forName(FACTORY)
            val create = factory.getDeclaredMethod("create", Context::class.java, CoroutineScope::class.java, Boolean::class.javaPrimitiveType)
            create.invoke(null, context, scope, BuildConfig.MWDAT_DEVELOPER_MODE) as GlassesProvider
        } catch (e: ReflectiveOperationException) {
            Log.e(TAG, "Meta glasses provider missing although META_SDK_ENABLED=true", e)
            NoGlassesProvider("Meta provider class missing: ${e.message}")
        }
    }

    private const val TAG = "GlassesProviderLoader"
}
