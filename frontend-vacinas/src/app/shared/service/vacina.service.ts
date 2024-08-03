import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vacina } from '../model/vacina';

@Injectable({
  providedIn: 'root'
})

export class VacinaService {

  constructor(private httpClient: HttpClient) {

  }

  private readonly API: string = 'http://localhost:8080/rest/vacina';

  public consultarTodas(): Observable<Array<Vacina>>{
    return this.httpClient.get<Array<Vacina>>(this.API + '/consultarTodasVacinas');
  }

  public consultarTodasCategorias(): Observable<Array<string>>{
    return this.httpClient.get<Array<string>>(this.API + '/consultarTodasCategorias');
  }

  public consultarPorId(id: number): Observable<Vacina>{
    return this.httpClient.get<Vacina>(this.API + '/' + id);
  }

  public excluir(id: number): Observable<boolean>{
    return this.httpClient.delete<boolean>(this.API + '/' + id);
  }

  public salvar(vacina: Vacina): Observable<Vacina>{
    return this.httpClient.post<Vacina>(this.API, vacina);
  }

  public atualizar(vacina: Vacina): Observable<boolean>{
    return this.httpClient.put<boolean>(this.API, vacina);
  }

}
