import { Component, Input, OnInit } from '@angular/core';
import { PersonaDetailDTO } from '../personaDetailDTO';
import { ActivatedRoute } from '@angular/router';
import { PersonaService } from '../persona.service';

@Component({
  selector: 'app-persona-detalle',
  templateUrl: './persona-detalle.component.html',
  styleUrls: ['./persona-detalle.component.css']
})
export class PersonaDetalleComponent implements OnInit {
  @Input() personaDetailDTO!:PersonaDetailDTO;
  idPersona!:string;
  constructor( private route:ActivatedRoute,
    private personaSerivice:PersonaService
    ) { 
  }

  ngOnInit() {
    if(this.personaDetailDTO === undefined){
      this.idPersona = this.route.snapshot.paramMap.get('id')!;
        if(this.idPersona){
          this.getPersona();
        }
    }
  }

  getPersona():void{
    this.personaSerivice.getPersona(this.idPersona).subscribe(apiData=>{
      this.personaDetailDTO = apiData;
    });
  }

}
