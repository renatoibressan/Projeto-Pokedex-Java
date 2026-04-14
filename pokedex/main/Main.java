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
import pokedex.util.OutputUtils;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        OutputUtils.slowPrint("=============== POKEDEX MODULAR ===============", 40);
        OutputUtils.slowPrint("Desenvolvido por: Renato Ikeda Bressan", 40);
        Scanner sc = new Scanner(System.in);
        int option = -1;
        String optionArquivo;
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
            optionArquivo = InputUtils.lerString("Deseja carregar os Pokemons do arquivo? (S/N): ", sc);
            while (!optionArquivo.equalsIgnoreCase("s") && !optionArquivo.equalsIgnoreCase("n")) {
                optionArquivo = InputUtils.lerString("Opcao invalida!\nDeseja carregar os Pokemons do arquivo? (S/N): ", sc);
            }
            if (optionArquivo.equalsIgnoreCase("s")) {
                try {
                    pokemons = repo.lerArquivo();
                    repo.inserirPokemons(pokemons);
                    serv.putPokemons(pokemons);
                    OutputUtils.slowPrint(repo.contarPokemons() + " Pokemons foram carregados com sucesso!", 50);
                } catch (IOException e) {
                    System.out.println("Nao foi possivel ler o arquivo!");
                }
            }
        }
        do {
            Menu.exibirMenuPrincipal(10);
            option = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            switch (option) {
                case 1:
                    sc.nextLine();
                    String nome = InputUtils.lerString("Insira o nome do Pokemon: ", sc);
                    while (nome == null || nome.isEmpty()) {
                        nome = InputUtils.lerString("Entrada invalida!\nInsira o nome do Pokemon: ", sc);
                    }
                    String nomePkmn = nome.substring(0, 1).toUpperCase() + nome.substring(1);
                    List<Tipo> tiposPkmn = new ArrayList<>();
                    boolean tipoValido = false;
                    if (!pokemons.isEmpty()) {
                        Pokemon anterior = pokemons.getLast();
                        String txt1 = "Deseja utilizar o tipo " + anterior.getTipos().getFirst() + " do Pokemon " + anterior.getNome() + "? (S/N): ";
                        String optionTipoAnterior1 = InputUtils.lerString(txt1, sc);
                        while (!optionTipoAnterior1.equalsIgnoreCase("s") && !optionTipoAnterior1.equalsIgnoreCase("n")) {
                            optionTipoAnterior1 = InputUtils.lerString("Opcao invalida!\n" + txt1, sc);
                        }
                        if (optionTipoAnterior1.equalsIgnoreCase("s")) {
                            Tipo tipoAnterior1 = anterior.getTipos().getFirst();
                            tiposPkmn.add(tipoAnterior1);
                            tipoValido = true;
                        } else {
                            String tipo1 = InputUtils.lerString("Insira o tipo principal desejado: ", sc);
                            try {
                                Tipo tipo1Pkmn = Tipo.fromString(tipo1);
                                tiposPkmn.add(tipo1Pkmn);
                                tipoValido = true;
                            } catch (DadoInvalidoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if (anterior.getTipos().size() == 2 && !tiposPkmn.contains(anterior.getTipos().getLast())) {
                            String txt2 = "Deseja utilizar o tipo " + anterior.getTipos().getLast() + " do Pokemon " + anterior.getNome() + "? (S/N): ";
                            String optionTipoAnterior2 = InputUtils.lerString(txt2, sc);
                            while (!optionTipoAnterior2.equalsIgnoreCase("s") && !optionTipoAnterior2.equalsIgnoreCase("n")) {
                                optionTipoAnterior2 = InputUtils.lerString("Opcao invalida!\n" + txt2, sc);
                            }
                            if (optionTipoAnterior2.equalsIgnoreCase("s")) {
                                Tipo tipoAnterior2 = anterior.getTipos().getLast();
                                tiposPkmn.add(tipoAnterior2);
                            } else {
                                String optionTipoSec = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                                    while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                                    optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                                }
                                if (optionTipoSec.equalsIgnoreCase("s")) {
                                    String tipo2 = InputUtils.lerString("Insira o tipo secundario desejado: ", sc);
                                    try {
                                        Tipo tipo2Pkmn = Tipo.fromString(tipo2);
                                        tiposPkmn.add(tipo2Pkmn);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        } else {
                            String optionTipoSec = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                            while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                                optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                            }
                            if (optionTipoSec.equalsIgnoreCase("s")) {
                                String tipo2 = InputUtils.lerString("Insira o tipo secundario desejado: ", sc);
                                try {
                                    Tipo tipo2Pkmn = Tipo.fromString(tipo2);
                                    tiposPkmn.add(tipo2Pkmn);
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    } else {
                        String tipo1 = InputUtils.lerString("Insira o tipo principal desejado: ", sc);
                        String optionTipoSec = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                        while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                            optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                        }
                        try {
                            Tipo tipo1Pkmn = Tipo.fromString(tipo1);
                            tiposPkmn.add(tipo1Pkmn);
                            tipoValido = true;
                            if (optionTipoSec.equalsIgnoreCase("s")) {
                                String tipo2 = InputUtils.lerString("Insira o tipo secundario desejado: ", sc);
                                Tipo tipo2Pkmn = Tipo.fromString(tipo2);
                                tiposPkmn.add(tipo2Pkmn);
                            }
                        } catch (DadoInvalidoException e) {
                            System.out.println("Nao foi possivel cadastrar o Pokemon: " + e.getMessage());
                        }
                    }
                    if (!tipoValido) break;
                    else {
                        int hp = InputUtils.lerInt("Insira o HP base do Pokemon: ", sc);
                        int atk = InputUtils.lerInt("Insira o ataque base do Pokemon: ", sc);
                        int def = InputUtils.lerInt("Insira a defesa base do Pokemon: ", sc);
                        int spAtk = InputUtils.lerInt("Insira o ataque especial base do Pokemon: ", sc);
                        int spDef = InputUtils.lerInt("Insira a defesa especial base do Pokemon: ", sc);
                        int speed = InputUtils.lerInt("Insira a velocidade base do Pokemon: ", sc);
                        try {
                            Stats statsPkmn = new Stats(hp, atk, def, spAtk, spDef, speed);
                            Pokemon p = new Pokemon(nomePkmn, tiposPkmn, statsPkmn);
                            int id = serv.gerarNovoId();
                            p.setId(id);
                            serv.cadastrarPokemon(nomePkmn, tiposPkmn, statsPkmn, id);
                            OutputUtils.slowPrint("Pokemon " + p.getNome() + " cadastrado com sucesso!", 50);
                            System.out.println("Numero de Pokedex: #" + String.format("%04d", p.getId()));
                            System.out.print("Tipo(s):");
                            for (Tipo t : p.getTipos()) {
                                System.out.print(" " + t);
                            }
                            System.out.println("\nBST: " + p.calcularBST());
                        } catch (DadoInvalidoException e) {
                            System.out.println("Nao foi possivel cadastrar o Pokemon: " + e.getMessage());
                        }
                    }
                    break;
                case 2:
                    List<Pokemon> listaPkmn = serv.listarPokemons();
                    if (!listaPkmn.isEmpty()) {
                        OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        for (Pokemon pkmn : listaPkmn) {
                            System.out.println("Pokemon #" + String.format("%04d", pkmn.getId()) + ": " + pkmn.getNome());
                            System.out.print("Tipo(s) de " + pkmn.getNome() + ":");
                            for (Tipo t : pkmn.getTipos()) System.out.print(" " + t);
                            OutputUtils.slowPrint("\n---------------------------------------------------------", 10);
                        }
                        OutputUtils.slowPrint(serv.contarListaPokemons() + " Pokemons listados com sucesso!", 50);
                    } else System.out.println("Nao ha Pokemons para listar!");
                    break;
                case 3:
                    sc.nextLine();
                    String nomeBusca = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    nomeBusca = nomeBusca.substring(0, 1).toUpperCase() + nomeBusca.substring(1);
                    try {
                        Pokemon pkmn = serv.buscarPorNome(nomeBusca);
                        Menu.exibirMenuPokemon(pkmn, 50);
                        OutputUtils.slowPrint("Pokemon " + nomeBusca + " encontrado com sucesso!", 50);
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    sc.nextLine();
                    String nomeEdicao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    try {
                        Pokemon pkmn = serv.buscarPorNome(nomeEdicao);
                        int edicao = -1;
                        do {
                            Menu.exibirMenuEdicao(20);
                            edicao = InputUtils.lerInt("Insira a opcao de edicao desejada: ", sc);
                            switch (edicao) {
                                case 1:
                                    sc.nextLine();
                                    String novoNome = InputUtils.lerString("Insira o novo nome do Pokemon: ", sc);
                                    String novoNomePkmn = novoNome.substring(0, 1).toUpperCase() + novoNome.substring(1);
                                    pkmn.setNome(novoNomePkmn);
                                    OutputUtils.slowPrint("Nome alterado com sucesso!", 50);
                                    break;
                                case 2:
                                    sc.nextLine();
                                    String novoTipo1 = InputUtils.lerString("Insira o novo tipo principal do Pokemon: ", sc);
                                    List<Tipo> novosTiposPkmn = new ArrayList<>();
                                    try {
                                        Tipo novoTipoPkmn1 = Tipo.fromString(novoTipo1);
                                        novosTiposPkmn.add(novoTipoPkmn1);
                                        String optionTipoSec = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                                        while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                                            optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                                        }
                                        if (optionTipoSec.equalsIgnoreCase("s")) {
                                            String novoTipo2 = InputUtils.lerString("Insira o novo tipo secundario do Pokemon: ", sc);
                                            Tipo novoTipoPkmn2 = Tipo.fromString(novoTipo2);
                                            novosTiposPkmn.add(novoTipoPkmn2);
                                        }
                                        pkmn.setTipos(novosTiposPkmn);
                                        OutputUtils.slowPrint("Tipo(s) alterado(s) com sucesso!", 50);
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
                                        OutputUtils.slowPrint("Stats base alterados com sucesso!", 50);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 0: break;
                                default: System.out.println("Opcao invalida!");
                            }
                        } while (edicao != 0);
                        int i = 0;
                        for (Pokemon p : pokemons) {
                            if (p.getId() == pkmn.getId()) break;
                            i++;
                        }
                        pokemons.set(i, pkmn);
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    sc.nextLine();
                    String nomeRemocao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    nomeRemocao = nomeRemocao.substring(0, 1).toUpperCase() + nomeRemocao.substring(1);
                    try {
                        Pokemon pkmn = serv.buscarPorNome(nomeRemocao);
                        String optionRemocao = InputUtils.lerString("Certeza que deseja remover " + pkmn.getNome() + "? (S/N | Esta acao nao tem volta): ", sc);
                        while (!optionRemocao.equalsIgnoreCase("s") && !optionRemocao.equalsIgnoreCase("n")) {
                            optionRemocao = InputUtils.lerString("Opcao invalida!\nCerteza que deseja remover " + pkmn.getNome() + "? (S/N | Esta acao nao tem volta): ", sc);
                        }
                        if (optionRemocao.equalsIgnoreCase("s")) {
                            serv.removerPokemon(pkmn.getNome());
                            OutputUtils.slowPrint("Pokemon " + nomeRemocao + " removido com sucesso!", 50);
                        }
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    Pokemon p1 = null, p2 = null;
                    int i;
                    sc.nextLine();
                    String nomeP1 = InputUtils.lerString("Insira um nome para procura de um Pokemon: ", sc);
                    String nomeP2 = InputUtils.lerString("Insira um nome para procura do Pokemon oponente: ", sc);
                    try {
                        p1 = serv.buscarPorNome(nomeP1);
                        p2 = serv.buscarPorNome(nomeP2);
                        List<Golpe> golpesP1 = new ArrayList<>();
                        List<Golpe> golpesP2 = new ArrayList<>();
                        i = 1;
                        while (i <= 4) {
                            String golpes1 = InputUtils.lerString("Insira o " + i + "o golpe de " + p1.getNome() + " ou 0 para fechar a lista de golpes (min.1, max.4): ", sc);
                            if (golpes1.equalsIgnoreCase("0") && !golpesP1.isEmpty()) break;
                            try {
                                Golpe golpeP1 = Golpe.fromString(golpes1);
                                golpesP1.add(golpeP1);
                                System.out.println("Golpe " + golpeP1 + " adicionado a " + p1.getNome() + " com sucesso!");
                                i++;
                            } catch (DadoInvalidoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        String nature1 = InputUtils.lerString("Insira a nature de " + p1.getNome() + ": ", sc);
                        int nivelP1 = InputUtils.lerInt("Insira o nivel de " + p1.getNome() + ": ", sc);
                        sc.nextLine();
                        i = 1;
                        while (i <= 4) {
                            String golpes2 = InputUtils.lerString("Insira o " + i + "o golpe de " + p2.getNome() + " ou 0 para fechar a lista de golpes (min.1, max.4): ", sc);
                            if (golpes2.equalsIgnoreCase("0") && !golpesP2.isEmpty()) break;
                            try {
                                Golpe golpeP2 = Golpe.fromString(golpes2);
                                golpesP2.add(golpeP2);
                                System.out.println("Golpe " + golpeP2 + " adicionado a " + p2.getNome() + " com sucesso!");
                                i++;
                            } catch (DadoInvalidoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        String nature2 = InputUtils.lerString("Insira a nature de " + p2.getNome() + ": ", sc);
                        int nivelP2 = InputUtils.lerInt("Insira o nivel de " + p2.getNome() + ": ", sc);
                        try {
                            Nature natureP1 = Nature.fromString(nature1);
                            p1.setGolpes(golpesP1);
                            p1.setNature(natureP1);
                            p1.setNivel(nivelP1);
                            p1.setStats(p1.getBaseStats(), p1.getNature(), p1.getNivel());
                            Nature natureP2 = Nature.fromString(nature2);
                            p2.setGolpes(golpesP2);
                            p2.setNature(natureP2);
                            p2.setNivel(nivelP2);
                            p2.setStats(p2.getBaseStats(), p2.getNature(), p2.getNivel());
                            Pokemon vencedor = battle.batalhar(p1, p2, sc);
                            OutputUtils.slowPrint("O Pokemon vencedor foi " + vencedor.getNome() + "!", 50);
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
                    int optionStatistics = -1;
                    String optionStat;
                    Pokemon pkmn;
                    do {
                        Menu.exibirMenuEstatisticas(20);
                        optionStatistics = InputUtils.lerInt("Insira a opcao desejada: ", sc);
                        switch (optionStatistics) {
                            case 1:
                                sc.nextLine();
                                optionStat = InputUtils.lerString("Insira o stat desejado: ", sc);
                                try {
                                    pkmn = serv.maiorStat(optionStat);
                                    System.out.println("Pokemon de maior " + optionStat.toLowerCase() + ": " + pkmn.getNome());
                                    System.out.println("Valor do stat " + optionStat.toLowerCase() + ": " + pkmn.statFromString(optionStat));
                                } catch (PokemonNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                sc.nextLine();
                                optionStat = InputUtils.lerString("Insira o stat desejado: ", sc);
                                try {
                                    pkmn = serv.menorStat(optionStat);
                                    System.out.println("Pokemon de menor " + optionStat.toLowerCase() + ": " + pkmn.getNome());
                                    System.out.println("Valor do stat " + optionStat.toLowerCase() + ": " + pkmn.statFromString(optionStat));
                                } catch (PokemonNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 0: break;
                            default: System.out.println("Opcao invalida!");
                        }
                    } while (optionStatistics != 0);
                    break;
                case 8:
                    try {
                        String optionDelete = InputUtils.lerString("Tem certeza que deseja limpar o arquivo? (S/N | Esta acao nao tem volta): ", sc);
                        while (!optionDelete.equalsIgnoreCase("s") && !optionDelete.equalsIgnoreCase("n")) {
                            optionDelete = InputUtils.lerString("Opcao invalida!\nTem certeza que deseja limpar o arquivo? (S/N | Esta acao nao tem volta): ", sc);
                        }
                        if (optionDelete.equalsIgnoreCase("s")) {
                            repo.limparArquivo();
                            OutputUtils.slowPrint(serv.contarListaPokemons() + " Pokemons foram removidos do arquivo com sucesso!", 50);
                        }
                    } catch (IOException e) {
                        System.out.println("Nao foi possivel limpar o arquivo!");
                    }
                    break;
                case 0:
                    System.out.print("Retornando ao inicio");
                    Thread.sleep(500);
                    OutputUtils.slowPrint("...", 150);
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    sc.nextLine();
                    System.out.print("Retornando ao menu do programa principal");
                    Thread.sleep(500);
                    OutputUtils.slowPrint("...", 150);
            }
        } while (option != 0);
        sc.nextLine();
        optionArquivo = InputUtils.lerString("Deseja salvar os Pokemons no arquivo? (S/N): ", sc);
        while (!optionArquivo.equalsIgnoreCase("s") && !optionArquivo.equalsIgnoreCase("n")) {
            optionArquivo = InputUtils.lerString("Opcao invalida!\nDeseja salvar os Pokemons no arquivo? (S/N): ", sc);
        }
        if (optionArquivo.equalsIgnoreCase("s")) {
            try {
                repo.escreverArquivo(pokemons);
                OutputUtils.slowPrint(serv.contarListaPokemons() + " Pokemons foram salvos com sucesso!", 50);
            } catch (IOException e) {
                System.out.println("Nao foi possivel escrever no arquivo!");
            }
        }
        System.out.print("Encerrando o programa");
        Thread.sleep(500);
        OutputUtils.slowPrint("...", 150);
        sc.close();
    }
}
