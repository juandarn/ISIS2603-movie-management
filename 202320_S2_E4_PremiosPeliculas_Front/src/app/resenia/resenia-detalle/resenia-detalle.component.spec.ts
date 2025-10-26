/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ReseniaDetalleComponent } from './resenia-detalle.component';
import { PremioDetailDTO } from 'src/app/premio/premioDetail';
import { faker } from '@faker-js/faker';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { OrganizacionDTO } from 'src/app/organizacion/organizacion';
import { PeliculaDetail } from 'src/app/pelicula/peliculaDetail';
import { GeneroDTO } from 'src/app/genero/genero';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { ReseniaDetail } from '../reseniaDetail';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ReseniaService } from '../resenia.service';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { GanadorDTO } from 'src/app/ganador/ganador';

describe('ReseniaDetalleComponent', () => {
  let component: ReseniaDetalleComponent;
  let fixture: ComponentFixture<ReseniaDetalleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ ReseniaDetalleComponent ],
      providers: [ReseniaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReseniaDetalleComponent);
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
        faker.lorem.words(),
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


  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
