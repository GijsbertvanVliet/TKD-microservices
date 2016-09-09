package nl.sogyo.tkd.exceptions

class InternalServerError : ApiException {
    override val statusCode = 500

    constructor(message: String) : super(message) {
    }

    constructor(message: String, throwable: Throwable) : super(message, throwable) {
    }
}
