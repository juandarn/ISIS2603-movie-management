import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {OrganizacionListarComponent} from './organizacion-listar/organizacion-listar.component';
import { OrganizacionDetalleComponent } from './organizacion-detalle/organizacion-detalle.component';
import { RouterModule, Routes } from '@angular/router';


const routes : Routes = [
  {
    path: 'organizacion',
    children:[
      {
      path: 'list',
      component : OrganizacionListarComponent
      },
      {
      path: ':id',
      component: OrganizacionDetalleComponent
      }
    ]
  }
]

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],exports: [RouterModule],
  declarations: []
})
export class OrganizacionRoutingModule { }
