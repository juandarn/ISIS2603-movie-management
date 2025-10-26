import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PremiacionComponent } from './premiacion.component';
import { PremiacionListarComponent } from './premiacion-listar/premiacion-listar.component';
import { PremiacionDetailComponent } from './premiacion-detail/premiacion-detail.component';
import { RouterModule } from '@angular/router';
import { PremiacionRoutingModule } from './premiacion-routing.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    PremiacionRoutingModule
  ],
  exports: [PremiacionListarComponent],
  declarations: [PremiacionListarComponent, PremiacionDetailComponent]
})
export class PremiacionModule { }
