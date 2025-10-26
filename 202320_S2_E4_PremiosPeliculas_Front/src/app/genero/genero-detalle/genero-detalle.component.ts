import { Component, Input, OnInit } from '@angular/core';
import { GeneroDetail } from '../generoDetail';
import { ActivatedRoute } from '@angular/router';
import { GeneroService } from '../genero.service';
@Component({
  selector: 'app-genero-detalle',
  templateUrl: './genero-detalle.component.html',
  styleUrls: ['./genero-detalle.component.css']
})
export class GeneroDetalleComponent implements OnInit {
  generoId!: string;
  selected: boolean = false;
  @Input() generoDetail!: GeneroDetail;

  constructor(private route: ActivatedRoute,
    private generoService: GeneroService) { }

    getGenero(){
      this.generoService.getGenero(this.generoId).subscribe(apiData=>{
        this.generoDetail = apiData;
      })
    }
  ngOnInit() { if(this.generoDetail === undefined){
    this.generoId = this.route.snapshot.paramMap.get('id')!;
    if(this.generoId){
      this.getGenero();
    }
  }
  }

}
