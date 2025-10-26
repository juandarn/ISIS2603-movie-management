import { NgModule } from '@angular/core';
import {Routes,RouterModule} from '@angular/router';
import { GeneroListarComponent } from './genero-listar/genero-listar.component';
import { GeneroDetalleComponent } from './genero-detalle/genero-detalle.component';

const routes: Routes = [
  {
    path: 'generos',
    children: [
      {
        path: 'list',
        component: GeneroListarComponent,
      },
      {path: ":id",
    component: GeneroDetalleComponent }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GeneroRoutingModule { }
