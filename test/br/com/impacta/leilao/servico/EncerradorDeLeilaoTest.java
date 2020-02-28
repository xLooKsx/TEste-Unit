package br.com.impacta.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;

import br.com.impacta.leilao.builder.CriadorDeLeilao;
import br.com.impacta.leilao.dominio.Leilao;

public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {

        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
            .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
            .naData(antiga).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();

        assertEquals(2, encerrador.getTotalEncerrados());
        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());
    }

}
