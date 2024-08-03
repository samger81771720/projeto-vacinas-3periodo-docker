import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AplicacaoDetalheComponent } from './aplicacao-detalhe/aplicacao-detalhe.component';
import { AplicacaoListagemComponent } from './aplicacao-listagem/aplicacao-listagem.component';

const routes: Routes = [
  { path: "cadastro", component: AplicacaoDetalheComponent },
  { path: "listagem", component: AplicacaoListagemComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AplicacaoRoutingModule { }
