package nl.sogyo.tkd.errorhandlers
import nl.sogyo.tkd.exceptions.ApiException
import org.slf4j.LoggerFactory
import ratpack.error.ServerErrorHandler
import ratpack.handling.Context

class TKDServerErrorHandler : ServerErrorHandler {
    companion object {
        private val log = LoggerFactory.getLogger(TKDServerErrorHandler::class.java)
    }

    override fun error(context: Context, throwable: Throwable) {
        when (throwable) {
            is ApiException -> {
                log.error("{}: {} -- {}", throwable.statusCode, context.request.path, throwable.message)
                context.response.status(throwable.statusCode)
                context.render(throwable.message)
            }
            else -> {
                log.error("An unknown error occurred at {}: ", context.request.path, throwable)
                context.response.status(500)
                context.render(throwable.message)
            }
        }
    }
}