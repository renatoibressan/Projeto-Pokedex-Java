package pokedex.model;

import pokedex.exception.DadoInvalidoException;

public enum Tipo {
    GRAMA,
    FOGO,
    AGUA,
    NORMAL,
    ELETRICO,
    PSIQUICO,
    LUTADOR,
    PEDRA,
    TERRESTRE,
    VOADOR,
    INSETO,
    VENENO,
    SOMBRIO,
    FANTASMA,
    GELO,
    ACO,
    DRAGAO,
    FADA;
    public static Tipo fromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "grama": return GRAMA;
            case "fogo": return FOGO;
            case "agua": return AGUA;
            case "normal": return NORMAL;
            case "eletrico": return ELETRICO;
            case "psiquico": return PSIQUICO;
            case "lutador": return LUTADOR;
            case "pedra": return PEDRA;
            case "terrestre": return TERRESTRE;
            case "voador": return VOADOR;
            case "inseto": return INSETO;
            case "veneno": return VENENO;
            case "sombrio": return SOMBRIO;
            case "fantasma": return FANTASMA;
            case "gelo": return GELO;
            case "aco": return ACO;
            case "dragao": return DRAGAO;
            case "fada": return FADA;
            default: throw new DadoInvalidoException("Tipo invalido!");
        }
    }
}
