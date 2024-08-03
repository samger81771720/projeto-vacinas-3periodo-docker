package model.seletor;

public abstract class BaseSeletor {
	
	private int pagina;
	private int limite;
	
	public BaseSeletor() {
		this.pagina = 0;
		this.limite = 0;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}
	
	//Método da classe BaseSeletor utilizado para verificar se há paginação
	public boolean temPaginacao() {
		return this.limite > 0 && this.pagina > 0;
	}
	
	/*Método para pular o número de registros que forem necessários 
	para chegar ao início da página desejada, a qual conterá a qtde dos 
	registros 	estipulados pela variável "limite".*/
	public int getOffSet() {
		return this.limite * (this.pagina - 1);
	} 
	//Explicação abaixo
	
}

/*
  
 O método getOffSet() é responsável por calcular o deslocamento necessário para uma consulta paginada em um banco de dados.

Vamos entender como ele funciona:

this.pagina - 1: Isso subtrai 1 do número da página atual. Isso é feito porque, em uma consulta paginada, geralmente as páginas 
são indexadas a partir de 1 (a primeira página é a página 1), mas em programação, os índices geralmente começam em 0. Portanto, 
subtrair 1 ajusta a contagem da página para que corresponda ao índice correto.

this.limite * (this.pagina - 1): Multiplica o número de itens por página (limite) pelo número da página ajustado. Isso resulta 
no número de itens que devem ser pulados antes de começar a recuperar os resultados para a página atual.

Por exemplo, se você estiver na página 3 e tiver 10 itens por página, o cálculo seria:

Deslocamento = 10 * (3 - 1) = 10 * 2 = 20
Isso significa que, para obter os resultados da página 3, você precisa pular os 20 primeiros resultados. 
Esse é o propósito do método getOffSet(): determinar quantos itens devem ser pulados na consulta para 
obter os resultados da página desejada.

*/