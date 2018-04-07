/**
 * 
 */
package contas;

import java.util.Date;

import transaccoes.Levantamento;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.PrintWriter;
import java.text.ParseException;

/**
 * @author FormabaseUser
 *
 */
public class Prazo extends Conta {
	protected double JUROS = 0.05;
	protected Date validade;
	protected double juros_acu;
	
	public void definirValidade(int ano){
		Calendar cal = Calendar.getInstance();
		cal.setTime(datacriacao);
		cal.add(Calendar.YEAR, ano);
		validade = cal.getTime();
	}
	
	public Prazo (){
		super();
		definirValidade(1);
	}
	
	public Prazo(double s, int id, boolean act, Date data, double jurosacu, Date val, double juros){
		saldo = s;
		nib = id;
		activa = act;
		data = new Date();
		juros_acu = jurosacu;
		validade = val;
		JUROS = juros;
	}
	
	public void definirValidade(String val) throws ParseException{
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		validade = formato.parse(val);
	}
	public double tempoDecorrido(){
		long c = datacriacao.getTime();
		Date actual = new Date();
		long a = actual.getTime();
		long v = validade.getTime();
		long t1 = a - c;
		long t2 = v - c;
		double tempo = (double)t1 / t2;
		return tempo;
	}
	public void definirJuros(double movimento){
		
		juros_acu += tempoDecorrido() * JUROS * movimento;
	}
	
	public boolean levantar(double valor){
		if(saldo > valor){
			saldo = saldo - valor;
			definirJuros(valor);
			Levantamento l = new Levantamento(new Date(), this, valor);
			listaTransaccao.add(l);
			return true;
		}
		return false;
	}
	
	public boolean transferir(double valor, Conta contadestino){
		if(saldo > valor){
			contadestino.saldo = contadestino.saldo + valor;
			saldo = saldo - valor;
			definirJuros(valor);
			return true;
		}
		return false;
	}
	
	public String mostrar(){
		
		return "Conta: " + nib + " " + obterTipo(); 
	}
	
	public String mostrarInformacoes(){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return nib + ":" + "\nSaldo: " + saldo + "\nData de Criação: " + formato.format(datacriacao) + "\nData de Validade: " + formato.format(validade) + "\nJuros: " + juros() + "\nActiva: " + activa + "\nTipo Prazo";
	}
	
	public double juros(){
		//String juros = "" + juros_acu;
		
		/*
		for(int i = 1; i < juros.length(); i++){
			if(juros.charAt(i) == '.'){
				
			}
		}
		*/
		
		//com split
		//String[] blocos = juros.split(".");
		//juros = blocos[0]+"." + blocos[1].substring(0, 2);
		
		//com substr
		//juros = juros.substring(0, juros.indexOf(".") + 2);
		
		
		double j = juros_acu * 100;
		j = (int)(j + 0.5);//j = Math.round(j);
		j /= 100;
		
		return j;
	}
	
	
	public void mostrarSaldo(){
		System.out.println("O seu saldo é: " + saldo);
		System.out.println("Já acumulou " + juros() + " em juros");
	}
	
	public String obterTipo(){
		
		return "Prazo";
	}
	
	public void escreverFicheiro(PrintWriter pw){
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		pw.write(obterTipo() + ";" + saldo + ";" + nib + ";" + activa + ";" + formato.format(datacriacao) + ";" + juros_acu + ";" + formato.format(validade) + ";" + JUROS + "\n");
	}
}
