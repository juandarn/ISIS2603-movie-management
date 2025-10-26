import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReseniaListarComponent } from './resenia-listar/resenia-listar.component';
import { ReseniaDetalleComponent } from './resenia-detalle/resenia-detalle.component';
import { RouterModule } from '@angular/router';
import { ReseniaRoutingModule } from './resenia-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    ReseniaRoutingModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    NgxPaginationModule
  ],
  declarations: [
    ReseniaListarComponent, 
    ReseniaDetalleComponent
  ],
  exports: [
    ReseniaListarComponent, 
    ReseniaDetalleComponent 
  ]
})
export class ReseniaModule { }
  