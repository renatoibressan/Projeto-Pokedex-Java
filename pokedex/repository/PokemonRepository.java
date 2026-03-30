package pokedex.repository;

import java.util.List;

import pokedex.model.Pokemon;

public interface PokemonRepository {
    public void salvar(Pokemon p);
    public List<Pokemon> listar();
    public Pokemon buscarPorNome(String nome);
    public void remover(String nome);
}
