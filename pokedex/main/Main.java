package pokedex.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pokedex.exception.*;
import pokedex.model.*;
import pokedex.repository.FilePokemonRepository;
import pokedex.service.PokemonService;
import pokedex.ui.Menu;
import pokedex.util.InputUtils;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("============= POKEDEX MODULAR =============");
        System.out.println("Desenvolvido por: Renato Ikeda Bressan");
        Scanner sc = new Scanner(System.in);
        int option = -1;
        String load = "...\n", caminhoArquivo = "pokedex/data/pokemons.txt", optionArquivo;
        File arquivo = new File(caminhoArquivo);
        List<Pokemon> pokemons = new ArrayList<>();
        FilePokemonRepository repo = new FilePokemonRepository(caminhoArquivo);
        PokemonService serv = new PokemonService(repo);
        if (arquivo.exists() && arquivo.length() > 0) {
            optionArquivo = InputUtils.lerString("Deseja carregar os Pokemons de 'pokemons.txt'? (S/N): ", sc);
            while (!optionArquivo.toUpperCase().equalsIgnoreCase("s") && !optionArquivo.toUpperCase().equalsIgnoreCase("n")) {
                optionArquivo = InputUtils.lerString("Opcao invalida!\nDeseja carregar os Pokemons de 'pokemons.txt'? (S/N): ", sc);
            }
            if (optionArquivo.equalsIgnoreCase("s")) {
                try {
                    pokemons = repo.lerArquivo();
                    repo.inserirPokemons(pokemons);
                    System.out.println("Pokemons carregados com sucesso!");
                } catch (IOException e) {
                    System.out.println("Nao foi possivel ler o arquivo!");
                }
            }
        }
        do {
            Menu.exibirMenu();
            option = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            switch (option) {
                case 1:
                    sc.nextLine();
                    String nomePkmn = InputUtils.lerString("Insira o nome do Pokemon: ", sc);
                    String tipo1 = InputUtils.lerString("Insira o tipo principal: ", sc);
                    List<Tipo> tiposPkmn = new ArrayList<>();
                    boolean tipoValido = false;
                    try {
                        Tipo tipo1Pkmn = Tipo.fromString(tipo1);
                        tiposPkmn.add(tipo1Pkmn);
                        tipoValido = true;
                        String optionTipo2 = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                        while (!optionTipo2.toUpperCase().equalsIgnoreCase("s") && !optionTipo2.toUpperCase().equalsIgnoreCase("n")) {
                            optionTipo2 = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                        }
                        if (optionTipo2.equalsIgnoreCase("s")) {
                            String tipo2 = InputUtils.lerString("Insira o tipo secundario: ", sc);
                            try {
                                Tipo tipo2Pkmn = Tipo.fromString(tipo2);
                                tiposPkmn.add(tipo2Pkmn);
                            } catch (DadoInvalidoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } catch (DadoInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    int hp = InputUtils.lerInt("Insira o HP base do Pokemon: ", sc);
                    int atk = InputUtils.lerInt("Insira o ataque base do Pokemon: ", sc);
                    int def = InputUtils.lerInt("Insira a defesa base do Pokemon: ", sc);
                    int spAtk = InputUtils.lerInt("Insira o ataque especial base do Pokemon: ", sc);
                    int spDef = InputUtils.lerInt("Insira a defesa especial base do Pokemon: ", sc);
                    int speed = InputUtils.lerInt("Insira a velocidade base do Pokemon: ", sc);
                    try {
                        Stats statsPkmn = new Stats(hp, atk, def, spAtk, spDef, speed);
                        try {
                            if (tipoValido) {
                                Pokemon p = new Pokemon(nomePkmn, tiposPkmn, statsPkmn);
                                int id = serv.gerarNovoId();
                                p.setId(id);
                                serv.cadastrarPokemon(nomePkmn, tiposPkmn, statsPkmn);
                                pokemons.add(p);
                                System.out.println("Pokemon " + nomePkmn + " cadastrado com sucesso!");
                            }
                        } catch (DadoInvalidoException e) {
                            System.out.println(e.getMessage());
                        }
                    } catch (DadoInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    List<Pokemon> listaPkmn = serv.listarPokemons();
                    System.out.println("---------------------------------------------------");
                    for (Pokemon p : listaPkmn) {
                        System.out.println("Nome: " + p.getNome());
                        System.out.println("Numero de Pokedex: #" + String.format("%04d", p.getId()));
                        System.out.print("Tipos: ");
                        for (Tipo t : p.getTipos()) {
                            System.out.print(t + " ");
                        }
                        System.out.print("\n");
                        System.out.println("Stats: ");
                        System.out.println("HP: " + p.getBaseStats().getHp());
                        System.out.println("Ataque: " + p.getBaseStats().getAtaque());
                        System.out.println("Defesa: " + p.getBaseStats().getDefesa());
                        System.out.println("Ataque especial: " + p.getBaseStats().getAtaqueEspecial());
                        System.out.println("Defesa especial: " + p.getBaseStats().getDefesaEspecial());
                        System.out.println("Velocidade: " + p.getBaseStats().getVelocidade());
                        System.out.println("---------------------------------------------------");
                    }
                    System.out.println("Pokemons listados com sucesso!");
                    break;
                case 3:
                    sc.nextLine();
                    String nomeBusca = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    try {
                        Pokemon pkmn = serv.buscarPorNome(nomeBusca);
                        System.out.println("Pokemon " + nomeBusca + " encontrado com sucesso!");
                        System.out.println("---------------------------------------------------");
                        System.out.println("Nome: " + pkmn.getNome());
                        System.out.println("Numero de Pokedex: #" + String.format("%04d", pkmn.getId()));
                        System.out.print("Tipos: ");
                        for (Tipo t : pkmn.getTipos()) {
                            System.out.print(t + " ");
                        }
                        System.out.print("\n");
                        System.out.println("Stats: ");
                        System.out.println("HP: " + pkmn.getBaseStats().getHp());
                        System.out.println("Ataque: " + pkmn.getBaseStats().getAtaque());
                        System.out.println("Defesa: " + pkmn.getBaseStats().getDefesa());
                        System.out.println("Ataque especial: " + pkmn.getBaseStats().getAtaqueEspecial());
                        System.out.println("Defesa especial: " + pkmn.getBaseStats().getDefesaEspecial());
                        System.out.println("Velocidade: " + pkmn.getBaseStats().getVelocidade());
                        System.out.println("---------------------------------------------------");
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    sc.nextLine();
                    String nomeRemocao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    try {
                        serv.removerPokemon(nomeRemocao);
                        System.out.println("Pokemon " + nomeRemocao + " removido com sucesso!");
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    Pokemon p1 = null, p2 = null;
                    sc.nextLine();
                    String nomeP1 = InputUtils.lerString("Insira um nome para procura de um Pokemon: ", sc);
                    String nomeP2 = InputUtils.lerString("Insira um nome para procura do Pokemon oponente: ", sc);
                    try {
                        p1 = serv.buscarPorNome(nomeP1);
                        p2 = serv.buscarPorNome(nomeP2);
                        String nature1 = InputUtils.lerString("Insira a nature de " + p1.getNome() + ": ", sc);
                        int nivelP1 = InputUtils.lerInt("Insira o nivel de " + p1.getNome() + ": ", sc);
                        sc.nextLine();
                        String nature2 = InputUtils.lerString("Insira a nature de " + p2.getNome() + ": ", sc);
                        int nivelP2 = InputUtils.lerInt("Insira o nivel de " + p2.getNome() + ": ", sc);
                        try {
                            Nature natureP1 = Nature.fromString(nature1);
                            p1.setNature(natureP1);
                            p1.setNivel(nivelP1);
                            p1.setStats(p1.getBaseStats(), p1.getNature(), p1.getNivel());
                            Nature natureP2 = Nature.fromString(nature2);
                            p2.setNature(natureP2);
                            p2.setNivel(nivelP2);
                            p2.setStats(p2.getBaseStats(), p2.getNature(), p2.getNivel());
                            System.out.println("---------------------------------------------------");
                            System.out.println("Dados de " + p1.getNome() + ":");
                            System.out.println("Nature: " + p1.getNature());
                            System.out.println("Nivel: " + p1.getNivel());
                            System.out.println("HP: " + p1.getStats().getHp());
                            System.out.println("Ataque: " + p1.getStats().getAtaque());
                            System.out.println("Defesa: " + p1.getStats().getDefesa());
                            System.out.println("Ataque especial: " + p1.getStats().getAtaqueEspecial());
                            System.out.println("Defesa especial: " + p1.getStats().getDefesaEspecial());
                            System.out.println("Velocidade: " + p1.getStats().getVelocidade());
                            System.out.println("---------------------------------------------------");
                            System.out.println("Dados de " + p2.getNome() + ":");
                            System.out.println("Nature: " + p2.getNature());
                            System.out.println("Nivel: " + p2.getNivel());
                            System.out.println("HP: " + p2.getStats().getHp());
                            System.out.println("Ataque: " + p2.getStats().getAtaque());
                            System.out.println("Defesa: " + p2.getStats().getDefesa());
                            System.out.println("Ataque especial: " + p2.getStats().getAtaqueEspecial());
                            System.out.println("Defesa especial: " + p2.getStats().getDefesaEspecial());
                            System.out.println("Velocidade: " + p2.getStats().getVelocidade());
                            System.out.println("---------------------------------------------------");
                        } catch (DadoInvalidoException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 6:
                    if (arquivo.exists()) {
                        try {
                            repo.limparArquivo();
                            System.out.println("Arquivo limpo com sucesso!");
                        } catch (IOException e) {
                            System.out.println("Nao foi possivel limpar o arquivo!");
                        }
                    } else {
                        System.out.println("Arquivo inexistente!");
                    }
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
        sc.nextLine();
        optionArquivo = InputUtils.lerString("Deseja salvar os Pokemons em 'pokemons.txt'? (S/N): ", sc);
        while (!optionArquivo.toUpperCase().equalsIgnoreCase("s") && !optionArquivo.toUpperCase().equalsIgnoreCase("n")) {
            optionArquivo = InputUtils.lerString("Opcao invalida!\nDeseja salvar os Pokemons em 'pokemons.txt'? (S/N): ", sc);
        }
        if (optionArquivo.equalsIgnoreCase("s")) {
            try {
                repo.escreverArquivo(pokemons);
                System.out.println("Pokemons salvos com sucesso!");
            } catch (IOException e) {
                System.out.println("Nao foi possivel escrever no arquivo!");
            }
        }
        System.out.print("Encerrando o programa");
        Thread.sleep(750);
        for (char c : load.toCharArray()) {
            System.out.print(c);
            Thread.sleep(150);
        }
        sc.close();
    }
}
