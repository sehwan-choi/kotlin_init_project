package security.me.com.kotlinProject.common.generator.strategy

import security.me.com.kotlinProject.common.generator.exeption.KeyGenerationPolicyViolationException

abstract class AbstractGenerateStrategy(
    open val maxKeySize: Int,
    open val minKeySize: Int,
    keySize: Int,
): UUIDKeyGenerateStrategy {

    override var keySize: Int = keySize
        set(size) {
            checkKeySizePolicy(size)
            field = size
        }

    override fun checkKeySizePolicy(keySize: Int) {
        if (keySize < this.minKeySize || keySize > this.maxKeySize) {
            throw KeyGenerationPolicyViolationException("Larger or smaller than the default key size [setKeySize : ${keySize}] [minKeySize : ${minKeySize}] [maxKeySize : ${maxKeySize}]");
        }
    }

    override fun generateUUID(): String {
        val uuid = createUUID()

        return uuid.substring(0, keySize)
    }

    abstract fun createUUID(): String
}