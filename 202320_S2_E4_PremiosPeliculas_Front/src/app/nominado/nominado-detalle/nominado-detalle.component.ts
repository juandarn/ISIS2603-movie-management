import { Component, Input, OnInit } from '@angular/core';
import { NominadoDetail } from '../nominadoDetail';
import { Nominado } from '../nominado';
import { ActivatedRoute } from '@angular/router';
import { NominadoService } from '../nominado.service';

@Component({
  selector: 'app-nominado-detalle',
  templateUrl: './nominado-detalle.component.html',
  styleUrls: ['./nominado-detalle.component.css']
})
export class NominadoDetalleComponent implements OnInit {

  nominadoId!: string;
  @Input() nominadoDetail!: NominadoDetail;

  constructor(    
    private route: ActivatedRoute,
    private nominadoService: NominadoService
  ) {}

  getNominado(){
    this.nominadoService.getNominado(this.nominadoId).subscribe(nominado=>{
      this.nominadoDetail = nominado;
    })
  }

  ngOnInit() {
    if(this.nominadoDetail === undefined){
      this.nominadoId = this.route.snapshot.paramMap.get('id')!
      if (this.nominadoId) {
        this.getNominado();
      }
    }
  }
}
