/**
 * 
 */
package transaccoes;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import contas.Conta;

/**
 * @author FormabaseUser
 *
 */
public class Deposito extends Transaccao {
		
	public Deposito(Date d, Conta c, double v){
		super(d, c, v);
		tipo = "Deposito";
	}
	
	public Deposito(int n, double v, Date d){
		super(n, v, d);
		tipo = "Deposito";
	}
	
	@Override
	public String mostrar() {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return tipo + " - " + formato.format(date) + " - " + nib + " - " + valor;
	}
	
	public void escreverFicheiro(PrintWriter pw){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		pw.write( tipo + ";" + nib + ";" + valor + ";" + formato.format(date) + "\n");
	}
	
}
