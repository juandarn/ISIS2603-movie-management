
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonaDetalleComponent } from './persona-detalle/persona-detalle.component';
import { ActorListarComponent } from './actor-listar/actor-listar.component';
import { DirectorListarComponent } from './director-listar/director-listar.component';

const routes: Routes = [{path:'personas', 
                        children:[
                            {
                                path:'actores/listar',
                                component:ActorListarComponent
                            },
                            {
                                path:'directores/listar',
                                component:DirectorListarComponent
                            },
                            {
                                path:':id',
                                component:PersonaDetalleComponent
                            }
                        ]
                       }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: []
})
export class PersonaRoutingModule{}