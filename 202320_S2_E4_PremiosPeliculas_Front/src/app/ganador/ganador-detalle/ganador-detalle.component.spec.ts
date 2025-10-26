/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';
import { GanadorDetalleComponent } from './ganador-detalle.component';
import { GanadorDTO } from '../ganador';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { GanadorDetail } from '../ganadorDetail';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { GanadorService } from '../ganador.service';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';

describe('GanadorDetalleComponent', () => {
  let component: GanadorDetalleComponent;
  let fixture: ComponentFixture<GanadorDetalleComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule,RouterTestingModule],
      declarations: [ GanadorDetalleComponent ],
      providers: [GanadorService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GanadorDetalleComponent);
    component = fixture.componentInstance;


    // PeliculaDTO
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
      [],
    );

    component.ganadorDetail = new GanadorDetail(
      faker.number.int(),
      persona,
      premiacion
    );


  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
