import { AplicacaoSeletor } from './../../shared/model/seletor/aplicacao.seletor';
import { Component } from '@angular/core';
import { PessoaService } from '../../shared/service/pessoa.service';
import Swal from 'sweetalert2';
import { Pessoa } from '../../shared/model/pessoa';
import { Unidade } from '../../shared/model/unidade';
import { UnidadeService } from '../../shared/service/unidade.service';
import { VacinaService } from '../../shared/service/vacina.service';
import { Vacina } from '../../shared/model/vacina';
import { AplicacaoService } from '../../shared/service/aplicacao.service';
import { Aplicacao } from '../../shared/model/aplicacao';
import { AplicacaoDTO } from '../../shared/model/dto/aplicacao.DTO';
import { Router } from '@angular/router';

  @Component({
  selector: 'app-aplicacao-listagem',
  templateUrl: './aplicacao-listagem.component.html',
  styleUrl: './aplicacao-listagem.component.scss'
  })
  export class AplicacaoListagemComponent {

  public pessoas : Array<Pessoa> = new Array();
  public unidades : Array<Unidade> = new Array();
  public aplicacoes : Array<Aplicacao> = new Array();
  public vacinas : Array<Vacina> = new Array();
  public aplicacoesDTO : Array<AplicacaoDTO> = new Array();
  public seletor : AplicacaoSeletor = new AplicacaoSeletor();
  public aplicacaoDTO : AplicacaoDTO = new AplicacaoDTO();

  constructor(
    private pessoaService : PessoaService,
    private unidadeService : UnidadeService,
    private vacinaService : VacinaService,
    private aplicacaoService : AplicacaoService,
    private router: Router
  ){
  }

  ngOnInit(): void {
    this.consultarTodosAsPessoas();
    this.consultarTodasAsUnidades();
    this.consultarTodasAsVacinas();
  }

  public consultarTodosAsPessoas(): void{
    this.pessoaService.consultarTodos().subscribe(
      (resultado) => {
        this.pessoas = resultado;
      },
      (erro) => {
        Swal.fire('Erro ao buscar a lista de pessoas','','error');
      }
    );
  }

  public consultarTodasAsUnidades(): void{
    this.unidadeService.consultarTodas().subscribe(
      (resultado) => {
        this.unidades = resultado;
      },
      (erro) => {
        Swal.fire('Erro ao buscar a lista de unidades','','error');
      }
    );
  }

  public consultarTodasAsVacinas(): void{
    this.vacinaService.consultarTodas().subscribe(
      (resultado) => {
        this.vacinas = resultado;
      },
      (erro) => {
        Swal.fire('Erro ao buscar a lista de vacinas','','error');
      }
    );
  }

  public pesquisar(){
    this.aplicacaoService.consultarComFiltros(this.seletor).subscribe(
      (resultado) => {
        this.aplicacoesDTO = resultado;
      },
      (erro) => {
        Swal.fire('Erro ao buscar todas as aplicações da pessoa com o seletor.','','error');
      }
    );
  }

  public limpar(){
    this.seletor = new AplicacaoSeletor();
    this.aplicacoesDTO =[];
  }

  public voltar(): void {
    this.router.navigate(['/aplicacao']);
  }

}
