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
public class Transferencia extends Transaccao {
	protected int nib2;
	
	public Transferencia(Date d, Conta c, double v, Conta b){
		super(d, c, v);
		nib2 = b.obterNib();
		tipo = "Transferencia";
	}
	
	public Transferencia(int n, double s, Date d, int n2){
		super(n, s, d);
		nib2 = n2;
		tipo = "Transferencia";
	}
	
	@Override
	public String mostrar() {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return tipo + " - " + formato.format(date) + " - " + nib + " - " + valor + " - " + nib2;
	}
	
	public void escreverFicheiro(PrintWriter pw){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		pw.write( tipo + ";" + nib + ";" + valor + ";" + formato.format(date) + ";" + nib2 + "\n");
	}

}
