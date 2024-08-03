import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Contato } from "../model/contato";

@Injectable({
  providedIn: 'root'
})

export class ContatoService {

  constructor(private httpClient: HttpClient) {

  }

  private readonly API: string = 'http://localhost:8080/rest/contato';

  public consultarPorId(id: number): Observable<Contato>{
    return this.httpClient.get<Contato>(this.API + '/' + id);
  }

  public salvar(contato: Contato): Observable<Contato>{
    return this.httpClient.post<Contato>(this.API, contato);
  }

  public atualizar(contato: Contato): Observable<boolean>{
    return this.httpClient.put<boolean>(this.API, contato);
  }

}
