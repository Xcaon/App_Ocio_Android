package com.example.vivemurcia.model.clases

data class Categoria(
    var idCategoria: String? = "9999",
    var nombre: String? = "Indefinido",
    var iconoUri: String? = "vacio"
){
    fun toDomain(): Categoria {
        return Categoria(
            idCategoria = idCategoria,
            nombre = nombre,
            iconoUri = iconoUri
        )
    }

}