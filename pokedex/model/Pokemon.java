package pokedex.model;

import java.util.List;

public class Pokemon {
    private String nome;
    private List<Tipo> tipos;
    private Stats stats;
    private int nivel;
    public Pokemon(String nome, List<Tipo> tipos, Stats stats, int nivel) {
        this.nome = nome;
        this.tipos = tipos;
        this.stats = stats;
        this.nivel = nivel;
    }
    public String getNome() {
        return nome;
    }
    public List<Tipo> getTipos() {
        return tipos;
    }
    public Stats getStats() {
        return stats;
    }
    public int getNivel() {
        return nivel;
    }
    public void setNivel(int novoNivel) {
        this.nivel = novoNivel;
    }
    public String toString() {
        StringBuilder tipoString = new StringBuilder();
        for (Tipo tipo : tipos) {
            tipoString.append(tipo);
            tipoString.append("/");
        }
        if (tipoString.length() > 0) tipoString.setLength(tipoString.length() - 1);
        String linha = "Nome: " + nome + " | Tipos: " + tipoString + " | Stats: " + stats.toString() + " | Nivel: " + nivel;
        return linha;
    }
    // public boolean temTipo(Tipo tipo) {}
}
