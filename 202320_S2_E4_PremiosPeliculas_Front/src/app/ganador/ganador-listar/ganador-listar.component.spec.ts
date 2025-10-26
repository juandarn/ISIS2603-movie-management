/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { GanadorListarComponent } from './ganador-listar.component';
import { GanadorDetail } from '../ganadorDetail';
import { HttpClientModule } from '@angular/common/http';
import { GanadorService } from '../ganador.service';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { faker } from '@faker-js/faker';
import { GanadorDTO } from '../ganador';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';

describe('GanadorListarComponent', () => {
  let component: GanadorListarComponent;
  let fixture: ComponentFixture<GanadorListarComponent>;
  let debug: DebugElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ GanadorListarComponent ],
      providers: [GanadorService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GanadorListarComponent);
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
      const ganador = new GanadorDetail(
        faker.number.int(),
        persona,
        premiacion
      );
      component.ganadores.push(ganador);
    }

    fixture.detectChanges();
    debug = fixture.debugElement;


  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
