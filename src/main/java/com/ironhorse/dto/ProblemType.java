package com.ironhorse.dto;

import lombok.Getter;

@Getter
public enum ProblemType {
    RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
    INVALID_DATA("/dados-invalidos", "Dados inválidos"),
    FORBIDDEN_ACCESS("/acesso-proibido", "Não há privilégios necessários para o acesso"),
    FILE_SIZE_EXCEEDED("/tamanho-excedido", "O tamanho do arquivo foi excedido"),
    DUPLICATED_DATA("/dados-duplicados", "Dados duplicados");


    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://ironhorse.com.br" + path;
        this.title = title;
    }
}
