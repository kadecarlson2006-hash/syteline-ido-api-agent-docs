package com.operator.glasses.meta

import android.content.Context
import com.operator.core.glasses.GlassesProvider
import kotlinx.coroutines.CoroutineScope

/**
 * Entry point looked up reflectively by `:app` (see app/glasses/GlassesProviderLoader.kt), so the
 * app never has a compile-time reference to this module or the SDK. Keep the signature stable.
 */
object MetaGlassesProviderFactory {
    @JvmStatic
    fun create(context: Context, scope: CoroutineScope, developerModeBuild: Boolean): GlassesProvider =
        MetaGlassesManager(context.applicationContext, scope, developerModeBuild)
}
