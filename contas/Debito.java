/**
 * 
 */
package contas;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import transaccoes.Levantamento;
import transaccoes.Transferencia;

/**
 * @author FormabaseUser
 *
 */
public class Debito extends Conta{

	public Debito(){
		super();
	}
	
	public Debito(double s, int id, boolean act, Date data){
		saldo = s;
		nib = id;
		activa = act;
		data = new Date();
	}
	
	/**
	 * M�todo levantar dinheiro em conta de d�bito
	 */
	public boolean levantar(double valor){
		if(saldo > valor){
			saldo = saldo - valor;
			Levantamento l = new Levantamento(new Date(), this, valor);
			listaTransaccao.add(l);
			return true;
		}
		return false;
	}
	
	/**
	 * M�todo transferir dinheiro entre contas d�bito
	 */
	public boolean transferir(double valor, Conta contadestino){
		if(saldo > valor){
			contadestino.saldo = contadestino.saldo + valor;
			saldo = saldo - valor;
			Transferencia t = new Transferencia(new Date(), this, valor, contadestino);
			listaTransaccao.add(t);
			contadestino.listaTransaccao.add(t);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns string com informa�oes conta d�bito
	 */
	public String mostrar(){
		
		return "Conta: " + nib + " " + obterTipo();		
	}
	
	/**
	 * Returns string com informacoes de conta debito espec�fica
	 */
	public String mostrarInformacoes(){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return nib + ":" + "\nSaldo: " + saldo + "\nData de Cria��o: " + formato.format(datacriacao) + "\nActiva: " + activa + "\nTipo D�bito";
	}
	
	/**
	 * Saldo corrente na conta d�bito
	 */
	public void mostrarSaldo(){
		System.out.println("O seu saldo � " + saldo);
	}
	
	public String obterTipo(){
		return "Debito";
	}
	
	public void escreverFicheiro(PrintWriter pw){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		pw.write(obterTipo() + ";" + saldo + ";" + nib + ";" + activa + ";" + formato.format(datacriacao) + "\n");
	}
}
