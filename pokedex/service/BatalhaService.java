package pokedex.service;

import java.util.List;
import java.util.Scanner;

import pokedex.model.*;
import pokedex.util.InputUtils;
import pokedex.util.OutputUtils;

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
        int vidaP1 = primeiro.getStats().getHp();
        int vidaP2 = segundo.getStats().getHp();
        OutputUtils.slowPrint("---------------------------------------------------------", 50);
        OutputUtils.slowPrint("Batalha entre " + primeiro.getNome() + " e " + segundo.getNome() + " iniciada!", 50);
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
            while (opcaoGolpe < 1 || opcaoGolpe > 2) opcaoGolpe = InputUtils.lerInt("Opcao invalida!\nInsira uma das opcoes acima: ", sc);
            golpeP1 = (opcaoGolpe == 1) ? primeiro.getGolpes().getFirst() : primeiro.getGolpes().getLast();
            danoP1 = (golpeP1.getCategoria().equals("fisico")) ? calcularDanoFisico(primeiro, segundo, golpeP1) : calcularDanoEspecial(primeiro, segundo, golpeP1);
            danoP1 += calcularSTAB(primeiro, golpeP1, danoP1);
            danoP1 *= calcularEficaciaDeTipo(golpeP1.getTipo(), segundo.getTipos());
            if (calcularEficaciaDeTipo(golpeP1.getTipo(), segundo.getTipos()) <= 0.5) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " nao foi muito eficaz!", 50);
            else if (calcularEficaciaDeTipo(golpeP1.getTipo(), segundo.getTipos()) >= 2) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " foi super-eficaz!", 50);
            hpPerdido = (vidaP2 < (int)danoP1) ? vidaP2 : (int)danoP1;
            vidaP2 -= (int)danoP1;
            if (hpPerdido == 0) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " nao fez efeito em " + segundo.getNome() + "!", 50);
            else OutputUtils.slowPrint("\nO Pokemon " + segundo.getNome() + " perdeu " + hpPerdido + " pontos de vida!", 50);
            if (vidaP2 <= 0) {
                if (turno == 1) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " foi um OH-KO!", 50);
                else OutputUtils.slowPrint("\nO Pokemon " + segundo.getNome() + " desmaiou em " + turno + " turnos!", 50);
                OutputUtils.slowPrint("---------------------------------------------------------", 50);
                return primeiro;
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
            while (opcaoGolpe < 1 || opcaoGolpe > 2) opcaoGolpe = InputUtils.lerInt("Opcao invalida! Insira uma das opcoes acima: ", sc);
            golpeP2 = (opcaoGolpe == 1) ? segundo.getGolpes().getFirst() : segundo.getGolpes().getLast();
            danoP2 = (golpeP2.getCategoria().equals("fisico")) ? calcularDanoFisico(segundo, primeiro, golpeP2) : calcularDanoEspecial(segundo, primeiro, golpeP2);
            danoP2 += calcularSTAB(segundo, golpeP2, danoP2);
            danoP2 *= calcularEficaciaDeTipo(golpeP2.getTipo(), primeiro.getTipos());
            if (calcularEficaciaDeTipo(golpeP2.getTipo(), primeiro.getTipos()) <= 0.5) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " nao foi muito eficaz!", 50);
            else if (calcularEficaciaDeTipo(golpeP2.getTipo(), primeiro.getTipos()) >= 2) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " foi super-eficaz!", 50);
            hpPerdido = (vidaP1 < (int)danoP2) ? vidaP1 : (int)danoP2;
            vidaP1 -= (int)danoP2;
            if (hpPerdido == 0) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " nao fez efeito em " + primeiro.getNome() + "!", 50);
            else OutputUtils.slowPrint("\nO Pokemon " + primeiro.getNome() + " perdeu " + hpPerdido + " pontos de vida!", 50);
            if (vidaP1 <= 0) {
                if (turno == 1) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " foi um OH-KO!", 50);
                else OutputUtils.slowPrint("\nO Pokemon " + primeiro.getNome() + " desmaiou em " + turno + " turnos!", 50);
                OutputUtils.slowPrint("---------------------------------------------------------", 50);
                return segundo;
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
        return (p1.getStats().getVelocidade() >= p2.getStats().getVelocidade()) ? p1 : p2;
    }
    public Pokemon definirSegundo(Pokemon p1, Pokemon p2) {
        return (definirPrimeiro(p1, p2) == p1) ? p2 : p1;
    }
}
