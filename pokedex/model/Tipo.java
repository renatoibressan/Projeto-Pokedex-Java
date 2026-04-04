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
            case "normal": return NORMAL;
            case "grama": case "grass": return GRAMA;
            case "fogo": case "fire": return FOGO;
            case "agua": case "water": return AGUA;
            case "eletrico": case "electric": return ELETRICO;
            case "psiquico": case "psychic": return PSIQUICO;
            case "lutador": case "fighting": return LUTADOR;
            case "pedra": case "rock": return PEDRA;
            case "terrestre": case "ground": return TERRESTRE;
            case "voador": case "flying": return VOADOR;
            case "inseto": case "bug": return INSETO;
            case "veneno": case "poison": return VENENO;
            case "sombrio": case "dark": return SOMBRIO;
            case "fantasma": case "ghost": return FANTASMA;
            case "gelo": case "ice": return GELO;
            case "aco": case "steel": return ACO;
            case "dragao": case "dragon": return DRAGAO;
            case "fada": case "fairy": return FADA;
            default: throw new DadoInvalidoException("Tipo invalido!");
        }
    }
}
