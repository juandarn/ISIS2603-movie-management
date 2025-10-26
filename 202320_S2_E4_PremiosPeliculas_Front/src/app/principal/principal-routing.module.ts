import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PrincipalComponent } from './principal.component';


const routes : Routes = [
  {
    path: 'principal',
    children:[
      {
      path: 'list',
      component : PrincipalComponent
      },
    ]
  }
]

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],exports: [RouterModule],
  declarations: []
})
export class PrincipalRoutingModule { }
