package pokedex.service;

import java.util.Comparator;
import java.util.List;

import pokedex.exception.*;
import pokedex.model.*;
import pokedex.repository.PokemonRepository;

public class PokemonService {
    private PokemonRepository repository;
    private List<Pokemon> pokemons;
    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }
    public void putPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    public int gerarNovoId() {
        return repository.listar().stream().mapToInt(Pokemon::getId).max().orElse(0) + 1;
    }
    public void cadastrarPokemon(String nome, List<Tipo> tipos, Stats stats, int id) throws DadoInvalidoException {
        if (!repository.pokemonExiste(nome)) {
            Pokemon p = new Pokemon(nome, tipos, stats);
            p.setId(id);
            repository.salvar(p);
            pokemons.add(p);
            return;
        }
        throw new DadoInvalidoException("Ja existe um Pokemon com o nome " + nome + "!");
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
        int id;
        for (Pokemon p : pokemons) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                id = p.getId();
                repository.remover(id);
                pokemons.remove(p);
                return;
            }
        }
        throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
    }
    public int contarListaPokemons() {
        int count = 0;
        while (count < pokemons.size()) count++;
        return count;
    }
    public Pokemon maiorStat(String optionStat) throws PokemonNaoEncontradoException, DadoInvalidoException {
        if (pokemons.isEmpty()) throw new PokemonNaoEncontradoException("Lista de Pokemons vazia!");
        Pokemon maior = pokemons.getFirst();
        for (Pokemon p : pokemons) if (p.statFromString(optionStat) > maior.statFromString(optionStat)) maior = p;
        return maior;
    }
    public Pokemon menorStat(String optionStat) throws PokemonNaoEncontradoException, DadoInvalidoException {
        if (pokemons.isEmpty()) throw new PokemonNaoEncontradoException("Lista de Pokemons vazia!");
        Pokemon menor = pokemons.getFirst();
        for (Pokemon p : pokemons) if (p.statFromString(optionStat) < menor.statFromString(optionStat)) menor = p;
        return menor;
    }
}
