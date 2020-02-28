package br.com.impacta.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.impacta.leilao.builder.CriadorDeLeilao;
import br.com.impacta.leilao.dominio.Leilao;

@RunWith(MockitoJUnitRunner.class)
public class EncerradorDeLeilaoTest {
	
	private List<Leilao> leiloes;

	@Before
    public void setUp() throws Exception {
		
		Calendar data = Calendar.getInstance();
        data.set(1999, 1, 20);
		
		leiloes = Arrays.asList(new Leilao[] {
				new CriadorDeLeilao().para("desc1").naData(new Calendar.Builder().setDate(2020, Calendar.FEBRUARY, 1).build()).constroi(), 
	            new CriadorDeLeilao().para("desc2").naData(Calendar.getInstance()).constroi()});
		
		
	}
	
    @Test
    public void encerrarLeiloes() {

        
    	RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(leiloes);
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();

        assertEquals(1, encerrador.getTotalEncerrados());
        assertEquals(true, leiloes.get(0).isEncerrado());
        assertEquals(false, leiloes.get(1).isEncerrado());
    }

}
