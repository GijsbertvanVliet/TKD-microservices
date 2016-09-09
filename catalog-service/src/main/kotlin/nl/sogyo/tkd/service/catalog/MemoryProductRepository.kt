package nl.sogyo.tkd.service.catalog

import nl.sogyo.tkd.exceptions.ConflictException
import nl.sogyo.tkd.exceptions.NotFoundException
import rx.Observable

class MemoryProductRepository(private val state: MutableList<Product> = mutableListOf()) : ProductRepository {

    private fun obs(): Observable<Product> = Observable.from(state)

    override fun get(id: String?): Observable<Product> {
        if (id == null) {
            return obs()
        } else {
            return obs().filter { id.equals(it.id) }.switchIfEmpty(Observable.error(NotFoundException("Can't find product with id $id")))
        }
    }

    override fun add(p: Product): Observable<Product> {
        if (state.find { p.id.equals(it.id) } != null) {
            return Observable.error(ConflictException("Resource ${p.id} already exists"))
        }
        state.add(p)
        return Observable.just(p)
    }

    override fun update(p: Product): Observable<Product> {
        val existing = state.find { p.id.equals(it.id) } ?: return Observable.error(NotFoundException("Can't find product with id ${p.id}"))
        state.remove(existing)
        state.add(p)
        return Observable.just(p)
    }

    override fun delete(id: String): Observable<Product> {
        val existing = state.find { id.equals(it.id) } ?: return Observable.error(NotFoundException("Can't find product with id $id"))
        state.remove(existing)
        return Observable.just(existing)
    }

}