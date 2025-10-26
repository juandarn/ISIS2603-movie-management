import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';


import { GeneroModule } from './genero/genero.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PeliculaModule } from './pelicula/pelicula.module';
import {OrganizacionModule} from './organizacion/organizacion.module';
import { PremiacionModule } from './premiacion/premiacion.module';
import { PremioModule } from './premio/premio.module';
import { GanadorModule } from './ganador/ganador.module';
import { ReseniaModule } from './resenia/resenia.module';
import { NominadoModule } from './nominado/nominado.module';
import { PlataformaModule} from './plataforma/plataforma.module';
import { PersonaModule } from './persona/persona.module';
import { CinefiloModule } from './cinefilo/cinefilo.module';
import { ToastrModule } from 'ngx-toastr';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PrincipalModule } from './principal/principal.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    PeliculaModule,
    OrganizacionModule,
    PersonaModule,
    GeneroModule,
    PremiacionModule,
    PremioModule,
    GanadorModule,
    ReseniaModule,
    NominadoModule,
    PlataformaModule,
    CinefiloModule,
    PrincipalModule,
    PeliculaModule,
    ToastrModule.forRoot({
      timeOut: 10000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
      }
    ),
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
