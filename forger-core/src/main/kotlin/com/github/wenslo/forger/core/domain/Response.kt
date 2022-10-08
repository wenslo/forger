package com.github.wenslo.forger.core.domain

import java.io.Serializable


data class Response(
    val code: Int,
    val msg: String,
    val data: Any? = null
) : Serializable {
    companion object {
        private const val SUCCESS_CODE = 0
        private const val SUCCESS_MSG = "Succeed"
        private const val COMPLETE_SUCCESS_CODE = 1
        private const val COMPLETE_SUCCESS_MSG = "Complete"
        private const val ERROR_CODE = -1
        private const val ERROR_MSG = "Failed!"
        private const val UNAUTHORIZED_CODE = 401
        private const val UNAUTHORIZED_MSG = "Unauthorized"
        private const val FORBIDDEN_CODE = 403
        private const val FORBIDDEN_MSG = "Forbidden"
        private const val SERVER_EXCEPTION_CODE = 500
        private const val SERVER_EXCEPTION_MSG = "Server Exception"


        private const val LOGIN_FAIL_MSG = "Username or password is error, try again later please"
        private const val LOGIN_SUCCESS_MSG = "Login success"
        private const val LOGOUT_SUCCESS_MSG = "Logout success"
        const val LOCKED_MSG = "User is locked"
        const val DISABLES_MSG = "User is disabled"
        val SUCCESS =
            Response(
                SUCCESS_CODE,
                SUCCESS_MSG
            )
        val COMPLETE =
            Response(
                COMPLETE_SUCCESS_CODE,
                COMPLETE_SUCCESS_MSG
            )
        val ERROR =
            Response(
                ERROR_CODE,
                ERROR_MSG
            )
        val UNAUTHORIZED =
            Response(
                UNAUTHORIZED_CODE,
                UNAUTHORIZED_MSG
            )
        val FORBIDDEN =
            Response(
                FORBIDDEN_CODE,
                FORBIDDEN_MSG
            )
        val LOGIN_FAIL =
            Response(
                ERROR_CODE,
                LOGIN_FAIL_MSG
            )
        val LOGIN_SUCCESS =
            Response(
                SUCCESS_CODE,
                LOGIN_SUCCESS_MSG
            )
        val LOGOUT_SUCCESS =
            Response(
                SUCCESS_CODE,
                LOGOUT_SUCCESS_MSG
            )
        val SERVER_ERROR =
            Response(
                SERVER_EXCEPTION_CODE,
                SERVER_EXCEPTION_MSG
            )

        fun success(data: Any?): Response {
            return Response(
                SUCCESS_CODE,
                SUCCESS_MSG,
                data
            )
        }

        fun complete(msg: String): Response {
            return Response(
                COMPLETE_SUCCESS_CODE,
                msg
            )
        }

        fun error(msg: String): Response {
            return Response(
                ERROR_CODE,
                msg
            )
        }

        fun serverError(msg: String): Response {
            return Response(
                SERVER_EXCEPTION_CODE,
                msg
            )
        }

        fun error(code: Int, msg: String): Response {
            return Response(code, msg)
        }
    }
}