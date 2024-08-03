import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EstoqueDetalheComponent } from './estoque-detalhe/estoque-detalhe.component';
import { EstoqueListagemComponent } from './estoque-listagem/estoque-listagem.component';

const routes: Routes = [
  { path: 'cadastrar', component: EstoqueDetalheComponent },
  { path: 'cadastrar/:idUnidade/:idVacina', component: EstoqueDetalheComponent },
  { path: 'listagem', component: EstoqueListagemComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstoqueRoutingModule {
  /*Define as rotas de navegação para
  os componentes de um módulo específico.*/
}
