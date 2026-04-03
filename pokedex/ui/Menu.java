package pokedex.ui;

import pokedex.util.PrintUtils;

public class Menu {
    public static void exibirMenu() throws InterruptedException {
        PrintUtils.slowPrint("==================================", 50);
        PrintUtils.slowPrint("1. Cadastrar Pokemon", 50);
        PrintUtils.slowPrint("2. Listar Pokemons", 50);
        PrintUtils.slowPrint("3. Buscar Pokemon", 50);
        PrintUtils.slowPrint("4. Editar Pokemon", 50);
        PrintUtils.slowPrint("5. Remover Pokemon", 50);
        PrintUtils.slowPrint("6. Simular batalha", 50);
        PrintUtils.slowPrint("7. Limpar o arquivo", 50);
        PrintUtils.slowPrint("0. Sair do programa", 50);
        PrintUtils.slowPrint("==================================", 50);
    }
}
