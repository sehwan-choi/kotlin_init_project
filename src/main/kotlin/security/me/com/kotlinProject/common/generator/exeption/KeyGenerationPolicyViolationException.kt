package security.me.com.kotlinProject.common.generator.exeption

import security.me.com.kotlinProject.common.code.ServerCode
import security.me.com.kotlinProject.common.exception.InvalidDataException

class KeyGenerationPolicyViolationException(
    override val message: String
): InvalidDataException(ServerCode.KEY_GENERATION_FAIL, message)