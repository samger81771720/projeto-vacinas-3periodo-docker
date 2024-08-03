import { Fabricante } from "./fabricante";

export class Vacina{
  id: number;
	nome: string;
  fabricanteDaVacina: Fabricante;
	categoria: string;
	idadeMinima: number;
	idadeMaxima: number;
	contraIndicacao: boolean;
}
