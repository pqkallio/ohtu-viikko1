package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void josTilavuusOnNollaTilavuusOnNolla1() {
        Varasto v = new Varasto(0);
        assertEquals(v.getTilavuus(), 0, vertailuTarkkuus);
    }
    
    @Test
    public void josTilavuusOnAlleNollaTilavuusOnNolla1() {
        Varasto v = new Varasto(-0.1);
        assertEquals(v.getTilavuus(), 0, vertailuTarkkuus);
    }
    
    @Test
    public void josTilavuusOnNollaTilavuusOnNolla2() {
        Varasto v = new Varasto(0, 0);
        assertEquals(v.getTilavuus(), 0, vertailuTarkkuus);
    }
    
    @Test
    public void josTilavuusOnAlleNollaTilavuusOnNolla2() {
        Varasto v = new Varasto(-0.1, 0);
        assertEquals(v.getTilavuus(), 0, vertailuTarkkuus);
    }
    
    @Test
    public void josTilavuusOnYliNollaTilavuusOnOikein() {
        Varasto v = new Varasto(0.1, 0);
        assertEquals(v.getTilavuus(), 0.1, vertailuTarkkuus);
    }
    
    @Test
    public void josAlkusaldoOnAlleNollaSaldoOnNolla() {
        Varasto v = new Varasto(10, -0.1);
        assertEquals(v.getSaldo(), 0.0, vertailuTarkkuus);
    }
    
    @Test
    public void josAlkusaldoOnNollaSaldoOnNolla() {
        Varasto v = new Varasto(10, 0);
        assertEquals(v.getSaldo(), 0.0, vertailuTarkkuus);
    }
    
    @Test
    public void josAlkusaldoOnAlleTilavuudenSaldoOnOikein() {
        Varasto v = new Varasto(10, 1);
        assertEquals(v.getSaldo(), 1.0, vertailuTarkkuus);
    }
    
    @Test
    public void josAlkusaldoOnSamanVerranKuinTilavuusSaldoOnOikein() {
        Varasto v = new Varasto(10, 10);
        assertEquals(v.getSaldo(), 10.0, vertailuTarkkuus);
    }
    
    @Test
    public void josAlkusaldoOnSuurempiKuinTilavuusSaldoOnTilavuus() {
        Varasto v = new Varasto(10, 15);
        assertTrue(v.getSaldo() == v.getTilavuus());
    }
    
    @Test
    public void negatiivisenMaaranLisaaminenEiVaikutaSaldoon() {
        assertEquals(varasto.getSaldo(), 0, vertailuTarkkuus);
        varasto.lisaaVarastoon(3);
        assertEquals(varasto.getSaldo(), 3, vertailuTarkkuus);
        varasto.lisaaVarastoon(-3);
        assertEquals(varasto.getSaldo(), 3, vertailuTarkkuus);
    }
    
    @Test
    public void yliTilavuudenLisaaminenAiheuttaaSaldonTayttymisen() {
        assertEquals(varasto.getSaldo(), 0, vertailuTarkkuus);
        varasto.lisaaVarastoon(3);
        assertEquals(varasto.getSaldo(), 3, vertailuTarkkuus);
        varasto.lisaaVarastoon(100);
        assertEquals(varasto.getSaldo(), 10, vertailuTarkkuus);
    }
    
    @Test
    public void negatiivisenMaaranOttaminenEiVaikutaSaldoon() {
        varasto.lisaaVarastoon(3);
        assertEquals(varasto.getSaldo(), 3, vertailuTarkkuus);
        double otettu = varasto.otaVarastosta(-3);
        assertEquals(otettu, 0, vertailuTarkkuus);
        assertEquals(varasto.getSaldo(), 3, vertailuTarkkuus);
    }
    
    @Test
    public void yliSaldonOttaminenTyhjentaaVaraston() {
        varasto.lisaaVarastoon(3);
        assertEquals(varasto.getSaldo(), 3, vertailuTarkkuus);
        double otettu = varasto.otaVarastosta(50);
        assertEquals(otettu, 3, vertailuTarkkuus);
        assertEquals(varasto.getSaldo(), 0, vertailuTarkkuus);
    }
    
    @Test
    public void toStringToimiiOikein() {
        varasto.lisaaVarastoon(3);
        double saldo = varasto.getSaldo();
        double tilaa = varasto.paljonkoMahtuu();
        
        String toString = varasto.toString();
        String vertailu = "saldo = " + saldo + ", vielä tilaa " + tilaa;
    }
}