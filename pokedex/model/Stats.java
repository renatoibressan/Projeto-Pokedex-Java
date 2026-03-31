package pokedex.model;

import pokedex.exception.DadoInvalidoException;

public class Stats {
    private int hp;
    private int ataque;
    private int defesa;
    private int ataqueEspecial;
    private int defesaEspecial;
    private int velocidade;
    public Stats(int hp, int ataque, int defesa, int ataqueEspecial, int defesaEspecial, int velocidade) throws DadoInvalidoException {
        if (hp < 1) throw new DadoInvalidoException("Stat de hp invalido!");
        this.hp = hp;
        if (ataque < 1) throw new DadoInvalidoException("Stat de ataque invalido!");
        this.ataque = ataque;
        if (defesa < 1) throw new DadoInvalidoException("Stat de defesa invalido!");
        this.defesa = defesa;
        if (ataqueEspecial < 1) throw new DadoInvalidoException("Stat de ataque especial invalido!");
        this.ataqueEspecial = ataqueEspecial;
        if (defesaEspecial < 1) throw new DadoInvalidoException("Stat de defesa especial invalido!");
        this.defesaEspecial = defesaEspecial;
        if (velocidade < 1) throw new DadoInvalidoException("Stat de velocidade invalido!");
        this.velocidade = velocidade;
    }
    public int getHp() {
        return hp;
    }
    public int getAtaque() {
        return ataque;
    }
    public int getDefesa() {
        return defesa;
    }
    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }
    public int getDefesaEspecial() {
        return defesaEspecial;
    }
    public int getVelocidade() {
        return velocidade;
    }
    public String toString() {
        return hp + "/" + ataque + "/" + defesa + "/" + ataqueEspecial + "/" + defesaEspecial + "/" + velocidade;
    }
}
