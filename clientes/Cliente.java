package clientes;

import java.io.PrintWriter;
import java.util.ArrayList;
import contas.Conta;

/**
 * 
 */

/**
 * @author FormabaseUser
 *
 */
public class Cliente  implements Comparable{
	private String nome;
	private int password;
	private int log;
	private boolean activo;
	private ArrayList<Conta> listaConta = new ArrayList<Conta>();
	
	/**
	 * Construtor clientes
	 * @param n
	 * @param id
	 * @param pass
	 */
	public Cliente (String n, int id, int pass){
		nome = n;
		log = id;
		password = pass;
		
		activo = true;
	}
	
	public Cliente (String n, int id, int pass, boolean act){
		nome = n;
		log = id;
		password = pass;
		activo = act;
	}
	
	/**
	 * Mostra as informa��es do cliente
	 */
	public String obterInformacoes(){
		
		return "Nome: " + nome + "\nUser ID: " + log + "\nPassword: " + password + "\nActivo: " + activo;
	}
	
	
	public void adicionarConta(Conta c){
		listaConta.add(c);
	}
	
	public ArrayList<Conta> obterContas(){
		return listaConta;
	}
	
	public Conta obterConta (int nib){
		
		for(int i = 0; i < listaConta.size(); ++i){
			Conta contacorrente = listaConta.get(i);
			if (nib == contacorrente.obterNib()){
				return contacorrente;
			}
		}
		return null;
	}
	
	public int obterlog(){
		return log;
	}
	
	public boolean estaActivo(){
		return activo;
	}
	
	public void desactivarConta(){
		activo = false;
	}
	
	public String obterNome(){
		return nome;
	}
	
	public int obterPass(){
		return password;
	}
	
	@Override
	public int compareTo(Object arg0){
		Cliente clienteAComparar = (Cliente)arg0;
		return nome.compareTo(clienteAComparar.nome);	
		//	Para ordena��o por id
		//	Cliente clienteAComparar = (Cliente)arg0;
		//	return log-clienteAComparar.log;
	}
	
	public void escreverFicheiro(PrintWriter pw){
		pw.write(nome + ";" + log + ";" + password + ";" + activo + "\n");
	}
}
