import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrganizacionListarComponent } from './organizacion-listar/organizacion-listar.component';
import { OrganizacionComponent } from './organizacion.component';
import { OrganizacionDetalleComponent } from './organizacion-detalle/organizacion-detalle.component';
import { OrganizacionRoutingModule } from './organizacion-routing.module';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    OrganizacionRoutingModule
  ],
  declarations: [OrganizacionComponent,OrganizacionListarComponent, OrganizacionDetalleComponent],
  exports: [OrganizacionComponent,OrganizacionListarComponent , OrganizacionDetalleComponent]
})
export class OrganizacionModule { }
