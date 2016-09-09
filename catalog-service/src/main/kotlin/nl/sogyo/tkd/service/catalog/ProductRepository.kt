package nl.sogyo.tkd.service.catalog

import rx.Observable

interface ProductRepository {
    /**
     * Retrieving a product, errorring if it doesn't exist.
     */
    fun get(id: String? = null): Observable<Product>
    /**
     * Add a product, errorring if it already exists
     */
    fun add(p: Product): Observable<Product>

    /**
     * Update an existing product, errorring if it doesn't exist.
     */
    fun update(p: Product): Observable<Product>

    /**
     * Delete a product, errorring if it doesn't exist
     */
    fun delete(id: String): Observable<Product>
}
