export abstract class BaseSeletor {
  public readonly QTDE_INICIAL_DE_REGISTROS_POR_PAGINA: number = 5;
  limite: number = this.QTDE_INICIAL_DE_REGISTROS_POR_PAGINA;
  pagina: number;
}
