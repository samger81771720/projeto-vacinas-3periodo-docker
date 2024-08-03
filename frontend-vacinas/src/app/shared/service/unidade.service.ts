import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Unidade } from "../model/unidade";
import { Observable } from "rxjs";


@Injectable({
  providedIn: 'root'
})

export class UnidadeService {

  constructor(private httpClient: HttpClient) {

  }

  private readonly API: string = 'http://localhost:8080/rest/unidade';

  public consultarPorId(id: number): Observable<Unidade>{
    return this.httpClient.get<Unidade>(this.API + '/' + id);
  }

  public consultarTodas(): Observable<Array<Unidade>>{
    return this.httpClient.get<Array<Unidade>>(this.API + '/consultarTodas');
  }

}
