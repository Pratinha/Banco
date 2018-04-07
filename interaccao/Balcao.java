/**
 * 
 */
package interaccao;

import banco.Banco;
import clientes.Cliente;
import contas.Conta;

/**
 * @author FormabaseUser
 *
 */
public class Balcao extends InteraccaoBanco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Banco.iniciar();
		int opcao = 0;
		String n;
		int id;
		int pass;
		
		while(opcao != 7){
			System.out.println("\nMenu Principal\n");
			System.out.println("1 -> Criar Cliente");
			System.out.println("2 -> Desactivar Cliente");
			System.out.println("3 -> Criar Conta");
			System.out.println("4 -> Desactivar Conta");
			System.out.println("5 -> Listar Clientes");
			System.out.println("6 -> Operações sobre cliente");
			System.out.println("7 -> Sair");
			opcao = teclado.nextInt();
			
			switch(opcao){
			case 1: 
				teclado.nextLine();
				System.out.println("Insira o nome do novo cliente");
				n = teclado.nextLine();
				System.out.println("Insira a password do novo cliente");
				pass = teclado.nextInt();
				id = Banco.gerarNumConta();
				Banco.criarCliente(n, id, pass);
				System.out.println("O id do novo cliente é " + id);
				System.out.println("Cliente criado");
				break;
			case 2:
				System.out.println("Qual o id do cliente a desactivar?");
				id = teclado.nextInt();
				if(Banco.desactivarCliente(id) == true){ 
					System.out.println("Cliente desactivado com sucesso");
				}
				else{
					System.out.println("Cliente não existe");
				}
				break;
			case 3: 
				System.out.println("Qual o id do cliente a procurar?");
				id = teclado.nextInt();
				Cliente c = Banco.procurarCliente(id);
				if(c != null){
					System.out.println("Insira o tipo de conta a criar:");
					System.out.println("1 -> Debito");
					System.out.println("2 -> Prazo");
					int tipoconta = teclado.nextInt();
					if(tipoconta >= 1 && tipoconta <= 2){
						System.out.println("Conta criada -> " + Banco.criarConta(c, tipoconta));
						System.out.println("Conta criada com sucesso");
					}
					else{
						System.out.println("Tipo de conta inválido");
					}
				}
				else{
					System.out.println("Cliente não existe");
				} 
				
				break;
			case 4:
				System.out.println("Qual o id do cliente a procurar?");
				id = teclado.nextInt();
				Cliente b = Banco.procurarCliente(id);
				if(b != null){
					System.out.println("Insira o nib da conta a desactivar");
					int nib = teclado.nextInt();
					Conta c1 = b.obterConta(nib);
					if(c1 != null){
						c1.desactivar();
						System.out.println("Conta desactivada com sucesso");
					}
					else{
						System.out.println("Conta inexistente");
					}
				}
				else{
					System.out.println("Cliente não existe");
				}
				break;
			case 5:
				System.out.println("Pesquise o nome do cliente a encontrar ou"
						+ " pesquise \"vazio\" para listar todos os clientes");
				teclado.nextLine();
				n = teclado.nextLine();
				Banco.listarClientes(n); break;
			case 6:
			System.out.println("Qual o id do cliente a procurar?");
			id = teclado.nextInt();
			Cliente a = Banco.procurarCliente(id);
			if(a != null){
				processaMenuContas(a.obterContas());
				}
			else{
				System.out.println("Cliente não existe");
			}
			break;
			case 7: System.out.println("End"); break;
			default: System.out.println("Erro"); break;
			}
		}
		teclado.close();
		Banco.guardarDados();
	}

}
