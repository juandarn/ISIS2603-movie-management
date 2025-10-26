/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { NominadoListarComponent } from './nominado-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { NominadoService } from '../nominado.service';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { faker } from '@faker-js/faker';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { NominadoDetail } from '../nominadoDetail';
import { PersonaDetailDTO } from 'src/app/persona/personaDetailDTO';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { GanadorDTO } from 'src/app/ganador/ganador';


describe('NominadoListarComponent', () => {
  let component: NominadoListarComponent;
  let fixture: ComponentFixture<NominadoListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ NominadoListarComponent],
      providers: [NominadoService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NominadoListarComponent);
    component = fixture.componentInstance;

    let pelicula = new PeliculaDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.number.int(),
      faker.location.country(),
      faker.lorem.word(),
      faker.date.past(),
      faker.lorem.word(),
      faker.lorem.word()
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

    let premiacion1 = new PremiacionDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      pelicula
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
      pelicula,
      [],
      ganador,
      [],
      []
    );

    for(let i = 0; i<10; i++) {
      const nominados = new NominadoDetail(
        faker.number.int(),
        premiacion,
        persona
      );
      component.nominados.push(nominados);
    
    }

    fixture.detectChanges();
    debug = fixture.debugElement;




    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
