import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pessoa } from '../model/pessoa';
import { PessoaSeletor } from '../model/seletor/pessoa.seletor';

@Injectable({
  providedIn: 'root'
})

export class PessoaService {

  constructor(private httpClient: HttpClient) {

  }

  private readonly API: string = 'http://localhost:8080/rest/pessoa';

  public efetuarLogin(pessoa: Pessoa): Observable<Pessoa>{
    return this.httpClient.post<Pessoa>(this.API + '/login', pessoa);
  }

  public salvar(pessoa: Pessoa): Observable<Pessoa>{
    return this.httpClient.post<Pessoa>(this.API, pessoa);
  }

  public atualizar(pessoa: Pessoa): Observable<boolean>{
    return this.httpClient.put<boolean>(this.API, pessoa);
  }

  public consultarPessoasComFiltro(seletor: PessoaSeletor): Observable<Array<Pessoa>>{
    return this.httpClient.post<Array<Pessoa>>(this.API
                                + '/filtroConsultarPessoas', seletor);
  }

  public consultarTodos(): Observable<Array<Pessoa>>{
    return this.httpClient.get<Array<Pessoa>>(this.API + '/consultarTodasPessoas');
  }

  public excluir(id: number): Observable<boolean>{
    return this.httpClient.delete<boolean>(this.API + '/' + id);
  }

  public consultarPorId(id: number): Observable<Pessoa>{
    return this.httpClient.get<Pessoa>(this.API + '/' + id);
  }

}
