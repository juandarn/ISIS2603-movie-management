/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { GeneroDetalleComponent } from './genero-detalle.component';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { faker } from '@faker-js/faker';
import { GeneroDetail } from '../generoDetail';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

describe('GeneroDetalleComponent', () => {
  let component: GeneroDetalleComponent;
  let fixture: ComponentFixture<GeneroDetalleComponent>;
  let debug:DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[RouterTestingModule, HttpClientModule],
      declarations: [ GeneroDetalleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneroDetalleComponent);
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

    component.generoDetail = new GeneroDetail(
      faker.number.int(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      peliculas
    );
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
