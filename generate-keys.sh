#!/bin/bash

# Definir o diretório onde as chaves serão armazenadas
RESOURCE_DIR="./src/main/resources"

# Verificar se as chaves já existem
if [ ! -f "$RESOURCE_DIR/app.key" ] || [ ! -f "$RESOURCE_DIR/app.pub" ]; then
    echo "Gerando novo par de chaves RSA..."

    # Criação da pasta resources caso não exista
    mkdir -p $RESOURCE_DIR

    # Gerar chave privada (app.key)
    openssl genrsa > app.key

    # Gerar chave pública (app.pub)
    openssl rsa -in $RESOURCE_DIR/app.key -pubout -out $RESOURCE_DIR/app.pub

    echo "Chaves RSA geradas com sucesso!"
else
    echo "Chaves RSA já existem, não foi necessário gerar novas."
fi
