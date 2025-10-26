/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';
import { PlataformaDetalleComponent } from './plataforma-detalle.component';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { PlataformaDetail } from '../plataformaDetail';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { PlataformaService } from '../plataforma.service';

describe('PlataformaDetalleComponent', () => {
  let component: PlataformaDetalleComponent;
  let fixture: ComponentFixture<PlataformaDetalleComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule, RouterTestingModule],
      declarations: [ PlataformaDetalleComponent],
      providers: [PlataformaService]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlataformaDetalleComponent);
    component = fixture.componentInstance;

    const peliculas: PeliculaDTO[] = [];
    for (let i = 0; i < 3; i++) {
      const pelicula = new PeliculaDTO (
        faker.number.int(),
        faker.lorem.sentence(),
        faker.number.int(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.date.past(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
      );
      peliculas.push(pelicula);
    }

    component.plataformaDetail = new PlataformaDetail(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      peliculas,
    );


    fixture.detectChanges();
    debug = fixture.debugElement;
    
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
