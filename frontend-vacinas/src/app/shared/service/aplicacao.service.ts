import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Aplicacao } from "../model/aplicacao";
import { AplicacaoSeletor } from "../model/seletor/aplicacao.seletor";
import { AplicacaoDTO } from "../model/dto/aplicacao.DTO";

@Injectable({
  providedIn: 'root'
})

export class AplicacaoService {

  constructor(private httpClient: HttpClient) {

  }

  private readonly API: string = 'http://localhost:8080/rest/aplicacao';

  public consultarPorId(id: number): Observable<Aplicacao>{
    return this.httpClient.get<Aplicacao>(this.API + '/' + id);
  }

  public salvar(aplicacao: Aplicacao): Observable<Aplicacao>{
    return this.httpClient.post<Aplicacao>(this.API, aplicacao);
  }

  public consultarComFiltros(seletor: AplicacaoSeletor): Observable<Array<AplicacaoDTO>>{
    return this.httpClient.post<Array<AplicacaoDTO>>(this.API
                                + '/filtroAplicacoesPessoa', seletor);
  }

}

