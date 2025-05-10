package com.example.agrocontrolv1.presentation.product_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrocontrolv1.common.Resource
import com.example.agrocontrolv1.common.UIState
import com.example.agrocontrolv1.data.repository.ProductRepository
import com.example.agrocontrolv1.domain.model.Product
import kotlinx.coroutines.launch

class ProductListViewModel (private val repository: ProductRepository) : ViewModel() {

    private val _productIdInput = mutableStateOf("")
    val productIdInput: State<String> get() = _productIdInput

    private val _state = mutableStateOf(UIState<List<Product>>())
    val state: State<UIState<List<Product>>> get() = _state


    fun onProductIdInputChanged(id: String) {
        _productIdInput.value = id
    }

    fun searchProductById() {
        // 1. Ponemos el estado a cargando
        _state.value = UIState(isLoading = true)

        viewModelScope.launch {
            // 2. Llamamos al Repository (en segundo plano gracias a withContext(Dispatchers.IO) en el Repository)
            val result = repository.searchProduct(productIdInput.value)

            // 3. Procesamos el resultado una vez que el Repository nos responde
            when(result) {
                is Resource.Success -> {
                    // Si fue exitoso: actualizamos los datos Y ponemos isLoading a false
                    _state.value = UIState(data = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    // Si hubo un error: actualizamos el mensaje Y ponemos isLoading a false
                    _state.value = UIState(message = result.message ?: "Ocurrió un error desconocido.", isLoading = false)
                }
                // Si tu Repository no emite Resource.Loading, este caso no es necesario aquí,
                // pero es buena práctica si el Repository tuviera pasos intermedios de carga.
                // is Resource.Loading -> { /* No hacemos nada especial aquí ya que isLoading ya es true */ }
            }
        }
    }
}