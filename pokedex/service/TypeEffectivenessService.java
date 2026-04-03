package pokedex.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pokedex.exception.DadoInvalidoException;
import pokedex.model.Tipo;
import pokedex.util.FileUtils;

public class TypeEffectivenessService {
    private String caminhoArquivo;
    private Map<Tipo, Map<Tipo, Double>> efetividade;
    public TypeEffectivenessService(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        efetividade = new HashMap<>();
    }
    public double getMultiplicador(Tipo atacante, Tipo defensor) {
        if (!efetividade.containsKey(atacante)) return 1.0;
        Map<Tipo, Double> mapaInterno = efetividade.get(atacante);
        if (!mapaInterno.containsKey(defensor)) return 1.0;
        double multiplicador = mapaInterno.get(defensor);
        return multiplicador;
    }
    public void extrairDeArquivo() throws IOException {
        List<String> linhas = FileUtils.ler(caminhoArquivo);
        Tipo atacante, defensor;
        double multiplicador;
        int linhaNumero = 0;
        for (String linha : linhas) {
            linhaNumero++;
            try {
                String[] partes = linha.split(";");
                if (partes.length != 3) throw new IllegalArgumentException("Formato invalido!");
                atacante = Tipo.fromString(partes[0]);
                defensor = Tipo.fromString(partes[1]);
                multiplicador = Double.parseDouble(partes[2]);
                efetividade.computeIfAbsent(atacante, k -> new HashMap<>()).put(defensor, multiplicador);
            } catch (DadoInvalidoException e) {
                System.out.println("Linha " + linhaNumero + " invalida!");
            }
        }
    }
}
