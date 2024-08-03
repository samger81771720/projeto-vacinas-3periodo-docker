import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Fabricante } from "../model/fabricante";


@Injectable({
  providedIn: 'root'
})

export class FabricanteService {

  constructor(private httpClient: HttpClient) {

  }

  private readonly API: string = 'http://localhost:8080/rest/fabricante';

  public consultarPorId(id: number): Observable<Fabricante>{
    return this.httpClient.get<Fabricante>(this.API + '/' + id);
  }

  public consultarTodos(): Observable<Array<Fabricante>>{
    return this.httpClient.get<Array<Fabricante>>(this.API + '/consultarTodos');
  }

}
