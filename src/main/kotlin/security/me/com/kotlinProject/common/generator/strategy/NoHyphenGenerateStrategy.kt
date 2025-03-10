package security.me.com.kotlinProject.common.generator.strategy

import security.me.com.kotlinProject.common.generator.strategy.UUIDKeyGenerateStrategy.Companion.defaultMaxKeySizePolicy
import security.me.com.kotlinProject.common.generator.strategy.UUIDKeyGenerateStrategy.Companion.defaultMinKeySizePolicy
import security.me.com.kotlinProject.common.generator.strategy.UUIDKeyGenerateStrategy.Companion.uuidHyphenSize
import java.util.*

class NoHyphenGenerateStrategy(
): AbstractGenerateStrategy(noPyphenDefaultMaxKeySizePlicy, defaultMinKeySizePolicy, noPyphenDefaultMaxKeySizePlicy) {

    companion object {
        private const val noPyphenDefaultMaxKeySizePlicy = defaultMaxKeySizePolicy - uuidHyphenSize
    }

    override fun createUUID(): String {
        return UUID.randomUUID().toString().replace("-", "");
    }
}