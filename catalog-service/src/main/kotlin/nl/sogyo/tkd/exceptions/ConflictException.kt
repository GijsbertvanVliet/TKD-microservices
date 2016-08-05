package nl.sogyo.tkd.exceptions

class ConflictException : ApiException {
    override val statusCode = 409

    constructor(): super("Conflict")

    constructor(message: String) : super(message) {
    }

    constructor(message: String, throwable: Throwable) : super(message, throwable) {
    }
}
