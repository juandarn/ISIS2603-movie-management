
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CinefiloDetalleComponent } from './cinefilo-detalle/cinefilo-detalle.component';

const routes: Routes = [{path:'cinefilos', 
                        children:[
                            {
                                path:':id',
                                component:CinefiloDetalleComponent
                            }
                        ]
                       }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports:[RouterModule],
  declarations: []
})
export class CinefiloRoutingModule{}