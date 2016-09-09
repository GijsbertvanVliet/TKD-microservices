package nl.sogyo.tkd.service.catalog

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import nl.sogyo.tkd.errorhandlers.TKDClientErrorHandler
import nl.sogyo.tkd.errorhandlers.TKDServerErrorHandler
import nl.sogyo.tkd.serverOf
import ratpack.error.ClientErrorHandler
import ratpack.error.ServerErrorHandler
import ratpack.jackson.Jackson.json
import ratpack.rx.RxRatpack


data class ServiceDescription(val name: String) {

}

fun main(args: Array<String>) {
    RxRatpack.initialize() // must be called once per JVM b/c RxRatpack

    val repository: ProductRepository = MemoryProductRepository(ProductCatalogSeed.seed)

    val mapper = ObjectMapper().registerModule(KotlinModule())

    serverOf {
        serverConfig {
            port(8080)
        }
        handlers {
            register {
                it.add(ServerErrorHandler::class.java, TKDServerErrorHandler())
                it.add(ClientErrorHandler::class.java, TKDClientErrorHandler())
            }
            path("") {
                render("hallo")
            }
            path("description") { ctx ->
                val desc = ServiceDescription(
                        name = "Catalog Service"
                )
                ctx.render(json(desc))
            }
            prefix("product") {
                path("") {
                    byMethod { m ->
                        m.get {
                            repository.get()
                                    .toList()
                                    .map { json(it) }
                                    .subscribe({ context.render(it) })
                        }.post {
                            RxRatpack.observe(request.body)
                                    .map{ it.text }
                                    .map { mapper.readValue(it, Product::class.java) }
                                    .flatMap { repository.add(it) }
                                    .map { json(it) }
                                    .subscribe({ context.render(it) })
                        }
                    }
                }
                path(":identification") {
                    byMethod { m ->
                        m.get {
                            val id = pathTokens["identification"]
                            repository.get(id)
                                    .map { json(it) }
                                    .subscribe({ context.render(it) })
                        }.
                        delete {
                            val id = pathTokens["identification"]
                            if (id == null) {
                                context.response.status(400).send("A product ID is mandatory")
                            }
                            repository.delete(id!!)
                                    .map { json(it) }
                                    .subscribe({ context.render(it) })
                        }.
                        put {
                            RxRatpack.observe(request.body)
                                    .map{ it.text }
                                    .map { mapper.readValue(it, Product::class.java) }
                                    .flatMap { repository.update(it) }
                                    .map { json(it) }
                                    .subscribe({ context.render(it) })
                        }
                    }
                }
            }
            all {
                clientError(404)
            }
        }
    }.start()
}
