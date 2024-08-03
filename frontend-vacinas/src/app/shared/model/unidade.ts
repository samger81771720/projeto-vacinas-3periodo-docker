import { Contato } from "./contato";
import { Endereco } from "./endereco";

export class Unidade{
  id: number;
	nome: string;
	enderecoDaUnidade: Endereco;
	contatoDaUnidade: Contato;
}
