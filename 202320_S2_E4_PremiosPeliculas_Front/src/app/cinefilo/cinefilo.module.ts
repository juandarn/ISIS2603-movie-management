import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CinefiloDetalleComponent } from './cinefilo-detalle/cinefilo-detalle.component';
import { CinefiloRoutingModule } from './cinefilo-routing.module';

@NgModule({
  imports: [
    CommonModule,
    CinefiloRoutingModule
  ],
  exports:[CinefiloDetalleComponent],
  declarations: [ CinefiloDetalleComponent]
})
export class CinefiloModule { }
