import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PremioListarComponent } from './premio-listar/premio-listar.component';
import { PremioDetalleComponent } from './premio-detalle/premio-detalle.component';

const routes: Routes = [{

  path: 'premios',
  children: [
    {
      path: 'list',
      component : PremioListarComponent
    },
    {
      path: ':id',
      component: PremioDetalleComponent
      }
    ]
  }];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class PremioRoutingModule { }
