import { Component, Input, OnInit } from '@angular/core';
import { GanadorDetail } from '../ganadorDetail';
import { ActivatedRoute } from '@angular/router';
import { GanadorService } from '../ganador.service';
import { GanadorDTO } from '../ganador';

@Component({
  selector: 'app-ganador-detalle',
  templateUrl: './ganador-detalle.component.html',
  styleUrls: ['./ganador-detalle.component.css']
})
export class GanadorDetalleComponent implements OnInit {
  ganadorID!: string;
  @Input() ganadorDetail!: GanadorDetail;
  constructor(
    private route: ActivatedRoute,
    private ganadorService: GanadorService
  ) { }


  getGanadorDetail(): void {
    this.ganadorService.getGanadorDetail(this.ganadorID).subscribe(GanadorDTO => {
      this.ganadorDetail = GanadorDTO
    });
    
  }

  ngOnInit() {
    if (this.ganadorDetail == undefined) {
      this.ganadorID = this.route.snapshot.paramMap.get('id')!;
      if (this.ganadorID) {
        this.getGanadorDetail();
      }
    }
  }

}
