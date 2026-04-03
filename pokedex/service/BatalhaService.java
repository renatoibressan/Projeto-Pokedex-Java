package pokedex.service;

import java.util.List;
import java.util.Scanner;

import pokedex.model.*;
import pokedex.util.InputUtils;
import pokedex.util.PrintUtils;

public class BatalhaService {
    private TypeEffectivenessService effect;
    public BatalhaService(TypeEffectivenessService effect) {
        this.effect = effect;
    }
    public Pokemon batalhar(Pokemon p1, Pokemon p2, Scanner sc) throws InterruptedException {
        int turno = 0, i, opcaoGolpe, hpPerdido = 0;
        double danoP1, danoP2;
        Golpe golpeP1, golpeP2;
        Pokemon primeiro = definirPrimeiro(p1, p2);
        Pokemon segundo = definirSegundo(p1, p2);
        Pokemon vencedor;
        int vidaP1 = primeiro.getStats().getHp();
        int vidaP2 = segundo.getStats().getHp();
        PrintUtils.clearScreen();
        PrintUtils.slowPrint("---------------------------------------------------", 250);
        PrintUtils.slowPrint("Batalha entre " + primeiro.getNome() + " e " + segundo.getNome() + " iniciada!", 375);
        while (true) {
            turno++;
            System.out.print("\n");
            System.out.println(primeiro.getNome() + " Lv." + primeiro.getNivel() + ": " + vidaP1 + " / " + primeiro.getStats().getHp());
            System.out.println(segundo.getNome() + " Lv." + segundo.getNivel() + ": " + vidaP2 + " / " + segundo.getStats().getHp());
            System.out.print("\n");
            System.out.println("Golpes de " + primeiro.getNome() + ":");
            i = 1;
            for (Golpe g : primeiro.getGolpes()) {
                System.out.println(i + ". " + g);
                i++;
            }
            opcaoGolpe = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            while (opcaoGolpe < 1 || opcaoGolpe > 2) {
                opcaoGolpe = InputUtils.lerInt("Opcao invalida!\nInsira uma das opcoes acima: ", sc);
            }
            if (opcaoGolpe == 1) golpeP1 = primeiro.getGolpes().getFirst();
            else golpeP1 = primeiro.getGolpes().getLast();
            if (golpeP1.getCategoria().equals("fisico")) danoP1 = calcularDanoFisico(primeiro, segundo, golpeP1);
            else danoP1 = calcularDanoEspecial(primeiro, segundo, golpeP1);
            danoP1 += calcularSTAB(primeiro, golpeP1, danoP1);
            danoP1 *= calcularEficaciaDeTipo(golpeP1.getTipo(), segundo.getTipos());
            vidaP2 -= (int) danoP1;
            hpPerdido = (int) danoP1;
            if (hpPerdido > vidaP2) hpPerdido += vidaP2;
            if (hpPerdido == 0) PrintUtils.slowPrint("\nO golpe " + golpeP1 + " nao fez efeito em " + segundo.getNome() + "!", 250);
            else PrintUtils.slowPrint("\nO Pokemon " + segundo.getNome() + " perdeu " + hpPerdido + " pontos de vida!", 250);
            if (vidaP2 <= 0) {
                if (turno == 1) PrintUtils.slowPrint("O golpe " + golpeP1 + " foi um OH-KO!", 250);
                else PrintUtils.slowPrint("\nO Pokemon " + segundo.getNome() + " desmaiou em " + turno + " turnos!", 250);
                PrintUtils.slowPrint("---------------------------------------------------", 250);
                vencedor = primeiro;
                return vencedor;
            }
            System.out.print("\n");
            System.out.println(primeiro.getNome() + " Lv." + primeiro.getNivel() + ": " + vidaP1 + " / " + primeiro.getStats().getHp());
            System.out.println(segundo.getNome() + " Lv." + segundo.getNivel() + ": " + vidaP2 + " / " + segundo.getStats().getHp());
            System.out.print("\n");
            System.out.println("Golpes de " + segundo.getNome() + ":");
            i = 1;
            for (Golpe g : segundo.getGolpes()) {
                System.out.println(i + ". " + g);
                i++;
            }
            opcaoGolpe = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            while (opcaoGolpe < 1 || opcaoGolpe > 2) {
                opcaoGolpe = InputUtils.lerInt("Opcao invalida! Insira uma das opcoes acima: ", sc);
            }
            if (opcaoGolpe == 1) golpeP2 = segundo.getGolpes().getFirst();
            else golpeP2 = segundo.getGolpes().getLast();
            if (golpeP2.getCategoria().equals("fisico")) danoP2 = calcularDanoFisico(segundo, primeiro, golpeP2);
            else danoP2 = calcularDanoEspecial(segundo, primeiro, golpeP2);
            danoP2 += calcularSTAB(segundo, golpeP2, danoP2);
            danoP2 *= calcularEficaciaDeTipo(golpeP2.getTipo(), primeiro.getTipos());
            vidaP1 -= (int) danoP2;
            hpPerdido = (int) danoP2;
            if (hpPerdido > vidaP1) hpPerdido += vidaP1;
            if (hpPerdido == 0) PrintUtils.slowPrint("\nO golpe " + golpeP2 + " nao fez efeito em " + primeiro.getNome() + "!", 250);
            else PrintUtils.slowPrint("\nO Pokemon " + primeiro.getNome() + " perdeu " + hpPerdido + " pontos de vida!", 250);
            if (vidaP1 <= 0) {
                if (turno == 1) PrintUtils.slowPrint("O golpe " + golpeP2 + " foi um OH-KO!", 250);
                else PrintUtils.slowPrint("\nO Pokemon " + primeiro.getNome() + " desmaiou em " + turno + " turnos!", 250);
                PrintUtils.slowPrint("---------------------------------------------------", 250);
                vencedor = segundo;
                return vencedor;
            }
        }
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
