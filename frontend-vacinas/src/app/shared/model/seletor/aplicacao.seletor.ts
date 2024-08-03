import { BaseSeletor } from "./base.seletor";

export class AplicacaoSeletor extends BaseSeletor{
  idPessoaRecebeuAplicacao: number;
	nomeUnidadeAplicacao: string;
	nomeVacinaAplicada: string;
	dataInicioPesquisaSeletor: Date;
	dataFinalPesquisaSeletor: Date;
}

