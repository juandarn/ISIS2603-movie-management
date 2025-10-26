import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NominadoListarComponent } from './nominado-listar/nominado-listar.component';
import { NominadoDetalleComponent } from './nominado-detalle/nominado-detalle.component';
import { RouterModule } from '@angular/router';
import { NominadoRoutingModule } from './nominado-routing.module';

@NgModule({

  imports: [
    CommonModule,
    RouterModule,
    NominadoRoutingModule 
  ],

  declarations: [
    NominadoListarComponent, 
    NominadoDetalleComponent
  ],

  exports: [
    NominadoListarComponent, 
    NominadoDetalleComponent
  ]
})
export class NominadoModule { }
