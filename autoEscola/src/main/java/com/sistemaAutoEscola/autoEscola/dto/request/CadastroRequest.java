package com.sistemaAutoEscola.autoescola.dto.request; // ⬅️ Este é o pacote que você deve usar
// Ele mapeia o JSON que vem do frontend.
public record CadastroRequest(
    

    String username,
    

    String password,
    

    Integer tipoUsuario
    
) {
    // Nota: Em Java 16+, records são DTOs imutáveis e já geram construtor, getters e equals.
}