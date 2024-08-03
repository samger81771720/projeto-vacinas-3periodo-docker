import { Unidade } from "./unidade";
import { Vacina } from "./vacina";

export class Estoque{
  unidade: Unidade;
	vacina: Vacina;
	quantidade: number;
	dataLote: Date;
	validade: Date;
}
