/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PremiacionListarComponent } from './premiacion-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { PremiacionDetail } from '../premiacionDetail';
import { PremiacionService } from '../premiacion.service';
import { PremioDTO } from 'src/app/premio/premio';
import { faker } from '@faker-js/faker';
import { RouterTestingModule } from '@angular/router/testing';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { PremiacionDTO } from '../premiacion';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { GanadorDTO } from 'src/app/ganador/ganador';

describe('PremiacionListarComponent', () => {
  let component: PremiacionListarComponent;
  let fixture: ComponentFixture<PremiacionListarComponent>;
  let debug: DebugElement;
  let debugPremio: PremiacionDetail;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule , RouterTestingModule],
      declarations: [ PremiacionListarComponent ],
      providers: [PremiacionService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PremiacionListarComponent);
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

    let premiacion1 = new PremiacionDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      pelicula
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

    for(let i = 0; i<10; i++) {
      const premiacion = new PremiacionDetail(
        faker.number.int(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        pelicula,
        [],
        ganador,
        [],
        []
      );
      component.premiaciones.push(premiacion);
      debug = fixture.debugElement;

    }


    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


});
