package security.me.com.kotlinProject.common.generator.strategy

import java.util.*

class DefaultGenerateStrategy: UUIDKeyGenerateStrategy {
    override fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }
}