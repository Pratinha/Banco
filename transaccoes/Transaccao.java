/**
 * 
 */
package transaccoes;

import contas.Conta;

import java.io.PrintWriter;
import java.util.Date;

/**
 * @author FormabaseUser
 *
 */
public abstract class Transaccao {
	protected Date date;
	protected int nib;
	protected double valor;
	protected String tipo;
	
	/**
	 * Método para obter o extracto da transacção
	 * @return String extracto
	 */
	public abstract String mostrar();
	
	public Transaccao(Date d, Conta c, double v){
		date = d;
		nib = c.obterNib();
		valor = v;		
	}
	
	public Transaccao(int n, double v, Date d){
		nib = n;
		valor = v;
		date = d;
	}
	
	public String obterTipo(){
		
		return tipo;
	}
	
	public abstract void escreverFicheiro(PrintWriter pw);

}
