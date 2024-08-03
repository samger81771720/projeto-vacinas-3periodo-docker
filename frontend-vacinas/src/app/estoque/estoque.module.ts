import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EstoqueRoutingModule } from './estoque-routing.module';
import { EstoqueDetalheComponent } from './estoque-detalhe/estoque-detalhe.component';
import { FormsModule } from '@angular/forms';
import { EstoqueListagemComponent } from './estoque-listagem/estoque-listagem.component';


@NgModule({
  declarations: [
    EstoqueDetalheComponent,
    EstoqueListagemComponent
  ],
  imports: [
    CommonModule,
    EstoqueRoutingModule,
    FormsModule
  ]
})
export class EstoqueModule { }
