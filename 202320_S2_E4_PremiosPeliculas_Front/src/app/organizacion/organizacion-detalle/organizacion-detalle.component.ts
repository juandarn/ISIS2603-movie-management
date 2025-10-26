import { OrganizacionService } from './../organizacion.service';
import { ActivatedRoute } from '@angular/router';
import { OrganizacionDetail } from './../organizacionDetail';
import { Component, OnInit, Input} from '@angular/core';


@Component({
  selector: 'app-organizacion-detalle',
  templateUrl: './organizacion-detalle.component.html',
  styleUrls: ['./organizacion-detalle.component.css']
})
export class OrganizacionDetalleComponent implements OnInit {
  organizacionId !: string;
  @Input() organizacionDetail !: OrganizacionDetail;
  select : boolean = false;

  constructor(private route : ActivatedRoute,
    private organizacionService : OrganizacionService) { }

  getOrganizacion(){
      this.organizacionService.getOrganizacion(this.organizacionId).subscribe(organizacion => this.organizacionDetail = organizacion);
    }
  ngOnInit() {if(this.organizacionDetail === undefined){
    this.organizacionId = this.route.snapshot.paramMap.get('id')!;
    if(this.organizacionId){
      this.getOrganizacion();
    }
  }
  }

}
