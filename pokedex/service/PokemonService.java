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
    public int gerarNovoId() {
        return repository.listar().stream().mapToInt(Pokemon::getId).max().orElse(0) + 1;
    }
    public Pokemon cadastrarPokemon(String nome, List<Tipo> tipos, Stats stats, int nivel) throws DadoInvalidoException {
        if (!repository.pokemonExiste(nome)) {
            Pokemon p = new Pokemon(nome, tipos, stats, nivel);
            int id = gerarNovoId();
            p.setId(id);
            repository.salvar(p);
            return p;
        }
        return null;
    }
    public List<Pokemon> listarPokemons() {
        return repository.listar().stream().sorted(Comparator.comparingInt(Pokemon::getId)).toList();
    }
    public Pokemon buscarPorNome(String nome) throws PokemonNaoEncontradoException {
        Pokemon pkmn = repository.buscarPorNome(nome);
        if (pkmn == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        return pkmn;
    }
    public void removerPokemon(String nome) throws PokemonNaoEncontradoException {
        repository.remover(nome);
    }
}
