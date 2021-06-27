import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConta{

    private Conta conta;

    @BeforeEach
    public void inicializacao(){
        conta = new Conta(1001, 2000);
    }

    @Test
    public void criarConta(){
        assertEquals(1001, conta.getNumero(), "O numero da conta deve igual ao informado na criacao");
        assertEquals(100, conta.getLimite(), 0.01, "O valor do limite padrao de uma conta 100");
        assertEquals(2100, conta.getSaldo(), 0.01, "O saldo deve ser igual ao limite + o saldo");

        conta = new Conta(1010, 1000);

        assertEquals(1010, conta.getNumero(), "O numero da conta deve igual ao informado na criacao");
        assertEquals(100, conta.getLimite(), 0.01, "O valor do limite padrao de uma conta 100");
        assertEquals(1100, conta.getSaldo(), 0.01, "O saldo deve ser igual ao limite + o saldo");
    }

    @Test
    public void verificarSaldo(){
        assertEquals(2100, conta.getSaldo(), 0.01, "O saldo deve ser igual ao limite + o saldo");
    }

    @Test
    public void usuarionNaoPodeRealizarSaquesNegativos(){
        assertFalse(conta.sacar(-100), "Sacar valores negativos nao deve ser permitido");
        assertEquals(2100, conta.getSaldo(), 0.01, "O saldo deve permanecer inalterado");
    }

    @Test
    public void usuarioNaoPoderSacarQuantiasMaioresQueOSaldo(){
        assertFalse(conta.sacar(5000), "Saldo da conta insuficiente");
        assertEquals(2100, conta.getSaldo(), 0.01, "O saldo deve permanecer inalterado");
    }

    @Test
    public void usuarioPodeSacarValoresUtilizandoOLimite(){
        assertTrue(conta.sacar(2050), "Ao sacar um valor o limite da conta deve ser considerado");
        assertEquals( 50, conta.getSaldo(), 0.01, "Valor sacado maior que o saldo");
        assertEquals(50, conta.getLimite(), 0.01, "O limite foi utilizado na operacao");
    }

    @Test
    public void usuarioPodeSacarValoresSemUtilizarLimite(){
        assertTrue(conta.sacar(1000), "Ao sacar um valor o limite da conta deve ser considerado");
        assertEquals(1100, conta.getSaldo(), 0.01, "Valor sacado maior que o saldo");
        assertEquals(100, conta.getLimite(), 0.01, "O limite nao foi utilizado na operacao");
    }

    @Test
    public void usuarionNaoDepositarValoresNegativos(){
        assertFalse(conta.depositar(-100), "Depositar valores negativos nao deve ser permitido");
        assertEquals(2100, conta.getSaldo(), 0.01, "O saldo deve permanecer inalterado");
    }

    @Test
    public void usuarioPodeDepositarValoresPositivos(){
        assertTrue(conta.depositar(900), "Depositar valores nao negativos deve ser permitido");
        assertEquals(3000, conta.getSaldo(), 0.01, "O saldo nao atualizado corretamente");
    }

    @Test
    public void depositosEmContasComLimiteUtilizadoDevemRestaurarOLimite(){
        conta.sacar(2100);
        assertTrue(conta.depositar(50), "Depositar valores positivos sao permitidos");
        assertEquals(50, conta.getSaldo(), 0.01, "O saldo nao atualizado corretamente");
        assertEquals(50, conta.getLimite(), 0.01, "Limite nao restaurado");

        assertTrue(conta.depositar(100), "Depositar valores positivos sao permitidos");
        assertEquals(150, conta.getSaldo(), 0.01, "O saldo nao atualizado corretamente");
        assertEquals(100, conta.getLimite(), 0.01, "Limite nao restaurado");

    }

    @Test
    public void usuarioNaoPodeTransferirQuantiasNegativas(){
        Conta destino = new Conta(10, 0);
        assertFalse(conta.transferir(destino, -50), "Transferir valores negativos nao e permitido");
    }

    @Test
    public void usuarioNaoPodeTransferirQuantiasMaioresQueOSaldo(){
        Conta destino = new Conta(10, 0);
        assertFalse(conta.transferir(destino, 3000), "Transferir valores negativos nao e permitido");
        assertEquals(2100, conta.getSaldo(), 0.01, "O saldo deve permanecer inalterado");
        assertEquals(100, destino.getSaldo(), 0.01, "O saldo deve permanecer inalterado");
    }

    @Test
    public void usuarioPodeTransferirQuantiasUtilizandoLimite(){
        Conta destino = new Conta(10, 0);
        assertTrue(conta.transferir(destino, 2050), "Transferir valores negativos nao e permitido");
        assertEquals(50, conta.getSaldo(), 0.01, "Valor sacado maior que o saldo");
        assertEquals(50, conta.getLimite(), 0.01, "O limite foi utilizado na operacao");
        assertEquals(2150, destino.getSaldo(), 0.01, "O saldo deve permanecer inalterado");
    }

    @Test
    public void usuarioPodeTransferirQuantiasSemUsarLimite(){
        Conta destino = new Conta(10, 0);
        assertTrue(conta.transferir(destino, 1000), "Transferir valores negativos nao e permitido");
        assertEquals(1100, conta.getSaldo(), 0.01, "Valor sacado maior que o saldo");
        assertEquals(100, conta.getLimite(), 0.01, "O limite nao foi utilizado na operacao");
        assertEquals(1100, destino.getSaldo(), 0.01, "O saldo deve permanecer inalterado");
    }

    @Test
    public void verificaExtrato(){
        Conta destino = new Conta(10, 0);
        conta.sacar(200);
        conta.sacar(10000);
        conta.depositar(500);
        conta.transferir(destino, 400);
        conta.transferir(destino, 4000000);
        conta.sacar(1950);
        conta.depositar(50);

        double[] extratoCorreto = {-200.0, 500.0, -400.0, -1950.0, 50.0};
        double[] extrato = conta.verExtrato();
        boolean igual = true;
        for(int i = 0; i < extratoCorreto.length; i++){
            System.out.println(extrato[i] + " " +  extratoCorreto[i]);
            igual &= Double.compare(extrato[i], extratoCorreto[i]) == 0;

        }

        assertTrue(igual, "O extrato nao confere");

    }
}