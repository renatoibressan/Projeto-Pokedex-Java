package pokedex.model;

public class Stats {
    private int hp;
    private int ataque;
    private int defesa;
    private int ataqueEspecial;
    private int defesaEspecial;
    private int velocidade;
    public Stats(int hp, int ataque, int defesa, int ataqueEspecial, int defesaEspecial, int velocidade) {
        this.hp = hp;
        this.ataque = ataque;
        this.defesa = defesa;
        this.ataqueEspecial = ataqueEspecial;
        this.defesaEspecial = defesaEspecial;
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
