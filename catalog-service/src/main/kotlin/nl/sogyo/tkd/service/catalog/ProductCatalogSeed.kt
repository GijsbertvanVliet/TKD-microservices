package nl.sogyo.tkd.service.catalog

import java.util.*

object ProductCatalogSeed {
    private fun uuid(): String = UUID.randomUUID().toString()

    val seed: MutableList<Product> = mutableListOf(
            Product(uuid(), "Appel", "Een appel"),
            Product(uuid(), "Peer", "Een peer"),
            Product(uuid(), "Banaan", "Een banaan")
    )
}
