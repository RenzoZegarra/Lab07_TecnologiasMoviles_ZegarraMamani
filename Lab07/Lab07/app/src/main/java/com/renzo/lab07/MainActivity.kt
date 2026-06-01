package com.renzo.lab07

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.example.persistenciaii.data.*
import com.example.persistenciaii.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var repository: ArticuloRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase
            .getInstance(this)
            .articuloDao()

        repository = ArticuloRepository(dao)

        binding.btnRegistrar.setOnClickListener {
            registrar()
        }

        binding.btnBuscar.setOnClickListener {
            buscar()
        }

        binding.btnModificar.setOnClickListener {
            modificar()
        }

        binding.btnEliminar.setOnClickListener {
            eliminar()
        }

        binding.btnGuardarArchivo.setOnClickListener {
            guardarArchivoExterno()
        }

        binding.btnLeerArchivo.setOnClickListener {
            leerArchivoExterno()
        }

        observarArticulos()
    }

    private fun registrar() {

        lifecycleScope.launch {

            repository.insertar(
                Articulo(
                    binding.txtCodigo.text.toString().toInt(),
                    binding.txtDescripcion.text.toString(),
                    binding.txtPrecio.text.toString().toDouble()
                )
            )

            toast("Registrado")
        }
    }

    private fun buscar() {

        lifecycleScope.launch {

            val articulo =
                repository.buscar(
                    binding.txtCodigo.text.toString().toInt()
                )

            if (articulo != null) {

                binding.txtDescripcion.setText(
                    articulo.descripcion
                )

                binding.txtPrecio.setText(
                    articulo.precio.toString()
                )
            }
        }
    }

    private fun modificar() {

        lifecycleScope.launch {

            repository.actualizar(
                Articulo(
                    binding.txtCodigo.text.toString().toInt(),
                    binding.txtDescripcion.text.toString(),
                    binding.txtPrecio.text.toString().toDouble()
                )
            )

            toast("Modificado")
        }
    }

    private fun eliminar() {

        lifecycleScope.launch {

            repository.eliminar(
                binding.txtCodigo.text.toString().toInt()
            )

            toast("Eliminado")
        }
    }

    private fun guardarArchivoExterno() {

        val archivo = File(
            getExternalFilesDir(null),
            "datos.txt"
        )

        archivo.writeText(
            binding.txtDescripcion.text.toString()
        )

        toast("Archivo guardado")
    }

    private fun leerArchivoExterno() {

        val archivo = File(
            getExternalFilesDir(null),
            "datos.txt"
        )

        if (archivo.exists()) {

            binding.txtDescripcion.setText(
                archivo.readText()
            )
        }
    }

    private fun observarArticulos() {

        lifecycleScope.launch {

            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                repository
                    .listarTodos()
                    .collect { lista ->

                        val texto = buildString {

                            lista.forEach {

                                append(
                                    "${it.codigo} - ${it.descripcion} - S/${it.precio}\n"
                                )
                            }
                        }

                        binding.txtLista.text = texto
                    }
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}