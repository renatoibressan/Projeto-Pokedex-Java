package pokedex.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pokedex.exception.PokemonNaoEncontradoException;
import pokedex.model.Pokemon;

public class FilePokemonRepository implements PokemonRepository {
    private TreeMap<String, Pokemon> pkmn;
    private String caminhoArquivo;
    public FilePokemonRepository(String caminhoArquivo) {
        pkmn = new TreeMap<>();
        this.caminhoArquivo = caminhoArquivo;
    }
    public void inserir(Pokemon p) {
        pkmn.put(p.getNome(), p);
    }
    public void salvar(Pokemon p) throws PokemonNaoEncontradoException {
        if (p == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        pkmn.put(p.getNome(), p);
    }
    public List<Pokemon> listar() {
        List<Pokemon> listaPokemon = new ArrayList<>();
        return listaPokemon;
    }
    public Pokemon buscarPorNome(String nome) throws PokemonNaoEncontradoException {
        Pokemon p = pkmn.get(nome);
        if (p == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        return p;
    }
    public void remover(String nome) throws PokemonNaoEncontradoException {
        Pokemon p = pkmn.get(nome);
        if (p == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        pkmn.remove(nome);
    }
    void escreverArquivo(List<Pokemon> pokemons) throws IOException {
    }
    // public List<Pokemon> lerArquivo() throws IOException {}
}
