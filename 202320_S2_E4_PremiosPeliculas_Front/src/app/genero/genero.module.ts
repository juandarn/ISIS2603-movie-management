import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GeneroComponent } from './genero.component';
import { GeneroDetalleComponent } from './genero-detalle/genero-detalle.component';
import { GeneroListarComponent } from './genero-listar/genero-listar.component';
import { RouterModule } from '@angular/router';
import { GeneroRoutingModule } from './genero-routing.module';
@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    GeneroRoutingModule,
  ],
  declarations: [GeneroListarComponent,GeneroDetalleComponent],
  exports: [GeneroListarComponent]
})
export class GeneroModule { }
