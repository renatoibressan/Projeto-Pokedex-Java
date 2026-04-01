package pokedex.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import pokedex.exception.DadoInvalidoException;

public class Pokemon {
    private int id;
    private String nome;
    private List<Tipo> tipos;
    private Stats baseStats;
    private Stats stats;
    private Nature nature;
    private int nivel;
    public Pokemon(String nome, List<Tipo> tipos, Stats baseStats, Nature nature, int nivel) throws DadoInvalidoException {
        this.nome = nome;
        if (tipos == null || tipos.isEmpty()) throw new DadoInvalidoException("Pokemon deve ter pelo menos 1 tipo!");
        if (tipos.size() > 2) throw new DadoInvalidoException("Pokemon nao pode ter mais de 2 tipos!");
        if (new HashSet<>(tipos).size() != tipos.size()) throw new DadoInvalidoException("Tipos duplicados nao sao permitidos!");
        this.tipos = tipos;
        this.baseStats = baseStats;
        this.stats = new Stats(1, 1, 1, 1, 1, 1);
        this.nature = nature;
        if (nivel < 1 || nivel > 100) throw new DadoInvalidoException("Pokemon deve ser de nivel 1 a 100!");
        this.nivel = nivel;
    }
    public String getNome() {
        return nome;
    }
    public List<Tipo> getTipos() {
        return Collections.unmodifiableList(tipos);
    }
    public Stats getBaseStats() {
        return baseStats;
    }
    public Stats getStats() {
        return stats;
    }
    public Nature getNature() {
        return nature;
    }
    public int getNivel() {
        return nivel;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setStats(Stats baseStats, Nature nature, int nivel) {
        int hp, atk, def, spAtk, spDef, speed;
        hp = (int) Math.floor(((baseStats.getHp() * 2) * nivel) / 100) + nivel + 10;
        atk = (int) Math.floor(((baseStats.getAtaque() * 2) * nivel) / 100) + 5;
        def = (int) Math.floor(((baseStats.getDefesa() * 2) * nivel) / 100) + 5;
        spAtk = (int) Math.floor(((baseStats.getAtaqueEspecial() * 2) * nivel) / 100) + 5;
        spDef = (int) Math.floor(((baseStats.getDefesaEspecial() * 2) * nivel) / 100) + 5;
        speed = (int) Math.floor(((baseStats.getVelocidade() * 2) * nivel) / 100) + 5;
        switch (nature) {
            case HARDY: case DOCILE: case SERIOUS: case BASHFUL: case QUIRKY: break;
            case LONELY: case ADAMANT: case NAUGHTY: case BRAVE: atk += atk / 10; break;
            case BOLD: case IMPISH: case LAX: case RELAXED: def += def / 10; break;
            case MODEST: case MILD: case RASH: case QUIET: spAtk += spAtk / 10; break;
            case CALM: case GENTLE: case CAREFUL: case SASSY: spDef += spDef / 10; break;
            case TIMID: case HASTY: case JOLLY: case NAIVE: speed += speed / 10; break;
        }
        switch (nature) {
            case HARDY: case DOCILE: case SERIOUS: case BASHFUL: case QUIRKY: break;
            case BOLD: case MODEST: case CALM: case TIMID: atk -= atk / 10; break;
            case LONELY: case MILD: case GENTLE: case HASTY: def -= def / 10; break;
            case ADAMANT: case IMPISH: case CAREFUL: case JOLLY: spAtk -= spAtk / 10; break;
            case NAUGHTY: case LAX: case RASH: case NAIVE: spDef -= spDef / 10; break;
            case BRAVE: case RELAXED: case QUIET: case SASSY: speed -= speed / 10; break;
        }
        stats.setHp(hp);
        stats.setAtaque(atk);
        stats.setDefesa(def);
        stats.setAtaqueEspecial(spAtk);
        stats.setDefesaEspecial(spDef);
        stats.setVelocidade(speed);
    }
    public String toFileString() {
        StringBuilder tipoString = new StringBuilder();
        for (Tipo tipo : tipos) {
            tipoString.append(tipo);
            tipoString.append(",");
        }
        if (tipoString.length() > 0) tipoString.setLength(tipoString.length() - 1);
        String statString = baseStats.getHp() + "," +  baseStats.getAtaque() + "," + baseStats.getDefesa() + "," + baseStats.getAtaqueEspecial() + "," + baseStats.getDefesaEspecial() + "," + baseStats.getVelocidade();
        return id + ";" + nome + ";" + tipoString + ";" + statString + ";" + nature + ";" + nivel;
    }
    public boolean temTipo(Tipo tipo) {
        if (tipo == null) return false;
        return tipos.contains(tipo);
    }
}
