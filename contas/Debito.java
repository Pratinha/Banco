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
	 * Método levantar dinheiro em conta de débito
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
	 * Método transferir dinheiro entre contas débito
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
	 * Returns string com informaçoes conta débito
	 */
	public String mostrar(){
		
		return "Conta: " + nib + " " + obterTipo();		
	}
	
	/**
	 * Returns string com informacoes de conta debito específica
	 */
	public String mostrarInformacoes(){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return nib + ":" + "\nSaldo: " + saldo + "\nData de Criação: " + formato.format(datacriacao) + "\nActiva: " + activa + "\nTipo Débito";
	}
	
	/**
	 * Saldo corrente na conta débito
	 */
	public void mostrarSaldo(){
		System.out.println("O seu saldo é " + saldo);
	}
	
	public String obterTipo(){
		return "Debito";
	}
	
	public void escreverFicheiro(PrintWriter pw){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		pw.write(obterTipo() + ";" + saldo + ";" + nib + ";" + activa + ";" + formato.format(datacriacao) + "\n");
	}
}
