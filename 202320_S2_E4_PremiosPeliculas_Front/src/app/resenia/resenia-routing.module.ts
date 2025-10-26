import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ReseniaListarComponent } from './resenia-listar/resenia-listar.component';
import { ReseniaDetalleComponent } from './resenia-detalle/resenia-detalle.component';

const routes: Routes = [{
  path: 'resenias',
  children: [{
    path: 'list',
    component: ReseniaListarComponent},
    {
      path: ':id',
      component: ReseniaDetalleComponent
    }
  ]
}];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
 exports: [RouterModule]
})
export class ReseniaRoutingModule { }
 