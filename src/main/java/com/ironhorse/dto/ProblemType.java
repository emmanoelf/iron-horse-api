package com.ironhorse.dto;

import lombok.Getter;

@Getter
public enum ProblemType {
    ENTITY_NOT_FOUND("/entidade-nao-encontrada", "Entidade não encontrada"),
    INVALID_DATA("/dados-invalidos", "Dados inválidos"),
    FORBIDDEN_ACCESS("/acesso-proibido", "Não há privilégios necessários para o acesso");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://ironhorse.com.br" + path;
        this.title = title;
    }
}
