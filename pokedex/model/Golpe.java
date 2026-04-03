package pokedex.model;

import pokedex.exception.DadoInvalidoException;

public enum Golpe {
    SEED_BOMB(Tipo.GRAMA, "fisico", 80),
    FLAME_CHARGE(Tipo.FOGO, "fisico", 80),
    WATERFALL(Tipo.AGUA, "fisico", 80),
    RETURN(Tipo.NORMAL, "fisico", 102),
    WILD_CHARGE(Tipo.ELETRICO, "fisico", 95),
    ZEN_HEADBUTT(Tipo.PSIQUICO, "fisico", 80),
    CLOSE_COMBAT(Tipo.LUTADOR, "fisico", 120),
    STONE_EDGE(Tipo.PEDRA, "fisico", 100),
    EARTHQUAKE(Tipo.TERRESTRE, "fisico", 100),
    FLY(Tipo.VOADOR, "fisico", 90),
    LEECH_LIFE(Tipo.INSETO, "fisico", 80),
    POISON_JAB(Tipo.VENENO, "fisico", 80),
    CRUNCH(Tipo.SOMBRIO, "fisico", 80),
    SHADOW_CLAW(Tipo.FANTASMA, "fisico", 80),
    ICICLE_CRASH(Tipo.GELO, "fisico", 85),
    IRON_HEAD(Tipo.ACO, "fisico", 80),
    OUTRAGE(Tipo.DRAGAO, "fisico", 120),
    PLAY_ROUGH(Tipo.FADA, "fisico", 90),
    ENERGY_BALL(Tipo.GRAMA, "especial", 90),
    FLAMETHROWER(Tipo.FOGO, "especial", 90),
    SURF(Tipo.AGUA, "especial", 90),
    HYPER_BEAM(Tipo.NORMAL, "especial", 150),
    THUNDERBOLT(Tipo.ELETRICO, "especial", 90),
    PSYCHIC(Tipo.PSIQUICO, "especial", 90),
    FOCUS_BLAST(Tipo.LUTADOR, "especial", 120),
    POWER_GEM(Tipo.PEDRA, "especial", 80),
    EARTH_POWER(Tipo.TERRESTRE, "especial", 90),
    HURRICANE(Tipo.VOADOR, "especial", 110),
    BUG_BUZZ(Tipo.INSETO, "especial", 90),
    SLUDGE_BOMB(Tipo.VENENO, "especial", 90),
    DARK_PULSE(Tipo.SOMBRIO, "especial", 80),
    SHADOW_BALL(Tipo.FANTASMA, "especial", 80),
    ICE_BEAM(Tipo.GELO, "especial", 90),
    FLASH_CANNON(Tipo.ACO, "especial", 90),
    DRACO_METEOR(Tipo.DRAGAO, "especial", 130),
    MOONBLAST(Tipo.FADA, "especial", 95);
    private final Tipo tipo;
    private final String categoria;
    private final int danoBase;
    Golpe(Tipo tipo, String categoria, int danoBase) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.danoBase = danoBase;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public String getCategoria() {
        return categoria;
    }
    public int getDanoBase() {
        return danoBase;
    }
    public static Golpe fromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "seed bomb": return SEED_BOMB;
            case "energy ball": return ENERGY_BALL;
            case "flame charge": return FLAME_CHARGE;
            case "flamethrower": return FLAMETHROWER;
            case "waterfall": return WATERFALL;
            case "surf": return SURF;
            case "return": return RETURN;
            case "hyper beam": return HYPER_BEAM;
            case "wild charge": return WILD_CHARGE;
            case "thunderbolt": return THUNDERBOLT;
            case "zen headbutt": return ZEN_HEADBUTT;
            case "psychic": return PSYCHIC;
            case "close combat": return CLOSE_COMBAT;
            case "focus blast": return FOCUS_BLAST;
            case "stone edge": return STONE_EDGE;
            case "power gem": return POWER_GEM;
            case "earthquake": return EARTHQUAKE;
            case "earth power": return EARTH_POWER;
            case "fly": return FLY;
            case "hurricane": return HURRICANE;
            case "leech life": return LEECH_LIFE;
            case "bug buzz": return BUG_BUZZ;
            case "poison jab": return POISON_JAB;
            case "sludge bomb": return SLUDGE_BOMB;
            case "crunch": return CRUNCH;
            case "dark pulse": return DARK_PULSE;
            case "shadow claw": return SHADOW_CLAW;
            case "shadow ball": return SHADOW_BALL;
            case "icicle crash": return ICICLE_CRASH;
            case "ice beam": return ICE_BEAM;
            case "iron head": return IRON_HEAD;
            case "flash cannon": return FLASH_CANNON;
            case "outrage": return OUTRAGE;
            case "draco meteor": return DRACO_METEOR;
            case "play rough": return PLAY_ROUGH;
            case "moonblast": return MOONBLAST;
            default: throw new DadoInvalidoException("Ataque invalido!");
        }
    }
}
