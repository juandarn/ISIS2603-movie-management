import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PersonaComponent } from './persona.component';
import { DirectorListarComponent } from './director-listar/director-listar.component';
import { PersonaDetalleComponent } from './persona-detalle/persona-detalle.component';
import { PersonaRoutingModule } from './persona-routing.module';
import { RouterModule } from '@angular/router';
import { ActorListarComponent } from './actor-listar/actor-listar.component';

@NgModule({
  imports: [
    CommonModule, 
    RouterModule,
    PersonaRoutingModule
  ],
  exports:[PersonaComponent, DirectorListarComponent, PersonaDetalleComponent, ActorListarComponent],
  declarations: [PersonaComponent, DirectorListarComponent, PersonaDetalleComponent, ActorListarComponent]
})
export class PersonaModule { }
