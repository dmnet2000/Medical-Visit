#!/bin/bash

echo "🔑 Generazione chiavi RSA per JWT..."

# Crea directory se non esiste
mkdir -p medical-visit-service/src/main/resources/META-INF/resources/

# Genera chiave privata (2048 bit)
openssl genrsa -out privateKey.pem 2048
echo "✅ Chiave privata generata:  privateKey.pem"

# Genera chiave pubblica
openssl rsa -in privateKey.pem -pubout -out publicKey.pem
echo "✅ Chiave pubblica generata: publicKey.pem"

# Sposta le chiavi nella cartella resources
mv privateKey.pem medical-visit-service/src/main/resources/META-INF/resources/
mv publicKey.pem medical-visit-service/src/main/resources/META-INF/resources/

echo "✅ Chiavi spostate in medical-visit-service/src/main/resources/META-INF/resources/"
echo "🎉 Completato!"