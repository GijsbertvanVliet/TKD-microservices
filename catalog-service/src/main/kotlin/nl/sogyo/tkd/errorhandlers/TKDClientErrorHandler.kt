package nl.sogyo.tkd.errorhandlers

import org.slf4j.LoggerFactory
import ratpack.error.ClientErrorHandler
import ratpack.handling.Context

class TKDClientErrorHandler : ClientErrorHandler {
    companion object {
        private val log = LoggerFactory.getLogger(TKDClientErrorHandler::class.java)
    }

    override fun error(ctx: Context, statusCode: Int) {
        ctx.response.status(statusCode)
        if (statusCode == 404) {
            ctx.render("404 not found")
        } else {
            log.error("Unexpected: {}", statusCode)
        }
    }

}