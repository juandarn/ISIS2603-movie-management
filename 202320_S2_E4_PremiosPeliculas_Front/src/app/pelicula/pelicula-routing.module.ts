import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PeliculaListarComponent } from './pelicula-listar/pelicula-listar.component';
import { PeliculaDetalleComponent } from './pelicula-detalle/pelicula-detalle.component';
import { PeliculaCrearComponent } from './pelicula-crear/pelicula-crear.component';

const routes: Routes = [{path: 'peliculas',children: [{path: 'list', component:PeliculaListarComponent}, {path:'create', component:PeliculaCrearComponent},
{path: ':id', component:PeliculaDetalleComponent}]}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class PeliculaRoutingModule { }