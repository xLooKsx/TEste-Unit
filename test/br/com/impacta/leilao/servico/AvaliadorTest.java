package br.com.impacta.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.impacta.leilao.builder.CriadorDeLeilao;
import br.com.impacta.leilao.dominio.Lance;
import br.com.impacta.leilao.dominio.Leilao;
import br.com.impacta.leilao.dominio.Usuario;
import br.com.impacta.leilao.servico.Avaliador;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private Usuario lucas;
	private Usuario maria;
	private Usuario marcio;

	@Before
	public void initialize() {
		this.leiloeiro = new Avaliador();
		this.marcio = new Usuario("Marcio");
		this.maria = new Usuario("Maria");
		this.lucas = new Usuario("lucas");
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();
		
		leiloeiro.avalia(leilao);
		
	}
	
    @Test
    public void entenderLancesEmOrdemCrescente() {
         
        Leilao leilao = new Leilao("Controle remoto");
         
        leilao.propoe(new Lance(marcio, 250.0));
        leilao.propoe(new Lance(maria, 300.0));
        leilao.propoe(new Lance(lucas, 400.0));
         
        leiloeiro.avalia(leilao);
         
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
    }
 
    @Test
    public void entenderLeilaoComUmLance() {
    	Usuario marcio = new Usuario("Marcio");
        Leilao leilao = new Leilao("TV");
         
        leilao.propoe(new Lance(marcio, 1000.0));
         
        leiloeiro.avalia(leilao);
         
        assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
    }
     
    @Test
    public void encontrarTresMaioresLances() {
        
        Leilao leilao = new CriadorDeLeilao().para("Computador")
        		.lance(marcio, 100.0)
        		.lance(lucas, 200.0)
        		.lance(marcio, 300.0)
        		.lance(lucas, 400.0)
        		.constroi();
         
        leiloeiro.avalia(leilao);
         
        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());
        
        assertThat(maiores, hasItems(
        		new Lance(lucas, 400),
        		new Lance(marcio, 300),
        		new Lance(lucas, 200)
        ));
        
    }
    
    
	
    @Test
    public void entenderLeilaoEmOrdemRandomica() {
       
        Leilao leilao = new Leilao("TV");

        leilao.propoe(new Lance(marcio,200.0));
        leilao.propoe(new Lance(lucas,450.0));
        leilao.propoe(new Lance(marcio,120.0));
        leilao.propoe(new Lance(lucas,700.0));
        leilao.propoe(new Lance(marcio,630.0));
        leilao.propoe(new Lance(lucas,230.0));

     
        leiloeiro.avalia(leilao);

        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
    }
    
    
    @Test
    public void entenderLeilaoEmOrdemDecrescente() {
     
        Leilao leilao = new Leilao("Radio");

        leilao.propoe(new Lance(marcio,400.0));
        leilao.propoe(new Lance(lucas,300.0));
        leilao.propoe(new Lance(marcio,200.0));
        leilao.propoe(new Lance(lucas,100.0));

      
        leiloeiro.avalia(leilao);

        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
    }
    
    

    @Test
    public void devolverTodosOsLances() {
     
        Leilao leilao = new Leilao("Radio");

        leilao.propoe(new Lance(marcio, 100.0));
        leilao.propoe(new Lance(lucas, 200.0));

      
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(2, maiores.size());
        assertEquals(200, maiores.get(0).getValor(), 0.00001);
        assertEquals(100, maiores.get(1).getValor(), 0.00001);
    }

    
    
     
}
