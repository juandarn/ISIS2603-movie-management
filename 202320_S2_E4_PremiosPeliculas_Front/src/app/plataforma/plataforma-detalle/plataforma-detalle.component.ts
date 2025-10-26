import { Component, Input, OnInit } from '@angular/core';
import { PlataformaDetail } from '../plataformaDetail';
import { ActivatedRoute } from '@angular/router';
import { PlataformaService } from '../plataforma.service';

@Component({
  selector: 'app-plataforma-detalle',
  templateUrl: './plataforma-detalle.component.html',
  styleUrls: ['./plataforma-detalle.component.css']
})
export class PlataformaDetalleComponent implements OnInit {

  plataformaId!: string;
  @Input() plataformaDetail!: PlataformaDetail;

  

  constructor(
    private route: ActivatedRoute,
    private plataformaService: PlataformaService,
  ) { 

  }

  
  getPlataforma(){
    this.plataformaService.getPlataforma(this.plataformaId).subscribe(plataforma=>{
      this.plataformaDetail = plataforma;
    })
  }



  ngOnInit() {
    if(this.plataformaDetail === undefined){
      this.plataformaId = this.route.snapshot.paramMap.get('id')!
      if (this.plataformaId) {
        this.getPlataforma();
      }
    }
  }
  
}