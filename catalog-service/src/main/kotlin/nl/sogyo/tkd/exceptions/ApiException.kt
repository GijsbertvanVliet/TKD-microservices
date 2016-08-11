package nl.sogyo.tkd.exceptions

abstract class ApiException : Exception {
    constructor(message: String) : super(message) {
    }

    constructor(message: String, throwable: Throwable) : super(message, throwable) {
    }

    abstract val statusCode: Int
}
