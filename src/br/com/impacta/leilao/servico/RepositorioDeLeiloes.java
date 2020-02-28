package br.com.impacta.leilao.servico;

import java.util.List;

import br.com.impacta.leilao.dominio.Leilao;

public interface RepositorioDeLeiloes {
    void salva(Leilao leilao);
    List<Leilao> encerrados();
    List<Leilao> correntes();
    void atualiza(Leilao leilao);
}
