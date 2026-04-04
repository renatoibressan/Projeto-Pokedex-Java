package pokedex.ui;

import pokedex.model.Pokemon;
import pokedex.model.Tipo;
import pokedex.util.PrintUtils;

public class Menu {
    public static void exibirMenuPrincipal(int delay) throws InterruptedException {
        PrintUtils.slowPrint("==============================", delay);
        PrintUtils.slowPrint("(MAIN FUNCTIONALITIES)", delay);
        PrintUtils.slowPrint("1. Cadastrar Pokemon", delay);
        PrintUtils.slowPrint("2. Listar Pokemons", delay);
        PrintUtils.slowPrint("3. Buscar Pokemon", delay);
        PrintUtils.slowPrint("4. Editar Pokemon", delay);
        PrintUtils.slowPrint("5. Remover Pokemon", delay);
        PrintUtils.slowPrint("6. Simular batalha", delay);
        PrintUtils.slowPrint("(GENERAL DATA)", delay);
        PrintUtils.slowPrint("7. Estatisticas", delay);
        PrintUtils.slowPrint("(DANGER ZONE)", delay);
        PrintUtils.slowPrint("8. Limpar o arquivo", delay);
        PrintUtils.slowPrint("(PROGRAM FLOW)", delay);
        PrintUtils.slowPrint("0. Sair do programa", delay);
        PrintUtils.slowPrint("==============================", delay);
    }
    public static void exibirMenuPokemon(Pokemon pkmn, int delay) throws InterruptedException {
        PrintUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println("Nome: " + pkmn.getNome());
        System.out.println("Numero de Pokedex: #" + String.format("%04d", pkmn.getId()));
        System.out.print("Tipo(s):");
        for (Tipo t : pkmn.getTipos()) {
            System.out.print(" " + t);
        }
        System.out.println("\nBase stats:");
        System.out.println("HP: " + pkmn.getBaseStats().getHp());
        System.out.println("Ataque: " + pkmn.getBaseStats().getAtaque());
        System.out.println("Defesa: " + pkmn.getBaseStats().getDefesa());
        System.out.println("Ataque especial: " + pkmn.getBaseStats().getAtaqueEspecial());
        System.out.println("Defesa especial: " + pkmn.getBaseStats().getDefesaEspecial());
        System.out.println("Velocidade: " + pkmn.getBaseStats().getVelocidade());
        System.out.println("BST: " + pkmn.calcularBST());
        PrintUtils.slowPrint("---------------------------------------------------------", delay);
    }
    public static void exibirMenuEdicao(int delay) throws InterruptedException {
        PrintUtils.slowPrint("==============================", delay);
        PrintUtils.slowPrint("1. Editar nome", delay);
        PrintUtils.slowPrint("2. Editar tipo(s)", delay);
        PrintUtils.slowPrint("3. Editar stats", delay);
        PrintUtils.slowPrint("0. Encerrar operacao", delay);
        PrintUtils.slowPrint("==============================", delay);
    }
    public static void exibirMenuEstatisticas(int delay) throws InterruptedException {
        PrintUtils.slowPrint("==============================", delay);
        PrintUtils.slowPrint("1. Pokemon de maior stat", delay);
        PrintUtils.slowPrint("2. Pokemon de menor stat", delay);
        PrintUtils.slowPrint("0. Encerrar operacao", delay);
        PrintUtils.slowPrint("==============================", delay);
    }
}
