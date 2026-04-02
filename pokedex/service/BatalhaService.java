package pokedex.service;

import pokedex.model.*;

public class BatalhaService {
    // public void batalhar(Pokemon p1, Pokemon p2) {}
    public Pokemon definirPrimeiro(Pokemon p1, Pokemon p2) {
        if (p1.getStats().getVelocidade() >= p2.getStats().getVelocidade()) return p1;
        else return p2;
    }
    public int calcularDanoFisico(Pokemon atacante, Pokemon defensor) {
        int d1 = ((2 * atacante.getNivel()) / 5) + 2;
        int d2 = (60 * atacante.getStats().getAtaque()) / defensor.getStats().getDefesa();
        return ((d1 * d2) / 50) + 2;
    }
    public int calcularDanoEspecial(Pokemon atacante, Pokemon defensor) {
        int d1 = ((2 * atacante.getNivel()) / 5) + 2;
        int d2 = (60 * atacante.getStats().getAtaqueEspecial()) / defensor.getStats().getDefesaEspecial();
        return ((d1 * d2) / 50) + 2;
    }
    // public boolean vantagemTipo(Tipo t1, Tipo t2) {}
}
