package pokedex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pokedex.exception.PokemonNaoEncontradoException;
import pokedex.model.Pokemon;
import pokedex.model.Stats;
import pokedex.model.Tipo;
import pokedex.repository.PokemonRepository;

public class PokemonService {
    private TreeMap<String, Pokemon> pkmns;
    private PokemonRepository repository;
    public PokemonService(PokemonRepository repository) {
        pkmns = new TreeMap<>();
        this.repository = repository;
    }
    public Pokemon cadastrarPokemon(String nome, List<Tipo> tipo, Stats stats, int nivel) {
        Pokemon pkmn = new Pokemon(nome, tipo, stats, nivel);
        pkmns.put(nome, pkmn);
        return pkmn;
    }
    public List<Pokemon> listarPokemons() {
        List<Pokemon> listaPokemon = new ArrayList<>(pkmns.values());
        return listaPokemon;
    }
    public Pokemon buscarPorNome(String nome) throws PokemonNaoEncontradoException {
        Pokemon pkmn = pkmns.get(nome);
        if (pkmn == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        else return pkmn;
    }
    public void removerPokemon(String nome) throws PokemonNaoEncontradoException {
        Pokemon pkmn = pkmns.get(nome);
        if (pkmn == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        else pkmns.remove(nome);
    }
}
