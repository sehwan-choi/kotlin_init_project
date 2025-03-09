package security.me.com.kotlinProject.common.generator

import security.me.com.kotlinProject.common.generator.strategy.DefaultGenerateStrategy
import security.me.com.kotlinProject.common.generator.strategy.UUIDKeyGenerateStrategy
import java.util.*

class UUIDKeyGenerator(
    val upper: Boolean? = false,
    var strategy: UUIDKeyGenerateStrategy? = DefaultGenerateStrategy()
): KeyGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}