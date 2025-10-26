import { Component, Input, OnInit } from '@angular/core';
import { CinefiloDetailDTO } from '../cinefiloDetailDTO';
import { ActivatedRoute } from '@angular/router';
import { CinefiloService } from '../cinefilo.service';

@Component({
  selector: 'app-cinefilo-detalle',
  templateUrl: './cinefilo-detalle.component.html',
  styleUrls: ['./cinefilo-detalle.component.css']
})
export class CinefiloDetalleComponent implements OnInit {
  @Input() cinefiloDetailDTO!: CinefiloDetailDTO;
  idCinefilo!:string;
  constructor(
    private route:ActivatedRoute,
    public cinefiloService:CinefiloService

  ) { }

  ngOnInit() {
    if(this.cinefiloDetailDTO === undefined){
      this.idCinefilo = this.route.snapshot.paramMap.get('id')!;
        if(this.idCinefilo){
          this.getCinefilo();
        }
    }
  }
  getCinefilo() {
    this.cinefiloService.getCinefilo(this.idCinefilo).subscribe(apiData => this.cinefiloDetailDTO = apiData)
  }

}
