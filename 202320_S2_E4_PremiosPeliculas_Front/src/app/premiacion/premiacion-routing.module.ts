import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PremiacionListarComponent } from './premiacion-listar/premiacion-listar.component';
import { PremiacionDetailComponent } from './premiacion-detail/premiacion-detail.component';

const routes : Routes = [
  {
    path: 'premiacion',
    children:[
      {
      path: 'list',
      component : PremiacionListarComponent
      },
      {
      path: ':id',
      component: PremiacionDetailComponent , data: { comoModal: true }
      }
    ]
  }
]


@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],exports: [RouterModule],
  declarations: []
})
export class PremiacionRoutingModule { }
