import { Component, OnInit, Input } from '@angular/core';
import { PeliculaDetail } from '../peliculaDetail';
import { ActivatedRoute } from '@angular/router';
import { PeliculaService } from '../pelicula.service';
import { ReseniaListarComponent } from 'src/app/resenia/resenia-listar/resenia-listar.component';
import { Resenia } from 'src/app/resenia/resenia';
@Component({
  selector: 'app-pelicula-detalle',
  templateUrl: './pelicula-detalle.component.html',
  styleUrls: ['./pelicula-detalle.component.css']
})
export class PeliculaDetalleComponent implements OnInit {
  peliculaId!: string;
  @Input() peliculaDetail!: PeliculaDetail;


  resenias: Resenia[] = [];
  constructor(private route:ActivatedRoute, private peliculaService: PeliculaService) { }

  ngOnInit() {
    if(this.peliculaDetail == undefined){
      this.peliculaId = this.route.snapshot.paramMap.get('id')!;
      if(this.peliculaId){    
        this.getPelicula();
      }
      
    }
  }
  
  

  getPelicula(): void {
    this.peliculaService.getPelicula(this.peliculaId).subscribe(peliculaDetail => {
      this.peliculaDetail = peliculaDetail
      this.resenias = peliculaDetail.resenias || [];
    });
  }
 

}
