import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrincipalComponent } from './principal.component';
import { PeliculaModule } from '../pelicula/pelicula.module';
import { PremiacionModule } from '../premiacion/premiacion.module';
import { PersonaModule } from '../persona/persona.module';
import { PrincipalRoutingModule } from './principal-routing.module';

@NgModule({
  imports: [
    CommonModule,
    PeliculaModule,
    PremiacionModule,
    PersonaModule,
    PrincipalRoutingModule,
    PeliculaModule
  ],
  declarations: [PrincipalComponent]
})
export class PrincipalModule { }
