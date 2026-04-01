package pokedex.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import pokedex.exception.DadoInvalidoException;

public class Pokemon {
    private String nome;
    private List<Tipo> tipos;
    private Stats stats;
    private int nivel;
    private int id;
    public Pokemon(String nome, List<Tipo> tipos, Stats stats, int nivel) throws DadoInvalidoException {
        this.nome = nome;
        if (tipos == null || tipos.isEmpty()) throw new DadoInvalidoException("Pokemon deve ter pelo menos 1 tipo!");
        if (tipos.size() > 2) throw new DadoInvalidoException("Pokemon nao pode ter mais de 2 tipos!");
        if (new HashSet<>(tipos).size() != tipos.size()) throw new DadoInvalidoException("Tipos duplicados nao sao permitidos!");
        this.tipos = tipos;
        this.stats = stats;
        if (nivel < 1 || nivel > 100) throw new DadoInvalidoException("Pokemon deve ser de nivel 1 a 100!");
        this.nivel = nivel;
    }
    public String getNome() {
        return nome;
    }
    public List<Tipo> getTipos() {
        return Collections.unmodifiableList(tipos);
    }
    public Stats getStats() {
        return stats;
    }
    public int getNivel() {
        return nivel;
    }
    public int getId() {
        return id;
    }
    public void setNivel(int novoNivel) {
        if (novoNivel > nivel && novoNivel <= 100) this.nivel = novoNivel;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String toFileString() {
        StringBuilder tipoString = new StringBuilder();
        for (Tipo tipo : tipos) {
            tipoString.append(tipo);
            tipoString.append(",");
        }
        if (tipoString.length() > 0) tipoString.setLength(tipoString.length() - 1);
        String statString = stats.getHp() + "," +  stats.getAtaque() + "," + stats.getDefesa() + "," + stats.getAtaqueEspecial() + "," + stats.getDefesaEspecial() + "," + stats.getVelocidade();
        return id + ";" + nome + ";" + tipoString + ";" + statString + ";" + nivel;
    }
    public boolean temTipo(Tipo tipo) {
        if (tipo == null) return false;
        return tipos.contains(tipo);
    }
}
