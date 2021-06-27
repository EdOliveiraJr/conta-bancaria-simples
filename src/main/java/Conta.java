import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * @author bruno
 *
 */
public class Conta {
    private int numero;
    private double saldo;
    private double saldoSemLimite;
    private double limite;
    private double[] extrato = new double[10];

    public Conta(int numero, double saldoSemLimite) {
        this.numero = numero;
        this.saldoSemLimite = saldoSemLimite;
        this.limite = 100;
        this.saldo = saldoSemLimite + limite;
        System.out.println("Conta criada!!");
        visao();  
    }

    public int getNumero() {
        return numero;
    }
    
    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public boolean sacar(double valor) {
        if(valor > saldo || valor < 0.00){
            System.out.println("Erro " + valor);
            return false;
        }else if(valor == saldo){
            saldo -= valor;
            saldoSemLimite = 0.0;
            limite = 0.0;
            setExtrato(-valor);
            System.out.println("Sacou " + valor);
            visao();   
            return true;
        }else{
            if(valor <= saldoSemLimite){
                saldoSemLimite -= valor;
                saldo -= valor;
                setExtrato(-valor);
                System.out.println("Sacou " + valor);
                visao();
                return true;
            }else{
                limite -= valor - saldoSemLimite;
                saldoSemLimite = 0.00;
                saldo -= valor;
                setExtrato(-valor);
                System.out.println("Sacou " + valor);
                visao();
                return true;
            }          
        }

    }
   
    public boolean depositar(double valor) {
        if(valor < 0.00){
            System.out.println("Erro " + valor);
            return false;
        }else{
            if(limite <= 100.00 && valor <= 100){
                if(limite + valor >= 100.00){
                    saldoSemLimite += (limite + valor - 100.00);
                    limite = 100.00;
                    saldo = limite + saldoSemLimite;
                    setExtrato(valor);
                    System.out.println("Depositou " + valor);
                    visao();
                    return true;    
                }else{
                    limite += valor;
                    saldo = limite + saldoSemLimite;
                    System.out.println("Depositou " + valor);
                    setExtrato(valor);
                    visao();
                    return true;
                }
            }else{
                saldoSemLimite += valor;
                saldo = limite + saldoSemLimite;
                setExtrato(valor);
                System.out.println("Depositou " + valor);
                visao();
                return true;    
            }
            
        }

    }
    
    public boolean transferir(Conta destino, double valor) {
        if(valor < 0.00 || valor > getSaldo()){
            System.out.println("Erro " + valor);
            return false;
        }else{
            sacar(valor);
            destino.depositar(valor);
            System.out.println("transferiu " + valor);
            visao();
            return true;
        }
    }

    public double[] verExtrato() {
        double[] vExtrato = new double[10] ;
        for (int i = 0; i < extrato.length; i++) {
            if(extrato[i] != 0) vExtrato[i] = extrato[i];
        }
        return vExtrato;
    }

    public void setExtrato(double valor){
        for (int i = 0; i < extrato.length; i++) {
            if(extrato[i] == 0 ){
                extrato[i] = valor;
                break;
            }
        }
    }

    public void visao(){
        System.out.println("Saldo: " + getSaldo() + ", Limite: " + getLimite());
    }

}