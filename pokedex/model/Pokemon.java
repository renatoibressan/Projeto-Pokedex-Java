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
    private List<Golpe> golpes;
    public Pokemon(String nome, List<Tipo> tipos, Stats baseStats) throws DadoInvalidoException {
        this.nome = nome;
        if (tipos == null || tipos.isEmpty()) throw new DadoInvalidoException("Pokemon deve ter pelo menos 1 tipo!");
        if (tipos.size() > 2) throw new DadoInvalidoException("Pokemon nao pode ter mais de 2 tipos!");
        if (new HashSet<>(tipos).size() != tipos.size()) throw new DadoInvalidoException("Tipos duplicados nao sao permitidos!");
        this.tipos = tipos;
        this.baseStats = baseStats;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Tipo> getTipos() {
        return Collections.unmodifiableList(tipos);
    }
    public void setTipos(List<Tipo> tipos) throws DadoInvalidoException {
        if (tipos == null || tipos.isEmpty()) throw new DadoInvalidoException("Pokemon deve ter pelo menos 1 tipo!");
        if (tipos.size() > 2) throw new DadoInvalidoException("Pokemon nao pode ter mais de 2 tipos!");
        if (new HashSet<>(tipos).size() != tipos.size()) throw new DadoInvalidoException("Tipos duplicados nao sao permitidos!");
        this.tipos = tipos;
    }
    public Stats getBaseStats() {
        return baseStats;
    }
    public void setBaseStats(Stats baseStats) {
        this.baseStats = baseStats;
    }
    public Stats getStats() {
        return stats;
    }
    public void setStats(Stats baseStats, Nature nature, int nivel) throws DadoInvalidoException {
        int hpBase = baseStats.getHp();
        int atkBase = baseStats.getAtaque();
        int defBase = baseStats.getDefesa();
        int spAtkBase = baseStats.getAtaqueEspecial();
        int spDefBase = baseStats.getDefesaEspecial();
        int speedBase = baseStats.getVelocidade();
        int hp = (int) Math.floor(((hpBase * 2) * nivel) / 100) + nivel + 10;
        int atk = (int) Math.floor(((atkBase * 2) * nivel) / 100) + 5;
        int def = (int) Math.floor(((defBase * 2) * nivel) / 100) + 5;
        int spAtk = (int) Math.floor(((spAtkBase * 2) * nivel) / 100) + 5;
        int spDef = (int) Math.floor(((spDefBase * 2) * nivel) / 100) + 5;
        int speed = (int) Math.floor(((speedBase * 2) * nivel) / 100) + 5;
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
        this.stats = new Stats(hp, atk, def, spAtk, spDef, speed);
    }
    public Nature getNature() {
        return nature;
    }
    public void setNature(Nature nature) {
        this.nature = nature;
    }
    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) throws DadoInvalidoException {
        if (nivel < 1 || nivel > 100) throw new DadoInvalidoException("Pokemon deve ser de nivel 1 a 100!");
        this.nivel = nivel;
    }
    public  List<Golpe> getGolpes() {
        return golpes;
    }
    public void setGolpes(List<Golpe> golpes) throws DadoInvalidoException {
        if (golpes == null || golpes.isEmpty()) throw new DadoInvalidoException("Pokemon deve ter pelo menos 1 golpe!");
        if (golpes.size() > 4) throw new DadoInvalidoException("Pokemon nao pode ter mais de 4 golpes!");
        if (new HashSet<>(golpes).size() != golpes.size()) throw new DadoInvalidoException("Golpes duplicados nao sao permitidos!");
        this.golpes = golpes;
    }
    public int calcularBST() {
        int hpBase = baseStats.getHp();
        int atkBase = baseStats.getAtaque();
        int defBase = baseStats.getDefesa();
        int spAtkBase = baseStats.getAtaqueEspecial();
        int spDefBase = baseStats.getDefesaEspecial();
        int speedBase = baseStats.getVelocidade();
        return hpBase + atkBase + defBase + spAtkBase + spDefBase + speedBase;
    }
    public String toFileString() {
        StringBuilder tipoString = new StringBuilder();
        for (Tipo tipo : tipos) {
            tipoString.append(tipo);
            tipoString.append(",");
        }
        if (tipoString.length() > 0) tipoString.setLength(tipoString.length() - 1);
        String statString = baseStats.getHp() + "," +  baseStats.getAtaque() + "," + baseStats.getDefesa() + "," + baseStats.getAtaqueEspecial() + "," + baseStats.getDefesaEspecial() + "," + baseStats.getVelocidade();
        return id + ";" + nome + ";" + tipoString + ";" + statString;
    }
}
