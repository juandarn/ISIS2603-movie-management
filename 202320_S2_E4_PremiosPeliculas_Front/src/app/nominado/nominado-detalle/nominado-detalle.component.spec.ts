/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { faker } from '@faker-js/faker';
import { NominadoDetalleComponent } from './nominado-detalle.component';
import { PremiacionDetail } from 'src/app/premiacion/premiacionDetail';
import { NominadoDetail } from '../nominadoDetail';
import { PersonaDTO } from 'src/app/persona/personaDTO';
import { NominadoService } from '../nominado.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { PremiacionDTO } from 'src/app/premiacion/premiacion';
import { GanadorDTO } from 'src/app/ganador/ganador';

describe('NominadoDetalleComponent', () => {
  let component: NominadoDetalleComponent;
  let fixture: ComponentFixture<NominadoDetalleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule, RouterTestingModule],
      declarations: [ NominadoDetalleComponent],
      providers: [NominadoService]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NominadoDetalleComponent);
    component = fixture.componentInstance;

    let persona = new PersonaDTO(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.date.anytime(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence()
    );

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

    component.nominadoDetail = new NominadoDetail(
      faker.number.int(),
      premiacion,
      persona

    );

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
