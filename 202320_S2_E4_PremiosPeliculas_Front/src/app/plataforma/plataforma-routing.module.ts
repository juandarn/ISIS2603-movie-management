import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlataformaListarComponent } from './plataforma-listar/plataforma-listar.component';
import { PlataformaDetalleComponent } from './plataforma-detalle/plataforma-detalle.component';


const routes: Routes = [
  { path: 'plataformas', 
  children:[
    {path: 'list', component: PlataformaListarComponent}, 
    {path: ':id', component: PlataformaDetalleComponent}]
  }
]
;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlataformaRoutingModule { }
