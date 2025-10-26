import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NominadoListarComponent } from './nominado-listar/nominado-listar.component';
import { NominadoDetalleComponent } from './nominado-detalle/nominado-detalle.component';

const routes: Routes = [
  { path: 'nominados', 
  children:[
    {path: 'list', component: NominadoListarComponent}, 
    {path: 'id', component: NominadoDetalleComponent}]
  }
]
;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NominadoRoutingModule { }

