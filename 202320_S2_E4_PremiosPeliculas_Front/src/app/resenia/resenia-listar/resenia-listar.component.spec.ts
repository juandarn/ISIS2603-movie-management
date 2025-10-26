/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ReseniaListarComponent } from './resenia-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { ReseniaService } from '../resenia.service';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { OrganizacionDTO } from 'src/app/organizacion/organizacion';
import { faker } from '@faker-js/faker';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { GeneroDTO } from 'src/app/genero/genero';
import { PeliculaDetail } from 'src/app/pelicula/peliculaDetail';
import { PremioDetailDTO } from 'src/app/premio/premioDetail';
import { ReseniaDetail } from '../reseniaDetail';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { GanadorDTO } from 'src/app/ganador/ganador';
import { NgxPaginationModule } from 'ngx-pagination';

describe('ReseniaListarComponent', () => {
  let component: ReseniaListarComponent;
  let fixture: ComponentFixture<ReseniaListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, NgxPaginationModule],
      declarations: [ ReseniaListarComponent ],
      providers: [ReseniaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReseniaListarComponent);
    component = fixture.componentInstance;

    let pelicula1 = new PeliculaDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.number.int(),
      faker.location.country(),
      faker.lorem.word(),
      faker.date.past(),
      faker.lorem.word(),
      faker.lorem.word()
    );

    let premiacion1 = new PremiacionDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      pelicula1
    );

    let persona = new PersonaDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.date.anytime(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence()
    );

    let ganador = new GanadorDTO(
      faker.number.int(),
      persona,
      premiacion1
    );

    let premiacion = new PremiacionDetail(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      pelicula1,
      [],
      ganador,
      [],
      []
    );

    let organizacion = new OrganizacionDTO(
      faker.number.int(),
      faker.lorem.sentence()
    );

    let premiaciones: Array<PremiacionDTO> = [ ];
    for (let i = 0; i < 3; i++) {
      let premiacion = new PremiacionDTO(
        faker.number.int(),
        faker.lorem.words(),
        faker.lorem.words(),
        pelicula1
      );
    premiaciones.push(premiacion);
    }

    let personas: Array<PersonaDTO> = [ ];
    
    for (let i = 0; i < 3; i++) {
      let persona = new PersonaDTO(
        faker.number.int(),
        faker.lorem.words(),
        faker.lorem.words(),
        faker.date.past(),
        faker.lorem.words(),
        faker.lorem.words(),
        faker.lorem.sentence()
      );
    personas.push(persona);
    }




    
    let premio = new PremioDetailDTO(
      faker.number.int(),
      faker.date.anytime(),
      faker.lorem.sentence(),
      premiacion,
      organizacion,
      []
    );




    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
