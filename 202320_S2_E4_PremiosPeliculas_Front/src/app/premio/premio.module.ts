import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PremioComponent } from './premio.component';
import { PremioListarComponent } from './premio-listar/premio-listar.component';
import { PremioDetalleComponent } from './premio-detalle/premio-detalle.component';
import { RouterModule } from '@angular/router';
import { PremioRoutingModule } from './premio-routing.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule, 
    PremioRoutingModule
  ],
  exports: [PremioListarComponent],
  declarations: [PremioComponent, PremioListarComponent, PremioDetalleComponent]
})
export class PremioModule { }
