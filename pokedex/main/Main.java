package pokedex.main;

import java.util.Scanner;

import pokedex.repository.FilePokemonRepository;
import pokedex.service.PokemonService;
import pokedex.ui.Menu;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int option = -1;
        String load = "...\n";
        FilePokemonRepository repo = new FilePokemonRepository("data/pokemons.txt");
        PokemonService serv = new PokemonService(repo);
        System.out.println("============= POKEDEX MODULAR =============");
        System.out.println("Desenvolvido por: Renato Ikeda Bressan");
        do {
            Menu.exibirMenu();
            option = sc.nextInt();
            switch (option) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    System.out.print("Retornando ao inicio");
                    Thread.sleep(750);
                    for (char c : load.toCharArray()) {
                        System.out.print(c);
                        Thread.sleep(150);
                    }
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    sc.nextLine();
                    System.out.print("Retornando ao menu do programa principal");
                    Thread.sleep(750);
                    for (char c : load.toCharArray()) {
                        System.out.print(c);
                        Thread.sleep(150);
                    }
            }
        } while (option != 0);
        sc.close();
    }
}
