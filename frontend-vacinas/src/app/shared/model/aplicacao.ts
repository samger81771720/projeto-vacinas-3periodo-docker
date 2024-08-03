import { Pessoa } from "./pessoa";
import { Unidade } from "./unidade";
import { Vacina } from "./vacina";

export class Aplicacao{
  id: number;
	pessoaQueRecebeu: Pessoa;
	vacinaAplicada: Vacina;
	unidadeOndeAplicou: Unidade;
	dataAplicacao: Date;
}
