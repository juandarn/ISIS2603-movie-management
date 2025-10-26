import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlataformaListarComponent } from './plataforma-listar/plataforma-listar.component';
import { PlataformaDetalleComponent } from './plataforma-detalle/plataforma-detalle.component';
import { RouterModule } from '@angular/router';
import { PlataformaRoutingModule } from './plataforma-routing.module';
import {  NgxPaginationModule } from 'ngx-pagination';
import { PaginatePipe } from 'ngx-pagination';

@NgModule({

  imports: [
    CommonModule, 
    RouterModule, 
    PlataformaRoutingModule,
    NgxPaginationModule,
  ],

  declarations: [
    PlataformaDetalleComponent, 
    PlataformaListarComponent
  ],

  exports: [
    PlataformaDetalleComponent, 
    PlataformaListarComponent,
    PaginatePipe,
    NgxPaginationModule
  ]

})
export class PlataformaModule { }
