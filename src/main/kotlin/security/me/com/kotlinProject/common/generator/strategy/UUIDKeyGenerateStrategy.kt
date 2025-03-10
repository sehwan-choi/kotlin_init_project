package security.me.com.kotlinProject.common.generator.strategy

interface UUIDKeyGenerateStrategy {
    companion object {
        const val defaultMaxKeySizePolicy: Int = 36
        const val defaultMinKeySizePolicy: Int = 10
        const val uuidHyphenSize: Int = 4
    }

    var keySize: Int

    fun checkKeySizePolicy(keySize: Int)

    fun generateUUID(): String
}