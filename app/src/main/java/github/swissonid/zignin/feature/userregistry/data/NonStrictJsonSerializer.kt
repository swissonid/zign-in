package github.swissonid.zignin.feature.userregistry.data

import kotlinx.serialization.json.Json

/**
 * A [Json] serializer that ignores unknown keys.
 * This makes it open for extension, in the future.
 */
private object NonStrictJsonSerializerObject {

    val ignoreUnknownKeysConfig = Json { ignoreUnknownKeys = true }
}

val NonStrictJsonSerializer = NonStrictJsonSerializerObject.ignoreUnknownKeysConfig


