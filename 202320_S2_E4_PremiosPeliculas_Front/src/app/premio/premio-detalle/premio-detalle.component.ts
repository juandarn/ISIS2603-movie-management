import { Component, Input, OnInit } from '@angular/core';
import { PremioDTO } from '../premio';
import { PremioDetailDTO } from '../premioDetail';
import { PremioService } from '../premio.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-premio-detalle',
  templateUrl: './premio-detalle.component.html',
  styleUrls: ['./premio-detalle.component.css']
})
export class PremioDetalleComponent implements OnInit {

  premioId !: string;
  @Input() premioDetalle!: PremioDetailDTO;

  constructor(
    private route : ActivatedRoute,
    private premioService : PremioService
  ) { }


  getPremio(){
    this.premioService.getPremio(this.premioId).subscribe(premio => this.premioDetalle = premio);
  }


  ngOnInit() {
    if(this.premioDetalle === undefined){
      this.premioId = this.route.snapshot.paramMap.get('id')!;
      if(this.premioId){
        this.getPremio();
      }
    }
  }

}
