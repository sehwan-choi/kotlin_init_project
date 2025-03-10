package security.me.com.kotlinProject.common.generator

import security.me.com.kotlinProject.common.generator.strategy.DefaultGenerateStrategy
import security.me.com.kotlinProject.common.generator.strategy.UUIDKeyGenerateStrategy

class UUIDKeyGenerator(
    private val upper: Boolean = false,
    private var strategy: UUIDKeyGenerateStrategy = DefaultGenerateStrategy()
): KeyGenerator {
    override fun generate(): String {
        return if (upper) {
            strategy.generateUUID().uppercase()
        } else {
            strategy.generateUUID()
        }
    }
}