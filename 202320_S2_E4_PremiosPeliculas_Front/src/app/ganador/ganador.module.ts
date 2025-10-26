import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GanadorListarComponent } from './ganador-listar/ganador-listar.component';
import { GanadorDetalleComponent } from './ganador-detalle/ganador-detalle.component';
import { RouterModule } from '@angular/router';
import { GanadorRoutingModule } from './ganador-routing.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    GanadorRoutingModule
  ],
  exports: [GanadorListarComponent],
  declarations: [GanadorListarComponent,GanadorDetalleComponent]
})
export class GanadorModule { }
