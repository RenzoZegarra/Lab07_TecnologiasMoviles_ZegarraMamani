package com.renzo.lab07.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticuloDao {

    @Insert
    suspend fun insertar(articulo: Articulo)

    @Update
    suspend fun actualizar(articulo: Articulo)

    @Query("DELETE FROM articulos WHERE codigo = :codigo")
    suspend fun eliminar(codigo: Int)

    @Query("SELECT * FROM articulos WHERE codigo = :codigo")
    suspend fun buscar(codigo: Int): Articulo?

    @Query("SELECT * FROM articulos ORDER BY codigo")
    fun listarTodos(): Flow<List<Articulo>>
}