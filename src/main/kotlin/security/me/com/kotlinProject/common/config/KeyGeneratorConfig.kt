package security.me.com.kotlinProject.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import security.me.com.kotlinProject.common.generator.KeyGenerator
import security.me.com.kotlinProject.common.generator.UUIDKeyGenerator
import security.me.com.kotlinProject.common.generator.strategy.NoHyphenGenerateStrategy

@Configuration
class KeyGeneratorConfig {

    private val logKeyDefaultSize: Int = 10

    @Bean
    fun logKeyGenerator(): KeyGenerator {
        val strategy = NoHyphenGenerateStrategy()
        strategy.keySize = logKeyDefaultSize
        return UUIDKeyGenerator(strategy = strategy)
    }
}