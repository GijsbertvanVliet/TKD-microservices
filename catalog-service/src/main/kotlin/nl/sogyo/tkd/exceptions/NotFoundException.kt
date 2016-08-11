package nl.sogyo.tkd.exceptions

class NotFoundException : ApiException {
    override val statusCode = 404

    constructor(): super("Resource not found.")

    constructor(message: String) : super(message) {
    }

    constructor(message: String, throwable: Throwable) : super(message, throwable) {
    }
}
