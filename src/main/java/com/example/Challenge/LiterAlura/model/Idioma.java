package com.example.Challenge.LiterAlura.model;

public enum Idioma {
    EN("en", "Inglês"),
    PT("pt", "Português"),
    FR("fr", "Francês"),
    ES("es", "Espanhol"),
    DE("de", "Alemão"),
    IT("it", "Italiano"),
    RU("ru", "Russo"),
    ZH("zh", "Chinês"),
    JA("ja", "Japonês"),
    KO("ko", "Coreano"),
    AR("ar", "Árabe"),
    HI("hi", "Hindi"),
    HE("he", "Hebraico"),
    NL("nl", "Holandês"),
    SV("sv", "Sueco"),
    DA("da", "Dinamarquês"),
    NO("no", "Norueguês"),
    FI("fi", "Finlandês"),
    EL("el", "Grego"),
    TR("tr", "Turco"),
    PL("pl", "Polonês"),
    CS("cs", "Tcheco"),
    HU("hu", "Húngaro"),
    RO("ro", "Romeno"),
    UK("uk", "Ucraniano"),
    VI("vi", "Vietnamita"),
    TH("th", "Tailandês"),
    MS("ms", "Malaio"),
    ID("id", "Indonésio"),
    FA("fa", "Persa"),
    BN("bn", "Bengali"),
    PA("pa", "Punjabi"),
    TE("te", "Telugu"),
    MR("mr", "Marathi"),
    TA("ta", "Tâmil"),
    KN("kn", "Canarês"),
    ML("ml", "Malaiala"),
    GU("gu", "Gujarati"),
    UR("ur", "Urdu"),
    AF("af", "Africâner"),
    AM("am", "Amárico"),
    AZ("az", "Azerbaijano"),
    BE("be", "Bielorrusso"),
    BG("bg", "Búlgaro"),
    BS("bs", "Bósnio"),
    CA("ca", "Catalão"),
    CO("co", "Corso"),
    CY("cy", "Galês"),
    EO("eo", "Esperanto"),
    ET("et", "Estoniano"),
    EU("eu", "Basco"),
    GA("ga", "Irlandês"),
    GL("gl", "Galego"),
    HA("ha", "Hauçá"),
    HAW("haw", "Havaiano"),
    HR("hr", "Croata"),
    HT("ht", "Haitiano"),
    HY("hy", "Armênio"),
    IG("ig", "Igbo"),
    IS("is", "Islandês"),
    KA("ka", "Georgiano"),
    KK("kk", "Cazaque"),
    KM("km", "Cambojano"),
    KU("ku", "Curdo"),
    KY("ky", "Quirguiz"),
    LA("la", "Latim"),
    LB("lb", "Luxemburguês"),
    LO("lo", "Laosiano"),
    LT("lt", "Lituano"),
    LV("lv", "Letão"),
    MG("mg", "Malgaxe"),
    MI("mi", "Maori"),
    MN("mn", "Mongol"),
    MY("my", "Birmanês"),
    NE("ne", "Nepalês"),
    NY("ny", "Chichewa"),
    PS("ps", "Pachto"),
    SM("sm", "Samoano"),
    SN("sn", "Shona"),
    SO("so", "Somali"),
    SQ("sq", "Albanês"),
    SR("sr", "Sérvio"),
    ST("st", "Soto"),
    SU("su", "Sundanês"),
    SW("sw", "Swahili"),
    TG("tg", "Tadjique"),
    TK("tk", "Turcomano"),
    TL("tl", "Tagalo"),
    UZ("uz", "Uzbeque"),
    XH("xh", "Xhosa"),
    YI("yi", "Iídiche"),
    YO("yo", "Iorubá"),
    ZU("zu", "Zulu"),
    DESCONHECIDO("desconhecido", "Desconhecido");

    private final String codigoIdioma;
    private final String nomeIdioma;

    Idioma(String codigoIdioma, String nomeIdioma) {
        this.codigoIdioma = codigoIdioma;
        this.nomeIdioma = nomeIdioma;
    }

    public String getCodigoIdioma() {
        return codigoIdioma;
    }

    public String getNomeIdioma() {
        return nomeIdioma;
    }

    public static Idioma setCodigoIdioma(String codigoIdioma) {
        for (Idioma idioma : values()) {
            if (idioma.getCodigoIdioma().equalsIgnoreCase(codigoIdioma)) {
                return idioma;
            }
        }
        return DESCONHECIDO;
    }

    public static String getNomeIdiomaByCodigoIdioma(String codigoIdioma) {
        return setCodigoIdioma(codigoIdioma).getNomeIdioma();
    }
}