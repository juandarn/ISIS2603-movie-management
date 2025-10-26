/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PlataformaListarComponent } from './plataforma-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { PlataformaService } from '../plataforma.service';
import { PeliculaDTO } from 'src/app/pelicula/pelicula';
import { faker } from '@faker-js/faker';
import { PlataformaDetail } from '../plataformaDetail';
import { NgxPaginationModule } from 'ngx-pagination';

describe('PlataformaListarComponent', () => {
  let component: PlataformaListarComponent;
  let fixture: ComponentFixture<PlataformaListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, NgxPaginationModule],
      declarations: [ PlataformaListarComponent],
      providers: [PlataformaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlataformaListarComponent);
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

    for(let i = 0; i < 10; i++) {
      const genero = new PlataformaDetail(
        faker.number.int(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        [],
      );
      component.plataformas.push(genero);
      debug = fixture.debugElement;
    }

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
