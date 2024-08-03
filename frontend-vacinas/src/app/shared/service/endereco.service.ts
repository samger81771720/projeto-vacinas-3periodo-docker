import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Endereco } from "../model/endereco";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class EnderecoService {

  constructor(private httpClient: HttpClient) {

  }

  private readonly API: string = 'http://localhost:8080/rest/endereco';

  public consultarTodos(): Observable<Array<Endereco>>{
    return this.httpClient.get<Array<Endereco>>(this.API + '/consultarTodosEnderecos');
  }

  public consultarPorId(id: number): Observable<Endereco>{
    return this.httpClient.get<Endereco>(this.API + '/' + id);
  }

  public excluir(id: number): Observable<boolean>{
    return this.httpClient.delete<boolean>(this.API + '/' + id);
  }

  public salvar(endereco: Endereco): Observable<Endereco>{
    return this.httpClient.post<Endereco>(this.API, endereco);
  }

  public atualizar(endereco: Endereco): Observable<boolean>{
    return this.httpClient.put<boolean>(this.API, endereco);
  }

}
