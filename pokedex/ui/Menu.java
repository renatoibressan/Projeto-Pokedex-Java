package pokedex.ui;

import pokedex.util.PrintUtils;

public class Menu {
    public static void exibirMenu() throws InterruptedException {
        PrintUtils.slowPrint("==================================", 125);
        PrintUtils.slowPrint("1. Cadastrar Pokemon", 250);
        PrintUtils.slowPrint("2. Listar Pokemons", 250);
        PrintUtils.slowPrint("3. Buscar Pokemon", 250);
        PrintUtils.slowPrint("4. Editar Pokemon", 250);
        PrintUtils.slowPrint("5. Remover Pokemon", 250);
        PrintUtils.slowPrint("6. Simular batalha", 250);
        PrintUtils.slowPrint("7. Limpar o arquivo", 250);
        PrintUtils.slowPrint("0. Sair do programa", 250);
        PrintUtils.slowPrint("==================================", 125);
    }
}
