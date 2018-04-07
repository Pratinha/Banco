package banco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import transaccoes.Deposito;
import transaccoes.CapitalizacaoJuros;
import transaccoes.Levantamento;
import transaccoes.Transaccao;
import transaccoes.Transferencia;

import clientes.Cliente;

import contas.Conta;
import contas.Prazo;
import contas.Debito;

public class Banco {
	private static ArrayList<Cliente> clientes;
	private static int numerocliente = 1; //utilizado para as numeracoes dos clientes
	private static int numeroconta = 1;	//utilizado para as numeracoes das contas
	
	private static final String FICHEIROCLIENTES = "dados/clientes.csv";
	private static final String FICHEIROCONTAS = "dados/contas.csv";
	private static final String FICHEIROTRANSACCOES = "dados/transaccoes.csv";
	private static final String FICHEIROCONFIGURACOES ="dados/configuracoes.txt";
		
	public static int gerarNumConta() {
		return numeroconta++;
	}
	
	public static int gerarNumCliente() {
		return numerocliente++;
	}
	
	

	
	
	
	//METODOS CLIENTES
	
	/**
	 * Desactiva o cliente com o id passado como parametro. Para isto deve usar
	 * o metodo procurar cliente do banco para encontrar o cliente com esse id. Caso exista
	 * define o estado como inactivo através do metodo desactivar do cliente
	 * @param idcliente id do cliente a desactivar
	 * @return booleano a indicar se foi ou nao possivel de desactivar o cliente
	 */
	public static boolean desactivarCliente(int idcliente){
		for(int i = 0; i < clientes.size(); i++){
			Cliente corrente = clientes.get(i);
			if(idcliente == corrente.obterlog()){
				corrente.desactivarConta();
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Lista os clientes do banco que tem o nome igual ao recebido como parametro.
	 * Caso seja passada uma string vazia entao sao listados todos os clientes
	 * @param criterionome nome do cliente a listar
	 */
	public static void listarClientes(String criterionome){
		Collections.sort(clientes);
		int count = 0;
		
		for(int i = 0; i < clientes.size(); i++){
			Cliente corrente = clientes.get(i);
			
			if(criterionome.equals(corrente.obterNome())){
				System.out.println(corrente.obterInformacoes());
				count++;
			}
			else if(criterionome.equals("")){
				System.out.println(corrente.obterInformacoes() + "\n");
				count++;
			}	
		}
		if (count == 0){
			System.out.println("Não existem clientes com esse nome");
		}
		System.out.println();
	}
	
	/**
	 * Procura o cliente com o id recebido como parametro e devolve-o caso exista.
	 * Caso não exista devolve null. Este metodo e utilizado noutros metodos do banco.
	 * @param userid id do cliente a procurar
	 * @return devolve o cliente com o id procurado ou null
	 */
	public static Cliente procurarCliente(int userid){
		for(int i = 0; i < clientes.size(); i++){
			Cliente corrente = clientes.get(i);
			if(userid == corrente.obterlog()){
				return corrente;
			}
		}
		return null;
	}
	
	/**
	 * Utiliza o userid para encontrar o respectivo cliente e se existir confirma
	 * que a password e a correcta para esse utilizador. Caso isso se verifique 
	 * devolve o cliente que fez login
	 * @param userid id do utilizador que esta a fazer login
	 * @param password passwor dod utilizador que esta a fazer login
	 * @return Cliente que acabou de fazer login ou null caso não coincidam as credênciais
	 */
	public static Cliente validarLogin(int userid, int password) {
		for(int i = 0; i < clientes.size(); i++){
			Cliente corrente = clientes.get(i);
			if(userid == corrente.obterlog() && password == corrente.obterPass()){
				return corrente;
			}
		}
		return null;
	}
	
	/**
	 * Cria um cliente com os dados recebidos e adiciona-o à lista de 
	 * clientes do banco
	 * @param nome nome do cliente a adicionar
	 * @param userid userid do cliente a adicionar
	 * @param password password do cliente a adicionar
	 */
	public static void criarCliente(String nome, int userid, int password){
		Cliente corrente = new Cliente(nome, userid, password);
		clientes.add(corrente);
	}
	
	public static void criarCliente(String nome, int userid, int password, boolean activo){
		Cliente corrente = new Cliente(nome, userid, password, activo);
		clientes.add(corrente);
	}
	
	/**
	 * Procura em todos os clientes por uma conta com o nib recebio com parâmetro
	 * Devolve o objecto conta caso exista ou null caso não exista
	 * @param nib nib da conta a procurar
	 * @return Conta com o nib especificado
	 */
	public static Conta obterConta(int nib){
		for(int i = 0; i < clientes.size(); i++){
			Cliente corrente = clientes.get(i);
			Conta cn = corrente.obterConta(nib);
			if(cn != null){
				return cn;
			}
		}
		return null;
	}
	
	/**
	 * Procura o cliente com o id recebido por parâmetro. Caso este exista adiciona
	 * o objecto conta recebido por parâmetro às contas desse cliente
	 * @param idcliente id do cliente a adicionar à conta
	 * @param c Conta a adicionar
	 */
	public static void adicionarConta(int idcliente, Conta c){
		for(int i = 0; i < clientes.size(); i++){
			Cliente corrente = clientes.get(i);
			if(idcliente == corrente.obterlog()){
				corrente.adicionarConta(c);
			}
		}
	}
	
	/**
	 * Cria uma conta para o cliente c com o tipo tipoconta
	 * ESTE METODO ASSUME QUE O TIPO DE CONTA E VALIDO (1 - DEBITO / 2 - PRAZO)
	 * @param c O cliente sobre o qual vai ser criada a conta
	 * @param tipoconta O tipo da conta a criar
	 * @return O nib da conta a criar
	 */
	public static int criarConta(Cliente c, int tipoconta){
		if(tipoconta == 1){
			Debito deb = new Debito();
			c.adicionarConta(deb);
			
			return deb.obterNib();
		}
		else{
			Prazo pr = new Prazo();
			c.adicionarConta(pr);
			
			return pr.obterNib();
		}
	}
	
	private static void carregarDados(){
		File ficheiroclientes = new File(FICHEIROCLIENTES);
		File ficheirocontas = new File(FICHEIROCONTAS);
		File ficheirotransaccoes = new File(FICHEIROTRANSACCOES);
		File ficheiroconfiguracoes = new File(FICHEIROCONFIGURACOES);
		
		if(!ficheiroclientes.exists()){
			System.out.println("Não é possível carregar dados pois não existe o ficheiro de clientes para carregar");
			return;
		}
		
		if(!ficheirocontas.exists()){
			System.out.println("Não é possível carregar dados pois não existe o ficheiro de contas para carregar");
			return;
		}
		
		if(!ficheirotransaccoes.exists()){
			System.out.println("Não é possível carregar dados pois não existe o ficheiro de transacções para carregar");
			return;
		}
		
		try{
			importarClientes(ficheiroclientes);
			importarContas(ficheirocontas);
			importarTransaccoes(ficheirotransaccoes);
		} catch (Exception e){
			System.out.println("Erro na importação dos ficheiros");
		}
	}
	
	private static void importarClientes(File ficheiroclientes) throws IOException{
		BufferedReader br = new BufferedReader (new FileReader(ficheiroclientes));
		String texto = br.readLine();
		
		while(texto != null){
			String[] dadosficheiro = texto.split(";");
			String nome = dadosficheiro[0];
			int userid = Integer.parseInt(dadosficheiro[1]);
			int password = Integer.parseInt(dadosficheiro[2]);
			boolean activo = Boolean.parseBoolean(dadosficheiro[3]);
			
			Banco.criarCliente(nome, userid, password, activo);
			texto = br.readLine();
		}
		br.close();
	}
	
	private static void importarContas(File ficheirocontas) throws IOException, ParseException{
		BufferedReader br = new BufferedReader (new FileReader(ficheirocontas));
		String texto = br.readLine();
		
		while(texto != null){
			String[] dadosficheiro = texto.split(";");
			int userid = Integer.parseInt(dadosficheiro[0]);
			
			String tipo = dadosficheiro[1];
			double saldo = Double.parseDouble(dadosficheiro[2]);
			int nib = Integer.parseInt(dadosficheiro[3]);
			boolean activa = Boolean.parseBoolean(dadosficheiro[4]);
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
			Date datacriacao = formato.parse(dadosficheiro[5]);
						
			if (tipo.equals("Debito")){
				Debito d = new Debito(saldo, nib, activa, datacriacao);
				adicionarConta(userid, d);
			}
			else{
				double jurosacu = Double.parseDouble(dadosficheiro[6]);
				Date datavalidade = formato.parse(dadosficheiro[7]);
				double taxajuros = Double.parseDouble(dadosficheiro[8]);
				
				Prazo p = new Prazo(saldo, nib, activa, datacriacao, jurosacu, datavalidade, taxajuros);
				adicionarConta(userid, p);
			}
			texto = br.readLine();
		}
		br.close();
	}
	
	private static void importarTransaccoes(File ficheirotransaccoes) throws IOException, ParseException{
		BufferedReader br = new BufferedReader (new FileReader(ficheirotransaccoes));
		String texto = br.readLine();
		
		while(texto != null){
			String[] dadosficheiro = texto.split(";");
			int usernib = Integer.parseInt(dadosficheiro[0]);
			String tipo = dadosficheiro[1];
			int nibcorrente = Integer.parseInt(dadosficheiro[2]);
			double valor = Double.parseDouble(dadosficheiro[3]);
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date data = formato.parse(dadosficheiro[4]);
			
			if(tipo.equals("Levantamento")){
				Levantamento l = new Levantamento(nibcorrente, valor, data);
				Conta c = obterConta(usernib);
				c.adicionarTransaccao(l);
			}
			else if(tipo.equals("Deposito")){
				Deposito d = new Deposito(nibcorrente, valor, data);
				Conta c = obterConta(usernib);
				c.adicionarTransaccao(d);
			}
			else if(tipo.equals("Capitalizacao de juros")){
				CapitalizacaoJuros cj = new CapitalizacaoJuros(nibcorrente, valor, data);
				Conta c = obterConta(usernib);
				c.adicionarTransaccao(cj);
			}
			else{
				int nibdestino = Integer.parseInt(dadosficheiro[5]);
				
				Transferencia t = new Transferencia(nibcorrente, valor, data, nibdestino);
				Conta c = obterConta(usernib);
				c.adicionarTransaccao(t);
			}
			texto = br.readLine();
		}
		
		br.close();
	}
	
	public static void guardarDados(){
		guardarTransaccoes();
		guardarContas();
		guardarClientes();
	}
	
	private static void guardarClientes(){
		try{
			PrintWriter pw = new PrintWriter(FICHEIROCLIENTES);
			
			for(int i = 0; i < clientes.size(); i++){
				clientes.get(i).escreverFicheiro(pw);
			}
			
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e){
			System.out.println("A pasta para gravação de clientes não existe");
		}
	}
	
	private static void guardarContas(){
		try{
			PrintWriter pw = new PrintWriter(FICHEIROCONTAS);
			
			for (int i = 0; i < clientes.size(); i++){
				ArrayList<Conta>listaConta = clientes.get(i).obterContas();
				for (int c = 0; c < listaConta.size(); c++){
					pw.write(clientes.get(i).obterlog() + ";");
					listaConta.get(c).escreverFicheiro(pw);
				}					
			}
			
			pw.flush();
			pw.close();
			} catch (FileNotFoundException e){
				System.out.println("A pasta para gravação de clientes não existe");
		}
	}
	
	private static void guardarTransaccoes(){
		try{
			PrintWriter pw = new PrintWriter(FICHEIROTRANSACCOES);
			for(int i = 0; i < clientes.size(); i++){
				ArrayList<Conta>listaConta = clientes.get(i).obterContas();
				for (int c = 0; c < listaConta.size(); c++){
					ArrayList <Transaccao> listaTrans = listaConta.get(c).obterTransaccoes();
					for(int a = 0; a < listaTrans.size(); a++){
						pw.write(listaConta.get(c).obterNib() + ";");
						listaTrans.get(a).escreverFicheiro(pw);
					}
				}
			}
			
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e){
			System.out.println("A pasta para gravação de clientes não existe");
		}
	}
	
	
	
//	public static void simularDados(){
//		Cliente joao = new Cliente("joao", 111, 222);
//		joao.adicionarConta(new Prazo());
//		joao.adicionarConta(new Debito());
//		
//		Cliente pedro = new Cliente("pedro", 123, 456);
//		pedro.adicionarConta(new Prazo());
//		pedro.adicionarConta(new Debito());
//		
//		clientes.add(joao);
//		clientes.add(pedro);
//	}
	
//	public static void iniciar(){
//		clientes =  new ArrayList<Cliente>();
//		simularDados();
//	}
	
	public static void iniciar(){
		clientes =  new ArrayList<Cliente>();
		carregarDados();
	}
}
