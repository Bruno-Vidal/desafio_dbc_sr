# Sistema de Atualização de contas

Para rodar o projeto garanta as dependencias de Sistema.

* Java 11
* Maven

Na raiz do projeto executar os comandos :
```bash
mvn clean
mvn package
```

Isso irá realizar os testes e build da aplicação. <br>
Ao final do processo será gerado um *.JAR* dentro da pasta *target*

Para executar o projeto:

```bash
java -jar  target/receita-0.0.1-SNAPSHOT.jar <input-file>

ex: java -jar  target/receita-0.0.1-SNAPSHOT.jar C:\sicred\receita_21_04_2022.csv
```

