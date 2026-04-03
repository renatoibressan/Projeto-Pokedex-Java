package pokedex.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pokedex.exception.*;
import pokedex.model.*;
import pokedex.repository.FilePokemonRepository;
import pokedex.service.*;
import pokedex.ui.Menu;
import pokedex.util.InputUtils;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("============= POKEDEX MODULAR =============");
        System.out.println("Desenvolvido por: Renato Ikeda Bressan");
        Scanner sc = new Scanner(System.in);
        int option = -1;
        String load = "...\n", optionArquivo;
        List<Pokemon> pokemons = new ArrayList<>();
        File arquivoPkmn = new File("pokedex/data/pokemons.txt");
        FilePokemonRepository repo = new FilePokemonRepository("pokedex/data/pokemons.txt");
        PokemonService serv = new PokemonService(repo);
        TypeEffectivenessService efct = new TypeEffectivenessService("pokedex/data/matchups.txt");
        BatalhaService battle = new BatalhaService(efct);
        try {
            efct.extrairDeArquivo();
        } catch (IOException e) {
            System.out.println("Nao foi possivel carregar o arquivo!");
        }
        if (arquivoPkmn.exists() && arquivoPkmn.length() > 0) {
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
                    try {
                        Tipo tipo1Pkmn = Tipo.fromString(tipo1);
                        tiposPkmn.add(tipo1Pkmn);
                        String optionTipo2 = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                        while (!optionTipo2.toUpperCase().equalsIgnoreCase("s") && !optionTipo2.toUpperCase().equalsIgnoreCase("n")) {
                            optionTipo2 = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                        }
                        if (optionTipo2.equalsIgnoreCase("s")) {
                            String tipo2 = InputUtils.lerString("Insira o tipo secundario: ", sc);
                            Tipo tipo2Pkmn = Tipo.fromString(tipo2);
                            tiposPkmn.add(tipo2Pkmn);
                        }
                        int hp = InputUtils.lerInt("Insira o HP base do Pokemon: ", sc);
                        int atk = InputUtils.lerInt("Insira o ataque base do Pokemon: ", sc);
                        int def = InputUtils.lerInt("Insira a defesa base do Pokemon: ", sc);
                        int spAtk = InputUtils.lerInt("Insira o ataque especial base do Pokemon: ", sc);
                        int spDef = InputUtils.lerInt("Insira a defesa especial base do Pokemon: ", sc);
                        int speed = InputUtils.lerInt("Insira a velocidade base do Pokemon: ", sc);
                        Stats statsPkmn = new Stats(hp, atk, def, spAtk, spDef, speed);
                        Pokemon p = new Pokemon(nomePkmn, tiposPkmn, statsPkmn);
                        int id = serv.gerarNovoId();
                        p.setId(id);
                        serv.cadastrarPokemon(nomePkmn, tiposPkmn, statsPkmn);
                        pokemons.add(p);
                        System.out.println("Pokemon " + p.getNome() + " cadastrado com sucesso!");
                    } catch (DadoInvalidoException e) {
                        System.out.println("Nao foi possivel cadastrar o Pokemon: " + e.getMessage());
                    }
                    break;
                case 2:
                    List<Pokemon> listaPkmn = serv.listarPokemons();
                    System.out.println("---------------------------------------------------");
                    for (Pokemon pkmn : listaPkmn) {
                        System.out.println("Nome: " + pkmn.getNome());
                        System.out.println("Numero de Pokedex: #" + String.format("%04d", pkmn.getId()));
                        System.out.print("Tipos:");
                        for (Tipo t : pkmn.getTipos()) {
                            System.out.print(" " + t);
                        }
                        System.out.print("\n");
                        System.out.println("Stats:");
                        System.out.println("HP: " + pkmn.getBaseStats().getHp());
                        System.out.println("Ataque: " + pkmn.getBaseStats().getAtaque());
                        System.out.println("Defesa: " + pkmn.getBaseStats().getDefesa());
                        System.out.println("Ataque especial: " + pkmn.getBaseStats().getAtaqueEspecial());
                        System.out.println("Defesa especial: " + pkmn.getBaseStats().getDefesaEspecial());
                        System.out.println("Velocidade: " + pkmn.getBaseStats().getVelocidade());
                        System.out.println("---------------------------------------------------");
                    }
                    System.out.println("Pokemons listados com sucesso!");
                    break;
                case 3:
                    sc.nextLine();
                    String nomeBusca = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    try {
                        Pokemon pkmn = serv.buscarPorNome(nomeBusca);
                        System.out.println("---------------------------------------------------");
                        System.out.println("Nome: " + pkmn.getNome());
                        System.out.println("Numero de Pokedex: #" + String.format("%04d", pkmn.getId()));
                        System.out.print("Tipos:");
                        for (Tipo t : pkmn.getTipos()) {
                            System.out.print(" " + t);
                        }
                        System.out.print("\n");
                        System.out.println("Stats:");
                        System.out.println("HP: " + pkmn.getBaseStats().getHp());
                        System.out.println("Ataque: " + pkmn.getBaseStats().getAtaque());
                        System.out.println("Defesa: " + pkmn.getBaseStats().getDefesa());
                        System.out.println("Ataque especial: " + pkmn.getBaseStats().getAtaqueEspecial());
                        System.out.println("Defesa especial: " + pkmn.getBaseStats().getDefesaEspecial());
                        System.out.println("Velocidade: " + pkmn.getBaseStats().getVelocidade());
                        System.out.println("---------------------------------------------------");
                        System.out.println("Pokemon " + nomeBusca + " encontrado com sucesso!");
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    sc.nextLine();
                    String nomeEdicao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    try {
                        Pokemon pkmn = serv.buscarPorNome(nomeEdicao);
                        System.out.println("1. Editar nome");
                        System.out.println("2. Editar tipagem");
                        System.out.println("3. Editar stats");
                        int edicao = InputUtils.lerInt("Insira a opcao de edicao desejada: ", sc);
                        while (edicao < 1 || edicao > 2) edicao = InputUtils.lerInt("Opcao invalida!\nInsira a opcao de edicao desejada: ", sc);
                        switch (edicao) {
                            case 1:
                                sc.nextLine();
                                String novoNome = InputUtils.lerString("Insira o novo nome do Pokemon: ", sc);
                                pkmn.setNome(novoNome);
                                System.out.println("Nome alterado com sucesso!");
                                break;
                            case 2:
                                sc.nextLine();
                                String novoTipo1 = InputUtils.lerString("Insira o novo tipo principal do Pokemon: ", sc);
                                List<Tipo> novosTiposPkmn = new ArrayList<>();
                                try {
                                    Tipo novoTipoPkmn1 = Tipo.fromString(novoTipo1);
                                    novosTiposPkmn.add(novoTipoPkmn1);
                                    String optionTipo2 = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                                    while (!optionTipo2.toUpperCase().equalsIgnoreCase("s") && !optionTipo2.toUpperCase().equalsIgnoreCase("n")) {
                                        optionTipo2 = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                                    }
                                    if (optionTipo2.equalsIgnoreCase("s")) {
                                        String novoTipo2 = InputUtils.lerString("Insira o novo tipo secundario do Pokemon: ", sc);
                                        Tipo novoTipoPkmn2 = Tipo.fromString(novoTipo2);
                                        novosTiposPkmn.add(novoTipoPkmn2);
                                    }
                                    pkmn.setTipos(novosTiposPkmn);
                                    System.out.println("Tipos alterados com sucesso!");
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                int novoHp = InputUtils.lerInt("Insira o novo HP base: ", sc);
                                int novoAtaque = InputUtils.lerInt("Insira o novo ataque base: ", sc);
                                int novaDefesa = InputUtils.lerInt("Insira a nova defesa base: ", sc);
                                int novoAtaqueEspecial = InputUtils.lerInt("Insira o novo ataque especial base: ", sc);
                                int novaDefesaEspecial = InputUtils.lerInt("Insira a nova defesa especial base: ", sc);
                                int novaVelocidade = InputUtils.lerInt("Insira a nova velocidade base: ", sc);
                                try {
                                    Stats novosBaseStats = new Stats(novoHp, novoAtaque, novaDefesa, novoAtaqueEspecial, novaDefesaEspecial, novaVelocidade);
                                    pkmn.setBaseStats(novosBaseStats);
                                    System.out.println("Stats base alterados com sucesso!");
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                        }
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    sc.nextLine();
                    String nomeRemocao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    try {
                        serv.removerPokemon(nomeRemocao);
                        System.out.println("Pokemon " + nomeRemocao + " removido com sucesso!");
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    Pokemon p1 = null, p2 = null;
                    sc.nextLine();
                    String nomeP1 = InputUtils.lerString("Insira um nome para procura de um Pokemon: ", sc);
                    String nomeP2 = InputUtils.lerString("Insira um nome para procura do Pokemon oponente: ", sc);
                    try {
                        p1 = serv.buscarPorNome(nomeP1);
                        p2 = serv.buscarPorNome(nomeP2);
                        List<Golpe> golpesP1 = new ArrayList<>();
                        List<Golpe> golpesP2 = new ArrayList<>();
                        String golpe1P1 = InputUtils.lerString("Insira um golpe para " + p1.getNome() + ": ", sc);
                        String golpe2P1 = InputUtils.lerString("Insira outro golpe para " + p1.getNome() + ": ", sc);
                        String nature1 = InputUtils.lerString("Insira a nature de " + p1.getNome() + ": ", sc);
                        int nivelP1 = InputUtils.lerInt("Insira o nivel de " + p1.getNome() + ": ", sc);
                        sc.nextLine();
                        String golpe1P2 = InputUtils.lerString("Insira um golpe para " + p2.getNome() + ": ", sc);
                        String golpe2P2 = InputUtils.lerString("Insira outro golpe para " + p2.getNome() + ": ", sc);
                        String nature2 = InputUtils.lerString("Insira a nature de " + p2.getNome() + ": ", sc);
                        int nivelP2 = InputUtils.lerInt("Insira o nivel de " + p2.getNome() + ": ", sc);
                        try {
                            Golpe golpeP1n1 = Golpe.fromString(golpe1P1);
                            golpesP1.add(golpeP1n1);
                            Golpe golpeP1n2 = Golpe.fromString(golpe2P1);
                            golpesP1.add(golpeP1n2);
                            Nature natureP1 = Nature.fromString(nature1);
                            p1.setGolpes(golpesP1);
                            p1.setNature(natureP1);
                            p1.setNivel(nivelP1);
                            p1.setStats(p1.getBaseStats(), p1.getNature(), p1.getNivel());
                            Golpe golpeP2n1 = Golpe.fromString(golpe1P2);
                            golpesP2.add(golpeP2n1);
                            Golpe golpeP2n2 = Golpe.fromString(golpe2P2);
                            golpesP2.add(golpeP2n2);
                            Nature natureP2 = Nature.fromString(nature2);
                            p2.setGolpes(golpesP2);
                            p2.setNature(natureP2);
                            p2.setNivel(nivelP2);
                            p2.setStats(p2.getBaseStats(), p2.getNature(), p2.getNivel());
                            Pokemon vencedor = battle.batalhar(p1, p2, sc);
                            System.out.println("O Pokemon vencedor foi " + vencedor.getNome() + "!");
                        } catch (DadoInvalidoException e) {
                            System.out.println("Nao foi possivel executar a batalha: " + e.getMessage());
                            break;
                        }
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println("Nao foi possivel executar a batalha: " + e.getMessage());
                        break;
                    }
                    break;
                case 7:
                    if (arquivoPkmn.exists()) {
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
