import { Component, OnInit } from '@angular/core';
import { OrganizacionDetail } from '../organizacionDetail';
import { OrganizacionService } from '../organizacion.service';

@Component({
  selector: 'app-organizacion-listar',
  templateUrl: './organizacion-listar.component.html',
  styleUrls: ['./organizacion-listar.component.css']
})
export class OrganizacionListarComponent implements OnInit {


  organizaciones : Array<OrganizacionDetail> = [];
  OrganizacionSeleccionada !: OrganizacionDetail;
  selected : boolean = false;


  constructor(private organizacionService: OrganizacionService) { }

  getOrganizaciones(){
    this.organizacionService.getOrganizaciones().subscribe(apiData => {
      this.organizaciones = apiData;
    })
  }

  onSelected(_t11: OrganizacionDetail) {
    this.OrganizacionSeleccionada = _t11;
    this.selected = true;
  }

  

  ngOnInit() {
    this.getOrganizaciones();
  }


}
