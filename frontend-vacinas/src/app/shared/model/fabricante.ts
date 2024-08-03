import { Contato } from "./contato";
import { Endereco } from "./endereco";

export class Fabricante{
  id: number;
	nome: string;
	enderecoDoFabricante: Endereco;
	contatoDoFabricante: Contato;
}
