package com.renzo.lab07.data

class ArticuloRepository(
    private val dao: ArticuloDao
) {

    suspend fun insertar(articulo: Articulo) =
        dao.insertar(articulo)

    suspend fun actualizar(articulo: Articulo) =
        dao.actualizar(articulo)

    suspend fun eliminar(codigo: Int) =
        dao.eliminar(codigo)

    suspend fun buscar(codigo: Int) =
        dao.buscar(codigo)

    fun listarTodos() =
        dao.listarTodos()
}