package pokedex.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pokedex.model.*;
import pokedex.util.FileUtils;

public class FilePokemonRepository implements PokemonRepository {
    private TreeMap<Integer, Pokemon> pkmn;
    private String caminhoArquivo;
    public FilePokemonRepository(String caminhoArquivo) {
        pkmn = new TreeMap<>();
        this.caminhoArquivo = caminhoArquivo;
    }
    @Override
    public boolean pokemonExiste(String nome) {
        for (Pokemon p : pkmn.values()) {
            if (p.getNome().equalsIgnoreCase(nome)) return true;
        }
        return false;
    }
    @Override
    public void salvar(Pokemon p) {
        pkmn.put(p.getId(), p);
    }
    @Override
    public List<Pokemon> listar() {
        List<Pokemon> listaPokemon = new ArrayList<>(pkmn.values());
        return listaPokemon;
    }
    @Override
    public Pokemon buscarPorNome(String nome) {
        for (Pokemon p : pkmn.values()) {
            if (p.getNome().equalsIgnoreCase(nome)) return p;
        }
        return null;
    }
    @Override
    public void remover(int id) {
        pkmn.remove(id);
    }
    public void inserirPokemons(List<Pokemon> pokemons) {
        for (Pokemon p : pokemons) {
            pkmn.put(p.getId(), p);
        }
    }
    public int contarPokemons() {
        int count = 0;
        while (count < pkmn.size()) count++;
        return count;
    }
    public void escreverArquivo(List<Pokemon> pokemons) throws IOException {
        List<String> linhas = new ArrayList<>();
        String linha;
        for (Pokemon p : pokemons) {
            linha = p.toFileString();
            linhas.add(linha);
        }
        FileUtils.escrever(caminhoArquivo, linhas);
    }
    public List<Pokemon> lerArquivo() throws IOException {
        List<Pokemon> listaPokemon = new ArrayList<>();
        List<String> linhas = FileUtils.ler(caminhoArquivo);
        int linhaNumero = 0;
        for (String linha : linhas) {
            linhaNumero++;
            try {
                String[] partes = linha.split(";");
                if (partes.length != 4) throw new IllegalArgumentException("Formato invalido!");
                int id = Integer.parseInt(partes[0]);
                String nomePkmn = partes[1];
                String[] tiposString = partes[2].split(",");
                List<Tipo> tiposPkmn = new ArrayList<>();
                for (String tipoStr : tiposString) {
                    tiposPkmn.add(Tipo.fromString(tipoStr));
                }
                String[] statsString = partes[3].split(",");
                if (statsString.length != 6) throw new IllegalArgumentException("Formato invalido!");
                int hp = Integer.parseInt(statsString[0]);
                int atk = Integer.parseInt(statsString[1]);
                int def = Integer.parseInt(statsString[2]);
                int spAtk = Integer.parseInt(statsString[3]);
                int spDef = Integer.parseInt(statsString[4]);
                int speed = Integer.parseInt(statsString[5]);
                Stats statsPkmn;
                statsPkmn = new Stats(hp, atk, def, spAtk, spDef, speed);
                Pokemon p = new Pokemon(nomePkmn, tiposPkmn, statsPkmn);
                p.setId(id);
                listaPokemon.add(p);
            } catch (Exception e) {
                System.out.println("Linha " + linhaNumero + " invalida!");
            }
        }
        return listaPokemon;
    }
    public void limparArquivo() throws IOException {
        FileUtils.limpar(caminhoArquivo);
    }
}
