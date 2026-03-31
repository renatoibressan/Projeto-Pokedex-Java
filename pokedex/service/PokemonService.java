package pokedex.service;

import java.util.Comparator;
import java.util.List;

import pokedex.exception.*;
import pokedex.model.*;
import pokedex.repository.PokemonRepository;

public class PokemonService {
    private PokemonRepository repository;
    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }
    public PokemonRepository getRepository() {
        return repository;
    }
    public int gerarNovoId() {
        return repository.listar().stream().mapToInt(Pokemon::getId).max().orElse(0) + 1;
    }
    public void cadastrarPokemon(String nome, List<Tipo> tipo, Stats stats, int nivel) throws DadoInvalidoException, PokemonNaoEncontradoException {
        int id = gerarNovoId();
        Pokemon p = new Pokemon(nome, tipo, stats, nivel, id);
        repository.salvar(p);
    }
    public List<Pokemon> listarPokemons() {
        return repository.listar().stream().sorted(Comparator.comparingInt(Pokemon::getId)).toList();
    }
    public Pokemon buscarPorNome(String nome) throws PokemonNaoEncontradoException {
        Pokemon pkmn = repository.buscarPorNome(nome);
        return pkmn;
    }
    public void removerPokemon(String nome) throws PokemonNaoEncontradoException {
        repository.remover(nome);
    }
}
