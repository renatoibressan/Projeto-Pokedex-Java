# Projeto Pokedex Java

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

Uma aplicação de console em Java para gerenciar uma Pokédex pessoal, permitindo cadastrar, listar, buscar, editar e remover Pokémons, além de simular batalhas baseadas em tipos e efetividades.

## Funcionalidades Principais

- **Cadastrar Pokémon**: Adicione novos Pokémons com nome, tipos (primário e secundário opcional) e estatísticas base (HP, Ataque, Defesa, Ataque Especial, Defesa Especial, Velocidade).
- **Listar Pokémons**: Visualize todos os Pokémons cadastrados com seus detalhes.
- **Buscar Pokémon**: Encontre um Pokémon específico pelo nome.
- **Editar Pokémon**: Modifique as informações de um Pokémon existente.
- **Remover Pokémon**: Exclua um Pokémon da Pokédex.
- **Simular Batalha**: Realize batalhas entre dois Pokémons, considerando efetividades de tipos.
- **Carregar/Salvar Dados**: Carregue Pokémons de um arquivo de texto e salve alterações automaticamente.

## Pré-requisitos

- **Java 11 ou superior** instalado no sistema.
- Sistema operacional compatível: Windows, macOS ou Linux.

## Instalação

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/Projeto-Pokedex-Java.git
   cd Projeto-Pokedex-Java
   ```

2. **Compile o projeto** (se necessário):
   - O projeto já inclui um arquivo `Pokedex.jar` pré-compilado. Se preferir compilar manualmente:
     ```bash
     javac -d out -cp . pokedex/**/*.java
     jar cfe Pokedex.jar pokedex.main.Main -C out .
     ```

## Como Rodar a Aplicação

Execute o JAR diretamente:
```bash
java -jar Pokedex.jar
```

A aplicação iniciará no modo console, exibindo um menu interativo.

## Exemplo de Uso

Após executar, o menu principal será exibido:

```
==================================
1. Cadastrar Pokemon
2. Listar Pokemons
3. Buscar Pokemon
4. Editar Pokemon
5. Remover Pokemon
6. Simular batalha
7. Limpar o arquivo
0. Sair do programa
==================================
```

- Para cadastrar um Pokémon, selecione a opção 1 e siga as instruções para inserir nome, tipos e estatísticas.
- Para simular uma batalha, selecione a opção 6 e escolha dois Pokémons da lista.

Exemplo de saída ao listar Pokémons:
```
Nome: Pikachu
Numero de Pokedex: #0025
Tipos: ELETRICO
HP: 35, Ataque: 55, Defesa: 40, Ataque Especial: 50, Defesa Especial: 50, Velocidade: 90
```

## Tecnologias Utilizadas

- **Java**: Linguagem principal para desenvolvimento.
- **Arquitetura Modular**: Separação em camadas (model, service, repository, ui, util).
- **Manipulação de Arquivos**: Leitura e escrita de dados em arquivos de texto para persistência.

## Como Contribuir

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`).
3. Commit suas mudanças (`git commit -am 'Adiciona nova funcionalidade'`).
4. Push para a branch (`git push origin feature/nova-funcionalidade`).
5. Abra um Pull Request.

Sinta-se à vontade para reportar bugs ou sugerir melhorias via Issues.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.