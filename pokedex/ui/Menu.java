package pokedex.ui;

import pokedex.model.Pokemon;
import pokedex.model.Tipo;
import pokedex.util.OutputUtils;

public class Menu {
    public static void exibirMenuPrincipal(int delay) throws InterruptedException {
        OutputUtils.slowPrint("==============================", delay);
        OutputUtils.slowPrint("1. Cadastrar Pokemon", delay);
        OutputUtils.slowPrint("2. Listar Pokemons", delay);
        OutputUtils.slowPrint("3. Buscar Pokemon", delay);
        OutputUtils.slowPrint("4. Editar Pokemon", delay);
        OutputUtils.slowPrint("5. Remover Pokemon", delay);
        OutputUtils.slowPrint("6. Simular batalha", delay);
        OutputUtils.slowPrint("7. Estatisticas", delay);
        OutputUtils.slowPrint("8. Limpar os arquivos", delay);
        OutputUtils.slowPrint("0. Encerrar o programa", delay);
        OutputUtils.slowPrint("==============================", delay);
    }
    public static void exibirMenuPokemon(Pokemon pkmn, int delay) throws InterruptedException {
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println("Nome: " + pkmn.getNome());
        System.out.println("Numero de Pokedex: #" + String.format("%04d", pkmn.getId()));
        System.out.print("Tipo(s):");
        for (Tipo t : pkmn.getTipos()) System.out.print(" " + t);
        System.out.println("\nBase stats:");
        System.out.println("HP: " + pkmn.getBaseStats().getHp());
        System.out.println("Ataque: " + pkmn.getBaseStats().getAtaque());
        System.out.println("Defesa: " + pkmn.getBaseStats().getDefesa());
        System.out.println("Ataque especial: " + pkmn.getBaseStats().getAtaqueEspecial());
        System.out.println("Defesa especial: " + pkmn.getBaseStats().getDefesaEspecial());
        System.out.println("Velocidade: " + pkmn.getBaseStats().getVelocidade());
        System.out.println("BST: " + pkmn.calcularBST());
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
    }
    public static void exibirMenuEdicao(int delay) throws InterruptedException {
        OutputUtils.slowPrint("==============================", delay);
        OutputUtils.slowPrint("1. Editar nome", delay);
        OutputUtils.slowPrint("2. Editar tipo(s)", delay);
        OutputUtils.slowPrint("3. Editar stats", delay);
        OutputUtils.slowPrint("0. Encerrar operacao", delay);
        OutputUtils.slowPrint("==============================", delay);
    }
    public static void exibirMenuEstatisticas(int delay) throws InterruptedException {
        OutputUtils.slowPrint("==============================", delay);
        OutputUtils.slowPrint("1. Pokemon de maior stat", delay);
        OutputUtils.slowPrint("2. Pokemon de menor stat", delay);
        OutputUtils.slowPrint("0. Encerrar operacao", delay);
        OutputUtils.slowPrint("==============================", delay);
    }
}
