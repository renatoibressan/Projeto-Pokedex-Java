package pokedex.repository;

import java.util.List;

import pokedex.exception.PokemonNaoEncontradoException;
import pokedex.model.Pokemon;

public interface PokemonRepository {
    public void salvar(Pokemon p) throws PokemonNaoEncontradoException;
    public List<Pokemon> listar();
    public Pokemon buscarPorNome(String nome) throws PokemonNaoEncontradoException;
    public void remover(String nome) throws PokemonNaoEncontradoException;
}
