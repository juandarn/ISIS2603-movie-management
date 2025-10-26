import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { GanadorListarComponent } from './ganador-listar/ganador-listar.component';
import { GanadorDetalleComponent } from './ganador-detalle/ganador-detalle.component';


const routes : Routes = [
  { path: 'ganadores', 
  children:[
    {path: 'list', component: GanadorListarComponent}, 
    {path: 'id', component: GanadorDetalleComponent}]
  }
]

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class GanadorRoutingModule { }
