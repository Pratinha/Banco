package interaccao;

import java.util.Scanner;

import banco.Banco;
import clientes.Cliente;

import java.util.ArrayList;
import contas.Conta;

public abstract class InteraccaoBanco {

	protected static Cliente cli; //À medida que os menus avançam o cliente a ser usado é guardado nesta variavel
	protected static Conta con;//À medida que os menus avançam a conta a ser usada é guardada nesta variavel
		
	/**
	 * Este metodo está em repetição a mostrar o menu de operações disponiveis numa conta.
	 * Para cada uma das opções existentes e através de um switch solicita a informação
	 * necessaria ao utilizador e invoca os metodos correspondentes.
	 */
	static Scanner teclado = new Scanner(System.in);
	
	protected static void processaMenuConta(){
		double valor;
		int opcao = 0;
				
		while(opcao != 7){
			System.out.println("\nMenu Operações Conta\n");
			System.out.println("1 -> Levantar");
			System.out.println("2 -> Depositar");
			System.out.println("3 -> Transferir");
			System.out.println("4 -> Obter extracto");
			System.out.println("5 -> Obter saldo");
			System.out.println("6 -> Obter informações");
			System.out.println("7 -> Sair do Menu");
			opcao = teclado.nextInt();
			
			switch(opcao){
				case 1: 
					System.out.println("Qual o valor a levantar?");
					valor = teclado.nextDouble();
					boolean resultado = con.levantar(valor);
					if (resultado == true){
						System.out.println("Levantamento efetuado com sucesso");
					}
					else {
						System.out.println("Saldo insufuciente");
					}
					break;
				case 2:
					System.out.println("Qual o valor a depositar?");
					valor = teclado.nextDouble();
					con.depositar(valor);
					System.out.println("Deposito efetuado com sucesso");
					break;
				case 3: 
					Conta contadestino;
					System.out.println("Qual a conta destino da transferencia?");
					int nib = teclado.nextInt();
					contadestino = Banco.obterConta(nib);
					if (contadestino != null){
						System.out.println("Qual o valor a transferir?");
						valor = teclado.nextDouble(); 
						if(con.transferir(valor, contadestino) == true){
							System.out.println("Transferência efetuada com sucesso");
						}
						else{
							System.out.println("Saldo insufeciente");
						}
					 }
					else{
						System.out.println("A conta destino não existe");
					} break;
				case 4: con.mostrarExtracto(); break;
				case 5: con.mostrarSaldo(); break;
				case 6: System.out.println(con.mostrarInformacoes()); break;
				case 7: System.out.println("End"); break;
				default: System.out.println("Erro"); break;
			}
		}
	}
	
	/**
	 * Este metodo está em repetição a mostrar o menu de contas disponiveis do cliente.
	 * De notar que APENAS AS CONTAS ACTIVAS são mostradas.
	 * Após ser seleccionada uma conta é invocado o metodo processaMenuConta referente à conta escolhida
	 * @param contascliente Cliente sobre o qual se quer visualizar as contas
	 */
	protected static void processaMenuContas(ArrayList<Conta> contascliente) {
		int opcao = 0;
		ArrayList<Conta> contasactiva = new ArrayList<Conta>();
		for(int a = 0; a < contascliente.size(); a++){
			Conta c = contascliente.get(a);
			if (c.estaActiva() == true){
				contasactiva.add(c);
			}
		}
		int size = contasactiva.size() + 1;
		while(opcao < size){
			System.out.println("\nMenu Contas\n");
			for(int i = 0; i < contasactiva.size(); i++){
				Conta corrente = contasactiva.get(i);
				System.out.println(i + 1 + " -> " + corrente.mostrar());
			}
			System.out.println(size + " -> Sair do Menu");
			opcao = teclado.nextInt();
			
			if(opcao < size){
				con = contasactiva.get(opcao -1);
				processaMenuConta();
			}
		}
	}
	
	public static void main(String[] args) {
		Banco.iniciar();
		Cliente c = Banco.procurarCliente(111);
		cli = c;
		processaMenuContas(c.obterContas());
		teclado.close();
		
	}
}
