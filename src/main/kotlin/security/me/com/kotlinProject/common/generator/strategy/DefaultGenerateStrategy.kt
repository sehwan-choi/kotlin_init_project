package security.me.com.kotlinProject.common.generator.strategy

import security.me.com.kotlinProject.common.generator.strategy.UUIDKeyGenerateStrategy.Companion.defaultMaxKeySizePolicy
import security.me.com.kotlinProject.common.generator.strategy.UUIDKeyGenerateStrategy.Companion.defaultMinKeySizePolicy
import java.util.*

/**
 * UUID 기반 키 생성 정책
 * 하이픈('-')을 포함한 총 길이36를 기본 정책으로 한다.
 */
class DefaultGenerateStrategy: AbstractGenerateStrategy(defaultMaxKeySizePolicy, defaultMinKeySizePolicy, defaultMaxKeySizePolicy) {

    override fun createUUID(): String {
        return UUID.randomUUID().toString()
    }
}