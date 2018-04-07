/**
 * 
 */
package interaccao;


import banco.Banco;

/**
 * @author FormabaseUser
 *
 */
public class Multibanco extends InteraccaoBanco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Banco.iniciar();
		int log;
		int pass;
		
		cli = null;
		
		while(cli == null){
			System.out.println("Insira o seu Login");
			log = teclado.nextInt();
			System.out.println("Insira a sua password");
			pass = teclado.nextInt();
			
			cli = Banco.validarLogin(log, pass);
			
			if (cli!= null){
				if(cli.estaActivo() == true){
					System.out.println("Bem Vindo");
					processaMenuContas(cli.obterContas());				
				}
				else{
					System.out.println("Cliente inactivo");
				}
			}
			else{
				System.out.println("Login errado");
			}
		}
		System.out.println("Fim do Multibanco");	
		
		teclado.close();
		Banco.guardarDados();
	}

}
