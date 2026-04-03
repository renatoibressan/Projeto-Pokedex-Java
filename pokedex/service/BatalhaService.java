package pokedex.service;

import java.util.List;
import java.util.Scanner;

import pokedex.model.*;
import pokedex.util.InputUtils;

public class BatalhaService {
    private TypeEffectivenessService effect;
    public BatalhaService(TypeEffectivenessService effect) {
        this.effect = effect;
    }
    public void batalhar(Pokemon p1, Pokemon p2, Scanner sc) {
        boolean fainted = false;
        int turno = 0, i, opcaoGolpe1, opcaoGolpe2;
        double danoP1, danoP2;
        Golpe golpeP1, golpeP2;
        Pokemon primeiro = definirPrimeiro(p1, p2);
        Pokemon segundo = definirSegundo(p1, p2);
        Pokemon vencedor;
        int vidaP1 = primeiro.getStats().getHp();
        int vidaP2 = segundo.getStats().getHp();
        System.out.println("---------------------------------------------------");
        System.out.println("Batalha entre " + primeiro.getNome() + " e " + segundo.getNome() + " iniciada!");
        while (!fainted) {
            turno++;
            System.out.println(primeiro.getNome() + ": " + vidaP1 + " / " + primeiro.getStats().getHp());
            System.out.println(segundo.getNome() + ": " + vidaP2 + " / " + segundo.getStats().getHp());
            i = 1;
            for (Golpe g : primeiro.getGolpes()) {
                System.out.println(i + ". " + g);
                i++;
            }
            opcaoGolpe1 = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            while (opcaoGolpe1 != 1 && opcaoGolpe1 != 2) {
                opcaoGolpe1 = InputUtils.lerInt("Opcao invalida!\nInsira uma das opcoes acima: ", sc);
            }
            if (opcaoGolpe1 == 1) golpeP1 = primeiro.getGolpes().getFirst();
            else golpeP1 = primeiro.getGolpes().getLast();
            if (golpeP1.getCategoria().equals("fisico")) danoP1 = calcularDanoFisico(primeiro, segundo, golpeP1);
            else danoP1 = calcularDanoEspecial(primeiro, segundo, golpeP1);
            danoP1 += calcularSTAB(primeiro, golpeP1, danoP1);
            danoP1 *= calcularEficaciaDeTipo(golpeP1.getTipo(), segundo.getTipos());
            vidaP2 -= (int) danoP1;
            if (vidaP2 <= 0) {
                System.out.println("O Pokemon " + segundo.getNome() + " desmaiou!");
                vencedor = primeiro;
                System.out.println("O Pokemon vencedor foi " + vencedor.getNome() + "!");
                fainted = true;
            }
            System.out.println(primeiro.getNome() + ": " + vidaP1 + " / " + primeiro.getStats().getHp());
            System.out.println(segundo.getNome() + ": " + vidaP2 + " / " + segundo.getStats().getHp());
            i = 1;
            for (Golpe g : segundo.getGolpes()) {
                System.out.println(i + ". " + g);
                i++;
            }
            opcaoGolpe2 = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            while (opcaoGolpe2 != 1 && opcaoGolpe2 != 2) {
                opcaoGolpe2 = InputUtils.lerInt("Opcao invalida! Insira uma das opcoes acima: ", sc);
            }
            if (opcaoGolpe2 == 1) golpeP2 = segundo.getGolpes().getFirst();
            else golpeP2 = segundo.getGolpes().getLast();
            if (golpeP2.getCategoria().equals("fisico")) danoP2 = calcularDanoFisico(segundo, primeiro, golpeP2);
            else danoP2 = calcularDanoEspecial(segundo, primeiro, golpeP2);
            danoP2 += calcularSTAB(segundo, golpeP2, danoP2);
            danoP2 *= calcularEficaciaDeTipo(golpeP2.getTipo(), primeiro.getTipos());
            vidaP1 -= (int) danoP2;
            if (vidaP1 <= 0) {
                System.out.println("O Pokemon " + primeiro.getNome() + " desmaiou!");
                vencedor = segundo;
                System.out.println("O Pokemon vencedor foi " + vencedor.getNome() + "!");
                fainted = true;
            }
        }
        System.out.println("Turnos executados: " + turno);
        System.out.println("---------------------------------------------------");
    }
    public double calcularEficaciaDeTipo(Tipo atacante, List<Tipo> defensor) {
        double multiplicador = 1.0;
        for (Tipo t : defensor) {
            multiplicador *= effect.getMultiplicador(atacante, t);
        }
        return multiplicador;
    }
    public double calcularSTAB(Pokemon atacante, Golpe golpe, double dano) {
        for (Tipo t : atacante.getTipos()) {
            if (golpe.getTipo() == t) return dano * 0.5;
        }
        return 0.0;
    }
    public double calcularDanoFisico(Pokemon atacante, Pokemon defensor, Golpe golpe) {
        double d1 = ((2 * atacante.getNivel()) / 5) + 2;
        double d2 = (golpe.getDanoBase() * atacante.getStats().getAtaque()) / defensor.getStats().getDefesa();
        return ((d1 * d2) / 50) + 2;
    }
    public double calcularDanoEspecial(Pokemon atacante, Pokemon defensor, Golpe golpe) {
        double d1 = ((2 * atacante.getNivel()) / 5) + 2;
        double d2 = (golpe.getDanoBase() * atacante.getStats().getAtaqueEspecial()) / defensor.getStats().getDefesaEspecial();
        return ((d1 * d2) / 50) + 2;
    }
    public Pokemon definirPrimeiro(Pokemon p1, Pokemon p2) {
        if (p1.getStats().getVelocidade() >= p2.getStats().getVelocidade()) return p1;
        else return p2;
    }
    public Pokemon definirSegundo(Pokemon p1, Pokemon p2) {
        Pokemon primeiro = definirPrimeiro(p1, p2);
        if (primeiro == p1) return p2;
        else return p1;
    }
}
