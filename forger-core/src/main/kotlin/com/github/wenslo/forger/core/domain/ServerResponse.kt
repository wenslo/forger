package com.github.wenslo.forger.core.domain

/**
 * @author wenhailin
 * @date 2022/10/24 09:26
 */
data class ServerResponse<T>(
    var code: Int = 0,
    var message: String = "",
    var data: T? = null
) {
    companion object {
        private const val SUCCESS_CODE = 0
        private const val SUCCESS_MSG = "Succeed"
        private const val COMPLETE_SUCCESS_CODE = 1
        private const val COMPLETE_SUCCESS_MSG = "Complete"
        private const val ERROR_CODE = -1
        private const val ERROR_MSG = "Failed!"

        private const val SERVER_EXCEPTION_CODE = 500
        private const val SERVER_EXCEPTION_MSG = "Server Exception"

        fun <T> success(data: T?): ServerResponse<T> {
            return ServerResponse(
                SUCCESS_CODE,
                SUCCESS_MSG,
                data
            )
        }

        fun <T> complete(msg: String): ServerResponse<T> {
            return ServerResponse(
                COMPLETE_SUCCESS_CODE,
                msg
            )
        }

        fun <T> error(msg: String): ServerResponse<T> {
            return ServerResponse(
                ERROR_CODE,
                msg
            )
        }

        fun <T> serverError(msg: String): ServerResponse<T> {
            return ServerResponse(
                SERVER_EXCEPTION_CODE,
                msg
            )
        }

        fun <T> error(code: Int, msg: String): ServerResponse<T> {
            return ServerResponse(code, msg)
        }
    }
}